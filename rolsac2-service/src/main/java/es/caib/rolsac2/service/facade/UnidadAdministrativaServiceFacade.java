package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

import java.util.List;

/**
 * Servicio para los casos de uso de mantenimiento de UA.
 *
 * @author Indra
 */
public interface UnidadAdministrativaServiceFacade {

    /**
     * Obtener los hijos de una UA
     *
     * @param idUnitat identificador de la unidad
     * @return EL identificador del nuevo personal
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    List<UnidadAdministrativaDTO> getHijos(Long idUnitat, String idioma);


    /**
     * Devuelve la UA simple
     *
     * @param id
     * @param idioma
     * @param idEntidadRoot
     * @return
     */
    UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot);


    /**
     * Crea un nueva ua a la base de datos.
     *
     * @param dto datos de la UA
     * @return EL identificador de la nueva UA
     */
    Long create(UnidadAdministrativaDTO dto);

    /**
     * Actualiza los datos de una UA a la base de datos.
     *
     * @param dto nuevos datos del ua
     * @throws RecursoNoEncontradoException si el ua con el id no existe.
     */
    void update(UnidadAdministrativaDTO dto) throws RecursoNoEncontradoException;

    /**
     * Borra una ua de la bbdd
     *
     * @param id identificador dela ua a borrar
     * @throws RecursoNoEncontradoException si la ua con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Devuelve una ua indicat por el identificador.
     *
     * @param id identificador de la ua a buscar
     * @return un opcional con los datos de la ua indicador o vacio si no existe.
     */
    UnidadAdministrativaDTO findById(Long id);

    /**
     * Devuelve una página con el ua relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pagina con el numero total de ua y la lista de uas por el rango indicado.
     */
    Pagina<UnidadAdministrativaGridDTO> findByFiltro(UnidadAdministrativaFiltro filtro);

    /**
     * Devuelve el total de uas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(UnidadAdministrativaFiltro filtro);

    Long getCountHijos(Long parentId);

    List<TipoSexoDTO> findTipoSexo();

    Boolean checkIdentificador(String identificador);

    List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId);

    List<UnidadAdministrativaDTO> getHijosSimple(Long codigo, String idioma, UnidadAdministrativaDTO padre);

    /**
     * Devuelve un listado de UAs relacionadas a un usuario
     *
     * @param usuarioId
     * @return listado de UAs
     */

    /**
     * Retorna las UAs relacionadas a un usuario
     *
     * @param usuarioId
     * @return
     */
    List<UnidadAdministrativaDTO> getUnidadesAdministrativasByUsuario(Long usuarioId);

    /**
     * Obtiene el padre DIR3 de una UA
     *
     * @param codigo
     * @param idioma
     * @return
     */
    String obtenerPadreDir3(Long codigo, String idioma);


    /**
     * Verifica si existe un tipo de Sexo en alguna UA.
     *
     * @param codigoSex
     * @return
     */
    boolean existeTipoSexo(Long codigoSex);

    /**
     * Devuelve una lista recursiva de los hijos segun un codigo UA
     *
     * @param codigoUA
     * @return
     */
    List<Long> getListaHijosRecursivo(Long codigoUA);
}
