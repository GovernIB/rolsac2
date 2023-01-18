package es.caib.rolsac2.service.exception;


import java.util.Locale;

/**
 * Cuando se produce una excepcion de auditoria.
 *
 * @author Indra
 */
public class AuditoriaException extends ServiceException {

  Throwable causa;

  public AuditoriaException(final Throwable cause) {
    this.causa = cause;
  }


  @Override
  public String getLocalizedMessage(Locale locale) {
    return causa.getLocalizedMessage();
  }

}
