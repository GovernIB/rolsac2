package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

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
     * Obtiene los hijos de una UA en un modelo más simple
     *
     * @param idUnitat
     * @param idioma
     * @return
     */
    List<UnidadAdministrativaGridDTO> getHijosGrid(Long idUnitat, String idioma);


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
    Long create(UnidadAdministrativaDTO dto, TypePerfiles perfil);

    /**
     * Actualiza los datos de una UA a la base de datos.
     *
     * @param dto nuevos datos del ua
     * @throws RecursoNoEncontradoException si el ua con el id no existe.
     */
    void update(UnidadAdministrativaDTO dto, UnidadAdministrativaDTO dtoAntiguo, TypePerfiles perfil) throws RecursoNoEncontradoException;

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

    UnidadAdministrativaGridDTO findGridById(Long id);

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

    Long countByEntidad(Long entidadId);

    Long getCountHijos(Long parentId);

    List<TipoSexoDTO> findTipoSexo();

    Boolean checkIdentificador(String identificador);

    List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId, String idioma);

    List<UnidadAdministrativaDTO> getHijosSimple(Long codigo, String idioma, UnidadAdministrativaDTO padre);

    List<AuditoriaGridDTO> findUaAuditoriasById(Long id);

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

    /**
     * Obtiene las unidades hijas asociadas a una unidad orgánica del organigrama de DIR3
     *
     * @param codigoDir3
     * @return
     */

    List<UnidadOrganicaDTO> obtenerUnidadesHijasDir3(String codigoDir3, Long idEntidad);


    /**
     * Obtiene las unidades hijas asociadas a una unidad orgánica del organigrama de ROLSAC2
     *
     * @param codigoDir3
     * @return
     */
    List<UnidadOrganicaDTO> obtenerUnidadesHijasRolsac(String codigoDir3, Long idEntidad);

    /**
     * Obtiene la unidad raíz del organigrama de DIR3
     *
     * @return
     */
    UnidadOrganicaDTO obtenerUnidadRaizDir3(Long idEntidad);

    /**
     * Obtiene la unidad raíz del organigrama de ROLSAC2
     *
     * @return
     */
    UnidadOrganicaDTO obtenerUnidadRaizRolsac(Long idEntidad);

    /**
     * Elimina el organigrama DIR3
     */
    void eliminarOrganigrama(Long idEntidad);

    /**
     * Crea el organigrama DIR3
     *
     * @param unidades
     */
    void crearOrganigrama(List<UnidadOrganicaDTO> unidades, Long idEntidad);

    /**
     * Obtiene el procedimientoSolrDTO
     *
     * @param codElemento
     * @return
     */
    ProcedimientoSolrDTO findDataIndexacionUAById(Long codElemento);

    Pagina<IndexacionDTO> getUAsParaIndexacion(Long idEntidad);

    void actualizarSolr(IndexacionDTO indexacionDTO, ResultadoAccion resultadoAccion);

    boolean isVisibleUA(UnidadAdministrativaDTO uaInstructor);

    String obtenerCodigoDIR3(Long codigoUA);

    EntidadRaizDTO getUaRaiz(Long codigoUA);

    Pagina<UnidadAdministrativaDTO> findByFiltroRest(UnidadAdministrativaFiltro fg);

    EntidadRaizDTO getUaRaizByProcedimiento(Long codProcedimiento);
}
