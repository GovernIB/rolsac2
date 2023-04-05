package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.model.JListaDocumentos;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoRepository extends CrudRepository<JProcedimiento, Long> {

    void mergePublicoObjetivoProcWF(Long codigoWF, List<TipoPublicoObjetivoEntidadGridDTO> listaNuevos);

    void mergeNormativaProcWF(Long codigoWF, List<NormativaGridDTO> listaNuevos);

    void mergeTramitesProcWF(Long codigoWF, List<ProcedimientoTramiteDTO> lista, String ruta);

    void updateWF(JProcedimientoWorkflow jProcWF);

    Optional<JProcedimiento> findById(String id);

    List<ProcedimientoGridDTO> findProcedimientosPagedByFiltro(ProcedimientoFiltro filtro);

    List<ServicioGridDTO> findServiciosPagedByFiltro(ProcedimientoFiltro filtro);

    long countByFiltro(ProcedimientoFiltro filtro);

    JProcedimientoWorkflow getWF(Long id, boolean procedimientoEnmodificacion);

    void createWF(JProcedimientoWorkflow jProcWF);

    boolean existeProcedimientoConMateria(Long materiaSIA);

    List<TipoMateriaSIAGridDTO> getMateriaGridSIAByWF(Long codigoWF);

    void mergeMateriaSIAProcWF(Long codigoWF, List<TipoMateriaSIAGridDTO> listaNuevos);

    boolean existeProcedimientoConPublicoObjetivo(Long codigoPub);

    List<TipoPublicoObjetivoEntidadGridDTO> getTipoPubObjEntByWF(Long codigoWF);

    void mergeDocumentosTramite(Long codigoWF, Long codigoTramite, Long idListaDocumentos, boolean isModelo, List<ProcedimientoDocumentoDTO> docs, String ruta);

    void deleteWF(Long codigo, boolean wf);

    void deleteWF(Long codigoWF);

    boolean existeProcedimientoConFormaInicio(Long codigoForIni);

    boolean existeProcedimientoConLegitimacion(Long codigoLegi);

    boolean existeProcedimientoConSilencio(Long codigoSilen);

    boolean existeProcedimientosConNormativas(Long codigoNor);

    boolean existeTramitesConTipoTramitacionPlantilla(Long codigoNor);

    List<NormativaGridDTO> getNormativasByWF(Long codigoWF);

    void mergeDocumentos(Long codigo, Long idListaDocumentos, boolean isLopd, List<ProcedimientoDocumentoDTO> docs, String ruta);

    List<ProcedimientoDocumentoDTO> getDocumentosByListaDocumentos(JListaDocumentos listaDocumentos);

    List<ProcedimientoTramiteDTO> getTramitesByWF(Long codigoWF);

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


    Long obtenerCountPendientesIndexar(boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro);

    /**
     * Actualizar info de solr
     *
     * @param proc
     */
    void actualizarSolr(IndexacionDTO proc, ResultadoAccion resultadoAccion);


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

    /**
     * Obtiene todos los procedimientos para una indexacion SIA
     *
     * @param idEntidad
     * @return
     */
    Pagina<IndexacionSIADTO> getProcedimientosParaIndexacionSIA(Long idEntidad);
}
