package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEdificio;
import es.caib.rolsac2.service.model.EdificioGridDTO;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre edificio
 *
 * @author Indra
 */
public interface EdificioRepository extends CrudRepository<JEdificio, Long> {

    Optional<JEdificio> findById(String id);

    List<EdificioGridDTO> findPagedByFiltro(EdificioFiltro filtro);

    long countByFiltro(EdificioFiltro filtro);

}
