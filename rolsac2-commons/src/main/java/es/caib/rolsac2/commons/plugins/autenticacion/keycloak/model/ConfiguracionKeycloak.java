package es.caib.rolsac2.commons.plugins.autenticacion.keycloak.model;

public class ConfiguracionKeycloak {

    private String keyloackRealm;
    private String keycloakAuthUrl;
    private String keycloakResource;
    private String keycloakSecret;

    public String getKeyloackRealm() {
        return keyloackRealm;
    }

    public void setKeyloackRealm(String keyloackRealm) {
        this.keyloackRealm = keyloackRealm;
    }

    public String getKeycloakAuthUrl() {
        return keycloakAuthUrl;
    }

    public void setKeycloakAuthUrl(String keycloakAuthUrl) {
        this.keycloakAuthUrl = keycloakAuthUrl;
    }

    public String getKeycloakResource() {
        return keycloakResource;
    }

    public void setKeycloakResource(String keycloakResource) {
        this.keycloakResource = keycloakResource;
    }

    public String getKeycloakSecret() {
        return keycloakSecret;
    }

    public void setKeycloakSecret(String keycloakSecret) {
        this.keycloakSecret = keycloakSecret;
    }

    public String toStringAsJSON() {
        return "{" +
                "\"realm\":\"" + keyloackRealm + '\"' +
                ", \"auth-server-url\": \"" + keycloakAuthUrl + '\"' +
                ", \"resource\": \"" + keycloakResource + '\"' +
                ", \"credentials\": {\"secret\":" +
                '\"' + keycloakSecret + "\"}}";
    }
}
