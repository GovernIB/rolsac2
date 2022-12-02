package es.caib.rolsac2.ejb.facade.procesos;

import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.ResultadoProcesoProgramado;
/**
 * Interfaz proceso programado.
 *
 * @author Indra
 *
 */
public interface ProcesoProgramadoFacade {

  /**
   * Retorna código proceso.
   *
   * @return código proceso
   */
  String getCodigoProceso();

  /**
   * Ejecuta proceso.
   *
   * @param params parámetros
   */
  ResultadoProcesoProgramado ejecutar(ListaPropiedades params);

}
