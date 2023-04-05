package es.caib.rolsac2.commons.plugins.dir3.caib;

import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.IPluginDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.UtilsDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganica;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganicaResponse;
import es.caib.rolsac2.commons.rest.client.BasicAuthenticator;
import jdk.jshell.execution.Util;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Dir3CaibRestPlugin extends AbstractPluginProperties implements IPluginDir3 {

    private static final String BASE_URL = "url";
    private static final String USER= "usr";
    private static final String PASSWORD = "pwd";
    private static Client client;


    public Dir3CaibRestPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
        client = ClientBuilder.newClient().register(new BasicAuthenticator(getProperty(USER), getProperty(PASSWORD)));
    }

    @Override
    public List<UnidadOrganica> obtenerArbolUnidades(final ParametrosDir3 parametros) throws Dir3ErrorException{
        try {
            final Response response = client.target(getProperty(BASE_URL) + "/unidades/obtenerArbolUnidades")
                            .queryParam("codigo", parametros.getCodigo())
                            .queryParam("fechaActualizacion", (parametros.getFechaActualizacion() != null) ? UtilsDir3.formatearFecha(parametros.getFechaActualizacion()) : null)
                            .queryParam("fechaSincronizacion",(parametros.getFechaSincronizacion() != null) ? UtilsDir3.formatearFecha(parametros.getFechaSincronizacion()) : null)
                            .queryParam("denominacionCooficial", parametros.getDenominacionCooficial())
                            .request(MediaType.APPLICATION_JSON).get();

            List<UnidadOrganicaResponse> unidades = response.readEntity(new GenericType<List<UnidadOrganicaResponse>>(){});
            List<UnidadOrganica> unidadesOrganicas = new ArrayList<>();
            for(UnidadOrganicaResponse unidad : unidades) {
                unidadesOrganicas.add(unidad.createUnidadOrganica());
            }
            return unidadesOrganicas;
        } catch (Exception e) {
            throw new Dir3ErrorException("Se ha producido un error al obtener el árbol de unidades");
        }
    }

    @Override
    public List<UnidadOrganica> obtenerHistoricosFinales(final ParametrosDir3 parametros) throws Dir3ErrorException{
        try {
            final Response response = client.target(getProperty(BASE_URL) + "/unidades/obtenerHistoricosFinales")
                    .queryParam("codigo", parametros.getCodigo())
                    .queryParam("denominacionCooficial", parametros.getDenominacionCooficial())
                    .request(MediaType.APPLICATION_JSON).get();
            List<UnidadOrganicaResponse> unidades = response.readEntity(new GenericType<List<UnidadOrganicaResponse>>(){});
            List<UnidadOrganica> unidadesOrganicas = new ArrayList<>();
            if(unidades != null) {
                for(UnidadOrganicaResponse unidad : unidades) {
                    unidadesOrganicas.add(unidad.createUnidadOrganica());
                }
            }
            return unidadesOrganicas;
        } catch (Exception e) {
            throw new Dir3ErrorException("Se ha producido un error al obtener el histórico de unidades.");
        }
    }
}
