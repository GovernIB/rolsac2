package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoLegitimacionGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoLegitimacionFiltro;

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
@Local(TipoLegitimacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoLegitimacionRepositoryBean extends AbstractCrudRepository<JTipoLegitimacion, Long> implements TipoLegitimacionRepository {

    protected TipoLegitimacionRepositoryBean() {
        super(JTipoLegitimacion.class);
    }

    @Override
    public List<TipoLegitimacionGridDTO> findPagedByFiltro(TipoLegitimacionFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoLegitimaciones = query.getResultList();
        List<TipoLegitimacionGridDTO> tipoLegitimaciones = new ArrayList<>();
        if (jtipoLegitimaciones != null) {
            for (Object[] jtipoLegitimacion : jtipoLegitimaciones) {
                TipoLegitimacionGridDTO tipoLegitimacionGrid = new TipoLegitimacionGridDTO();
                tipoLegitimacionGrid.setCodigo((Long) jtipoLegitimacion[0]);
                tipoLegitimacionGrid.setIdentificador((String) jtipoLegitimacion[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoLegitimacion[2]));
                tipoLegitimacionGrid.setDescripcion(literal);
                tipoLegitimaciones.add(tipoLegitimacionGrid);
            }
        }
        return tipoLegitimaciones;
    }

    @Override
    public long countByFiltro(TipoLegitimacionFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoLegitimacion.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoLegitimacionFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoLegitimacion j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoLegitimacion j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro )");
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

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoLegitimacion> findById(String id) {
        TypedQuery<JTipoLegitimacion> query = entityManager.createNamedQuery(JTipoLegitimacion.FIND_BY_ID,
                JTipoLegitimacion.class);
        query.setParameter("id", id);
        List<JTipoLegitimacion> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
