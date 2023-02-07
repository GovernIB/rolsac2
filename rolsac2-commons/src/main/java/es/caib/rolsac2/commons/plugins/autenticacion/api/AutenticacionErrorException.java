package es.caib.rolsac2.commons.plugins.autenticacion.api;



/**
 * Cuando se produce una excepcion relacionada con boletín.
 *
 * @author Indra
 */
public class AutenticacionErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public AutenticacionErrorException() {
        super();
    }

    public AutenticacionErrorException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public AutenticacionErrorException(final String arg0) {
        super(arg0);
    }

    public AutenticacionErrorException(final Throwable arg0) {
        super(arg0);
    }
}
