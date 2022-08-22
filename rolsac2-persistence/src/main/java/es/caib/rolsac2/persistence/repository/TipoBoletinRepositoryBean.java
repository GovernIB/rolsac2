package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(TipoBoletinRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoBoletinRepositoryBean extends AbstractCrudRepository<JTipoBoletin, Long> implements TipoBoletinRepository {

    protected TipoBoletinRepositoryBean() {
        super(JTipoBoletin.class);
    }

    @Override
    public List<TipoBoletinGridDTO> findPagedByFiltro(TipoBoletinFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoBoletines = query.getResultList();
        List<TipoBoletinGridDTO> tipoBoletines = new ArrayList<>();
        if (jtipoBoletines != null) {
            for (Object[] jtipoBoletin : jtipoBoletines) {
                TipoBoletinGridDTO tipoBoletinGrid = new TipoBoletinGridDTO();
                tipoBoletinGrid.setCodigo((Long) jtipoBoletin[0]);
                tipoBoletinGrid.setIdentificador((String) jtipoBoletin[1]);
                tipoBoletinGrid.setNombre((String) jtipoBoletin[2]);
                tipoBoletinGrid.setUrl((String) jtipoBoletin[3]);

                tipoBoletines.add(tipoBoletinGrid);
            }
        }
        return tipoBoletines;
    }

    @Override
    public long countByFiltro(TipoBoletinFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean checkIdentificadorTipoBoletin(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoBoletin.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }


    private Query getQuery(boolean isTotal, TipoBoletinFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoBoletin j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.nombre, j.url FROM JTipoBoletin j where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(
                    " and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(j.nombre) LIKE :filtro OR LOWER(j.url) LIKE :filtro )");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoBoletin> findById(String id) {
        TypedQuery<JTipoBoletin> query = entityManager.createNamedQuery(JTipoBoletin.FIND_BY_ID, JTipoBoletin.class);
        query.setParameter("id", id);
        List<JTipoBoletin> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
