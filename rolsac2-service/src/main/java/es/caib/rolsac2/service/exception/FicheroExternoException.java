package es.caib.rolsac2.service.exception;


import java.util.Locale;

/**
 * Cuando se produce una excepcion de auditoria.
 *
 * @author Indra
 */
public class FicheroExternoException extends ServiceException {


    String mensaje;

    Throwable causa;


    public FicheroExternoException(String mensaje) {
        this.mensaje = mensaje;
    }

    public FicheroExternoException(String mensaje, Throwable error) {
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
