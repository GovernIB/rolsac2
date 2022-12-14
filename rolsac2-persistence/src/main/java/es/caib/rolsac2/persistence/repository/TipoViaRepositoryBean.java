package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoViaConverter;
import es.caib.rolsac2.persistence.model.JTipoVia;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.TipoViaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoViaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(TipoViaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoViaRepositoryBean extends AbstractCrudRepository<JTipoVia, Long> implements TipoViaRepository {

    @Inject
    private TipoViaConverter converter;

    protected TipoViaRepositoryBean() {
        super(JTipoVia.class);
    }

    @Override
    public List<TipoViaGridDTO> findPagedByFiltro(TipoViaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoViaes = query.getResultList();
        List<TipoViaGridDTO> tipoViaes = new ArrayList<>();
        if (jtipoViaes != null) {
            for (Object[] jtipoVia : jtipoViaes) {
                TipoViaGridDTO tipoViaGrid = new TipoViaGridDTO();
                tipoViaGrid.setCodigo((Long) jtipoVia[0]);
                tipoViaGrid.setIdentificador((String) jtipoVia[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoVia[2]));
                tipoViaGrid.setDescripcion(literal);
                tipoViaes.add(tipoViaGrid);
            }
        }
        return tipoViaes;
    }

    @Override
    public long countByFiltro(TipoViaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoVia.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<TipoViaDTO> findAll() {
        TypedQuery<JTipoVia> query =
                entityManager.createQuery("SELECT j FROM JTipoVia j", JTipoVia.class);
        List<JTipoVia> jTipos = query.getResultList();
        List<TipoViaDTO> tipos = new ArrayList<>();
        if (jTipos != null) {
            for (JTipoVia jtipoVia : jTipos) {
                tipos.add(this.converter.createDTO(jtipoVia));
            }
        }
        return tipos;
    }

    private Query getQuery(boolean isTotal, TipoViaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoVia j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoVia j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro)");
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
    public Optional<JTipoVia> findById(String id) {
        TypedQuery<JTipoVia> query = entityManager.createNamedQuery(JTipoVia.FIND_BY_ID, JTipoVia.class);
        query.setParameter("id", id);
        List<JTipoVia> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
