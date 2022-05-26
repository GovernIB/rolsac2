package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre personal.
 *
 * @author Indra
 */
public interface PersonalRepository extends CrudRepository<JPersonal, Long> {

    Optional<JPersonal> findById(String id);

    List<PersonalGridDTO> findPagedByFiltro(PersonalFiltro filtro);

    long countByFiltro(PersonalFiltro filtro);
}
