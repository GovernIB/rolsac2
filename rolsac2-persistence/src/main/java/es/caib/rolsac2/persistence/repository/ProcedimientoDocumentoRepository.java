package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoDocumento;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoDocumentoRepository extends CrudRepository<JProcedimientoDocumento, Long> {

    Optional<JProcedimientoDocumento> findById(String id);

    List<JProcedimientoDocumento> findByProcedimientoId(Long id);

	List<ProcedimientoDocumentoDTO> findPagedByFiltroRest(ProcedimientoDocumentoFiltro filtro);

	long countByFiltro(ProcedimientoDocumentoFiltro filtro);
}
