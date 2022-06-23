package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.TipoSexoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSexoFiltro;

/**
 * Servicio para los casos de uso de mantenimiento de las tablas maestras.
 *
 * @author Indra
 */
public interface TipoSexoServiceFacade {

  /**
   * Crea un nuevo tipoSexo a la base de datos.
   *
   * @param dto datos del tipoSexo
   * @return identificador
   */
  Long create(TipoSexoDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un tipoSexo a la base de datos.
   *
   * @param dto nuevos datos del tipoSexo
   * @throws RecursoNoEncontradoException si el tipoSexo con el id no existe.
   */
  void update(TipoSexoDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un tipoSexo de la bbdd
   *
   * @param id identificador del tipoSexo a borrar
   * @throws RecursoNoEncontradoException si el tipoSexo con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional amb el tipoSexo indicat per l'identificador.
   *
   * @param id identificador del tipoSexo a cercar
   * @return un opcional amb les dades del tipoSexo indicat o buid si no existeix.
   */
  TipoSexoDTO findById(Long id);

  /**
   * Devuelve una página con el tipoSexo relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una pàgina amb el nombre total de tipoSexo i la llista de tipoSexo pel rang indicat.
   */
  Pagina<TipoSexoGridDTO> findByFiltro(TipoSexoFiltro filtro);

  /**
   * Devuelve si existe un tipo sexo con el identificador indicado
   *
   * @param identificador identificador del tipo sexo
   * @return true si existe un tipo sexo con el identificador indicado, false en caso contrario
   */
  boolean existeIdentificador(String identificador);
}
