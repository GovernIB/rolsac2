package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;

/**
 * Servicio para los casos de uso de mantenimiento de tipo de materia SIA
 *
 * @author Indra
 */
public interface TipoMateriaSIAServiceFacade {

  /**
   * Crea un nuevo tipo de materia SIA a la base de datos relacionada con la unidad indicada.
   *
   * @param dto datos del tipo de materia SIA
   * @return EL identificador del nuevo tipo de materia SIA
   * @throws RecursoNoEncontradoException si la unidad no existe
   */
  Long create(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un tipo de materia SIA a la base de datos.
   *
   * @param dto nuevos datos del tipo de materia SIA
   * @throws RecursoNoEncontradoException si el tipo de materia SIA con el id no existe.
   */
  void update(TipoMateriaSIADTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un tipo de materia SIA de la bbdd
   *
   * @param id identificador del tipo de materia SIA a borrar
   * @throws RecursoNoEncontradoException si el tipo de materia SIA con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional con el tipo de materia SIA indicado por el identificador
   *
   * @param id identificador del tipo de materia SIA a buscar
   * @return un opcional con los datos del tipo de materia SIA indicado o vacío si no existe
   */
  TipoMateriaSIADTO findById(Long id);

  /**
   * Devuelve una página con el tipo de materia SIA relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una página con el número total de tipo de materia SIA y la lista tipo de materia SIA con el rango indicado
   */
  Pagina<TipoMateriaSIAGridDTO> findByFiltro(TipoMateriaSIAFiltro filtro);

  /**
   * Devuelve si existe un tipo de materia SIA con el identificador indicado
   *
   * @param identificador identificador del tipo de materia SIA
   * @return true si existe un tipo de materia SIA con el identificador indicado, false en caso contrario
   */
  boolean existeIdentificador(String identificador);

}
