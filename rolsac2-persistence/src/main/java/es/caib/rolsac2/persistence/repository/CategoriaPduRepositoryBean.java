package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.CategoriaPduConverter;
import es.caib.rolsac2.persistence.model.JCategoriaPDU;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.traduccion.JCategoriaPDUTraduccion;
import es.caib.rolsac2.service.model.CategoriaPDUDTO;
import es.caib.rolsac2.service.model.CategoriaPDUGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;

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
@Local(TipoMediaUARepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CategoriaPDURepositoryBean extends AbstractCrudRepository<JCategoriaPDU, Long> implements CategoriaPDURepository {

    @Inject
    private CategoriaPduConverter converter;

    protected CategoriaPDURepositoryBean() {
        super(JCategoriaPDU.class);
    }

    @Override
    public List<CategoriaPDUGridDTO> findPagedByFiltro(CategoriaPDUFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> JCategoriaPDUes = query.getResultList();
        List<CategoriaPDUGridDTO> categoriasPDUs = new ArrayList<>();
        if (JCategoriaPDUes != null) {
            for (Object[] JCategoriaPDU : JCategoriaPDUes) {
                CategoriaPDUGridDTO categoriaPDUGrid = new CategoriaPDUGridDTO();
                categoriaPDUGrid.setCodigo((Long) JCategoriaPDU[0]);
                categoriaPDUGrid.setEntidad(((JEntidad) JCategoriaPDU[1]).getDescripcion(filtro.getIdioma()));
                categoriaPDUGrid.setIdentificador((String) JCategoriaPDU[2]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) JCategoriaPDU[3]));
                categoriaPDUGrid.setDescripcion(literal);
                categoriasPDUs.add(categoriaPDUGrid);
            }
        }
        return categoriasPDUs;
    }

    @Override
    public long countByFiltro(CategoriaPDUFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador, Long idEntidad) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JCategoriaPDU.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador.toLowerCase());
        query.setParameter("entidad", idEntidad);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, CategoriaPDUFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JCategoriaPDU j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JCategoriaPDU j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.identificador, t.descripcion FROM JCategoriaPDU j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro  OR LOWER(t.descripcion) LIKE :filtro)");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.id = :entidad");
        }
        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
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
        if (filtro.isRellenoEntidad()) {
            query.setParameter("entidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
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
    public Optional<JCategoriaPDU> findById(String id) {
        TypedQuery<JCategoriaPDU> query = entityManager.createNamedQuery(JCategoriaPDU.FIND_BY_ID, JCategoriaPDU.class);
        query.setParameter("id", id);
        List<JCategoriaPDU> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<JCategoriaPDU> findByEntidad(Long idEntidad) {
        String sql = "SELECT j FROM JCategoriaPDU j WHERE j.entidad.codigo = :idEntidad";
        Query query = entityManager.createQuery(sql, JCategoriaPDU.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public List<CategoriaPDUDTO> findPagedByFiltroRest(CategoriaPDUFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JCategoriaPDU> JCategoriaPDUes = query.getResultList();
        List<CategoriaPDUDTO> categoriasPDUs = new ArrayList<>();
        if (JCategoriaPDUes != null) {
            for (JCategoriaPDU JCategoriaPDU : JCategoriaPDUes) {
                CategoriaPDUDTO categoriaPDU = converter.createDTO(JCategoriaPDU);

                categoriasPDUs.add(categoriaPDU);
            }
        }
        return categoriasPDUs;
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {
        String sqlTrad = "SELECT TRAD FROM JCategoriaPDUTraduccion trad INNER JOIN trad.categoriaPDU j where j.entidad.codigo = :entidad ";
        Query queryTrad = entityManager.createQuery(sqlTrad);
        queryTrad.setParameter("entidad", idEntidad);
        List<JCategoriaPDUTraduccion> jtrads = queryTrad.getResultList();
        if (jtrads != null) {
            for (JCategoriaPDUTraduccion jtrad : jtrads) {
                entityManager.remove(jtrad);
            }
        }
        entityManager.flush();


        String sql = "SELECT j FROM JCategoriaPDU j where j.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        List<JCategoriaPDU> jcategorias = query.getResultList();
        if (jcategorias != null) {
            for (JCategoriaPDU jcategoria : jcategorias) {
                entityManager.remove(jcategoria);
            }
        }
    }
}