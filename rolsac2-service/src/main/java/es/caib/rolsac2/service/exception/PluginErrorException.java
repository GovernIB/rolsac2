package es.caib.rolsac2.service.exception;


import java.util.Locale;

/**
 * Cuando se produce una excepcion relacionada con plugins.
 *
 * @author Indra
 */
public class PluginErrorException extends ServiceException {


    String mensaje;

    Throwable causa;


    public PluginErrorException(String mensaje) {
        this.mensaje = mensaje;
    }

    public PluginErrorException(String mensaje, Throwable error) {
        this.mensaje = mensaje;
        this.causa = error;
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        if (this.causa == null) {
            return mensaje;
        } else {
            return mensaje + " \n " + causa.getLocalizedMessage();
        }
    }

}
