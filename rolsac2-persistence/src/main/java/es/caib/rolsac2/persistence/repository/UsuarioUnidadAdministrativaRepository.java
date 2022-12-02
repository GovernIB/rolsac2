package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import java.util.List;
import java.util.Optional;

public interface UsuarioUnidadAdministrativaRepository extends CrudRepository<JUsuarioUnidadAdministrativa, Long> {



    Optional<JUsuarioUnidadAdministrativa> findById(String id);

    List<UsuarioGridDTO> findPagedByFiltro(UsuarioFiltro filtro);

    long countByFiltro(UsuarioFiltro filtro);

}
