package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.TipoAfectacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;

/**
 * Servicio para los casos de uso de mantenimiento de tipo de afectación
 *
 * @author Indra
 */
public interface TipoAfectacionServiceFacade {

  /**
   * Crea un nuevo tipo de afectación a la base de datos relacionada con la unidad indicada.
   *
   * @param dto datos del tipo de afectación
   * @return EL identificador del nuevo tipo de afectación
   * @throws RecursoNoEncontradoException si la unidad no existe
   */
  Long create(TipoAfectacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un tipo de afectación a la base de datos.
   *
   * @param dto nuevos datos del tipo de afectación
   * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
   */
  void update(TipoAfectacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un tipo de afectación de la bbdd
   *
   * @param id identificador del tipo de afectación a borrar
   * @throws RecursoNoEncontradoException si el tipo de afectación con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional con el tipo de afectación indicado por el identificador
   *
   * @param id identificador del tipo de afectación a buscar
   * @return un opcional con los datos del tipo de afectación indicado o vacío si no existe
   */
  TipoAfectacionDTO findById(Long id);

  /**
   * Devuelve una página con el tipo de afectación relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una página con el número total de tipo de afectación y la lista tipo de afectación con el rango indicado
   */
  Pagina<TipoAfectacionGridDTO> findByFiltro(TipoAfectacionFiltro filtro);

  /**
   * Devuelve si existe un tipo de afectación con el identificador indicado
   *
   * @param identificador identificador del tipo de afectación
   * @return true si existe un tipo de afectación con el identificador indicado, false en caso contrario
   */
  boolean existeIdentificador(String identificador);

}
