package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.AfectacionConverter;
import es.caib.rolsac2.persistence.model.JAfectacion;
import es.caib.rolsac2.service.model.AfectacionDTO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(AfectacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AfectacionRepositoryBean extends AbstractCrudRepository<JAfectacion, Long> implements AfectacionRepository{

    protected AfectacionRepositoryBean(){super(JAfectacion.class);}

    @Inject
    private AfectacionConverter converter;

    @Override
    public List<AfectacionDTO> findAfectacion() {
        TypedQuery<JAfectacion> query =
                entityManager.createQuery("SELECT j FROM JAfectacion j", JAfectacion.class);
        List<JAfectacion> jAfectacions = query.getResultList();
        List<AfectacionDTO> afectacionDTOS = new ArrayList<>();
        if (jAfectacions != null) {
            for (JAfectacion jAfectacion : jAfectacions) {
                afectacionDTOS.add(this.converter.createDTO(jAfectacion));
            }
        }
        return afectacionDTOS;
    }
}
