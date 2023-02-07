package es.caib.rolsac2.commons.plugins.traduccion.translatorib;

import es.caib.rolsac2.commons.plugins.traduccion.api.*;
import es.caib.rolsac2.commons.rest.client.BasicAuthenticator;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
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

    public void setUp() {
        // Construïm un client amb autenticació

        client = ClientBuilder.newClient().register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));
        //client.register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));
    }

    @Override
    public String traducir(String tipoEntrada, String textoEntrada, Idioma idiomaEntrada, Idioma idiomaSalida, Map<String, String> opciones) throws IPluginTraduccionException {
        String textoSalida = "";
//        client = ClientBuilder.newClient().register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));

        if (textoEntrada == null || textoEntrada.isEmpty()) {
            return textoSalida;
        } else {
            // En mock realizamos echo de la entrada
            if (tipoEntrada.equals(TipoEntrada.TEXTO_PLANO.toString())) {
                textoSalida = traducirString(textoEntrada, idiomaEntrada, idiomaSalida, TipoEntrada.TEXTO_PLANO);
            } else {
                textoSalida = traducirHTML(textoEntrada, idiomaEntrada, idiomaSalida, TipoEntrada.HTML);
            }
            return textoSalida;
        }
    }

    public String traducirHTML(String texto, Idioma entrada, Idioma salida, TipoEntrada te) throws IPluginTraduccionException {
        String res = "";
        ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
        parametros.setTextoEntrada(texto);
        parametros.setTipoEntrada(te);

        parametros.setIdiomaEntrada(entrada);
        parametros.setIdiomaSalida(salida);

        try {
            final Response response = client.target(getProperty(BASE_URL) + "/texto").request().post(Entity.json(parametros));

            final ResultadoTraduccionDocumento respuesta = response.readEntity(ResultadoTraduccionDocumento.class);

            if (respuesta != null && !respuesta.isError()) {
                res = respuesta.getTextoTraducido();
            } else {
                res = texto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IPluginTraduccionException("Ha habido un error en la comunicación con el plugin de traducción. " + e.getMessage());
        }
        return res;
    }

    public String traducirString(String texto, Idioma entrada, Idioma salida, TipoEntrada te) throws IPluginTraduccionException{
        String res = "";
        ParametrosTraduccionTexto parametros = new ParametrosTraduccionTexto();
        parametros.setTextoEntrada(texto);
        parametros.setTipoEntrada(te);

        parametros.setIdiomaEntrada(entrada);
        parametros.setIdiomaSalida(salida);
        //parametros.setOpciones(new Opciones());
        try{
            final Response response = client.target(getProperty(BASE_URL) + "/texto").request().post(Entity.json(parametros));

            final ResultadoTraduccionTexto respuesta = response.readEntity(ResultadoTraduccionTexto.class);

            if (respuesta != null && !respuesta.isError()) {
                res = respuesta.getTextoTraducido();
            } else {
                res = texto;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IPluginTraduccionException("Ha habido un error en la comunicación con el plugin de traducción. " + e.getMessage());
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
