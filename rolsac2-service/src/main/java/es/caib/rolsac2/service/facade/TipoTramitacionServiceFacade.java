package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;

/**
 * Servicio para los casos de uso de mantenimiento de tipo de tramitación
 *
 * @author Indra
 */
public interface TipoTramitacionServiceFacade {

  /**
   * Crea un nuevo tipo de tramitación a la base de datos relacionada con la unidad indicada.
   *
   * @param dto datos del tipo de tramitación
   * @return EL identificador del nuevo tipo de tramitación
   * @throws RecursoNoEncontradoException si la unidad no existe
   */
  Long create(TipoTramitacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un tipo de tramitación a la base de datos.
   *
   * @param dto nuevos datos del tipo de tramitación
   * @throws RecursoNoEncontradoException si el tipo de tramitación con el id no existe.
   */
  void update(TipoTramitacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un tipo de tramitación de la bbdd
   *
   * @param id identificador del tipo de tramitación a borrar
   * @throws RecursoNoEncontradoException si el tipo de tramitación con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional con el tipo de tramitación indicado por el identificador
   *
   * @param id identificador del tipo de tramitación a buscar
   * @return un opcional con los datos del tipo de tramitación indicado o vacío si no existe
   */
  TipoTramitacionDTO findById(Long id);

  /**
   * Devuelve una página con el tipo de tramitación relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una página con el número total de tipo de tramitación y la lista tipo de tramitación con el rango indicado
   */
  Pagina<TipoTramitacionGridDTO> findByFiltro(TipoTramitacionFiltro filtro);

}
