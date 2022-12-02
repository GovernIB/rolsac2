package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

import java.util.List;
import java.util.Optional;

public interface NormativaRepository extends CrudRepository<JNormativa, Long> {
    Optional<JNormativa> findById(String id);

    List<NormativaGridDTO> findPagedByFiltro(NormativaFiltro filtro);

    long countByFiltro(NormativaFiltro filtro);

    boolean existeTipoNormativa(Long codigoTipoNor);

    boolean existeBoletin(Long codigoBol);
}
