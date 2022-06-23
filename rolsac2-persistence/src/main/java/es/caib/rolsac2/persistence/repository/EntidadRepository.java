package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Silencio Administrativo
 *
 * @author jsegovia
 */
public interface EntidadRepository extends CrudRepository<JEntidad, Long> {

    Optional<JEntidad> findById(String id);

    List<EntidadGridDTO> findPagedByFiltro(EntidadFiltro filtro);

    List<EntidadDTO> findActivas();

    long countByFiltro(EntidadFiltro filtro);
}
