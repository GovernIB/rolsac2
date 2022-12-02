package es.caib.rolsac2.commons.plugins.traduccion.translatorib;

import es.caib.rolsac2.commons.plugins.traduccion.api.*;
import es.caib.rolsac2.commons.rest.client.BasicAuthenticator;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Properties;

public class TranslatorIBPlugin extends AbstractPluginProperties implements IPluginTraduccion {

    private static final String BASE_URL = "url";
    private static final String USER= "usr";
    private static final String PASSWORD = "pwd";
    private static Client client;

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public TranslatorIBPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
        setUp();

    }

    public static void setUp() {
        // Construïm un client amb autenticació

        client = ClientBuilder.newClient();
        //client.register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));
    }

    @Override
    public String traducir(String tipoEntrada, String textoEntrada, Idioma idiomaEntrada, Idioma idiomaSalida, Map<String, String> opciones) throws IPluginTraduccionException {
        String textoSalida = "";
        client = ClientBuilder.newClient().register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));

        // En mock realizamos echo de la entrada
        if (tipoEntrada.equals(TipoEntrada.TEXTO_PLANO.toString())) {
            textoSalida = traducirString(textoEntrada, idiomaEntrada, idiomaSalida, TipoEntrada.TEXTO_PLANO.fromString(tipoEntrada));
        }
        return textoSalida;
    }

    public String traducirString(String texto, Idioma entrada, Idioma salida, TipoEntrada te) {
        String res = "";
        final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
        parametros.setTextoEntrada(texto);
        parametros.setTipoEntrada(te);

        parametros.setIdiomaEntrada(entrada);
        parametros.setIdiomaSalida(salida);
        parametros.setOpciones(new Opciones());

        final Response response = client.target(getProperty(BASE_URL) + "/texto").request().post(Entity.json(parametros));

        final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

        if (respuesta != null && !respuesta.isError()) {
            res = respuesta.getTextoTraducido();
        } else {
            res = "XX ERROR XX";
        }
        return res;

    }

    public void test() {
        final ResultadoTraduccionTexto respuesta = client.target(getProperty(BASE_URL) + "/test").request(MediaType.APPLICATION_JSON)
                .get(ResultadoTraduccionTexto.class);


    }

    public void testTraduccion() {

        final ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
        parametros.setTextoEntrada("Texto a traducir");
        parametros.setTipoEntrada(TipoEntrada.TEXTO_PLANO);
        parametros.setIdiomaEntrada(Idioma.CASTELLANO);
        parametros.setIdiomaSalida(Idioma.CATALAN);

        final Response response = client.target(getProperty(BASE_URL) + "/texto").request().post(Entity.json(parametros));

        final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);


    }
}
