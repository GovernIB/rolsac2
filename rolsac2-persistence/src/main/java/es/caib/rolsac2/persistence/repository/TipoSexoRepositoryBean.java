package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoSexoConverter;
import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.TipoSexoGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoSexoFiltro;

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
@Local(TipoSexoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoSexoRepositoryBean extends AbstractCrudRepository<JTipoSexo, Long> implements TipoSexoRepository {

    protected TipoSexoRepositoryBean() {
        super(JTipoSexo.class);
    }

    @Inject
    private TipoSexoConverter converter;

    @Override
    public List<TipoSexoGridDTO> findPagedByFiltro(TipoSexoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoSexoes = query.getResultList();
        List<TipoSexoGridDTO> tipoSexoes = new ArrayList<>();
        if (jtipoSexoes != null) {
            for (Object[] jtipoSexo : jtipoSexoes) {
                TipoSexoGridDTO tipoSexoGrid = new TipoSexoGridDTO();
                tipoSexoGrid.setCodigo((Long) jtipoSexo[0]);
                tipoSexoGrid.setIdentificador((String) jtipoSexo[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoSexo[2]));
                tipoSexoGrid.setDescripcion(literal);
                tipoSexoes.add(tipoSexoGrid);
            }
        }
        return tipoSexoes;
    }

    @Override
    public long countByFiltro(TipoSexoFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoSexo.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<TipoSexoDTO> findAll() {
        TypedQuery<JTipoSexo> query =
                entityManager.createQuery("SELECT j FROM JTipoSexo j", JTipoSexo.class);
        List<JTipoSexo> jTipoSexos = query.getResultList();
        List<TipoSexoDTO> tipoSexoDTOS = new ArrayList<>();
        if (jTipoSexos != null) {
            for (JTipoSexo jtipoSexo : jTipoSexos) {
                tipoSexoDTOS.add(this.converter.createDTO(jtipoSexo));
            }
        }
        return tipoSexoDTOS;
    }

    private Query getQuery(boolean isTotal, TipoSexoFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoSexo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoSexo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
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
    public Optional<JTipoSexo> findById(String id) {
        TypedQuery<JTipoSexo> query = entityManager.createNamedQuery(JTipoSexo.FIND_BY_ID, JTipoSexo.class);
        query.setParameter("id", id);
        List<JTipoSexo> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
