package es.caib.rolsac2.commons.plugins.traduccion.mock;

import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccion;
import es.caib.rolsac2.commons.plugins.traduccion.api.IPluginTraduccionException;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.Map;
import java.util.Properties;

public class PluginTraduccionMock extends AbstractPluginProperties implements IPluginTraduccion {

    /**
     * Prefijo de la implementacion.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "translatemock.";

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public PluginTraduccionMock(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public String traducir(String tipoEntrada, String textoEntrada, String idiomaEntrada, String idiomaSalida, Map<String, String> opciones) throws IPluginTraduccionException {
        // En mock realizamos echo de la entrada
        return textoEntrada;
    }
}
