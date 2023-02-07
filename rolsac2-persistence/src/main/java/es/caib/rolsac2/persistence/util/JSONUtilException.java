package es.caib.rolsac2.persistence.util;

/**
 * Excepcion util JSON.
 */
@SuppressWarnings("serial")
public final class JSONUtilException extends Exception {

    /**
     * Constructor ValidacionTipoException.
     *
     * @param message message
     * @param cause   cause
     */
    public JSONUtilException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor ValidacionException.
     *
     * @param message message
     */
    public JSONUtilException(final String message) {
        super(message);
    }
}
