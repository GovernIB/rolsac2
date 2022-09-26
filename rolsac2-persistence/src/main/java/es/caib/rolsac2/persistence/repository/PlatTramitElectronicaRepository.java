package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.PlatTramitElectronicaGridDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface PlatTramitElectronicaRepository extends CrudRepository<JPlatTramitElectronica, Long> {

  Optional<JPlatTramitElectronica> findById(String id);

  List<JPlatTramitElectronica> findAll();

  List<PlatTramitElectronicaGridDTO> findPagedByFiltro(PlatTramitElectronicaFiltro filtro);

  Long countByFiltro(PlatTramitElectronicaFiltro filtro);

  Boolean checkIdentificador(String identificador);
}
