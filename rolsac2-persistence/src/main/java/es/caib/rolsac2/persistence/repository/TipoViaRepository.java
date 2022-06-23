package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoVia;
import es.caib.rolsac2.service.model.TipoViaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoViaFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoViaRepository extends CrudRepository<JTipoVia, Long> {

  Optional<JTipoVia> findById(String id);

  List<TipoViaGridDTO> findPagedByFiltro(TipoViaFiltro filtro);

  long countByFiltro(TipoViaFiltro filtro);

  boolean existeIdentificador(String identificador);

}
