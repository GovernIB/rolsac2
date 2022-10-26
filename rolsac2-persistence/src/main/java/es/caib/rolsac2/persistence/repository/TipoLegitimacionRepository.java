package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.TipoLegitimacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoLegitimacionFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoLegitimacionRepository extends CrudRepository<JTipoLegitimacion, Long> {

  Optional<JTipoLegitimacion> findById(String id);

  List<TipoLegitimacionGridDTO> findPagedByFiltro(TipoLegitimacionFiltro filtro);

  long countByFiltro(TipoLegitimacionFiltro filtro);

  boolean existeIdentificador(String identificador);

  List<TipoLegitimacionDTO> findAllTipoLegitimacion();

}
