package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre tipo de materia SIA
 *
 * @author Indra
 */
public interface TipoMateriaSIARepository extends CrudRepository<JTipoMateriaSIA, Long> {

    Optional<JTipoMateriaSIA> findById(String id);

    List<TipoMateriaSIAGridDTO> findPagedByFiltro(TipoMateriaSIAFiltro filtro);

    List<TipoMateriaSIADTO> getListTipoMateriaSIADTO(TipoMateriaSIAFiltro filtro);

    long countByFiltro(TipoMateriaSIAFiltro filtro);

    boolean existeIdentificador(String identificador);

    List<TipoMateriaSIADTO> findPagedByFiltroRest(TipoMateriaSIAFiltro filtro);
}
