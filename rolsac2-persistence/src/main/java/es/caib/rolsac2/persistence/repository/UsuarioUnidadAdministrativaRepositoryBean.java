package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(UsuarioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UsuarioUnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JUsuarioUnidadAdministrativa, Long> implements UsuarioUnidadAdministrativaRepository {

    /**
     * Emmagatzema el tipus d'entitat.
     *
     * @param entityClass
     */
    protected UsuarioUnidadAdministrativaRepositoryBean(Class<JUsuarioUnidadAdministrativa> entityClass) {
        super(entityClass);
    }

    @Override
    public Optional<JUsuarioUnidadAdministrativa> findById(String id) {
        //No es de momento necesario implementarlo
        return Optional.empty();
    }

    @Override
    public List<UsuarioGridDTO> findPagedByFiltro(UsuarioFiltro filtro) {
        //No es de momento necesario implementarlo
        return null;
    }

    @Override
    public long countByFiltro(UsuarioFiltro filtro) {
        //No es de momento necesario implementarlo
        return 0;
    }
}
