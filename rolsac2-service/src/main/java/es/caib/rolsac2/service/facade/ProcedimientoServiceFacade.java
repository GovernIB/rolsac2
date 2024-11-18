package es.caib.rolsac2.service.facade;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.DataIndexacion;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.IndexFile;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.service.exception.DatoDuplicadoException;
import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.auditoria.AuditoriaGridDTO;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;

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
     * @param ruta   La ruta de ficheros
     * @return EL identificador del nuevo proc/serv
     * @throws RecursoNoEncontradoException si la unidad no existe
     */
    Long create(ProcedimientoBaseDTO dto, TypePerfiles perfil, String ruta) throws RecursoNoEncontradoException;


    /**
     * Actualiza los datos de un procedimiento a la base de datos.
     *
     * @param dto        nuevos datos
     * @param dtoAntiguo datos antiguos
     * @param perfil     El perfil
     * @param idEntidad  Id de la entidad
     * @param ruta       La ruta de ficheros
     * @throws RecursoNoEncontradoException si el persoanl con el id no existe.
     */
    void update(ProcedimientoBaseDTO dto, ProcedimientoBaseDTO dtoAntiguo, TypePerfiles perfil, Long idEntidad, String ruta) throws RecursoNoEncontradoException;


    /**
     * Borra un procedimiento de la bbdd
     *
     * @param id identificador del proc/serv a borrar
     * @throws RecursoNoEncontradoException si el proc/serv con el id no existe.
     */
    void delete(Long id) throws RecursoNoEncontradoException;

    /**
     * Borra por wf.
     *
     * @param idWF
     * @throws RecursoNoEncontradoException
     */
    void deleteWF(Long idWF) throws RecursoNoEncontradoException;


    void deleteProcedimientoCompleto(Long id);

    /**
     * Retorna un procedimiento amb el proc/serv indicat per l'identificador.
     *
     * @param id identificador del proc/serv a cercar
     * @return un opcional amb les dades del proc/serv indicat o buid si no existeix.
     */
    ProcedimientoDTO findProcedimientoById(Long id);

    /**
     * Obtiene el dato procedimiento de indexacion.
     *
     * @param codigoWF
     * @return
     */
    ProcedimientoSolrDTO findDataIndexacionProcById(Long codigoWF);

    /**
     * Retorna un servicio amb el proc/serv indicat per l'identificador.
     *
     * @param id identificador del proc/serv a cercar
     * @return un opcional amb les dades del proc/serv indicat o buid si no existeix.
     */
    ServicioDTO findServicioById(Long id);

    /**
     * Obtiene el dato servicio de indexacion.
     *
     * @param codigoWF
     * @return
     */
    ProcedimientoSolrDTO findDataIndexacionServById(Long codigoWF);

    /**
     * Devuelve una página con las fichas relacionado con los parámetros del filtro
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de fichas i la llista de fichas pel rang indicat.
     */
    Pagina<ProcedimientoGridDTO> findProcedimientosByFiltro(ProcedimientoFiltro filtro);

    /**
     * Devuelve una página con las fichas relacionado con los parámetros del filtro rest
     *
     * @param filtro filtro de la búsqueda
     * @return una pàgina amb el nombre total de fichas i la llista de fichas pel rang indicat.
     */
    Pagina<ProcedimientoBaseDTO> findProcedimientosByFiltroRest(ProcedimientoFiltro filtro);

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
    Long countByFiltro(ProcedimientoFiltro filtro);

    Long countByEntidad(Long entidadId);

    Long countServicioByEntidad(Long entidadId);

    Optional<ProcedimientoTramiteDTO> findProcedimientoTramiteById(Long id);

    List<ProcedimientoTramiteDTO> findProcTramitesByProcedimientoId(Long id);

    Long createProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException, DatoDuplicadoException;

    void updateProcedimientoTramite(ProcedimientoTramiteDTO dto) throws RecursoNoEncontradoException;

    void deleteProcedimientoTramite(Long id) throws RecursoNoEncontradoException;

    void guardarFlujo(ProcedimientoBaseDTO data, TypeProcedimientoEstado estadoDestino, String mensajes, TypePerfiles perfil, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor, Long idEntidad, String ruta);

    void actualizarMensajes(Long idProc, String mensajes, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor);

    Long getCodigoByWF(Long codigo, boolean valor);

    Long generarModificacion(Long codigoWFPub, String usuario, TypePerfiles perfil, String ruta);

    /**
     * Devuelve las auditorias segun el id
     *
     * @param id
     * @return
     */
    List<AuditoriaGridDTO> findProcedimientoAuditoriasById(Long id);

    /**
     * Para marcar datos de solr.
     *
     * @param proc
     */
    void actualizarSolr(IndexacionDTO proc, ResultadoAccion resultadoAccion);

    void actualizarSIA(IndexacionSIADTO siadto, ResultadoSIA resultadoAccion);

    /**
     * Obtiene el codigo publicado (tiene WF = publicado)
     *
     * @param codProc
     * @return
     */
    Long getCodigoPublicado(Long codProc);


    DataIndexacion findDataIndexacionTram(ProcedimientoTramiteDTO tramite, ProcedimientoDTO procedimientoDTO, PathUA pathUA);

    /**
     * Obtiene los mensajes de un procedimiento
     *
     * @param codigo
     * @return
     */
    String getMensajesByCodigo(Long codigo);

    ProcedimientoDTO findProcedimientoByCodigo(Long codigo);

    ServicioDTO findServicioByCodigo(Long idEntidad);

    Pagina<IndexacionDTO> getProcedimientosParaIndexacion(boolean isTipoProcedimiento, Long idEntidad);

    Pagina<IndexacionSIADTO> getProcedimientosParaIndexacionSIA(Long idEntidad);

    IndexFile findDataIndexacionProcDoc(ProcedimientoDTO procedimientoDTO, ProcedimientoDocumentoDTO doc, DocumentoTraduccion documentoTraduccion, PathUA pathUA, String ruta);

    IndexFile findDataIndexacionTramDoc(ProcedimientoTramiteDTO tramite, ProcedimientoDTO procedimientoDTO, ProcedimientoDocumentoDTO doc, DocumentoTraduccion fichero, PathUA pathUA, String ruta);

    Pagina<ProcedimientoDocumentoDTO> findProcedimientoDocumentoByFiltroRest(ProcedimientoDocumentoFiltro filtro);

    Pagina<ProcedimientoTramiteDTO> findProcedimientoTramiteByFiltroRest(ProcedimientoTramiteFiltro filtro);

    ProcedimientoBaseDTO convertirDTO(Object obj);

    String getNombreProcedimientoServicio(Long codigo);

    String getEnlaceTelematicoByServicio(ProcedimientoFiltro filtro);


    String getEnlaceTelematicoByTramite(ProcedimientoTramiteFiltro filtro);

    ProcedimientoBaseDTO findProcedimientoBaseById(Long codigo);

    List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByProc(Long codigo, String estadoWF);

    List<ProcedimientoDocumentoDTO> getDocumentosByProc(Long codigo, String estadoWF);

    List<ProcedimientoDocumentoDTO> getDocumentosLOPDByProc(Long codigo, String estadoWF);

    List<NormativaDTO> getNormativasByProc(Long codigo, String estadoWF);

    List<TemaDTO> getTemasByProc(Long codigo, String estadoWF);

    List<ProcedimientoDocumentoDTO> getDocumentosByTram(Long codigo);

    List<ProcedimientoDocumentoDTO> getModelosByTram(Long codigo);

    Long getCodigoModificacion(Long codProc);

    List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByCodProcWF(Long codigo);

    List<ProcedimientoDocumentoDTO> getDocumentosLOPDByCodProcWF(Long codigo);

    List<ProcedimientoDocumentoDTO> getDocumentosByCodProcWF(Long codigo);

    List<TemaDTO> getTemasByCodProcWF(Long codigo);

    List<NormativaDTO> getNormativasByCodProcWF(Long codigo);

    String obtenerIdiomaEntidad(Long codigo);

    List<ProcedimientoCompletoDTO> findExportByFiltro(ProcedimientoFiltro filtro, ExportarDatos exportarDatos);

    /**
     * Indica si tiene el WF indicado
     *
     * @param id     El id del proc/serv
     * @param tipoWF true es definitivo, false es en modificacion
     * @return
     */
    boolean tieneWF(Long id, boolean tipoWF);

    /**
     * Clona un procedimiento.
     *
     * @param idLong
     * @param wfSeleccionado
     * @param usuario
     * @return
     */
    Long clonarProcedimiento(Long idLong, boolean wfSeleccionado, String usuario, String ruta);
}
