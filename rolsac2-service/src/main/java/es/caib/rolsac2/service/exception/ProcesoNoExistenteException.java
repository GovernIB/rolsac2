package es.caib.rolsac2.service.exception;

import java.util.Locale;

/**
 * Proceso no existe.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ProcesoNoExistenteException extends ServiceException {

  String idProceso;

  /**
   * Constructor ProcesoNoExistenteException.
   *
   * @param cause Causa
   */
  public ProcesoNoExistenteException(final String idProceso) {
    this.idProceso = idProceso;
  }

  /**
   * Constructor ProcesoNoExistenteException.
   *
   * @param cause Causa
   */
  public ProcesoNoExistenteException(final Long idProceso) {
    new ProcesoNoExistenteException(idProceso.toString());
  }


  @Override
  public String getLocalizedMessage(Locale locale) {
    return translate(locale, "error.procesoNoExistente", idProceso);
  }



}
