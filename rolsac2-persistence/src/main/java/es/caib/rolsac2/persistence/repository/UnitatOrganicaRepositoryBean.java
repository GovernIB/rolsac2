package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.UnitatOrganica;
import es.caib.rolsac2.service.model.AtributUnitat;
import es.caib.rolsac2.service.model.Ordre;
import es.caib.rolsac2.service.model.UnitatOrganicaDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació del repositori d'Unitats Orgàniques.
 *
 * @author areus
 */
@Stateless
@Local(UnitatOrganicaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UnitatOrganicaRepositoryBean extends AbstractCrudRepository<UnitatOrganica, Long>
        implements UnitatOrganicaRepository {

    protected UnitatOrganicaRepositoryBean() {
        super(UnitatOrganica.class);
    }

    @Override
    public Optional<UnitatOrganica> findByCodiDir3(String codiDir3) {
        TypedQuery<UnitatOrganica> query = entityManager.createNamedQuery(
                UnitatOrganica.FIND_BY_CODIDIR3,
                UnitatOrganica.class);
        query.setParameter("codiDir3", codiDir3);
        List<UnitatOrganica> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<UnitatOrganicaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                             Map<AtributUnitat, Object> filter,
                                                             List<Ordre<AtributUnitat>> ordenacio) {
        return new ArrayList<>();
    }

    @Override
    public long countByFilter(Map<AtributUnitat, Object> filter) {
        return 0;
    }
}
