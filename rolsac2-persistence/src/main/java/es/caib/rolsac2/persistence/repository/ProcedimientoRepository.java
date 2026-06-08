package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.model.JListaDocumentos;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypePerfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoRepository extends CrudRepository<JProcedimiento, Long> {

    void mergePublicoObjetivoProcWF(Long codigoWF, List<TipoPublicoObjetivoEntidadGridDTO> listaNuevos);

    void mergeCategoriasPDUProcWF(Long codigoWF, List<CategoriaPDUGridDTO> categorias);

    void mergeNormativaProcWF(Long codigoWF, List<NormativaGridDTO> listaNuevos);

    void mergeTramitesProcWF(Long codigoWF, List<ProcedimientoTramiteDTO> lista, String ruta);

    void mergeTasasTramite(Long codigoWF, Long codigoTramite, List<TasaProcedimientoDTO> tasas);
    
    void mergeTasaServicio(Long codigoWF, TasaServicioDTO tasa);

    List<TasaProcedimientoDTO> getTasasByListaTasas(Long codigoTramite);

    void updateWF(JProcedimientoWorkflow jProcWF);

    Optional<JProcedimiento> findById(String id);

    List<ProcedimientoGridDTO> findProcedimientosPagedByFiltro(ProcedimientoFiltro filtro);

    List<ProcedimientoCompletoDTO> findProcedimientosPagedByFiltroExport(ProcedimientoFiltro filtro);

    List<ProcedimientoBaseDTO> findProcedimientosPagedByFiltroRest(ProcedimientoFiltro filtro, boolean ignorarDocs);

    List<ServicioGridDTO> findServiciosPagedByFiltro(ProcedimientoFiltro filtro);

    long countByFiltro(ProcedimientoFiltro filtro);

    Long countByEntidad(Long entidadId);

    Long countByUa(Long uaId);

    Long countAll();

    Long countServicioByEntidad(Long entidadId);

    Long countServicioByUa(Long uaId);

    Long countAllServicio();

    Long countProcEstadoByUa(Long uaId, String estado);

    Long countServEstadoByUa(Long uaId, String estado);

    Boolean checkExisteProcedimiento(Long idProc);

    JProcedimientoWorkflow getWF(Long id, boolean procedimientoEnmodificacion);

    Long createWF(JProcedimientoWorkflow jProcWF);

    boolean existeProcedimientoConMateria(Long materiaSIA);

    boolean existeProcedimientoConPublicoObjetivo(Long codigoPub);

    List<TipoPublicoObjetivoEntidadGridDTO> getTipoPubObjEntByWF(Long codigoWF);

    void mergeDocumentosTramite(Long codigoWF, Long codigoTramite, Long idListaDocumentos, boolean isModelo, List<ProcedimientoDocumentoDTO> docs, String ruta);

    void deleteWF(Long codigo, boolean wf);

    void deleteWF(Long codigoWF);

    void clonarCategoriasPDU(Long codigoWF, Long codigoWFNuevo);

    void clonarTasaServicio(Long codigoWF, Long codigoWFNuevo);

    boolean existeProcedimientoConFormaInicio(Long codigoForIni);

    boolean existeProcedimientoConLegitimacion(Long codigoLegi);

    boolean existeProcedimientoConSilencio(Long codigoSilen);

    boolean existeProcedimientosConNormativas(Long codigoNor);

    boolean existeTramitesConTipoTramitacionPlantilla(Long codigoNor);

    List<NormativaGridDTO> getNormativasByWF(Long codigoWF);

    List<CategoriaPDUDTO> getCategoriasPDUByWFRest(Long codigoWF);

    public void clonarNormativas(Long codigoWF, Long codigoWFNuevo);

    void mergeDocumentos(Long codigo, Long idListaDocumentos, boolean isLopd, List<ProcedimientoDocumentoDTO> docs, String ruta);

    List<ProcedimientoDocumentoDTO> getDocumentosByListaDocumentos(JListaDocumentos listaDocumentos);

    List<ProcedimientoTramiteDTO> getTramitesByWF(Long codigoWF);

    List<CategoriaPDUGridDTO> getCategoriasPDUByWF(Long codigoWF);

    List<ProcedimientoNormativaDTO> getProcedimientosByNormativa(Long idNormativa);

    List<ProcedimientoNormativaDTO> getServiciosByNormativa(Long idNormativa);

    /**
     * Actualiza los mensajes
     *
     * @param codigo
     * @param mensajes
     * @param pendienteMensajeSupervisor
     * @param pendienteMensajesGestor
     */
    void actualizarMensajes(Long codigo, String mensajes, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor);

    Long getCodigoByWF(Long codigo, boolean valor);

    JProcedimientoWorkflow getWFByCodigoWF(Long codigoWF);

    String getNombreProcedimientoServicio(Long codigoWF);

    Long obtenerCountPendientesIndexar(boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro);

    /**
     * Actualizar info de solr
     *
     * @param proc
     */
    void actualizarSolr(IndexacionDTO proc, ResultadoAccion resultadoAccion);


    Long getUAbyCodProcedimiento(Long codProcedimiento);

    /**
     * Actualiza la fecha de actualizacion del jprocedimiento
     *
     * @param codigo
     */

    void actualizarFechaActualizacion(Long codigo);

    /**
     * Para obtener los mensajes de un procedimiento.
     *
     * @param codigo
     * @return
     */
    String getMensajesByCodigo(Long codigo);

    /**
     * Obtiene todos los procedimientos en formato indexacion (para indexar todo)
     *
     * @param isTipoProcedimiento Si es tipo procedimiento (true) o servicio (false)
     * @param idEntidad           Id de la entidad
     * @return
     */
    Pagina<IndexacionDTO> getProcedimientosParaIndexacion(boolean isTipoProcedimiento, Long idEntidad);

    /**
     * Actualizar SIA
     *
     * @param siadto
     * @param resultadoAccion
     */
    void actualizarSIA(IndexacionSIADTO siadto, ResultadoSIA resultadoAccion);

    void actualizarPDU(IndexacionPDUDto pduDto, ResultadoSIA resultadoAccion);

    /**
     * Obtiene todos los procedimientos para una indexacion SIA
     *
     * @param idEntidad
     * @return
     */
    Pagina<IndexacionSIADTO> getProcedimientosParaIndexacionSIA(Long idEntidad);

    Pagina<IndexacionPDUDto> getIndexacionProcedimientosIntegradosPdu(Long idEntidad);

    String getEnlaceTelematico(ProcedimientoFiltro filtro);

    List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByWFRest(Long codigoWF);

    List<NormativaDTO> getNormativasByWFRest(Long codigoWF);

    List<NormativaDTO> getNormativasByWFRest(Long codigoWF, Long codigoWF2, String enlaceWF);

    List<CategoriaPDUDTO> getCategoriasPDUByWFRest(Long codigoWF, Long codigoWF2, String enlaceWF);

    List<TipoPublicoObjetivoEntidadDTO> getTipoPubObjEntByWFRest(Long codigoWF, Long codigoWF2, String enlaceWF);

    void clonarPublicoObjetivo(Long idProcWF, Long idProcWFDestino);

    void clonarTramites(Long idProcWF, Long idProcWFDestino, String ruta);

    void clonarDocumentos(Long idProcWF, Long idProcWFDestino, String ruta);

    List<ProcedimientoDocumentoDTO> getDocumentosByListaDocumentos(JListaDocumentos listaDocumentos, JListaDocumentos listaDocumentos2, String enlaceWF);

    /**
     * Actualiza la UA de todos
     *
     * @param codigoUAOriginal
     * @param codigoUANueva
     * @param literal
     * @param nombreAntiguo
     * @param nombreNuevo
     * @param perfil
     * @param usuario
     */
    void actualizarUA(List<Long> codigoUAOriginal, Long codigoUANueva, String literal, String nombreAntiguo, String nombreNuevo, TypePerfiles perfil, String usuario);

    void evolucionarProc(Long codigoProcedimiento, Long codigoUAVieja, Long codigoUANueva, String literal, String nombreAntiguo, String nombreNuevo, TypePerfiles perfil, String usuario);

    /**
     * Obtiene los procedimientos asociados a una ua.
     *
     * @param uas     Lista de ua
     * @param tipo    Indica el tipo de procedimiento (PROCEDIMIENTO o SERVICIO)
     * @param idioma  Indica el idioma
     * @param visible Variable opcional, si es null no se tiene en cuenta
     * @return
     */
    List<ProcedimientoBaseDTO> getProcedimientosByUas(List<Long> uas, String tipo, String idioma, Boolean visible);

    /**
     * Obtiene el total de procedimientos asociados a una ua.
     *
     * @param uas     Lista de ua
     * @param tipo    Indica el tipo de procedimiento (PROCEDIMIENTO o SERVICIO)
     * @param idioma  Indica el idioma
     * @param visible Variable opcional, si es null no se tiene en cuenta
     * @return
     */
    Long getProcedimientosTotalByUas(List<Long> uas, String tipo, String idioma, Boolean visible);


    /**
     * Convert to DTO
     *
     * @param jprocWF      El procedimiento workflow a convertir
     * @param simplificado Indica si se quiere un DTO simplificado (con menos información, para listados)
     * @return Convierto un JProcedimientoWorkflow a un ProcedimientoBaseDTO, con la información necesaria para mostrarlo en un listado o en detalle dependiendo del valor de simplificado
     */
    ProcedimientoBaseDTO convertDTO(JProcedimientoWorkflow jprocWF, boolean simplificado);

    /**
     * Obtiene el idioma segun el codigo
     *
     * @param codigoProc
     * @return
     */
    String obtenerIdiomaEntidad(Long codigoProc);

    /**
     * Indica si existe el wf asociado al procedimiento.
     *
     * @param id
     * @param tipoWF
     * @return
     */
    boolean checkExisteWF(Long id, boolean tipoWF);

    /**
     * Guarda el tipo tramitacion
     *
     * @param tramiteElectronico
     * @return
     */
    JTipoTramitacion guardarTipoTramitacion(JTipoTramitacion tramiteElectronico);

    /**
     * Devuelve los codigos de los procedimientos publicados en SIA que están caducados
     *
     * @param idEntidad Codigo de la entidad
     * @return Lista de codigos de procedimientos
     */
    List<Long> revisarProcsPublicadosSIACaducados(Long idEntidad);

    /**
     * Devuelve los codigos de los procedimientos publicados en SIA que están caducados
     *
     * @param idEntidad Codigo de la entidad
     * @return Lista de codigos de servicios
     */
    List<Long> revisarServsPublicadosSIACaducados(Long idEntidad);

    /**
     * Obtiene la fecha de publicación de un procedimiento por su código.
     *
     * @param codigo Código del procedimiento
     * @return Fecha de publicación del procedimiento, o null si no se encuentra
     */
    Date getFechaPublicacionByCodigo(Long codigo);

    /**
     * Obtiene los estados del workflow de un procedimiento o servicio.
     *
     * @param codigo Código del procedimiento o servicio
     * @return Estados del workflow en formato String
     */
    String getWorkflowEstados(Long codigo);
}
