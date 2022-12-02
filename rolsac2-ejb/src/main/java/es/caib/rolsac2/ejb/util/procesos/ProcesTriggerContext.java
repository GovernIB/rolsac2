package es.caib.rolsac2.ejb.util.procesos;

import jakarta.enterprise.concurrent.LastExecution;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * Clase que implementa la interfaz LastExecution, necesaria para el cálculo del cron virtual.
 */
public final class ProcesTriggerContext implements LastExecution {

  /** last. */
  private Date last;



  /**
   * Devuelve el campo last.
   * 
   * @return el last.
   */
  public Date getLast() {
    return last;
  }

  /**
   * Metodo para settear el campo last.
   * 
   * @param pLast el last a settear.
   */
  public void setLast(final Date pLast) {
    last = pLast;
  }

  @Override
  public String getIdentityName() {
    return null;
  }

  @Override
  public Object getResult() {
    return null;
  }

  @Override
  public ZonedDateTime getScheduledStart(ZoneId zoneId) {
    return null;
  }

  @Override
  public ZonedDateTime getRunStart(ZoneId zoneId) {
    return null;
  }

  @Override
  public ZonedDateTime getRunEnd(ZoneId zoneId) {
    return ZonedDateTime.ofInstant(last.toInstant(), ZoneId.systemDefault());
  }
}
