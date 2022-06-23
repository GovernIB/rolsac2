package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Publico Objetivo
 *
 * @author jsegovia
 */
public interface TipoUnidadAdministrativaRepository extends CrudRepository<JTipoUnidadAdministrativa, Long> {

    Optional<JTipoUnidadAdministrativa> findById(String id);

    List<TipoUnidadAdministrativaGridDTO> findPagedByFiltro(TipoUnidadAdministrativaFiltro filtro);

    long countByFiltro(TipoUnidadAdministrativaFiltro filtro);

    Boolean checkIdentificador(String identificador);
}
