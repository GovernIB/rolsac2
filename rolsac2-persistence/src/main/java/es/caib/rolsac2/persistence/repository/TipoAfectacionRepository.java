package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.service.model.TipoAfectacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;

/**
 * Interface de las operaciones básicas sobre tipo de afectación
 *
 * @author Indra
 */
public interface TipoAfectacionRepository extends CrudRepository<JTipoAfectacion, Long> {

  Optional<JTipoAfectacion> findById(String id);

  List<TipoAfectacionGridDTO> findPagedByFiltro(TipoAfectacionFiltro filtro);

  long countByFiltro(TipoAfectacionFiltro filtro);

  boolean existeIdentificador(String identificador);

  List<JTipoAfectacion> listTipoAfectaciones();
}
