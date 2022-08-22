package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JFicha;
import es.caib.rolsac2.service.model.FichaGridDTO;
import es.caib.rolsac2.service.model.filtro.FichaFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre ficha.
 *
 * @author Indra
 */
public interface FichaRepository extends CrudRepository<JFicha, Long> {

    Optional<JFicha> findById(String id);

    List<FichaGridDTO> findPagedByFiltro(FichaFiltro filtro);

    long countByFiltro(FichaFiltro filtro);
}
