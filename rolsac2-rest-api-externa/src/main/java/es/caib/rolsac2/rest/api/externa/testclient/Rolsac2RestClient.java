package es.caib.rolsac2.rest.api.externa.testclient;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

/**
 * Cliente REST standalone para la API externa de ROLSAC2.
 * <p>
 * Requisitos:
 * - JDK 11+
 * - Procediment.java y Servei.java en este mismo paquete.
 * <p>
 * No utiliza Jackson, RESTEasy, JAX-RS, SLF4J ni ninguna otra dependencia externa.
 * <p>
 * Los métodos getProcediments() y getServeis() devuelven objetos Java tipados.
 */
public class Rolsac2RestClient {

    private static final String DEFAULT_BASE_URL =
            "http://localhost:8080/rolsac2api/externa/services/v1";

    private final HttpClient http;
    private final String baseUrl;

    public Rolsac2RestClient(String baseUrl) {
        this.baseUrl = normalizeBaseUrl(baseUrl);
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public static void main(String[] args) throws Exception {

        String baseUrl = args.length > 0 ? args[0] : DEFAULT_BASE_URL;
        Rolsac2RestClient client = new Rolsac2RestClient(baseUrl);

        printHeader("ROLSAC2 - CLIENTE REST EXTERNO");
        System.out.println("Base URL: " + baseUrl);

        Map<String, String> procParams = new LinkedHashMap<>();
        procParams.put("idioma", "ca");
        procParams.put("entitat", "1");
        procParams.put("page-size", "5");
        procParams.put("page", "0");
        procParams.put("ordenCampo", "codi");
        procParams.put("ordenAscendente", "asc");

        ApiResponse<Procediment> procediments = client.getProcediments(procParams);
        printProcediments(procediments);

        Map<String, String> serveiParams = new LinkedHashMap<>();
        serveiParams.put("idioma", "ca");
        serveiParams.put("entitat", "1");
        serveiParams.put("page-size", "5");
        serveiParams.put("page", "0");
        serveiParams.put("ordenCampo", "codi");
        serveiParams.put("ordenAscendente", "asc");

        ApiResponse<Servei> serveis = client.getServeis(serveiParams);
        printServeis(serveis);

        // Ejemplo de uso real de los objetos:
        if (!procediments.getItems().isEmpty()) {
            Procediment primer = procediments.getItems().get(0);
            System.out.println();
            System.out.println("Ejemplo objeto Procediment -> codi="
                    + primer.getCodi() + ", nom=" + primer.getNom());
        }

        if (!serveis.getItems().isEmpty()) {
            Servei primer = serveis.getItems().get(0);
            System.out.println("Ejemplo objeto Servei      -> codi="
                    + primer.getCodi() + ", nom=" + primer.getNom());
        }
    }

    /**
     * Llama a /procediments/ y devuelve una respuesta tipada.
     */
    public ApiResponse<Procediment> getProcediments(Map<String, String> query)
            throws Exception {

        Map<String, Object> root = executeGet("/procediments/", query);

        return mapApiResponse(root, new ItemMapper<Procediment>() {
            @Override
            public Procediment map(Map<String, Object> json) {
                return mapProcediment(json);
            }
        });
    }

    /**
     * Llama a /serveis/ y devuelve una respuesta tipada.
     */
    public ApiResponse<Servei> getServeis(Map<String, String> query)
            throws Exception {

        Map<String, Object> root = executeGet("/serveis/", query);

        return mapApiResponse(root, new ItemMapper<Servei>() {
            @Override
            public Servei map(Map<String, Object> json) {
                return mapServei(json);
            }
        });
    }

    /**
     * Ejecuta el GET y devuelve el objeto JSON raíz ya parseado.
     */
    private Map<String, Object> executeGet(String path,
                                           Map<String, String> query)
            throws Exception {

        String url = baseUrl + normalizePath(path) + toQuery(query);

        System.out.println();
        System.out.println("GET " + url);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                http.send(request,
                        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        System.out.println("HTTP " + response.statusCode());

        String body = response.body();

        Object parsed;
        try {
            parsed = new JsonParser(body).parse();
        } catch (RuntimeException e) {
            throw new RestClientException(
                    "La respuesta no es un JSON válido. HTTP "
                            + response.statusCode()
                            + "\nRespuesta:\n"
                            + body,
                    response.statusCode(),
                    body,
                    e
            );
        }

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new RestClientException(
                    "La llamada ha fallado con HTTP "
                            + response.statusCode()
                            + "\n"
                            + JsonPrinter.pretty(parsed),
                    response.statusCode(),
                    body
            );
        }

        return asObject(parsed, "respuesta raíz");
    }

    /**
     * Mapea la envoltura común de RespuestaBase y sus items.
     */
    private <T> ApiResponse<T> mapApiResponse(
            Map<String, Object> root,
            ItemMapper<T> mapper) {

        ApiResponse<T> response = new ApiResponse<>();

        response.setTitle(asString(root.get("title")));
        response.setDescription(asString(root.get("description")));
        response.setSpatial(asString(root.get("spatial")));
        response.setCreator(asString(root.get("creator")));
        response.setDateDownload(asString(root.get("dateDownload")));

        response.setTotalCount(asLong(root.get("totalCount")));
        response.setItemsReturned(asInteger(root.get("itemsReturned")));
        response.setPageSize(asInteger(root.get("pageSize")));
        response.setTotalPages(asInteger(root.get("totalPages")));
        response.setPage(asInteger(root.get("page")));

        response.setNextUrl(asString(root.get("nextUrl")));
        response.setPreviousUrl(asString(root.get("previousUrl")));
        response.setTiempo(asLong(root.get("tiempo")));

        Object itemsValue = root.get("items");

        if (itemsValue == null) {
            response.setItems(Collections.emptyList());
            return response;
        }

        if (!(itemsValue instanceof List)) {
            throw new IllegalArgumentException(
                    "El campo 'items' debería ser un array JSON, pero se recibió: "
                            + typeName(itemsValue)
            );
        }

        @SuppressWarnings("unchecked")
        List<Object> jsonItems = (List<Object>) itemsValue;

        List<T> items = new ArrayList<>(jsonItems.size());

        for (int i = 0; i < jsonItems.size(); i++) {
            Object value = jsonItems.get(i);
            Map<String, Object> item =
                    asObject(value, "items[" + i + "]");
            items.add(mapper.map(item));
        }

        response.setItems(items);
        return response;
    }

    /**
     * Mapeo completo de los campos actualmente presentes en Procediment.
     */
    private Procediment mapProcediment(Map<String, Object> json) {

        Procediment p = new Procediment();

        p.setUrl(asString(json.get("url")));
        p.setCodi(asLong(json.get("codi")));
        p.setNom(asString(json.get("nom")));
        p.setDataActualizacio(asString(json.get("dataActualizacio")));
        p.setDataCaducitat(asString(json.get("dataCaducitat")));
        p.setDataPublicacio(asString(json.get("dataPublicacio")));
        p.setDestinataris(asString(json.get("destinataris")));
        p.setCodiSIA(asString(json.get("codiSIA")));
        p.setEstatSIA(asString(json.get("estatSIA")));
        p.setDataSIA(asString(json.get("dataSIA")));

        p.setUaResponsableCodi(asLong(json.get("uaResponsableCodi")));
        p.setUaResponsableNom(asString(json.get("uaResponsableNom")));

        p.setUaCompetenteCodi(asLong(json.get("uaCompetenteCodi")));
        p.setUaCompetenteNom(asString(json.get("uaCompetenteNom")));

        p.setUaInstructor(asLong(json.get("uaInstructor")));
        p.setUaInstructorNom(asString(json.get("uaInstructorNom")));

        p.setComu(asBoolean(json.get("comu")));
        p.setObjecte(asString(json.get("objecte")));

        p.setTipusCodi(asLong(json.get("tipusCodi")));
        p.setTipusNom(asString(json.get("tipusNom")));

        p.setEstat(asString(json.get("estat")));

        p.setIniciacionCodi(asLong(json.get("iniciacionCodi")));
        p.setIniciacionNom(asString(json.get("iniciacionNom")));

        p.setSilenciCodi(asLong(json.get("silenciCodi")));
        p.setSilenciNom(asString(json.get("silenciNom")));

        p.setTipusViaCodi(asLong(json.get("tipusViaCodi")));
        p.setTipusViaNom(asString(json.get("tipusViaNom")));

        p.setHabilitatApoderat(asBoolean(json.get("habilitatApoderat")));
        p.setHabilitatFuncionari(asBoolean(json.get("habilitatFuncionari")));
        p.setTerminiResolucio(asString(json.get("terminiResolucio")));

        return p;
    }

    /**
     * Mapeo completo de los campos de Servei.
     * <p>
     * También acepta tipusTramitacio* y plantillaTramit* si el servidor
     * empieza a devolverlos en una versión posterior.
     */
    private Servei mapServei(Map<String, Object> json) {

        Servei s = new Servei();

        s.setUrl(asString(json.get("url")));
        s.setCodi(asLong(json.get("codi")));
        s.setNom(asString(json.get("nom")));

        s.setDataActualizacio(asString(json.get("dataActualizacio")));
        s.setDataPublicacio(asString(json.get("dataPublicacio")));
        s.setDataCaducitat(asString(json.get("dataCaducitat")));

        s.setCodiSIA(asString(json.get("codiSIA")));
        s.setEstatSIA(asString(json.get("estatSIA")));
        s.setDataSIA(asString(json.get("dataSIA")));

        s.setUaResponsableCodi(asLong(json.get("uaResponsableCodi")));
        s.setUaResponsableNom(asString(json.get("uaResponsableNom")));

        s.setUaInstructorCodi(asLong(json.get("uaInstructorCodi")));
        s.setUaInstructorNom(asString(json.get("uaInstructorNom")));

        s.setComu(asBoolean(json.get("comu")));
        s.setObjecte(asString(json.get("objecte")));
        s.setDestinataris(asString(json.get("destinataris")));
        s.setEstat(asString(json.get("estat")));

        s.setHabilitatApoderat(asBoolean(json.get("habilitatApoderat")));
        s.setHabilitatFuncionari(asBoolean(json.get("habilitatFuncionari")));
        s.setTerminiResolucio(asString(json.get("terminiResolucio")));

        s.setIntern(asString(json.get("intern")));
        s.setPublicat(asString(json.get("publicat")));
        s.setActiuLOPD(asBoolean(json.get("actiuLOPD")));


        s.setTramitPresencial(asBoolean(json.get("tramitPresencial")));
        s.setTramitElectronica(asBoolean(json.get("tramitElectronica")));
        s.setTramitTelefonica(asBoolean(json.get("tramitTelefonica")));

        s.setUrlTramitacio(asString(json.get("urlTramitacio")));

        s.setPlataformaTramitCodi(asLong(json.get("plataformaTramitCodi")));
        s.setPlataformaTramitNom(asString(json.get("plataformaTramitNom")));


        s.setPublicsObjectius(mapPublicsObjectius(json.get("publicsObjectius")));

        return s;
    }

    private List<Servei.PublicObjectiu> mapPublicsObjectius(Object value) {

        if (value == null) {
            return Collections.emptyList();
        }

        if (!(value instanceof List)) {
            throw new IllegalArgumentException(
                    "'publicsObjectius' debería ser un array JSON, pero se recibió: "
                            + typeName(value)
            );
        }

        @SuppressWarnings("unchecked")
        List<Object> source = (List<Object>) value;

        List<Servei.PublicObjectiu> result =
                new ArrayList<>(source.size());

        for (int i = 0; i < source.size(); i++) {
            Map<String, Object> json =
                    asObject(source.get(i), "publicsObjectius[" + i + "]");

            Servei.PublicObjectiu item = new Servei.PublicObjectiu();
            item.setCodi(asLong(json.get("codi")));
            item.setNom(asString(json.get("nom")));

            result.add(item);
        }

        return result;
    }

    // ---------------------------------------------------------------------
    // PRESENTACIÓN POR CONSOLA
    // ---------------------------------------------------------------------

    private static void printProcediments(ApiResponse<Procediment> response) {

        printHeader("PROCEDIMENTS");
        printResponseSummary(response);

        if (response.getItems().isEmpty()) {
            System.out.println("(sin resultados)");
            return;
        }

        int index = 1;
        for (Procediment p : response.getItems()) {

            System.out.println();
            System.out.println("[" + index + "] "
                    + nullSafe(p.getCodi())
                    + " - "
                    + nullSafe(p.getNom()));

            System.out.println(repeat('-', 100));

            printField("Estat", p.getEstat());
            printField("Codi SIA", p.getCodiSIA());
            printField("Estat SIA", p.getEstatSIA());
            printField("Data SIA", p.getDataSIA());

            printField("Data actualització", p.getDataActualizacio());
            printField("Data publicació", p.getDataPublicacio());
            printField("Data caducitat", p.getDataCaducitat());

            printCodeName(
                    "UA responsable",
                    p.getUaResponsableCodi(),
                    p.getUaResponsableNom()
            );

            printCodeName(
                    "UA competent",
                    p.getUaCompetenteCodi(),
                    p.getUaCompetenteNom()
            );

            printCodeName(
                    "UA instructora",
                    p.getUaInstructor(),
                    p.getUaInstructorNom()
            );

            printCodeName(
                    "Tipus",
                    p.getTipusCodi(),
                    p.getTipusNom()
            );

            printCodeName(
                    "Iniciació",
                    p.getIniciacionCodi(),
                    p.getIniciacionNom()
            );

            printCodeName(
                    "Silenci",
                    p.getSilenciCodi(),
                    p.getSilenciNom()
            );

            printCodeName(
                    "Tipus via",
                    p.getTipusViaCodi(),
                    p.getTipusViaNom()
            );

            printField("Comú", p.getComu());
            printField("Habilitat apoderat", p.getHabilitatApoderat());
            printField("Habilitat funcionari", p.getHabilitatFuncionari());
            printField("Termini resolució", p.getTerminiResolucio());

            printLongField("Objecte", p.getObjecte());
            printLongField("Destinataris", p.getDestinataris());

            printField("URL", p.getUrl());

            index++;
        }
    }

    private static void printServeis(ApiResponse<Servei> response) {

        printHeader("SERVEIS");
        printResponseSummary(response);

        if (response.getItems().isEmpty()) {
            System.out.println("(sin resultados)");
            return;
        }

        int index = 1;
        for (Servei s : response.getItems()) {

            System.out.println();
            System.out.println("[" + index + "] "
                    + nullSafe(s.getCodi())
                    + " - "
                    + nullSafe(s.getNom()));

            System.out.println(repeat('-', 100));

            printField("Estat", s.getEstat());
            printField("Codi SIA", s.getCodiSIA());
            printField("Estat SIA", s.getEstatSIA());
            printField("Data SIA", s.getDataSIA());

            printField("Data actualització", s.getDataActualizacio());
            printField("Data publicació", s.getDataPublicacio());
            printField("Data caducitat", s.getDataCaducitat());

            printCodeName(
                    "UA responsable",
                    s.getUaResponsableCodi(),
                    s.getUaResponsableNom()
            );

            printCodeName(
                    "UA instructora",
                    s.getUaInstructorCodi(),
                    s.getUaInstructorNom()
            );

            printField("Comú", s.getComu());
            printField("Intern", s.getIntern());
            printField("Publicat", s.getPublicat());
            printField("Actiu LOPD", s.getActiuLOPD());

            printField("Habilitat apoderat", s.getHabilitatApoderat());
            printField("Habilitat funcionari", s.getHabilitatFuncionari());
            printField("Termini resolució", s.getTerminiResolucio());


            printField("Tràmit presencial", s.getTramitPresencial());
            printField("Tràmit electrònic", s.getTramitElectronica());
            printField("Tràmit telefònic", s.getTramitTelefonica());

            printCodeName(
                    "Plataforma",
                    s.getPlataformaTramitCodi(),
                    s.getPlataformaTramitNom()
            );


            printLongField("Objecte", s.getObjecte());
            printLongField("Destinataris", s.getDestinataris());

            printField("URL", s.getUrl());
            printField("URL tramitació", s.getUrlTramitacio());

            if (s.getPublicsObjectius() != null
                    && !s.getPublicsObjectius().isEmpty()) {

                System.out.println("Públics objectiu:");

                for (Servei.PublicObjectiu po : s.getPublicsObjectius()) {
                    System.out.println("    - "
                            + nullSafe(po.getCodi())
                            + " | "
                            + nullSafe(po.getNom()));
                }
            }

            index++;
        }
    }

    private static void printResponseSummary(ApiResponse<?> response) {

        System.out.println();
        System.out.println("RESUMEN");
        System.out.println(repeat('-', 100));

        printField("Título", response.getTitle());
        printField("Descripción", response.getDescription());
        printField("Spatial", response.getSpatial());
        printField("Creator", response.getCreator());
        printField("Fecha descarga", response.getDateDownload());

        System.out.println();

        printField("Total", response.getTotalCount());
        printField("Items devueltos", response.getItemsReturned());
        printField("Página", response.getPage());
        printField("Tamaño página", response.getPageSize());
        printField("Total páginas", response.getTotalPages());
        printField("Tiempo (ms)", response.getTiempo());

        printField("Anterior", response.getPreviousUrl());
        printField("Siguiente", response.getNextUrl());

        System.out.println();
        System.out.println("ELEMENTOS");
        System.out.println(repeat('-', 100));
    }

    private static void printField(String label, Object value) {
        System.out.println(padRight(label, 24) + ": " + nullSafe(value));
    }

    private static void printCodeName(
            String label,
            Long code,
            String name) {

        if (code == null && isBlank(name)) {
            return;
        }

        printField(
                label,
                nullSafe(code) + " | " + nullSafe(name)
        );
    }

    private static void printLongField(String label, String value) {

        if (isBlank(value)) {
            printField(label, "-");
            return;
        }

        final int lineLength = 100;

        if (value.length() <= lineLength) {
            printField(label, value);
            return;
        }

        System.out.println(padRight(label, 24) + ":");

        int start = 0;

        while (start < value.length()) {

            int end = Math.min(start + lineLength, value.length());

            if (end < value.length()) {
                int lastSpace = value.lastIndexOf(' ', end);

                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            System.out.println("    " + value.substring(start, end).trim());

            start = end;

            while (start < value.length()
                    && Character.isWhitespace(value.charAt(start))) {
                start++;
            }
        }
    }

    private static void printHeader(String title) {

        String line = repeat('=', Math.max(60, title.length() + 8));

        System.out.println();
        System.out.println(line);
        System.out.println("  " + title);
        System.out.println(line);
    }

    // ---------------------------------------------------------------------
    // CONVERSIONES TOLERANTES
    // ---------------------------------------------------------------------

    private static String asString(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        }

        if (value instanceof Number
                || value instanceof Boolean) {
            return String.valueOf(value);
        }

        return JsonPrinter.compact(value);
    }

    private static Long asLong(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof String) {

            String text = ((String) value).trim();

            if (text.isEmpty()) {
                return null;
            }

            try {
                return Long.valueOf(text);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "No se puede convertir a Long: '" + text + "'",
                        e
                );
            }
        }

        throw new IllegalArgumentException(
                "No se puede convertir a Long el tipo "
                        + typeName(value)
        );
    }

    private static Integer asInteger(Object value) {

        Long result = asLong(value);

        if (result == null) {
            return null;
        }

        if (result > Integer.MAX_VALUE
                || result < Integer.MIN_VALUE) {
            throw new IllegalArgumentException(
                    "Valor fuera de rango Integer: " + result
            );
        }

        return result.intValue();
    }

    private static Boolean asBoolean(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }

        if (value instanceof String) {

            String text = ((String) value).trim();

            if (text.isEmpty()) {
                return null;
            }

            if ("true".equalsIgnoreCase(text)
                    || "s".equalsIgnoreCase(text)
                    || "si".equalsIgnoreCase(text)
                    || "sí".equalsIgnoreCase(text)
                    || "1".equals(text)) {
                return Boolean.TRUE;
            }

            if ("false".equalsIgnoreCase(text)
                    || "n".equalsIgnoreCase(text)
                    || "no".equalsIgnoreCase(text)
                    || "0".equals(text)) {
                return Boolean.FALSE;
            }
        }

        throw new IllegalArgumentException(
                "No se puede convertir a Boolean: "
                        + String.valueOf(value)
        );
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asObject(
            Object value,
            String context) {

        if (!(value instanceof Map)) {
            throw new IllegalArgumentException(
                    context
                            + " debería ser un objeto JSON, pero se recibió: "
                            + typeName(value)
            );
        }

        return (Map<String, Object>) value;
    }

    private static String typeName(Object value) {
        return value == null
                ? "null"
                : value.getClass().getName();
    }

    // ---------------------------------------------------------------------
    // URL / QUERY
    // ---------------------------------------------------------------------

    private String toQuery(Map<String, String> query) {

        if (query == null || query.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : query.entrySet()) {

            if (entry.getValue() == null) {
                continue;
            }

            sb.append(sb.length() == 0 ? '?' : '&')
                    .append(encode(entry.getKey()))
                    .append('=')
                    .append(encode(entry.getValue()));
        }

        return sb.toString();
    }

    private static String encode(String value) {
        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8
        );
    }

    private static String normalizeBaseUrl(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La URL base no puede estar vacía."
            );
        }

        String result = value.trim();

        while (result.endsWith("/")) {
            result = result.substring(
                    0,
                    result.length() - 1
            );
        }

        return result;
    }

    private static String normalizePath(String path) {

        if (path == null || path.trim().isEmpty()) {
            return "";
        }

        String result = path.trim();

        if (!result.startsWith("/")) {
            result = "/" + result;
        }

        return result;
    }

    // ---------------------------------------------------------------------
    // UTILIDADES
    // ---------------------------------------------------------------------

    private static String nullSafe(Object value) {

        if (value == null) {
            return "-";
        }

        String result = String.valueOf(value);

        return result.trim().isEmpty()
                ? "-"
                : result;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String padRight(String value, int length) {

        if (value.length() >= length) {
            return value;
        }

        return value + repeat(' ', length - value.length());
    }

    private static String repeat(char value, int count) {

        StringBuilder sb = new StringBuilder(count);

        for (int i = 0; i < count; i++) {
            sb.append(value);
        }

        return sb.toString();
    }

    private interface ItemMapper<T> {
        T map(Map<String, Object> json);
    }

    // ---------------------------------------------------------------------
    // RESPUESTA TIPADA
    // ---------------------------------------------------------------------

    public static class ApiResponse<T> {

        private String title;
        private String description;
        private String spatial;
        private String creator;
        private String dateDownload;

        private Long totalCount;
        private Integer itemsReturned;
        private Integer pageSize;
        private Integer totalPages;
        private Integer page;

        private String nextUrl;
        private String previousUrl;

        private List<T> items = Collections.emptyList();

        private Long tiempo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSpatial() {
            return spatial;
        }

        public void setSpatial(String spatial) {
            this.spatial = spatial;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getDateDownload() {
            return dateDownload;
        }

        public void setDateDownload(String dateDownload) {
            this.dateDownload = dateDownload;
        }

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getItemsReturned() {
            return itemsReturned;
        }

        public void setItemsReturned(Integer itemsReturned) {
            this.itemsReturned = itemsReturned;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public String getNextUrl() {
            return nextUrl;
        }

        public void setNextUrl(String nextUrl) {
            this.nextUrl = nextUrl;
        }

        public String getPreviousUrl() {
            return previousUrl;
        }

        public void setPreviousUrl(String previousUrl) {
            this.previousUrl = previousUrl;
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items == null
                    ? Collections.<T>emptyList()
                    : items;
        }

        public Long getTiempo() {
            return tiempo;
        }

        public void setTiempo(Long tiempo) {
            this.tiempo = tiempo;
        }
    }

    public static class RestClientException
            extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private final int statusCode;
        private final String responseBody;

        public RestClientException(
                String message,
                int statusCode,
                String responseBody) {

            super(message);
            this.statusCode = statusCode;
            this.responseBody = responseBody;
        }

        public RestClientException(
                String message,
                int statusCode,
                String responseBody,
                Throwable cause) {

            super(message, cause);
            this.statusCode = statusCode;
            this.responseBody = responseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getResponseBody() {
            return responseBody;
        }
    }

    // ---------------------------------------------------------------------
    // PARSER JSON AUTOCONTENIDO
    // ---------------------------------------------------------------------

    private static final class JsonParser {

        private final String input;
        private int pos;

        private JsonParser(String input) {
            this.input = input == null ? "" : input;
        }

        private Object parse() {

            skipWhitespace();

            Object result = parseValue();

            skipWhitespace();

            if (pos != input.length()) {
                throw error(
                        "Hay contenido adicional después del JSON"
                );
            }

            return result;
        }

        private Object parseValue() {

            skipWhitespace();

            if (pos >= input.length()) {
                throw error("Fin inesperado del JSON");
            }

            char current = input.charAt(pos);

            switch (current) {
                case '{':
                    return parseObject();

                case '[':
                    return parseArray();

                case '"':
                    return parseString();

                case 't':
                    expectLiteral("true");
                    return Boolean.TRUE;

                case 'f':
                    expectLiteral("false");
                    return Boolean.FALSE;

                case 'n':
                    expectLiteral("null");
                    return null;

                default:
                    if (current == '-'
                            || Character.isDigit(current)) {
                        return parseNumber();
                    }

                    throw error(
                            "Valor JSON no válido"
                    );
            }
        }

        private Map<String, Object> parseObject() {

            Map<String, Object> result =
                    new LinkedHashMap<>();

            expect('{');
            skipWhitespace();

            if (peek('}')) {
                pos++;
                return result;
            }

            while (true) {

                skipWhitespace();

                if (!peek('"')) {
                    throw error(
                            "Se esperaba una clave JSON"
                    );
                }

                String key = parseString();

                skipWhitespace();
                expect(':');

                Object value = parseValue();
                result.put(key, value);

                skipWhitespace();

                if (peek('}')) {
                    pos++;
                    return result;
                }

                expect(',');
            }
        }

        private List<Object> parseArray() {

            List<Object> result =
                    new ArrayList<>();

            expect('[');
            skipWhitespace();

            if (peek(']')) {
                pos++;
                return result;
            }

            while (true) {

                result.add(parseValue());

                skipWhitespace();

                if (peek(']')) {
                    pos++;
                    return result;
                }

                expect(',');
            }
        }

        private String parseString() {

            expect('"');

            StringBuilder sb = new StringBuilder();

            while (pos < input.length()) {

                char current = input.charAt(pos++);

                if (current == '"') {
                    return sb.toString();
                }

                if (current == '\\') {

                    if (pos >= input.length()) {
                        throw error(
                                "Escape JSON incompleto"
                        );
                    }

                    char escaped = input.charAt(pos++);

                    switch (escaped) {
                        case '"':
                            sb.append('"');
                            break;

                        case '\\':
                            sb.append('\\');
                            break;

                        case '/':
                            sb.append('/');
                            break;

                        case 'b':
                            sb.append('\b');
                            break;

                        case 'f':
                            sb.append('\f');
                            break;

                        case 'n':
                            sb.append('\n');
                            break;

                        case 'r':
                            sb.append('\r');
                            break;

                        case 't':
                            sb.append('\t');
                            break;

                        case 'u':
                            appendUnicode(sb);
                            break;

                        default:
                            throw error(
                                    "Escape JSON no válido: \\"
                                            + escaped
                            );
                    }

                } else {
                    sb.append(current);
                }
            }

            throw error(
                    "Cadena JSON sin cerrar"
            );
        }

        private void appendUnicode(StringBuilder sb) {

            int first = parseUnicodeCodeUnit();

            if (Character.isHighSurrogate((char) first)
                    && pos + 6 <= input.length()
                    && input.charAt(pos) == '\\'
                    && input.charAt(pos + 1) == 'u') {

                pos += 2;
                int second = parseUnicodeCodeUnit();

                if (Character.isLowSurrogate((char) second)) {
                    sb.append(
                            Character.toChars(
                                    Character.toCodePoint(
                                            (char) first,
                                            (char) second
                                    )
                            )
                    );
                    return;
                }

                sb.append((char) first);
                sb.append((char) second);
                return;
            }

            sb.append((char) first);
        }

        private int parseUnicodeCodeUnit() {

            if (pos + 4 > input.length()) {
                throw error(
                        "Escape Unicode incompleto"
                );
            }

            String hex = input.substring(
                    pos,
                    pos + 4
            );

            pos += 4;

            try {
                return Integer.parseInt(hex, 16);
            } catch (NumberFormatException e) {
                throw error(
                        "Escape Unicode no válido: "
                                + hex
                );
            }
        }

        private Number parseNumber() {

            int start = pos;

            if (peek('-')) {
                pos++;
            }

            consumeDigits();

            boolean decimal = false;

            if (peek('.')) {
                decimal = true;
                pos++;
                consumeDigits();
            }

            if (peek('e') || peek('E')) {

                decimal = true;
                pos++;

                if (peek('+') || peek('-')) {
                    pos++;
                }

                consumeDigits();
            }

            String raw =
                    input.substring(start, pos);

            try {
                if (decimal) {
                    return Double.valueOf(raw);
                }

                return Long.valueOf(raw);

            } catch (NumberFormatException e) {
                throw error(
                        "Número JSON no válido: "
                                + raw
                );
            }
        }

        private void consumeDigits() {

            int start = pos;

            while (pos < input.length()
                    && Character.isDigit(input.charAt(pos))) {
                pos++;
            }

            if (start == pos) {
                throw error(
                        "Se esperaba un dígito"
                );
            }
        }

        private void expectLiteral(String literal) {

            if (!input.startsWith(literal, pos)) {
                throw error(
                        "Se esperaba '" + literal + "'"
                );
            }

            pos += literal.length();
        }

        private void expect(char expected) {

            skipWhitespace();

            if (pos >= input.length()
                    || input.charAt(pos) != expected) {

                throw error(
                        "Se esperaba '" + expected + "'"
                );
            }

            pos++;
        }

        private boolean peek(char value) {
            return pos < input.length()
                    && input.charAt(pos) == value;
        }

        private void skipWhitespace() {

            while (pos < input.length()) {

                char current = input.charAt(pos);

                if (current == ' '
                        || current == '\n'
                        || current == '\r'
                        || current == '\t') {
                    pos++;
                } else {
                    break;
                }
            }
        }

        private IllegalArgumentException error(
                String message) {

            return new IllegalArgumentException(
                    message
                            + " en la posición "
                            + pos
            );
        }
    }

    private static final class JsonPrinter {

        private JsonPrinter() {
        }

        private static String pretty(Object value) {

            StringBuilder sb = new StringBuilder();

            appendPretty(
                    sb,
                    value,
                    0
            );

            return sb.toString();
        }

        private static String compact(Object value) {

            StringBuilder sb = new StringBuilder();

            appendCompact(
                    sb,
                    value
            );

            return sb.toString();
        }

        @SuppressWarnings("unchecked")
        private static void appendPretty(
                StringBuilder sb,
                Object value,
                int indent) {

            if (value instanceof Map) {

                Map<String, Object> map =
                        (Map<String, Object>) value;

                sb.append('{');

                if (!map.isEmpty()) {

                    sb.append('\n');

                    int index = 0;

                    for (Map.Entry<String, Object> entry
                            : map.entrySet()) {

                        indent(sb, indent + 2);

                        appendString(
                                sb,
                                entry.getKey()
                        );

                        sb.append(": ");

                        appendPretty(
                                sb,
                                entry.getValue(),
                                indent + 2
                        );

                        if (++index < map.size()) {
                            sb.append(',');
                        }

                        sb.append('\n');
                    }

                    indent(sb, indent);
                }

                sb.append('}');
                return;
            }

            if (value instanceof List) {

                List<Object> list =
                        (List<Object>) value;

                sb.append('[');

                if (!list.isEmpty()) {

                    sb.append('\n');

                    for (int i = 0;
                         i < list.size();
                         i++) {

                        indent(sb, indent + 2);

                        appendPretty(
                                sb,
                                list.get(i),
                                indent + 2
                        );

                        if (i + 1 < list.size()) {
                            sb.append(',');
                        }

                        sb.append('\n');
                    }

                    indent(sb, indent);
                }

                sb.append(']');
                return;
            }

            appendPrimitive(sb, value);
        }

        @SuppressWarnings("unchecked")
        private static void appendCompact(
                StringBuilder sb,
                Object value) {

            if (value instanceof Map) {

                Map<String, Object> map =
                        (Map<String, Object>) value;

                sb.append('{');

                int index = 0;

                for (Map.Entry<String, Object> entry
                        : map.entrySet()) {

                    if (index++ > 0) {
                        sb.append(',');
                    }

                    appendString(
                            sb,
                            entry.getKey()
                    );

                    sb.append(':');

                    appendCompact(
                            sb,
                            entry.getValue()
                    );
                }

                sb.append('}');
                return;
            }

            if (value instanceof List) {

                List<Object> list =
                        (List<Object>) value;

                sb.append('[');

                for (int i = 0;
                     i < list.size();
                     i++) {

                    if (i > 0) {
                        sb.append(',');
                    }

                    appendCompact(
                            sb,
                            list.get(i)
                    );
                }

                sb.append(']');
                return;
            }

            appendPrimitive(sb, value);
        }

        private static void appendPrimitive(
                StringBuilder sb,
                Object value) {

            if (value == null) {
                sb.append("null");
                return;
            }

            if (value instanceof String) {
                appendString(
                        sb,
                        (String) value
                );
                return;
            }

            sb.append(String.valueOf(value));
        }

        private static void appendString(
                StringBuilder sb,
                String value) {

            sb.append('"');

            for (int i = 0;
                 i < value.length();
                 i++) {

                char current = value.charAt(i);

                switch (current) {
                    case '"':
                        sb.append("\\\"");
                        break;

                    case '\\':
                        sb.append("\\\\");
                        break;

                    case '\n':
                        sb.append("\\n");
                        break;

                    case '\r':
                        sb.append("\\r");
                        break;

                    case '\t':
                        sb.append("\\t");
                        break;

                    default:
                        sb.append(current);
                }
            }

            sb.append('"');
        }

        private static void indent(
                StringBuilder sb,
                int count) {

            for (int i = 0;
                 i < count;
                 i++) {
                sb.append(' ');
            }
        }
    }
}
