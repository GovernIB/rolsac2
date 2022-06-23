package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

/**
 * Interface de las operaciones básicas sobre tipo de forma de inicio
 *
 * @author Indra
 */
public interface TipoFormaInicioRepository extends CrudRepository<JTipoFormaInicio, Long> {

  Optional<JTipoFormaInicio> findById(String id);

  List<TipoFormaInicioGridDTO> findPagedByFiltro(TipoFormaInicioFiltro filtro);

  long countByFiltro(TipoFormaInicioFiltro filtro);

  boolean existeIdentificador(String identificador);
}
