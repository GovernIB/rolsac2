package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;

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
 * Implementación del repositorio de tipo de materia SIA.
 *
 * @author areus
 */
@Stateless
@Local(TipoMateriaSIARepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoMateriaSIARepositoryBean extends AbstractCrudRepository<JTipoMateriaSIA, Long>
        implements TipoMateriaSIARepository {

    protected TipoMateriaSIARepositoryBean() {
        super(JTipoMateriaSIA.class);
    }

    @Override
    public List<TipoMateriaSIAGridDTO> findPagedByFiltro(TipoMateriaSIAFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposMateriaSIA = query.getResultList();
        List<TipoMateriaSIAGridDTO> tiposMateriaSIA = new ArrayList<>();
        if (jTiposMateriaSIA != null) {
            for (Object[] jTipoMateriaSIA : jTiposMateriaSIA) {
                TipoMateriaSIAGridDTO materiaSIAGrid = new TipoMateriaSIAGridDTO();
                materiaSIAGrid.setId((Long) jTipoMateriaSIA[0]);
                materiaSIAGrid.setIdentificador((String) jTipoMateriaSIA[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jTipoMateriaSIA[2]));
                materiaSIAGrid.setDescripcion(literal);
                tiposMateriaSIA.add(materiaSIAGrid);
            }
        }
        return tiposMateriaSIA;
    }

    @Override
    public long countByFiltro(TipoMateriaSIAFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoMateriaSIA.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoMateriaSIAFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoMateriaSIA j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
//      sql = new StringBuilder("SELECT j.id, j.identificador, j.descripcion FROM JTipoMateriaSIA j where 1 = 1 "); where t.idioma = :idioma
            sql = new StringBuilder("SELECT j.id, j.identificador, t.descripcion FROM JTipoMateriaSIA j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        }

//    if (filtro.isRellenoIdUA()) {
//      sql.append(" and j.unidadAdministrativa = :ua");
//    }


        if (filtro.isRellenoTexto()) {
//      sql.append(" LEFT JOIN e.descripcion.trads t ");
            sql.append(
//          " and ( cast(j.id as string) like :filtro OR (t.idioma.identificador = :idioma AND LOWER(t.literal) LIKE :filtro) OR LOWER(j.identificador) LIKE :filtro )");
                    " and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro )");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
//    if (filtro.isRellenoIdUA()) {
//      query.setParameter("ua", filtro.getIdUA());
//    }
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
//      query.setParameter("idioma", filtro.getIdioma());
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        return query;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JTipoMateriaSIA> findById(String id) {
        TypedQuery<JTipoMateriaSIA> query =
                entityManager.createNamedQuery(JTipoMateriaSIA.FIND_BY_ID, JTipoMateriaSIA.class);
        query.setParameter("id", id);
        List<JTipoMateriaSIA> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

}