package es.caib.rolsac2.commons.plugins.traduccion.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import java.util.Map;

/**
 * Plugin traducción.
 */
public interface IPluginTraduccion extends IPlugin{

    /** Prefijo. */
    public static final String TRADUCCION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "translate.";

    /**
     * Realiza traducción.
     * @param tipoEntrada Tipo entrada
     * @param textoEntrada Texto entrada
     * @param idiomaEntrada Idioma entrada
     * @param idiomaSalida Idioma salida
     * @param opciones Opciones traducción (depende del plugin)
     * @return Traducción
     */
    public String traducir(final String tipoEntrada, final String textoEntrada, final String idiomaEntrada, final String idiomaSalida, final Map<String, String> opciones) throws IPluginTraduccionException;

}
