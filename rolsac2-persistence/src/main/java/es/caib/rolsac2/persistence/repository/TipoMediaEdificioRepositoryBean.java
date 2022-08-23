package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTipoMediaEdificio;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaEdificioGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;

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
@Local(TipoMediaEdificioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoMediaEdificioRepositoryBean extends AbstractCrudRepository<JTipoMediaEdificio, Long> implements TipoMediaEdificioRepository {

    protected TipoMediaEdificioRepositoryBean() {
        super(JTipoMediaEdificio.class);
    }

    @Override
    public List<TipoMediaEdificioGridDTO> findPagedByFiltro(TipoMediaEdificioFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoMediaEdificioes = query.getResultList();
        List<TipoMediaEdificioGridDTO> tipoMediaEdificioes = new ArrayList<>();
        if (jtipoMediaEdificioes != null) {
            for (Object[] jtipoMediaEdificio : jtipoMediaEdificioes) {
                TipoMediaEdificioGridDTO tipoMediaEdificioGrid = new TipoMediaEdificioGridDTO();
                tipoMediaEdificioGrid.setCodigo((Long) jtipoMediaEdificio[0]);
                tipoMediaEdificioGrid.setEntidad(((JEntidad) jtipoMediaEdificio[1]).getDescripcion(filtro.getIdioma()));
                tipoMediaEdificioGrid.setIdentificador((String) jtipoMediaEdificio[2]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoMediaEdificio[3]));
                tipoMediaEdificioGrid.setDescripcion(literal);
                tipoMediaEdificioes.add(tipoMediaEdificioGrid);
            }
        }
        return tipoMediaEdificioes;
    }

    @Override
    public long countByFiltro(TipoMediaEdificioFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador, Long idEntidad) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoMediaEdificio.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador.toLowerCase());
        query.setParameter("entidad", idEntidad);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoMediaEdificioFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoMediaEdificio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.entidad, j.identificador, t.descripcion FROM JTipoMediaEdificio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(
                    " and ( cast(j.codigo as string) like :filtro OR LOWER(j.entidad) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(j.descripcion) LIKE :filtro)");
        }

        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo = :entidad ");
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
        return "j." + order;
    }

    @Override
    public Optional<JTipoMediaEdificio> findById(String id) {
        TypedQuery<JTipoMediaEdificio> query = entityManager.createNamedQuery(JTipoMediaEdificio.FIND_BY_ID,
                JTipoMediaEdificio.class);
        query.setParameter("id", id);
        List<JTipoMediaEdificio> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}