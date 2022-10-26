package es.caib.rolsac2.service.exception;

/**
 * Excepción producida en la parte de frontal.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FrontException extends RuntimeException {

	public FrontException() {
		super();
	}

	public FrontException(final String message) {
		super(message);
	}

	public FrontException(final String message, final Throwable arg0) {
		super(message, arg0);
	}

}
