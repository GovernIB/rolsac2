package es.caib.rolsac2.commons.plugins.boletin.mock;

import es.caib.rolsac2.commons.plugins.boletin.api.IPluginBoletin;
import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PluginBoletinMock extends AbstractPluginProperties implements IPluginBoletin {

    /**
     * Prefijo de la implementacion.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "boletinmock.";

    public static final String TIPO_BOLETIN_PROPIEDAD = "tipoBoletin";

    /**
     * Constructor.
     *
     * @param prefijoPropiedades Prefijo props.
     * @param properties         Propiedades plugins.
     */
    public PluginBoletinMock(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public List<Edicto> listar(String numeroboletin, String numeroregistro, String fecha, Boolean saltarseCertificado) {
        List<Edicto> lista = new ArrayList<>();
        return lista;
    }

    @Override
    public Long obtenerBoletinPlugin() {
        return Long.valueOf(this.getProperty(TIPO_BOLETIN_PROPIEDAD));
    }

}
