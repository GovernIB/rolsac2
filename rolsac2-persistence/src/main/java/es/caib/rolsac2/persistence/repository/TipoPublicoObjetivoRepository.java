package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Tipo Publico Objetivo
 *
 * @author jsegovia
 */
public interface TipoPublicoObjetivoRepository extends CrudRepository<JTipoPublicoObjetivo, Long> {

    Optional<JTipoPublicoObjetivo> findById(String id);

    List<TipoPublicoObjetivoGridDTO> findPagedByFiltro(TipoPublicoObjetivoFiltro filtro);

    long countByFiltro(TipoPublicoObjetivoFiltro filtro);

    Boolean checkIdentificador(String identificador);

    List<TipoPublicoObjetivoDTO> findAll();

	List<TipoPublicoObjetivoDTO> findPagedByFiltroRest(TipoPublicoObjetivoFiltro filtro);
}
