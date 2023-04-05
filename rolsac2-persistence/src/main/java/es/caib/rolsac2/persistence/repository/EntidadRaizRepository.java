package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidadRaiz;
import es.caib.rolsac2.service.model.EntidadRaizDTO;
import es.caib.rolsac2.service.model.EntidadRaizGridDTO;
import es.caib.rolsac2.service.model.filtro.EntidadRaizFiltro;

import java.util.List;
import java.util.Optional;

public interface EntidadRaizRepository extends CrudRepository<JEntidadRaiz, Long> {
    Optional<JEntidadRaiz> findById(String id);

    List<EntidadRaizGridDTO> findPageByFiltro(EntidadRaizFiltro filtro);

    long countByFiltro(EntidadRaizFiltro filtro);

    EntidadRaizDTO getEntidadRaizByUA(Long codigoUA);
}
