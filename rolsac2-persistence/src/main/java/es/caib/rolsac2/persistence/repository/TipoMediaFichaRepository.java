package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoMediaFicha;
import es.caib.rolsac2.service.model.TipoMediaFichaDTO;
import es.caib.rolsac2.service.model.TipoMediaFichaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoMediaFichaRepository extends CrudRepository<JTipoMediaFicha, Long> {

  Optional<JTipoMediaFicha> findById(String id);

  List<TipoMediaFichaGridDTO> findPagedByFiltro(TipoMediaFichaFiltro filtro);

  long countByFiltro(TipoMediaFichaFiltro filtro);

  boolean existeIdentificador(String identificador);

List<TipoMediaFichaDTO> findPagedByFiltroRest(TipoMediaFichaFiltro filtro);
}
