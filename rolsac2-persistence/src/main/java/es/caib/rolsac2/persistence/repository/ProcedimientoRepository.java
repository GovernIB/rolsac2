package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JListaDocumentos;
import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.solr.ProcedimientoBaseSolr;

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
     */
    void actualizarMensajes(Long codigo, String mensajes);

    Long getCodigoByWF(Long codigo, boolean valor);

    JProcedimientoWorkflow getWFByCodigoWF(Long codigoWF);

    List<ProcedimientoBaseSolr> obtenerPendientesIndexar(boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro);

    Long obtenerCountPendientesIndexar(boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro);

    /**
     * Actualizar info de solr
     *
     * @param proc
     */
    void actualizarSolr(ProcedimientoBaseSolr proc);


    /**
     * Marca un procedimiento para indexar
     *
     * @param codigo
     */
    void marcarParaIndexar(Long codigo);
}
