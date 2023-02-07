package es.caib.rolsac2.commons.plugins.autenticacion.keycloak;

import es.caib.rolsac2.commons.plugins.autenticacion.api.AutenticacionErrorException;
import es.caib.rolsac2.commons.plugins.autenticacion.api.IPluginAutenticacion;
import es.caib.rolsac2.commons.plugins.autenticacion.api.model.UsuarioAutenticado;
import es.caib.rolsac2.commons.plugins.autenticacion.keycloak.model.ConfiguracionKeycloak;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.JWSInputException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

public class KeyCloakRestPlugin extends AbstractPluginProperties implements IPluginAutenticacion {

    private ConfiguracionKeycloak configuracion;

    public static final String KEYCLOAK_RESOURCE = "keycloak.resource";
    public static final String KEYCLOAK_CLIENT_SECRET = "keycloak.secret";
    public static final String KEYCLOAK_REALM = "keycloak.realm";
    public static final String KEYCLOAK_AUTHORIZATION_URI = "keycloak.authUrl";


    public KeyCloakRestPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
        configuracion = new ConfiguracionKeycloak();
        configuracion.setKeycloakSecret(getProperty(KEYCLOAK_CLIENT_SECRET));
        configuracion.setKeycloakAuthUrl(getProperty(KEYCLOAK_AUTHORIZATION_URI));
        configuracion.setKeyloackRealm(getProperty(KEYCLOAK_REALM));
        configuracion.setKeycloakResource(getProperty(KEYCLOAK_RESOURCE));
    }

    @Override
    public UsuarioAutenticado login(String usuario, String password) throws AutenticacionErrorException {
        AuthzClient authzClient = null;
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(
                configuracion.toStringAsJSON().getBytes(StandardCharsets.UTF_8))) {
            authzClient = AuthzClient.create(bis);
        } catch (final Exception e) {
            throw new AutenticacionErrorException("No se puede conectar a servidor autenticación");
        }

        AuthorizationResponse response = null;
        final AuthorizationRequest request = new AuthorizationRequest();
        response = authzClient.authorization(usuario, password).authorize(request);
        AccessToken token = null;
        try {
            token = new JWSInput(response.getToken()).readJsonContent(AccessToken.class);
        } catch (final JWSInputException e) {
            throw new AutenticacionErrorException("No se ha podido autenticar correctamente el usuario: " + usuario);
        }

        return this.rellenarDatosUsuario(token);
    }

    private UsuarioAutenticado rellenarDatosUsuario(AccessToken token) {
        UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
        final String nombreCompleto = token.getName();
        final String apellidos = token.getFamilyName();
        if(nombreCompleto != null && apellidos != null) {
            usuarioAutenticado.setNombre(nombreCompleto.substring(0, nombreCompleto.length() - apellidos.length()));
        }
        usuarioAutenticado.setNif((String) token.getOtherClaims().get("nif"));
        usuarioAutenticado.setApellido1((String) token.getOtherClaims().get("apellido1"));
        usuarioAutenticado.setApellido2((String) token.getOtherClaims().get("apellido2"));
        usuarioAutenticado.setEmail(token.getEmail());
        usuarioAutenticado.setTelefono(token.getPhoneNumber());
        usuarioAutenticado.setRoles(token.getRealmAccess().getRoles());
        return usuarioAutenticado;
    }

}
