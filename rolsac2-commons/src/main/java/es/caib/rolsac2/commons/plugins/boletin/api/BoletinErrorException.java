package es.caib.rolsac2.commons.plugins.boletin.api;



/**
 * Cuando se produce una excepcion relacionada con boletín.
 *
 * @author Indra
 */
public class BoletinErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public BoletinErrorException() {
        super();
    }

    public BoletinErrorException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public BoletinErrorException(final String arg0) {
        super(arg0);
    }

    public BoletinErrorException(final Throwable arg0) {
        super(arg0);
    }
}
