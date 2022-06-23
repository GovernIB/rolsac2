package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.TipoLegitimacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoLegitimacionFiltro;

public interface TipoLegitimacionServiceFacade {

  /**
   * Crea un nuevo tipoLegitimacion a la base de datos.
   *
   * @param dto datos del tipoLegitimacion
   * @return identificador
   */
  Long create(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de un tipoLegitimacion a la base de datos.
   *
   * @param dto nuevos datos del tipoLegitimacion
   * @throws RecursoNoEncontradoException si el tipoLegitimacion con el id no existe.
   */
  void update(TipoLegitimacionDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra un tipoLegitimacion de la bbdd
   *
   * @param id identificador del tipoLegitimacion a borrar
   * @throws RecursoNoEncontradoException si el tipoLegitimacion con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional amb el tipoLegitimacion indicat per l'identificador.
   *
   * @param id identificador del tipoLegitimacion a cercar
   * @return un opcional amb les dades del tipoLegitimacion indicat o buid si no existeix.
   */
  TipoLegitimacionDTO findById(Long id);

  /**
   * Devuelve una página con el tipoLegitimacion relacionado con los parámetros del filtro
   *
   * @param filtro filtro de la búsqueda
   * @return una pàgina amb el nombre total de tipoLegitimacion i la llista de tipoLegitimacion pel rang indicat.
   */
  Pagina<TipoLegitimacionGridDTO> findByFiltro(TipoLegitimacionFiltro filtro);

  /**
   * Devuelve si existe un tipo sexo con el identificador indicado
   *
   * @param identificador identificador del tipo legit
   * @return true si existe un tipo legit con el identificador indicado, false en caso contrario
   */
  boolean existeIdentificador(String identificador);
}
