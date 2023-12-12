package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.TipoNormativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoNormativaFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Normativa.
 *
 * @author Indra
 */
public interface TipoNormativaRepository extends CrudRepository<JTipoNormativa, Long> {

    Optional<JTipoNormativa> findById(String id);

    List<TipoNormativaGridDTO> findPagedByFiltro(TipoNormativaFiltro filtro);

    long countByFiltro(TipoNormativaFiltro filtro);

    Boolean checkIdentificador(String identificador);

    List<TipoNormativaDTO> findAll();

    List<TipoNormativaDTO> findPagedByFiltroRest(TipoNormativaFiltro fg);
}
