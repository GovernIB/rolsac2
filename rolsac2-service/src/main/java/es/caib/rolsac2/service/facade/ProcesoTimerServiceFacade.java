package es.caib.rolsac2.service.facade;

/**
 * Instancia service.
 *
 * @author Indra
 *
 */
public interface ProcesoTimerServiceFacade {

  /**
   * Iniciar el timer de los procesos
   */
  void initTimer(String idInstancia);

  /**
   * Lógica para el procesado
   */
  void procesar();

  /**
   * Método utilizado para el procesado manual de los procesos
   */
  void procesadoManual(String proceso);


}
