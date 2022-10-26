package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimientoWorkflow;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@Local(ProcedimientoWorkflowRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoWorkflowRepositoryBean extends AbstractCrudRepository<JProcedimientoWorkflow, Long>
        implements ProcedimientoWorkflowRepository{

    protected ProcedimientoWorkflowRepositoryBean() {
        super(JProcedimientoWorkflow.class);
    }
}
