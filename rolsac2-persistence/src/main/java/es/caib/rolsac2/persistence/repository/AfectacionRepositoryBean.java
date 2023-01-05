package es.caib.rolsac2.persistence.repository;

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
public class AfectacionRepositoryBean extends AbstractCrudRepository<JAfectacion, Long> implements AfectacionRepository {

    protected AfectacionRepositoryBean() {
        super(JAfectacion.class);
    }

    @Override
    public List<JAfectacion> findAfectacionesRelacionadas(Long idNormativa) {
        TypedQuery<JAfectacion> query =
                entityManager.createNamedQuery(JAfectacion.FIND_BY_NORMATIVA_AFECTADA, JAfectacion.class);
        query.setParameter("codigo", idNormativa);
        List<JAfectacion> result = query.getResultList();
        return result;
    }

    @Override
    public List<JAfectacion> findAfectacionesOrigen(Long idNormativaOrigen) {
        TypedQuery<JAfectacion> query =
                entityManager.createNamedQuery(JAfectacion.FIND_BY_NORMATIVA_ORIGEN, JAfectacion.class);
        query.setParameter("codigo", idNormativaOrigen);
        List<JAfectacion> result = query.getResultList();
        return result;
    }
}
