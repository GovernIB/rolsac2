package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface ProcedimientoRepository extends CrudRepository<JProcedimiento, Long> {

    Optional<JProcedimiento> findById(String id);

    List<ProcedimientoGridDTO> findPagedByFiltro(ProcedimientoFiltro filtro);

    long countByFiltro(ProcedimientoFiltro filtro);
}
