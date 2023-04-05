package es.caib.rolsac2.commons.plugins.sia.mock;

import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIA;
import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIAException;
import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.Properties;

public class PluginSIAMock extends AbstractPluginProperties implements IPluginSIA {

    /**
     * Prefijo de la implementacion.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "siamock.";

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public PluginSIAMock(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public ResultadoSIA enviarSIA(EnvioSIA envioSIA, final boolean borrado, final boolean noactivo) throws IPluginSIAException {
        ResultadoSIA resultado = new ResultadoSIA();
        resultado.setResultado(ResultadoSIA.RESULTADO_OK);
        resultado.setCodSIA("SIA1");
        resultado.setEstadoSIA("ALTA");
        return resultado;
    }
}
