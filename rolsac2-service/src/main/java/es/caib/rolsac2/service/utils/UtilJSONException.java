package es.caib.rolsac2.service.utils;

/**
 * Excepcion util JSON.
 */
@SuppressWarnings("serial")
public final class UtilJSONException extends Exception {

  /**
   * Constructor ValidacionTipoException.
   *
   * @param message message
   * @param cause cause
   */
  public UtilJSONException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor ValidacionException.
   *
   * @param message message
   */
  public UtilJSONException(final String message) {
    super(message);
  }
}
