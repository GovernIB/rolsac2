package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.TipoViaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoViaFiltro;

public interface TipoViaServiceFacade {

  /**
   * Crea un nuevo TipoVia a la base de datos.
   *
   * @param dto datos del TipoVia
   * @return identificador
   */
  Long create(TipoViaDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un TipoVia a la base de datos.
   *
   * @param dto nuevos datos del TipoVia
   * @throws RecursoNoEncontradoException si el TipoVia con el id no existe.
   */
  void update(TipoViaDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un TipoVia de la bbdd
   *
   * @param id identificador del TipoVia a borrar
   * @throws RecursoNoEncontradoException si el TipoVia con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional amb el TipoVia indicat per l'identificador.
   *
   * @param id identificador del TipoVia a cercar
   * @return un opcional amb les dades del TipoVia indicat o buid si no existeix.
   */
  TipoViaDTO findById(Long id);

  /**
   * Devuelve una página con el TipoVia relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una pàgina amb el nombre total de TipoVia i la llista de TipoVia pel rang indicat.
   */
  Pagina<TipoViaGridDTO> findByFiltro(TipoViaFiltro filtro);

  /**
   * Devuelve si existe un tipo via con el identificador indicado
   *
   * @param identificador identificador del tipo via
   * @return true si existe un tipo via con el identificador indicado, false en caso contrario
   */
  boolean existeIdentificador(String identificador);
}
