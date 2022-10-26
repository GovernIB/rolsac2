package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;

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
@Local(SeccionRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeccionRepositoryBean extends AbstractCrudRepository<JSeccion, Long> implements SeccionRepository{

    protected SeccionRepositoryBean(){super(JSeccion.class);}

    private Query getQuery(boolean isTotal, SeccionFiltro filtro){
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JSeccion j LEFT OUTER JOIN j.padre tp "
                    + " LEFT OUTER JOIN tp.descripcion tpd ON tpd.idioma=:idioma "
                    + " LEFT OUTER JOIN j.entidad je "
                    + " LEFT OUTER JOIN j.descripcion jd ON jd.idioma=:idioma where 1 = 1 "
            );
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.entidad, j.identificador, tpd.nombre, jd.nombre "
                            + " FROM JSeccion j LEFT OUTER JOIN j.padre tp "
                            + " LEFT OUTER JOIN j.entidad je "
                            + " LEFT OUTER JOIN tp.descripcion tpd ON tpd.idioma=:idioma "
                            + " LEFT OUTER JOIN j.descripcion jd ON jd.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(j.identificador) LIKE :filtro "
                    + " OR LOWER(jd.nombre) LIKE :filtro OR LOWER (j.entidad.identificador) LIKE :filtro "
                    + " OR LOWER(tpd.nombre) LIKE :filtro OR LOWER(cast(j.codigo as string)) LIKE :filtro ) ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }


        return query;
    }

    @Override
    public Optional<JSeccion> findById(String id) {
        TypedQuery<JSeccion> query =
                entityManager.createNamedQuery(JSeccion.FIND_BY_ID, JSeccion.class);
        query.setParameter("id", id);
        List<JSeccion> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<SeccionGridDTO> findPageByFiltro(SeccionFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jSeccion = query.getResultList();
        List<SeccionGridDTO> seccionGridDTOS = new ArrayList<>();

        if (jSeccion != null) {
            for (Object[] seccion : jSeccion) {
                SeccionGridDTO seccionGridDTO = new SeccionGridDTO();
                seccionGridDTO.setCodigo((Long) seccion[0]);
                seccionGridDTO.setEntidad(((JEntidad) seccion[1]).getIdentificador());
                seccionGridDTO.setIdentificador((String) seccion[2]);
                seccionGridDTO.setPadre(createLiteral(((String) seccion[3]), filtro.getIdioma()));
                seccionGridDTO.setNombre(createLiteral((String) seccion[4], filtro.getIdioma()));

                seccionGridDTOS.add(seccionGridDTO);
            }
        }
        return seccionGridDTOS;
    }

    @Override
    public List<JSeccion> getRoot(String idioma, Long entidadId) {
        TypedQuery<JSeccion> query = null;

        String sql = "SELECT j FROM JSeccion j "
                + " LEFT OUTER JOIN j.descripcion t ON t.idioma = :idioma "
                + " WHERE j.padre.codigo IS NULL AND j.entidad.codigo = :entidadId ";

        query = entityManager.createQuery(sql, JSeccion.class);
        query.setParameter("idioma", idioma);
        query.setParameter("entidadId", entidadId);
        return query.getResultList();
    }

    @Override
    public List<JSeccion> getHijos(Long idSeccion, String idioma) {
        TypedQuery<JSeccion> query = null;

        String sql = "SELECT j FROM JSeccion j "
                + " LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma WHERE j.padre.codigo = :idPadre";

        query = entityManager.createQuery(sql, JSeccion.class);
        query.setParameter("idPadre", idSeccion);
        query.setParameter("idioma", idioma);
        return query.getResultList();
    }

    @Override
    public Long getCountHijos(Long parentId) {
        TypedQuery<Long> query = null;

        String sql = "SELECT COUNT(j) FROM JSeccion j WHERE j.padre.codigo = :idPadre";

        query = entityManager.createQuery(sql, Long.class);
        query.setParameter("idPadre", parentId);
        return query.getSingleResult();
    }

    @Override
    public long countByFiltro(SeccionFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query =
                entityManager.createNamedQuery(JSeccion.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    private String getOrden(String order) {
        // return "j." + order;
        switch (order) {
            case "codigo":
            case "entidad":
            case "identificador":
                return "j." + order;
            case "padre":
                return "tpd.nombre";
            default:
                return "jd." + order;
        }

    }
}
