package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoTramiteRepository extends CrudRepository<JProcedimientoTramite, Long> {

    Optional<JProcedimientoTramite> findById(String id);

    List<JProcedimientoTramite> findByProcedimientoId(Long id);

	List<ProcedimientoTramiteDTO> findPagedByFiltroRest(ProcedimientoTramiteFiltro filtro);

	long countByFiltro(ProcedimientoTramiteFiltro filtro);
}
