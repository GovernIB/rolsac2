package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcedimientoRepositoryBean extends AbstractCrudRepository<JProcedimiento, Long>
        implements ProcedimientoRepository {

    protected ProcedimientoRepositoryBean() {
        super(JProcedimiento.class);
    }

    @Override
    public List<ProcedimientoGridDTO> findPagedByFiltro(ProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jprocs = query.getResultList();
        List<ProcedimientoGridDTO> procs = new ArrayList<>();
        if (jprocs != null) {
            for (Object[] jproc : jprocs) {
                ProcedimientoGridDTO procedimientoGridDTO = new ProcedimientoGridDTO();
                procedimientoGridDTO.setCodigo((Long) jproc[0]);
                //personalGrid.setIdentificador((String) jficha[1]);
                //personalGrid.setNombre((String) jficha[2]);
                //personalGrid.setEmail((String) jficha[3]);

                procs.add(procedimientoGridDTO);
            }
        }
        return procs;
    }

    @Override
    public long countByFiltro(ProcedimientoFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, ProcedimientoFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JPersonal j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.nombre,  j.email FROM JProcedimiento j where 1 = 1 ");
        }
        if (filtro.isRellenoIdUA()) {
            sql.append(" and j.unidadAdministrativa = :ua");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.nombre) LIKE :filtro  OR LOWER(j.identificador) LIKE :filtro  OR LOWER(j.email) LIKE :filtro  )");
        }
        if (filtro.isRellenoNombre()) {
            sql.append(" and LOWER(j.nombre) like :nombre");
        }
        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER(j.identificador) like :identificador ");
        }
        if (filtro.isRellenoCargo()) {
            sql.append(" and LOWER(j.cargo) like :cargo ");
        }
        if (filtro.isRellenoFunciones()) {
            sql.append(" and LOWER(j.funciones) like :funciones ");
        }
        if (filtro.isRellenoUnidadAdministrativa()) {
            sql.append(" and LOWER(j.unidadAdministrativa) like :ua ");
        }
        if (filtro.isRellenoEmail()) {
            sql.append(" and LOWER(j.email) like :email ");
        }
        if (filtro.isRellenoTelefonoFijo()) {
            sql.append(" and j.telefonoFijo like :telefonoFijo ");
        }
        if (filtro.isRellenoTelefonoMovil()) {
            sql.append(" and j.telefonoMovil like :telefonoMovil ");
        }
        if (filtro.isRellenoTelefonoExteriorFijo()) {
            sql.append(" and j.telefonoExteriorFijo like :telefonoExteriorFijo ");
        }
        if (filtro.isRellenoTelefonoExteriorMovil()) {
            sql.append(" and j.telefonoExteriorMovil like :telefonoExteriorMovil ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoIdUA()) {
            query.setParameter("ua", filtro.getIdUA());
        }
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoNombre()) {
            query.setParameter("nombre", "%" + filtro.getNombre().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoCargo()) {
            query.setParameter("cargo", "%" + filtro.getCargo().toLowerCase() + "%");
        }
        if (filtro.isRellenoFunciones()) {
            query.setParameter("funciones", "%" + filtro.getFunciones().toLowerCase() + "%");
        }
        if (filtro.isRellenoUnidadAdministrativa()) {
            query.setParameter("ua", filtro.getUnidadAdministrativa());
        }
        if (filtro.isRellenoEmail()) {
            query.setParameter("email", "%" + filtro.getEmail().toLowerCase() + "%");
        }
        if (filtro.isRellenoTelefonoFijo()) {
            query.setParameter("telefonoFijo", "%" + filtro.getTelefonoFijo() + "%");
        }
        if (filtro.isRellenoTelefonoMovil()) {
            query.setParameter("telefonoMovil", "%" + filtro.getTelefonoMovil() + "%");
        }
        if (filtro.isRellenoTelefonoExteriorFijo()) {
            query.setParameter("telefonoExteriorFijo", "%" + filtro.getTelefonoExteriorFijo() + "%");
        }
        if (filtro.isRellenoTelefonoExteriorMovil()) {
            query.setParameter("telefonoExteriorMovil", "%" + filtro.getTelefonoExteriorMovil() + "%");
        }
        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JProcedimiento> findById(String id) {
        TypedQuery<JProcedimiento> query = entityManager.createNamedQuery(JProcedimiento.FIND_BY_ID, JProcedimiento.class);
        query.setParameter("id", id);
        List<JProcedimiento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
