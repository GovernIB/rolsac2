package es.caib.rolsac2.commons.rest.client;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Classe per incloure autenticació Basic dins un client JAX-RS mitjançant un filtre de client que
 * afegeix la capçalera <i>Authorization</i> corresponent d'acord amb un usuari i password.
 * Basada en http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access
 */
public class PduAuthenticator implements ClientRequestFilter {

    private final String user;
    private final String password;

    public PduAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add("x-api-user", this.user);
        requestContext.getHeaders().add("x-api-key", this.password);
    }
}