package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para los casos de uso de mantenimiento del proc/serv.
 *
 * @author Indra
 */
public interface ProcedimientoServiceFacade {

    /**
     * Crea un nuevo procedimiento a la base de datos relacionada con la unidad indicada.
     *
     * @param dto    el procedimiento
     * @param perfil El perfil
     * @return EL identificador del nuevo proc/serv
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(ProcedimientoBaseDTO dto, TypePerfiles perfil) throws RecursoNoEncontradoException;


    /**
     * Actualiza los datos de un procedimiento a la base de datos.
     *
     * @param dto nuevos datos del persoanl
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(ProcedimientoBaseDTO dto, ProcedimientoBaseDTO dtoAntiguo, TypePerfiles perfil) throws RecursoNoEncontradoException;


    /**
     * Borra una ficha de la bbdd
     *
     * @param id identificador del proc/serv a borrar
     * @throws RecursoNoEncontradoException si el proc/serv con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    @RolesAllowed({TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR, TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR,
            TypePerfiles.SUPER_ADMINISTRADOR_VALOR, TypePerfiles.GESTOR_VALOR, TypePerfiles.INFORMADOR_VALOR})
    void deleteWF(Long idWF) throws RecursoNoEncontradoException;

    /**
     * Retorna un procedimiento amb el proc/serv indicat per l'identificador.
     *
     * @param id identificador del proc/serv a cercar
     * @return un opcional amb les dades del proc/serv indicat o buid si no existeix.
     */
    ProcedimientoDTO findProcedimientoById(Long id);

    /**
     * Retorna un servicio amb el proc/serv indicat per l'identificador.
     *
     * @param id identificador del proc/serv a cercar
     * @return un opcional amb les dades del proc/serv indicat o buid si no existeix.
     */
    ServicioDTO findServicioById(Long id);

    /**
     * Devuelve una página con las fichas relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de fichas i la llista de fichas pel rang indicat.
     */
    Pagina<ProcedimientoGridDTO> findProcedimientosByFiltro(ProcedimientoFiltro filtro);

    /**
     * Devuelve una página con las fichas relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de fichas i la llista de fichas pel rang indicat.
     */
    Pagina<ServicioGridDTO> findServiciosByFiltro(ProcedimientoFiltro filtro);

    /**
     * Devuelve el total de fichas relacionado con los parámetros del filtro.
     *
     * @param filtro
     * @return
     */
    int countByFiltro(ProcedimientoFiltro filtro);

    Optional<ProcedimientoTramiteDTO> findProcedimientoTramiteById(Long id);

    List<ProcedimientoTramiteDTO> findProcTramitesByProcedimientoId(Long id);

    Long createProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException;

    void updateProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException;

    void deleteProcedimientoTramite(Long id) throws RecursoNoEncontradoException;

    void guardarFlujo(ProcedimientoBaseDTO data, TypeProcedimientoEstado estadoDestino, String mensajes, TypePerfiles perfil);

    void actualizarMensajes(Long idProc, String mensajes);

    Long getCodigoByWF(Long codigo, boolean valor);

    Long generarModificacion(Long codigoWFPub, String usuario, TypePerfiles perfil);

    /**
     * Devuelve las auditorias segun el id
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id);
}
