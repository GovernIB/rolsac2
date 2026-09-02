package es.caib.rolsac2.rest.api.externa.testclient;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cliente mínimo, sin dependencias externas, para probar procedimientos y servicios.
 * Requiere JDK 11+.
 * <p>
 * Uso:
 * javac Rolsac2RestClient.java
 * java Rolsac2RestClient http://localhost:8080/mi-app/api/v1
 * <p>
 * BASE_URL debe ser el prefijo anterior a /procediments/ y /serveis/.
 */
public class Rolsac2RestClient {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final String baseUrl;

    public Rolsac2RestClient(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Uso: java Rolsac2RestClient <BASE_URL>");
            System.err.println("Ejemplo: java Rolsac2RestClient http://localhost:8080/rolsac2/api/v1");
            System.exit(2);
        }
        Rolsac2RestClient client = new Rolsac2RestClient(args[0]);

        Map<String, String> proc = new LinkedHashMap<>();
        proc.put("idioma", "ca");
        proc.put("entitat", "1");
        proc.put("page-size", "5");
        proc.put("page", "0");
        proc.put("ordenCampo", "codi");
        proc.put("ordenAscendente", "asc");
        client.get("/procediments/", proc);

        Map<String, String> serveis = new LinkedHashMap<>();
        serveis.put("idioma", "ca");
        serveis.put("entitat", "1");
        serveis.put("tramitElectronica", "true");
        serveis.put("page-size", "5");
        serveis.put("page", "0");
        serveis.put("ordenCampo", "codi");
        serveis.put("ordenAscendente", "asc");
        client.get("/serveis/", serveis);
    }

    private void get(String path, Map<String, String> query) throws Exception {
        String url = baseUrl + path + toQuery(query);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nGET " + url);
        System.out.println("HTTP " + response.statusCode());
        System.out.println(response.body());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("La llamada ha fallado con HTTP " + response.statusCode());
        }
    }

    private String toQuery(Map<String, String> query) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : query.entrySet()) {
            if (e.getValue() == null) continue;
            sb.append(sb.length() == 0 ? '?' : '&')
                    .append(enc(e.getKey())).append('=').append(enc(e.getValue()));
        }
        return sb.toString();
    }

    private String enc(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
