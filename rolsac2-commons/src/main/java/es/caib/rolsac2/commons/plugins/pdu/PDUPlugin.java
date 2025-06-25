package es.caib.rolsac2.commons.plugins.pdu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RLinkData;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeDelete;
import es.caib.rolsac2.commons.rest.client.PduAuthenticator;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PDUPlugin extends AbstractPluginProperties implements IPluginPdu {

    /**
     * Dirección del servicio REST de Test
     */
    private String urlService;

    /** Utilidad de Spring para realizar peticiones REST */


    /**
     * Indica si se ha habilitado el debug
     */
    private boolean debugEnabled = false;

    /**
     * Logger
     */
//    private final Logger log = LoggerFactory.getLogger(PduRestClientImpl.class);

    private static final String BASE_URL = "url";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static Client client;
    /**
     * log.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PDUPlugin.class);

    public PDUPlugin(final String appId, final String apiKey, final String direccion, final Integer tiempoEspera, final boolean debug) {

    }

    public PDUPlugin(final String prefijoPropiedades, final Properties properties) throws Exception {
        super(prefijoPropiedades, properties);

        // 1. Construir SSLContext TLSv1.2
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null); // usa TrustManagers por defecto

        // 2. Crear el cliente registrando tu Authenticator y el sslContext
        client = ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier((hostname, session) -> true) // o uno más estricto
                .register(new PduAuthenticator(getProperty(USER), getProperty(PASSWORD)))
                .build();
    }


    @Override
    public RRespuestaImportarEnlace importarEnlace(RPeticionImportarEnlace peticionImportarEnlace) {

        Invocation.Builder request = client.target(getProperty(BASE_URL) + "/import").request(MediaType.APPLICATION_JSON);

        try {
            Response response = request.post(Entity.entity(
                    new ObjectMapper().writeValueAsString(peticionImportarEnlace),
                    MediaType.APPLICATION_JSON)
            );

            String json = response.readEntity(String.class);
            response.close();

            RRespuestaImportarEnlace respuesta = new ObjectMapper().readValue(json, RRespuestaImportarEnlace.class);
            List<String> enlaces = new ArrayList<>();
            for (RLinkData dato : peticionImportarEnlace.getLinkData()) {
                if (dato.getUrl() != null) {
                    enlaces.add(dato.getUrl());
                }
            }
            respuesta.setEnlaces(enlaces);
            return respuesta;
        } catch (JsonProcessingException e) {
            LOG.error("Error al procesar la respuesta de PDU", e);
            return null;
        }
    }

    // DSS No probado
    @Override
    public RRespuestaImportarEnlace eliminarEnlaces(List<String> urls) {
        RPeticionImportarEnlace peticionImportarEnlace = new RPeticionImportarEnlace();

        List<RLinkData> datos = new ArrayList<>();
        for (String url : urls) {
            RLinkData dato = new RLinkData();
            dato.setTitle(" ");
            dato.setDescription(" ");
            dato.setType(" ");
            dato.setCategories(new ArrayList<>());
            dato.setUrl(url);
            dato.setDelete(RTypeDelete.YES);
            datos.add(dato);
        }

        peticionImportarEnlace.setLinkData(datos);

        Invocation.Builder request = client.target(getProperty(BASE_URL) + "/import").request(MediaType.APPLICATION_JSON);

        try {
            Response response = request.post(Entity.entity(new ObjectMapper().writeValueAsString(peticionImportarEnlace), MediaType.APPLICATION_JSON));

            return new ObjectMapper().readValue(response.readEntity(String.class), RRespuestaImportarEnlace.class);
//            return response.readEntity(RRespuestaImportarEnlace.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
