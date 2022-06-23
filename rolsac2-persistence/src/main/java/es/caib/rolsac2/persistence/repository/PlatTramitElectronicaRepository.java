package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface PlatTramitElectronicaRepository extends CrudRepository<JPlatTramitElectronica, Long> {

  Optional<JPlatTramitElectronica> findById(String id);

  List<JPlatTramitElectronica> findAll();

  // List<TipoFormaInicioGridDTO> findPagedByFiltro(TipoFormaInicioFiltro filtro);

  // long countByFiltro(TipoFormaInicioFiltro filtro);
}
