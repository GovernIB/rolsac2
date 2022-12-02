package es.caib.rolsac2.ejb.util.procesos;

import jakarta.enterprise.concurrent.CronTrigger;

import java.time.ZoneId;
import java.util.Date;

/**
 * The Class InterpreteQuartz.
 */
public final class InterpreteQuartz {

  /** expresion. */
  private String expresion;

  /** fecha proxima ejecucion. */
  private Date fechaProximaEjecucion;

  /** fecha ultima ejecucion. */
  private Date fechaUltimaEjecucion;

  /**
   * Recupera el campo fecha ultima ejecucion.
   *
   * @return the fechaUltimaEjecucion
   */
  public Date getFechaUltimaEjecucion() {
    return fechaUltimaEjecucion;
  }

  /**
   * Método para setear el campo fecha ultima ejecucion.
   *
   * @param pFechaUltimaEjecucion the fechaUltimaEjecucion to set
   */
  public void setFechaUltimaEjecucion(final Date pFechaUltimaEjecucion) {
    this.fechaUltimaEjecucion = pFechaUltimaEjecucion;
  }

  /**
   * Recupera el campo fecha proxima ejecucion.
   *
   * @return the fechaProximaEjecucion
   */
  public Date getFechaProximaEjecucion() {
    return fechaProximaEjecucion;
  }

  /**
   * Instancia una nueva interprete quartz.
   */
  public InterpreteQuartz() {
    super();

  }

  /**
   * Checks if is activar.
   *
   * @return true, if is activar
   */

  public boolean isActivar() {
    final CronTrigger ct = new CronTrigger(this.expresion, ZoneId.systemDefault());
    final ProcesTriggerContext pc= new ProcesTriggerContext();
    pc.setLast(this.fechaUltimaEjecucion);
    fechaProximaEjecucion = ct.getNextRunTime(pc, pc.getRunEnd());
    return fechaProximaEjecucion.before(new Date());

  }
  /**
   * Recupera el campo expresion.
   *
   * @return the expresion
   */
  public String getExpresion() {
    return expresion;
  }

  /**
   * Método para setear el campo expresion.
   *
   * @param pExpresion the expresion to set
   */
  public void setExpresion(final String pExpresion) {
    this.expresion = pExpresion;
  }


}
