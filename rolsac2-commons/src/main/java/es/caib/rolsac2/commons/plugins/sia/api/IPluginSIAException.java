package es.caib.rolsac2.commons.plugins.sia.api;

/**
 * Excepción plugin SIA.
 */
public class IPluginSIAException extends Exception{


    public IPluginSIAException(String message) {
        super(message);
    }

    public IPluginSIAException(String message, Throwable cause) {
        super(message, cause);
    }
}
