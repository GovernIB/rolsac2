package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre procs.
 *
 * @author Indra
 */
public interface TipoPublicoObjetivoEntidadRepository extends CrudRepository<JTipoPublicoObjetivoEntidad, Long> {

    Optional<JTipoPublicoObjetivoEntidad> findById(String id);

    List<TipoPublicoObjetivoEntidadGridDTO> findPagedByFiltro(TipoPublicoObjetivoEntidadFiltro filtro);

    long countByFiltro(TipoPublicoObjetivoEntidadFiltro filtro);

    boolean existeIdentificador(String identificador);

    boolean existePublicoObjetivo(Long codigoPO);
}
