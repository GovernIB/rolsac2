package es.caib.rolsac2.commons.plugins.dir3.api;



/**
 * Cuando se produce una excepcion relacionada con boletín.
 *
 * @author Indra
 */
public class Dir3ErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public Dir3ErrorException() {
        super();
    }

    public Dir3ErrorException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public Dir3ErrorException(final String arg0) {
        super(arg0);
    }

    public Dir3ErrorException(final Throwable arg0) {
        super(arg0);
    }
}
