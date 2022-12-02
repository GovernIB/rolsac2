package es.caib.rolsac2.service.facade;


import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.ProcesoLogDTO;
import es.caib.rolsac2.service.model.ProcesoLogGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;

import java.util.List;

/**
 * Instancia service.
 *
 * @author Indra
 *
 */
public interface ProcesoLogServiceFacade {
  /**
   * Obtiene un filtro por codigo (Long).
   *
   * @param codigo Codigo pk del ProcesoLog
   * @return ProcesoLog que tenga el código
   */
  ProcesoLogDTO obtenerProcesoLogPorCodigo(Long codigo);

  /**
   * Lista Proceso según el filtro.
   *
   * @param filtro Filtro de Proceso
   * @return Lista de ProcesoGrid
   */
  List<ProcesoLogGridDTO> listar(ProcesoLogFiltro filtro);

  /**
   * Total de la Lista ProcesoLog según el filtro
   *
   * @param filtro Filtro de ProcesoLog
   * @return Total de elementos
   */
  Integer listarTotal(ProcesoLogFiltro filtro);

  List<ProcesoLogGridDTO> listar(final String idioma, final String tipo);

  List<ProcesoGridDTO> listarProceso(ProcesoFiltro filtro);

  Pagina<ProcesoLogGridDTO> findByFiltro(ProcesoLogFiltro filtro);

}
