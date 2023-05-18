package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoMediaEdificio;
import es.caib.rolsac2.service.model.TipoMediaEdificioDTO;
import es.caib.rolsac2.service.model.TipoMediaEdificioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;

import java.util.List;
import java.util.Optional;

public interface TipoMediaEdificioRepository extends CrudRepository<JTipoMediaEdificio, Long> {

    Optional<JTipoMediaEdificio> findById(String id);

    List<TipoMediaEdificioGridDTO> findPagedByFiltro(TipoMediaEdificioFiltro filtro);

    List<JTipoMediaEdificio> findByEntidad(Long idEntidad);

    long countByFiltro(TipoMediaEdificioFiltro filtro);

    boolean existeIdentificador(String identificador, Long idEntidad);

	List<TipoMediaEdificioDTO> findPagedByFiltroRest(TipoMediaEdificioFiltro filtro);

    void deleteByEntidad(Long idEntidad);
}
