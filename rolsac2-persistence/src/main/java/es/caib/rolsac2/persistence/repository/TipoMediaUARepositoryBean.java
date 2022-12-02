package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoMediaUA;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaUAGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;

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
@Local(TipoMediaUARepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoMediaUARepositoryBean extends AbstractCrudRepository<JTipoMediaUA, Long> implements TipoMediaUARepository {

    protected TipoMediaUARepositoryBean() {
        super(JTipoMediaUA.class);
    }

    @Override
    public List<TipoMediaUAGridDTO> findPagedByFiltro(TipoMediaUAFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoMediaUAes = query.getResultList();
        List<TipoMediaUAGridDTO> tipoMediaUAes = new ArrayList<>();
        if (jtipoMediaUAes != null) {
            for (Object[] jtipoMediaUA : jtipoMediaUAes) {
                TipoMediaUAGridDTO tipoMediaUAGrid = new TipoMediaUAGridDTO();
                tipoMediaUAGrid.setCodigo((Long) jtipoMediaUA[0]);
                tipoMediaUAGrid.setEntidad(((JEntidad) jtipoMediaUA[1]).getDescripcion(filtro.getIdioma()));
                tipoMediaUAGrid.setIdentificador((String) jtipoMediaUA[2]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoMediaUA[3]));
                tipoMediaUAGrid.setDescripcion(literal);
                tipoMediaUAes.add(tipoMediaUAGrid);
            }
        }
        return tipoMediaUAes;
    }

    @Override
    public long countByFiltro(TipoMediaUAFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador, Long idEntidad) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoMediaUA.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador.toLowerCase());
        query.setParameter("entidad", idEntidad);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoMediaUAFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoMediaUA j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.entidad, j.identificador, t.descripcion FROM JTipoMediaUA j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro  OR LOWER(t.descripcion) LIKE :filtro)");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(
                    " and j.entidad.id = :entidad");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("entidad", filtro.getIdEntidad());
        }

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        if ("descripcion".equals(order)) {
            return "t." + order;
        }
        return "j." + order;
    }

    @Override
    public Optional<JTipoMediaUA> findById(String id) {
        TypedQuery<JTipoMediaUA> query = entityManager.createNamedQuery(JTipoMediaUA.FIND_BY_ID, JTipoMediaUA.class);
        query.setParameter("id", id);
        List<JTipoMediaUA> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}