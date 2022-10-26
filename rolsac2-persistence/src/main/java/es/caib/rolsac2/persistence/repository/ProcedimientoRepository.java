package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoRepository extends CrudRepository<JProcedimiento, Long> {

    void mergePublicoObjetivoProcWF(Long codigoWF, List<TipoPublicoObjetivoEntidadGridDTO> listaNuevos);

    void updateWF(JProcedimientoWorkflow jProcWF);

    Optional<JProcedimiento> findById(String id);

    List<ProcedimientoGridDTO> findPagedByFiltro(ProcedimientoFiltro filtro);

    long countByFiltro(ProcedimientoFiltro filtro);

    JProcedimientoWorkflow getWF(Long id, boolean procedimientoEnmodificacion);

    void createWF(JProcedimientoWorkflow jProcWF);

    List<TipoMateriaSIAGridDTO> getMateriaGridSIAByWF(Long codigoWF);

    void mergeMateriaSIAProcWF(Long codigoWF, List<TipoMateriaSIAGridDTO> listaNuevos);

    List<TipoPublicoObjetivoEntidadGridDTO> getTipoPubObjEntByWF(Long codigoWF);

    void deleteWF(Long codigoProc, boolean enmodificacion);
}
