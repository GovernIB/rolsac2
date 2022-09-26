package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.BoletinOficialMapper;
import es.caib.rolsac2.persistence.model.JBoletinOficial;
import es.caib.rolsac2.service.model.BoletinOficialDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(BoletinOficialRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BoletinOficialRepositoryBean extends AbstractCrudRepository<JBoletinOficial, Long> implements BoletinOficialRepository{

    protected BoletinOficialRepositoryBean(){super(JBoletinOficial.class);}

    @Inject
    private BoletinOficialMapper converter;

    @Override
    public List<BoletinOficialDTO> findBoletinOficial() {
        TypedQuery<JBoletinOficial> query =
                entityManager.createQuery("SELECT j FROM JBoletinOficial j", JBoletinOficial.class);
        List<JBoletinOficial> jBoletinOficials = query.getResultList();
        List<BoletinOficialDTO> boletinOficialDTOS = new ArrayList<>();
        if (jBoletinOficials != null) {
            for (JBoletinOficial jBoletinOficial : jBoletinOficials) {
                boletinOficialDTOS.add(this.converter.createDTO(jBoletinOficial));
            }
        }
        return boletinOficialDTOS;
    }
}
