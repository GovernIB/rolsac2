package es.caib.rolsac2.commons.plugins.sia.api;

import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Plugin integración SIA.
 */
public interface IPluginSIA extends IPlugin {

    /**
     * Prefijo.
     */
    public static final String TRADUCCION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "sia.";

    /**
     * Enviar dato a sia para actualizar.
     *
     * @param envioSIA envio
     * @return resultado
     */
    ResultadoSIA enviarSIA(final EnvioSIA envioSIA, final boolean borrado, final boolean noactivo) throws IPluginSIAException;
}
