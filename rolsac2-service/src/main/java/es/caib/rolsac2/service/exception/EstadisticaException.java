package es.caib.rolsac2.service.exception;

import java.util.Locale;

/**
 * Proceso no existe.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadisticaException extends ServiceException {

  private String mensaje;

  private Throwable error;

  /**
   * Constructor.
   *
   * @param message Causa
   */
  public EstadisticaException(final String message) {

    this.mensaje = message;
  }

  @Override
  public String getLocalizedMessage(Locale locale) {
    return this.mensaje;
  }

}
