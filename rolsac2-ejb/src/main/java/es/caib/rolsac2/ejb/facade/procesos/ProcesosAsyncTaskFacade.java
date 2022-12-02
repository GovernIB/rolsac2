package es.caib.rolsac2.ejb.facade.procesos;

/**
 * Component con funcionalidades para ejecución procesos (asincrono).
 *
 * @author Indra
 *
 */
public interface ProcesosAsyncTaskFacade {

  /**
   * Ejecutar proceso.
   *
   * @param idProceso identificador proceso
   */
  void ejecutarProceso(String idProceso);

}
