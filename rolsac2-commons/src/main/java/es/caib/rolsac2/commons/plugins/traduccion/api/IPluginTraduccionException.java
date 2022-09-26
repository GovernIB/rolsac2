package es.caib.rolsac2.commons.plugins.traduccion.api;

/**
 * Excepción al consultar plugin de traducción.
 */
public class IPluginTraduccionException extends Exception{


    public IPluginTraduccionException(String message) {
        super(message);
    }

    public IPluginTraduccionException(String message, Throwable cause) {
        super(message, cause);
    }
}
