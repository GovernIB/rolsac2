package es.caib.rolsac2.service.facade;

import java.util.List;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;

/**
 * Servicio para los casos de uso de mantenimiento de una plataforma de tramitación electrónica
 *
 * @author Indra
 */
public interface PlatTramitElectronicaServiceFacade {

  /**
   * Crea una nueva plataforma de tramitación electrónica a la base de datos relacionada con la unidad indicada.
   *
   * @param dto datos de la plataforma de tramitación electrónica
   * @return EL identificador de la nueva platraforma de tramitación electrónica
   * @throws RecursoNoEncontradoException si la unidad no existe
   */
  Long create(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException;

  /**
   * Actualiza los datos de una plataforma de tramitación electrónica a la base de datos.
   *
   * @param dto nuevos datos de la plataforma de tramitación electrónica
   * @throws RecursoNoEncontradoException si la plataforma de tramitación electrónica con el id no existe.
   */
  void update(PlatTramitElectronicaDTO dto) throws RecursoNoEncontradoException;

  /**
   * Borra una plataforma de tramitación electrónica de la bbdd
   *
   * @param id identificador de la plataforma de tramitación electrónica a borrar
   * @throws RecursoNoEncontradoException si la plataforma de tramitación electrónica con el id no existe.
   */
  void delete(Long id) throws RecursoNoEncontradoException;

  /**
   * Retorna un opcional con la plataforma de tramitación electrónica indicado por el identificador
   *
   * @param id identificador de la plataforma de tramitación electrónica a buscar
   * @return un opcional con los datos de la plataforma de tramitación electrónica o vacío si no existe
   */
  PlatTramitElectronicaDTO findById(Long id);

  /**
   * Retorna una lista con todas las plataformas de tramitación electrónica
   *
   * @return una lista con los datos de todas las plataformas de tramitación electrónica o una lista vacía si no hay
   *         ninguna
   */
  List<PlatTramitElectronicaDTO> findAll();
//
//  /**
//   * Devuelve una página con la plataforma de tramitación electrónica relacionada con los parámetros del filtro
//   *
//   * @param filtro filtro de la búsqueda
//   * @return una página con el número total de la plataforma de tramitación electrónica y la lista de las plataformas de
//   *         tramitación electrónica con el rango indicado
//   */
//  Pagina<PlatTramitElectronicaGridDTO> findByFiltro(PlatTramitElectronicaFiltro filtro);

}
