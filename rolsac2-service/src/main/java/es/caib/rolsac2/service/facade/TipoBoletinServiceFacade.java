package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

public interface TipoBoletinServiceFacade {

  /**
   * Crea un nuevo TipoBoletin a la base de datos.
   *
   * @param dto datos del TipoBoletin
   * @return identificador
   */
  Long create(TipoBoletinDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un TipoBoletin a la base de datos.
   *
   * @param dto nuevos datos del TipoBoletin
   * @throws RecursoNoEncontradoException si el TipoBoletin con el id no existe.
   */
  void update(TipoBoletinDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un TipoBoletin de la bbdd
   *
   * @param id identificador del TipoBoletin a borrar
   * @throws RecursoNoEncontradoException si el TipoBoletin con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional amb el TipoBoletin indicat per l'identificador.
   *
   * @param id identificador del TipoBoletin a cercar
   * @return un opcional amb les dades del TipoBoletin indicat o buid si no existeix.
   */
  TipoBoletinDTO findById(Long id);

  /**
   * Devuelve una página con el TipoBoletin relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una pàgina amb el nombre total de TipoBoletin i la llista de TipoBoletin pel rang indicat.
   */
  Pagina<TipoBoletinGridDTO> findByFiltro(TipoBoletinFiltro filtro);
}
