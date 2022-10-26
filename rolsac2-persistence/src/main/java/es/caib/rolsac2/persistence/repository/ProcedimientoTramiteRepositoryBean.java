package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class  ProcedimientoTramiteRepositoryBean extends AbstractCrudRepository<JProcedimientoTramite, Long>
        implements  ProcedimientoTramiteRepository {

    protected ProcedimientoTramiteRepositoryBean() {
        super(JProcedimientoTramite.class);
    }

    @Override
    public Optional<JProcedimientoTramite> findById(String id) {
        TypedQuery<JProcedimientoTramite> query =
                entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_ID, JProcedimientoTramite.class);
        query.setParameter("id", id);
        List<JProcedimientoTramite> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<JProcedimientoTramite> findByProcedimientoId(Long id) {
        TypedQuery<JProcedimientoTramite> query = entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_PROC_ID,
                JProcedimientoTramite.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
