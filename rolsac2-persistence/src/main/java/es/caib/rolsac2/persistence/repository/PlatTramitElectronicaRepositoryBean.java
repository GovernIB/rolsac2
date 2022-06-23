package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;

/**
 * Implementación del repositorio de una plataforma de tramitación electrónica
 *
 * @author areus
 */
@Stateless
@Local(PlatTramitElectronicaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PlatTramitElectronicaRepositoryBean extends AbstractCrudRepository<JPlatTramitElectronica, Long>
    implements PlatTramitElectronicaRepository {

  protected PlatTramitElectronicaRepositoryBean() {
    super(JPlatTramitElectronica.class);
  }

  @Override
  public Optional<JPlatTramitElectronica> findById(String id) {
    TypedQuery<JPlatTramitElectronica> query =
        entityManager.createNamedQuery(JPlatTramitElectronica.FIND_BY_ID, JPlatTramitElectronica.class);
    query.setParameter("id", id);
    List<JPlatTramitElectronica> result = query.getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
  }

  @Override
  public List<JPlatTramitElectronica> findAll() {
    TypedQuery<JPlatTramitElectronica> query =
        entityManager.createNamedQuery(JPlatTramitElectronica.FIND_ALL, JPlatTramitElectronica.class);
    List<JPlatTramitElectronica> result = query.getResultList();
    return result;
  }
}
