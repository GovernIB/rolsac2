package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoMediaUA;
import es.caib.rolsac2.service.model.TipoMediaUADTO;
import es.caib.rolsac2.service.model.TipoMediaUAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoMediaUARepository extends CrudRepository<JTipoMediaUA, Long> {

    Optional<JTipoMediaUA> findById(String id);

    List<TipoMediaUAGridDTO> findPagedByFiltro(TipoMediaUAFiltro filtro);

    long countByFiltro(TipoMediaUAFiltro filtro);

    boolean existeIdentificador(String identificador, Long idEntidad);

	List<TipoMediaUADTO> findPagedByFiltroRest(TipoMediaUAFiltro filtro);
}
