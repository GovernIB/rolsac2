package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;

/**
 * Interface de las operaciones básicas sobre tipo de tramitación
 *
 * @author Indra
 */
public interface TipoTramitacionRepository extends CrudRepository<JTipoTramitacion, Long> {

  Optional<JTipoTramitacion> findById(String id);

  List<TipoTramitacionGridDTO> findPagedByFiltro(TipoTramitacionFiltro filtro);

  long countByFiltro(TipoTramitacionFiltro filtro);
}
