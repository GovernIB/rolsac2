package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEstadistica;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;

import java.util.List;
import java.util.Optional;

public interface EstadisticaRepository extends CrudRepository<JEstadistica, Long> {
    Optional<JEstadistica> findById(String id);

    JEstadistica findByUk(EstadisticaFiltro filtro);

    Boolean checkExisteEstadistica(EstadisticaFiltro filtro);

    EstadisticaCMDTO countEstadisticasMensuales(CuadroMandoFiltro filtro);

    List<String> obtenerAplicaciones();

}
