package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<JUsuario, Long> {

    Optional<JUsuario> findById(String id);

    JUsuario findByIdentificador(String identificador);

    List<UsuarioGridDTO> findPagedByFiltro(UsuarioFiltro filtro);

    List<UsuarioGridDTO> findAllUsuariosByFiltro(UsuarioFiltro filtro);

    long countByFiltro(UsuarioFiltro filtro);

    long countAllUsuariosByFiltro(UsuarioFiltro filtro);

    Boolean checkIdentificador(String identificador);

    void anyadirNuevoUsuarioEntidad(JUsuario usuario, Long idEntidad);

    void mergeUsuarioEntidad(JUsuario usuario, List<Long> entidades);

    void eliminarUsuarioEntidad(JUsuario usuario, Long idEntidad);

    List<Long> findEntidadesAsociadas(Long idUsuario);

    void deleteByUA(Long id);
}
