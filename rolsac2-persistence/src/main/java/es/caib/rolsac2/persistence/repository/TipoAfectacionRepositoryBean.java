package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoAfectacionGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;

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
 * Implementación del repositorio de tipo de afectación.
 *
 * @author Indra
 */
@Stateless
@Local(TipoAfectacionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoAfectacionRepositoryBean extends AbstractCrudRepository<JTipoAfectacion, Long>
        implements TipoAfectacionRepository {

    protected TipoAfectacionRepositoryBean() {
        super(JTipoAfectacion.class);
    }

    @Override
    public List<TipoAfectacionGridDTO> findPagedByFiltro(TipoAfectacionFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposAfectacion = query.getResultList();
        List<TipoAfectacionGridDTO> tiposAfectacion = new ArrayList<>();
        if (jTiposAfectacion != null) {
            for (Object[] jTipoAfectacion : jTiposAfectacion) {
                TipoAfectacionGridDTO tipoAfectacionGridDTO = new TipoAfectacionGridDTO();
                tipoAfectacionGridDTO.setCodigo((Long) jTipoAfectacion[0]);
                tipoAfectacionGridDTO.setIdentificador((String) jTipoAfectacion[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jTipoAfectacion[2]));
                tipoAfectacionGridDTO.setDescripcion(literal);
                tiposAfectacion.add(tipoAfectacionGridDTO);
            }
        }
        return tiposAfectacion;
    }

    @Override
    public long countByFiltro(TipoAfectacionFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoAfectacion.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoAfectacionFiltro filtro) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoAfectacion j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoAfectacion j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro  OR LOWER(t.descripcion) LIKE :filtro)");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
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
        if ("descripcion".equalsIgnoreCase(order)) {
            return "t." + order;
        } else {
            return "j." + order;
        }
    }

    @Override
    public Optional<JTipoAfectacion> findById(String id) {
        TypedQuery<JTipoAfectacion> query =
                entityManager.createNamedQuery(JTipoAfectacion.FIND_BY_ID, JTipoAfectacion.class);
        query.setParameter("id", id);
        List<JTipoAfectacion> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
