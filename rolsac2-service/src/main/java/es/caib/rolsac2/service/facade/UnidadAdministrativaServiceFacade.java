package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * Encuentra la ua entidad root
     *
     * @param idEntidad
     * @return
     */
    UnidadAdministrativaDTO findRootEntidad(Long idEntidad);


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
    List<UnidadAdministrativaDTO> getUnidadesAdministrativasByUsuario(Long usuarioId, boolean simplificado);

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
    List<Long> listarHijos(Long codigoUA);

    /**
     * Devuelve una lista recursiva de los padres segun un codigo UA
     *
     * @param codigoUA
     * @return
     */
    List<Long> listarPadres(Long codigoUA);

    /**
     * Obtiene las unidades hijas asociadas a una unidad orgánica del organigrama de DIR3
     *
     * @param codigoDir3
     * @return
     */

    List<UnidadOrganicaDTO> obtenerUnidadesHijasDir3(String codigoDir3, Long idEntidad, String idioma);


    /**
     * Obtiene las unidades hijas asociadas a una unidad orgánica del organigrama de ROLSAC2
     *
     * @param codigoDir3
     * @return
     */
    List<UnidadOrganicaDTO> obtenerUnidadesHijasRolsac(String codigoDir3, Long idEntidad, String idioma);

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
     * Retorna un listado con las unidades orgánicas asociados a una entidad.
     *
     * @param idEntidad identificadorde la entidad asociada
     * @return un listado de unidades orgánicas
     */
    List<UnidadOrganicaDTO> findUoByEntidad(Long idEntidad);

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

    /**
     * Evolucion básica de una UA.
     *
     * @param codigoUA
     * @param fechaBaja
     * @param nombreNuevo
     * @param entidad
     */
    void evolucionBasica(Long codigoUA, Date fechaBaja, Literal nombreNuevo, NormativaDTO normativa, EntidadDTO entidad, TypePerfiles perfil, String usuario, Integer version);

    /**
     * Devuelve la normativa de baja de una UA
     *
     * @param codigoUA
     * @return
     */
    NormativaDTO getNormativaBaja(Long codigoUA);

    /**
     * Evolución dependencia de una UA.
     *
     * @param codigoUA
     * @param codigoUAPadre
     * @param entidad
     */
    void evolucionDependencia(Long codigoUA, Long codigoUAPadre, EntidadDTO entidad, TypePerfiles perfil, String usuario, Integer version);

    /**
     * Comprueba si el padreNuevo cuelga del padreAntiguo
     *
     * @param padreAntiguo
     * @param padreNuevo
     * @return
     */
    boolean checkCuelga(UnidadAdministrativaDTO padreAntiguo, UnidadAdministrativaDTO padreNuevo);

    /**
     * Obtiene los usuarios de las uas
     *
     * @param uas
     * @return
     */
    List<UsuarioGridDTO> getUsuariosByUas(List<Long> uas);

    /**
     * Obtiene las normativas de las uas
     *
     * @param uas
     * @return
     */
    List<NormativaDTO> getNormativaByUas(List<Long> uas, String idioma);

    /**
     * Obtiene las normativas de la ua
     *
     * @param ua
     * @param idioma
     * @return
     */
    List<NormativaDTO> getNormativaByUa(Long ua, String idioma);

    /**
     * Obtiene procedimientos de la ua
     *
     * @param uas     Indica las uas
     * @param tipo    Indica el tipo de procedimiento
     * @param idioma  Indica el idioma
     * @param visible si es null no se filtra por visible
     * @return
     */
    List<ProcedimientoBaseDTO> getProcedimientosByUas(List<Long> uas, String tipo, String idioma, Boolean visible);

    /**
     * Obtiene procedimientos de la ua
     *
     * @param ua
     * @param tipo
     * @param idioma
     * @return
     */
    List<ProcedimientoBaseDTO> getProcedimientosByUa(Long ua, String tipo, String idioma, Boolean visible);

    /**
     * Obtiene el total de procedimientos de la ua
     *
     * @param ua
     * @param tipo
     * @param idioma
     * @param visible
     * @return
     */
    Long getProcedimientosTotalByUas(Long ua, String tipo, String idioma, Boolean visible);


    /**
     * Obtiene los temas segun las uas
     *
     * @param uas
     * @return
     */
    List<TemaGridDTO> getTemasByUas(List<Long> uas);

    /**
     * Evolución fusion de una UA.
     *
     * @param selectedUnidades Las unidades a fusionar
     * @param normativa        La normativa de baja seleccionada
     * @param fechaBaja        La fecha de baja seleccionada
     * @param uaFusion         La UA fusionada
     */
    void evolucionFusion(List<UnidadAdministrativaGridDTO> selectedUnidades, NormativaDTO normativa, Date fechaBaja, UnidadAdministrativaDTO uaFusion, TypePerfiles perfil, String usuario);


    /***
     * Devuelve una lista de codigos dir3 asociado a cada código.
     * @param codigos
     * @return
     */
    Map<Long, String> obtenerCodigosDIR3(List<Long> codigos);

    /**
     * Evolución de una UA (uaOrigen) dividido en varias ua (uasDestino)
     *
     * @param evolucionDivision
     * @param uaOrigen
     * @param uasDestino
     * @param fechaBaja
     * @param normativa
     * @param procedimientos
     * @param servicios
     * @param normativas
     * @param entidad
     * @param perfil
     * @param usuario
     * @param noMigrarReserva
     * @param comportamientoTodos
     */
    void evolucionDivisionCompetencias(boolean evolucionDivision, Long uaOrigen, List<UnidadAdministrativaDTO> uasDestino, Date fechaBaja, NormativaDTO normativa, List<ProcedimientoCompletoDTO> procedimientos, List<ProcedimientoCompletoDTO> servicios, List<NormativaGridDTO> normativas, EntidadDTO entidad, TypePerfiles perfil, String usuario, boolean noMigrarReserva, boolean comportamientoTodos);

    /**
     * Devuelve la lista de UAs segun el filtro y los datos de exportar.
     *
     * @param filtro
     * @param exportarDatos
     * @return
     */
    List<UnidadAdministrativaDTO> findExportByFiltro(UnidadAdministrativaFiltro filtro, ExportarDatos exportarDatos);

    /**
     * Devuelve la lista de UA raiz de una entidad
     *
     * @param codEntidad Código de la entidad
     * @return UA raiz
     */
    UnidadAdministrativaGridDTO getUaRaizEntidad(Long codEntidad);

    /**
     * Obtiene la ua raiz de una entidad
     *
     * @param idEntidad
     * @return
     */
    UnidadAdministrativaDTO findUaRaizByEntidad(Long idEntidad);
}
