package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.TipoSexoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoSexoFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoSexoRepository extends CrudRepository<JTipoSexo, Long> {

  Optional<JTipoSexo> findById(String id);

  List<TipoSexoGridDTO> findPagedByFiltro(TipoSexoFiltro filtro);

  long countByFiltro(TipoSexoFiltro filtro);

  boolean existeIdentificador(String identificador);

  List<TipoSexoDTO> findAll();

}
