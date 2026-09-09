package es.caib.rolsac2.rest.api.externa.testclient;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cliente de prueba standalone para la API REST externa de ROLSAC2.
 * <p>
 * - JDK 11+
 * - Sin Jackson
 * - Sin dependencias externas
 * - Procedimientos: /procediments/
 * - Servicios: /serveis/
 * <p>
 * Interpreta el JSON recibido y lo presenta por consola de forma legible.
 */
public class Rolsac2RestClient {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final String baseUrl;

    public Rolsac2RestClient(final String baseUrl) {
        this.baseUrl = normalizarBaseUrl(baseUrl);
    }

    public static void main(final String[] args) throws Exception {

        final String baseUrl = args.length > 0
                ? args[0]
                : "http://localhost:8080/rolsac2api/externa/services/v1";

        final Rolsac2RestClient client = new Rolsac2RestClient(baseUrl);

        imprimirCabecera("ROLSAC2 - CLIENTE REST EXTERNO");
        System.out.println("Base URL : " + baseUrl);

        final Map<String, String> procediments = new LinkedHashMap<>();
        procediments.put("idioma", "ca");
        procediments.put("entitat", "1");
        procediments.put("page-size", "5");
        procediments.put("page", "0");
        procediments.put("ordenCampo", "codi");
        procediments.put("ordenAscendente", "asc");

        client.getAndPrint("/procediments/", procediments, "PROCEDIMENTS");

        final Map<String, String> serveis = new LinkedHashMap<>();
        serveis.put("idioma", "ca");
        serveis.put("entitat", "1");
        serveis.put("tramitElectronica", "true");
        serveis.put("page-size", "5");
        serveis.put("page", "0");
        serveis.put("ordenCampo", "codi");
        serveis.put("ordenAscendente", "asc");

        client.getAndPrint("/serveis/", serveis, "SERVEIS");
    }

    private void getAndPrint(final String path,
                             final Map<String, String> query,
                             final String tipo) throws Exception {

        final String url = baseUrl + normalizarPath(path) + toQuery(query);

        System.out.println();
        imprimirCabecera(tipo);
        System.out.println("GET  : " + url);
        System.out.println();

        final HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        final HttpResponse<String> response =
                http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        System.out.println("HTTP : " + response.statusCode());

        final String body = response.body();

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            System.err.println();
            System.err.println("ERROR DEVUELTO POR EL SERVIDOR");
            System.err.println("-----------------------------");
            imprimirJsonOTexto(body, true);
            throw new IllegalStateException(
                    "La llamada ha fallado con HTTP " + response.statusCode()
            );
        }

        try {
            final Object parsed = new JsonParser(body).parse();

            if (!(parsed instanceof Map)) {
                System.out.println();
                System.out.println("La respuesta JSON no es un objeto.");
                System.out.println(JsonPrinter.pretty(parsed));
                return;
            }

            @SuppressWarnings("unchecked") final Map<String, Object> root = (Map<String, Object>) parsed;

            imprimirRespuesta(root, tipo);

        } catch (RuntimeException ex) {
            System.out.println();
            System.out.println("No se ha podido interpretar el JSON recibido.");
            System.out.println("Se muestra la respuesta original:");
            System.out.println();
            imprimirJsonOTexto(body, false);
        }
    }

    private void imprimirRespuesta(final Map<String, Object> root, final String tipo) {

        imprimirResumen(root);

        final Object itemsObject = root.get("items");

        if (!(itemsObject instanceof List)) {
            System.out.println();
            System.out.println("La respuesta no contiene un array 'items'.");
            System.out.println(JsonPrinter.pretty(root));
            return;
        }

        @SuppressWarnings("unchecked") final List<Object> items = (List<Object>) itemsObject;

        System.out.println();
        System.out.println("ELEMENTOS");
        System.out.println("---------");

        if (items.isEmpty()) {
            System.out.println("(sin resultados)");
            return;
        }

        int posicion = 1;

        for (Object itemObject : items) {
            if (!(itemObject instanceof Map)) {
                System.out.println();
                System.out.println("[" + posicion + "] " + JsonPrinter.pretty(itemObject));
                posicion++;
                continue;
            }

            @SuppressWarnings("unchecked") final Map<String, Object> item = (Map<String, Object>) itemObject;

            if ("PROCEDIMENTS".equalsIgnoreCase(tipo)) {
                imprimirProcediment(posicion, item);
            } else if ("SERVEIS".equalsIgnoreCase(tipo)) {
                imprimirServei(posicion, item);
            } else {
                imprimirItemGenerico(posicion, item);
            }

            posicion++;
        }
    }

    private void imprimirResumen(final Map<String, Object> root) {

        System.out.println();
        System.out.println("RESUMEN");
        System.out.println("-------");

        imprimirCampo("Título", root, "title");
        imprimirCampo("Descripción", root, "description");
        imprimirCampo("Entidad / spatial", root, "spatial");
        imprimirCampo("Creator", root, "creator");
        imprimirCampo("Fecha descarga", root, "dateDownload");

        System.out.println();

        imprimirCampo("Total", root, "totalCount");
        imprimirCampo("Devueltos", root, "itemsReturned");
        imprimirCampo("Página", root, "page");
        imprimirCampo("Tamaño página", root, "pageSize");
        imprimirCampo("Total páginas", root, "totalPages");
        imprimirCampo("Tiempo", root, "tiempo", " ms");

        System.out.println();

        imprimirCampo("Anterior", root, "previousUrl");
        imprimirCampo("Siguiente", root, "nextUrl");
    }

    private void imprimirProcediment(final int posicion, final Map<String, Object> item) {

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────────────────────");
        System.out.println("│ [" + posicion + "] PROCEDIMENT");
        System.out.println("├────────────────────────────────────────────────────────────────────────────");

        fila("Codi", valor(item, "codi"));
        fila("Nom", valor(item, "nom"));
        fila("Estat", valor(item, "estat"));
        fila("Codi SIA", valor(item, "codiSIA"));
        fila("Estat SIA", valor(item, "estatSIA"));
        fila("Data actualització", valor(item, "dataActualizacio"));
        fila("Data publicació", valor(item, "dataPublicacio"));
        fila("Data caducitat", valor(item, "dataCaducitat"));

        filaCodiNom("UA responsable",
                item, "uaResponsableCodi", "uaResponsableNom");

        filaCodiNom("UA competent",
                item, "uaCompetenteCodi", "uaCompetenteNom");

        filaCodiNom("UA instructora",
                item, "uaInstructor", "uaInstructorNom");

        filaCodiNom("Tipus",
                item, "tipusCodi", "tipusNom");

        filaCodiNom("Iniciació",
                item, "iniciacionCodi", "iniciacionNom");

        filaCodiNom("Silenci",
                item, "silenciCodi", "silenciNom");

        filaCodiNom("Tipus via",
                item, "tipusViaCodi", "tipusViaNom");

        fila("Comú", valor(item, "comu"));
        fila("Apoderat", valor(item, "habilitatApoderat"));
        fila("Funcionari", valor(item, "habilitatFuncionari"));
        fila("Termini resolució", valor(item, "terminiResolucio"));

        filaTextoLargo("Objecte", valor(item, "objecte"));
        filaTextoLargo("Destinataris", valor(item, "destinataris"));

        fila("URL", valor(item, "url"));

        System.out.println("└────────────────────────────────────────────────────────────────────────────");
    }

    private void imprimirServei(final int posicion, final Map<String, Object> item) {

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────────────────────");
        System.out.println("│ [" + posicion + "] SERVEI");
        System.out.println("├────────────────────────────────────────────────────────────────────────────");

        fila("Codi", valor(item, "codi"));
        fila("Nom", valor(item, "nom"));
        fila("Estat", valor(item, "estat"));
        fila("Codi SIA", valor(item, "codiSIA"));
        fila("Estat SIA", valor(item, "estatSIA"));
        fila("Data actualització", valor(item, "dataActualizacio"));
        fila("Data publicació", valor(item, "dataPublicacio"));
        fila("Data caducitat", valor(item, "dataCaducitat"));

        filaCodiNom("UA responsable",
                item, "uaResponsableCodi", "uaResponsableNom");

        filaCodiNom("UA instructora",
                item, "uaInstructorCodi", "uaInstructorNom");

        filaCodiNom("Tipus tramitació",
                item, "tipusTramitacioCodi", "tipusTramitacioNom");

        filaCodiNom("Plataforma",
                item, "plataformaTramitCodi", "plataformaTramitNom");

        filaCodiNom("Plantilla",
                item, "plantillaTramitCodi", "plantillaTramitNom");

        fila("Comú", valor(item, "comu"));
        fila("Apoderat", valor(item, "habilitatApoderat"));
        fila("Funcionari", valor(item, "habilitatFuncionari"));
        fila("Termini resolució", valor(item, "terminiResolucio"));

        fila("Presencial", valor(item, "tramitPresencial"));
        fila("Telefònica", valor(item, "tramitTelefonica"));
        fila("Electrònica", valor(item, "tramitElectronica"));

        filaTextoLargo("Objecte", valor(item, "objecte"));
        filaTextoLargo("Destinataris", valor(item, "destinataris"));

        fila("URL", valor(item, "url"));
        fila("URL tramitació", valor(item, "urlTramitacio"));

        imprimirPublicsObjectius(item.get("publicsObjectius"));

        System.out.println("└────────────────────────────────────────────────────────────────────────────");
    }

    private void imprimirPublicsObjectius(final Object value) {

        if (!(value instanceof List)) {
            return;
        }

        @SuppressWarnings("unchecked") final List<Object> list = (List<Object>) value;

        if (list.isEmpty()) {
            return;
        }

        System.out.println("│");
        System.out.println("│ Públics objectiu:");

        for (Object entry : list) {
            if (entry instanceof Map) {
                @SuppressWarnings("unchecked") final Map<String, Object> publicObjectiu = (Map<String, Object>) entry;

                System.out.println(
                        "│   • "
                                + valor(publicObjectiu, "codi")
                                + " | "
                                + valor(publicObjectiu, "nom")
                );
            } else {
                System.out.println("│   • " + texto(entry));
            }
        }
    }

    private void imprimirItemGenerico(final int posicion, final Map<String, Object> item) {

        System.out.println();
        System.out.println("[" + posicion + "]");

        for (Map.Entry<String, Object> entry : item.entrySet()) {
            System.out.println(
                    "    "
                            + rellenarDerecha(entry.getKey(), 24)
                            + ": "
                            + texto(entry.getValue())
            );
        }
    }

    private void imprimirCampo(final String etiqueta,
                               final Map<String, Object> root,
                               final String campo) {
        imprimirCampo(etiqueta, root, campo, "");
    }

    private void imprimirCampo(final String etiqueta,
                               final Map<String, Object> root,
                               final String campo,
                               final String sufijo) {

        final String value = valor(root, campo);

        System.out.println(
                rellenarDerecha(etiqueta, 20)
                        + ": "
                        + value
                        + ("-".equals(value) ? "" : sufijo)
        );
    }

    private void fila(final String etiqueta, final String value) {
        System.out.println(
                "│ "
                        + rellenarDerecha(etiqueta, 20)
                        + ": "
                        + value
        );
    }

    private void filaCodiNom(final String etiqueta,
                             final Map<String, Object> item,
                             final String campoCodi,
                             final String campoNom) {

        final String codi = valor(item, campoCodi);
        final String nom = valor(item, campoNom);

        if ("-".equals(codi) && "-".equals(nom)) {
            return;
        }

        fila(etiqueta, codi + " | " + nom);
    }

    private void filaTextoLargo(final String etiqueta, final String value) {

        if (value == null || "-".equals(value)) {
            fila(etiqueta, "-");
            return;
        }

        final int ancho = 86;

        if (value.length() <= ancho) {
            fila(etiqueta, value);
            return;
        }

        System.out.println(
                "│ "
                        + rellenarDerecha(etiqueta, 20)
                        + ":"
        );

        int inicio = 0;

        while (inicio < value.length()) {

            int fin = Math.min(inicio + ancho, value.length());

            if (fin < value.length()) {
                final int espacio = value.lastIndexOf(' ', fin);

                if (espacio > inicio) {
                    fin = espacio;
                }
            }

            System.out.println("│     " + value.substring(inicio, fin).trim());

            inicio = fin;

            while (inicio < value.length() && value.charAt(inicio) == ' ') {
                inicio++;
            }
        }
    }

    private void imprimirJsonOTexto(final String body, final boolean error) {

        try {
            final Object parsed = new JsonParser(body).parse();
            final String pretty = JsonPrinter.pretty(parsed);

            if (error) {
                System.err.println(pretty);
            } else {
                System.out.println(pretty);
            }

        } catch (RuntimeException ex) {
            if (error) {
                System.err.println(body);
            } else {
                System.out.println(body);
            }
        }
    }

    private String toQuery(final Map<String, String> query) {

        final StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : query.entrySet()) {

            if (entry.getValue() == null) {
                continue;
            }

            sb.append(sb.length() == 0 ? '?' : '&')
                    .append(enc(entry.getKey()))
                    .append('=')
                    .append(enc(entry.getValue()));
        }

        return sb.toString();
    }

    private static String enc(final String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static String valor(final Map<String, Object> map, final String key) {
        if (map == null || !map.containsKey(key)) {
            return "-";
        }
        return texto(map.get(key));
    }

    private static String texto(final Object value) {

        if (value == null) {
            return "-";
        }

        if (value instanceof String) {
            final String text = (String) value;
            return text.trim().isEmpty() ? "-" : text;
        }

        if (value instanceof Map || value instanceof List) {
            return JsonPrinter.compact(value);
        }

        return String.valueOf(value);
    }

    private static String normalizarBaseUrl(final String url) {

        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL base no puede estar vacía.");
        }

        return url.endsWith("/")
                ? url.substring(0, url.length() - 1)
                : url;
    }

    private static String normalizarPath(final String path) {

        if (path == null || path.trim().isEmpty()) {
            return "";
        }

        return path.startsWith("/") ? path : "/" + path;
    }

    private static void imprimirCabecera(final String titulo) {

        final int longitud = Math.max(60, titulo.length() + 8);
        final String linea = repetir('=', longitud);

        System.out.println(linea);
        System.out.println("  " + titulo);
        System.out.println(linea);
    }

    private static String rellenarDerecha(final String texto, final int longitud) {

        if (texto.length() >= longitud) {
            return texto;
        }

        return texto + repetir(' ', longitud - texto.length());
    }

    private static String repetir(final char caracter, final int veces) {

        final StringBuilder sb = new StringBuilder(veces);

        for (int i = 0; i < veces; i++) {
            sb.append(caracter);
        }

        return sb.toString();
    }

    /**
     * Parser JSON mínimo, autocontenido y suficiente para las respuestas REST.
     */
    private static final class JsonParser {

        private final String input;
        private int pos;

        private JsonParser(final String input) {
            this.input = input == null ? "" : input;
        }

        private Object parse() {

            skipWhitespace();

            final Object value = parseValue();

            skipWhitespace();

            if (pos != input.length()) {
                throw error("Hay contenido adicional después del JSON");
            }

            return value;
        }

        private Object parseValue() {

            skipWhitespace();

            if (pos >= input.length()) {
                throw error("Fin inesperado del JSON");
            }

            final char c = input.charAt(pos);

            if (c == '{') {
                return parseObject();
            }

            if (c == '[') {
                return parseArray();
            }

            if (c == '"') {
                return parseString();
            }

            if (c == 't') {
                expect("true");
                return Boolean.TRUE;
            }

            if (c == 'f') {
                expect("false");
                return Boolean.FALSE;
            }

            if (c == 'n') {
                expect("null");
                return null;
            }

            if (c == '-' || Character.isDigit(c)) {
                return parseNumber();
            }

            throw error("Valor JSON no válido");
        }

        private Map<String, Object> parseObject() {

            final Map<String, Object> result = new LinkedHashMap<>();

            expect('{');
            skipWhitespace();

            if (peek('}')) {
                pos++;
                return result;
            }

            while (true) {

                skipWhitespace();

                if (!peek('"')) {
                    throw error("Se esperaba una clave de objeto JSON");
                }

                final String key = parseString();

                skipWhitespace();
                expect(':');

                final Object value = parseValue();
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

            final List<Object> result = new ArrayList<>();

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

            final StringBuilder sb = new StringBuilder();

            while (pos < input.length()) {

                final char c = input.charAt(pos++);

                if (c == '"') {
                    return sb.toString();
                }

                if (c == '\\') {

                    if (pos >= input.length()) {
                        throw error("Escape JSON incompleto");
                    }

                    final char esc = input.charAt(pos++);

                    switch (esc) {
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
                            sb.append(parseUnicode());
                            break;
                        default:
                            throw error("Escape JSON no válido: \\" + esc);
                    }

                } else {
                    sb.append(c);
                }
            }

            throw error("Cadena JSON sin cerrar");
        }

        private char parseUnicode() {

            if (pos + 4 > input.length()) {
                throw error("Escape Unicode incompleto");
            }

            final String hex = input.substring(pos, pos + 4);
            pos += 4;

            try {
                return (char) Integer.parseInt(hex, 16);
            } catch (NumberFormatException ex) {
                throw error("Escape Unicode no válido: " + hex);
            }
        }

        private Number parseNumber() {

            final int start = pos;

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

            final String raw = input.substring(start, pos);

            try {
                if (decimal) {
                    return Double.valueOf(raw);
                }

                return Long.valueOf(raw);

            } catch (NumberFormatException ex) {
                throw error("Número JSON no válido: " + raw);
            }
        }

        private void consumeDigits() {

            final int start = pos;

            while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                pos++;
            }

            if (start == pos) {
                throw error("Se esperaba un dígito");
            }
        }

        private void expect(final String value) {

            if (!input.startsWith(value, pos)) {
                throw error("Se esperaba '" + value + "'");
            }

            pos += value.length();
        }

        private void expect(final char value) {

            skipWhitespace();

            if (pos >= input.length() || input.charAt(pos) != value) {
                throw error("Se esperaba '" + value + "'");
            }

            pos++;
        }

        private boolean peek(final char value) {
            return pos < input.length() && input.charAt(pos) == value;
        }

        private void skipWhitespace() {

            while (pos < input.length()) {

                final char c = input.charAt(pos);

                if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                    pos++;
                } else {
                    break;
                }
            }
        }

        private IllegalArgumentException error(final String message) {
            return new IllegalArgumentException(
                    message + " en la posición " + pos
            );
        }
    }

    /**
     * Formateador JSON para mostrar respuestas desconocidas o errores.
     */
    private static final class JsonPrinter {

        private JsonPrinter() {
        }

        private static String pretty(final Object value) {
            final StringBuilder sb = new StringBuilder();
            appendPretty(sb, value, 0);
            return sb.toString();
        }

        private static String compact(final Object value) {
            final StringBuilder sb = new StringBuilder();
            appendCompact(sb, value);
            return sb.toString();
        }

        @SuppressWarnings("unchecked")
        private static void appendPretty(final StringBuilder sb,
                                         final Object value,
                                         final int indent) {

            if (value instanceof Map) {

                final Map<String, Object> map = (Map<String, Object>) value;

                sb.append('{');

                if (!map.isEmpty()) {
                    sb.append('\n');

                    int index = 0;

                    for (Map.Entry<String, Object> entry : map.entrySet()) {

                        indent(sb, indent + 2);
                        appendString(sb, entry.getKey());
                        sb.append(": ");

                        appendPretty(sb, entry.getValue(), indent + 2);

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

                final List<Object> list = (List<Object>) value;

                sb.append('[');

                if (!list.isEmpty()) {
                    sb.append('\n');

                    for (int i = 0; i < list.size(); i++) {

                        indent(sb, indent + 2);
                        appendPretty(sb, list.get(i), indent + 2);

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
        private static void appendCompact(final StringBuilder sb, final Object value) {

            if (value instanceof Map) {

                final Map<String, Object> map = (Map<String, Object>) value;

                sb.append('{');

                int index = 0;

                for (Map.Entry<String, Object> entry : map.entrySet()) {

                    if (index++ > 0) {
                        sb.append(',');
                    }

                    appendString(sb, entry.getKey());
                    sb.append(':');
                    appendCompact(sb, entry.getValue());
                }

                sb.append('}');
                return;
            }

            if (value instanceof List) {

                final List<Object> list = (List<Object>) value;

                sb.append('[');

                for (int i = 0; i < list.size(); i++) {

                    if (i > 0) {
                        sb.append(',');
                    }

                    appendCompact(sb, list.get(i));
                }

                sb.append(']');
                return;
            }

            appendPrimitive(sb, value);
        }

        private static void appendPrimitive(final StringBuilder sb, final Object value) {

            if (value == null) {
                sb.append("null");
            } else if (value instanceof String) {
                appendString(sb, (String) value);
            } else {
                sb.append(String.valueOf(value));
            }
        }

        private static void appendString(final StringBuilder sb, final String value) {

            sb.append('"');

            for (int i = 0; i < value.length(); i++) {

                final char c = value.charAt(i);

                switch (c) {
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
                        sb.append(c);
                }
            }

            sb.append('"');
        }

        private static void indent(final StringBuilder sb, final int count) {
            for (int i = 0; i < count; i++) {
                sb.append(' ');
            }
        }
    }
}
