package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoNormativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoNormativaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoNormativaFiltro;

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
 * @author jsegovia
 */
@Stateless
@Local(TipoNormativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoNormativaRepositoryBean extends AbstractCrudRepository<JTipoNormativa, Long>
        implements TipoNormativaRepository {

    protected TipoNormativaRepositoryBean() {
        super(JTipoNormativa.class);
    }

    @Override
    public List<TipoNormativaGridDTO> findPagedByFiltro(TipoNormativaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTipoNormativa = query.getResultList();
        List<TipoNormativaGridDTO> tipoNormativa = new ArrayList<>();
        if (jTipoNormativa != null) {
            for (Object[] jtipoNom : jTipoNormativa) {
                TipoNormativaGridDTO tipoNormativaGridDTO = new TipoNormativaGridDTO();
                tipoNormativaGridDTO.setId((Long) jtipoNom[0]);
                tipoNormativaGridDTO.setIdentificador((String) jtipoNom[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoNom[2]));
                tipoNormativaGridDTO.setDescripcion(literal);

                tipoNormativa.add(tipoNormativaGridDTO);
            }
        }
        return tipoNormativa;
    }

    @Override
    public long countByFiltro(TipoNormativaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, TipoNormativaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.id, j.identificador, t.descripcion FROM JTipoNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1  ");
        }
//        if (filtro.isRellenoIdUA()) {
//            sql.append(" and j.unidadAdministrativa = :ua");
//        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro )");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
//        if (filtro.isRellenoIdUA()) {
//            query.setParameter("ua", filtro.getIdUA());
//        }
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        switch (order) {
            case "descripcion":
                return "t." + order;
            default:
                return "j." + order;
        }
    }

    @Override
    public Optional<JTipoNormativa> findById(String id) {
        TypedQuery<JTipoNormativa> query = entityManager.createNamedQuery(JTipoNormativa.FIND_BY_ID, JTipoNormativa.class);
        query.setParameter("id", id);
        List<JTipoNormativa> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoNormativa.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() == 1L;
    }
}
