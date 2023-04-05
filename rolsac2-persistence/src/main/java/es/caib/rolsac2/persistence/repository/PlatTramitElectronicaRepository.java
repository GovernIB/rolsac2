package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.PlatTramitElectronicaGridDTO;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface PlatTramitElectronicaRepository extends CrudRepository<JPlatTramitElectronica, Long> {

    Optional<JPlatTramitElectronica> findById(String id);

    List<JPlatTramitElectronica> findAll(Long idEntidad);

    List<PlatTramitElectronicaGridDTO> findPagedByFiltro(PlatTramitElectronicaFiltro filtro);

    Long countByFiltro(PlatTramitElectronicaFiltro filtro);

    Boolean checkIdentificador(String identificador);

	List<PlatTramitElectronicaDTO> findPagedByFiltroRest(PlatTramitElectronicaFiltro filtro);
}
