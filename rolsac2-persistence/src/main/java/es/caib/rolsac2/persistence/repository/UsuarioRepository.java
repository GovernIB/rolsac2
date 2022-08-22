package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.service.model.UsuarioDTO;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<JUsuario, Long> {

    Optional<JUsuario> findById(String id);

    List<UsuarioGridDTO> findPagedByFiltro(UsuarioFiltro filtro);

    long countByFiltro(UsuarioFiltro filtro);

    Boolean checkIdentificador(String identificador);

}
