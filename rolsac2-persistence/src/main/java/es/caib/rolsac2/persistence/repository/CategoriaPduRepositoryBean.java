package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JCategoriaPdu;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@Local(CategoriaPduRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CategoriaPduRepositoryBean extends AbstractCrudRepository<JCategoriaPdu, Long> implements CategoriaPduRepository{

    protected CategoriaPduRepositoryBean() {
        super(JCategoriaPdu.class);
    }

    @Override
    public List<JCategoriaPdu> findAll() {

        return entityManager.createQuery("select c from JCategoriaPdu c", JCategoriaPdu.class).getResultList();
    }
}
