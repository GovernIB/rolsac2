package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

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

/**
 * Implementación del repositorio de Personal.
 *
 * @author Indra
 */
@Stateless
@Local(EntidadRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EntidadRepositoryBean extends AbstractCrudRepository<JEntidad, Long> implements EntidadRepository {

    protected EntidadRepositoryBean() {
        super(JEntidad.class);
    }

    @Inject
    private EntidadConverter converter;

    @Override
    public List<EntidadGridDTO> findPagedByFiltro(EntidadFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jEntidades = query.getResultList();
        List<EntidadGridDTO> entidad = new ArrayList<>();
        if (jEntidades != null) {
            for (Object[] jEntidad : jEntidades) {
                EntidadGridDTO entidadGridDTO = new EntidadGridDTO();
                entidadGridDTO.setCodigo((Long) jEntidad[0]);
                entidadGridDTO.setIdentificador((String) jEntidad[1]);
                entidadGridDTO.setActiva((Boolean) jEntidad[2]);
                entidadGridDTO.setRolAdmin((String) jEntidad[3]);
                entidadGridDTO.setRolAdminContenido((String) jEntidad[4]);
                entidadGridDTO.setRolGestor((String) jEntidad[5]);
                entidadGridDTO.setRolInformador((String) jEntidad[6]);
                //El 7 es el logo
                Literal descripcion = new Literal();
                Traduccion trad = new Traduccion(filtro.getIdioma(), (String) jEntidad[7]);
                descripcion.add(trad);
                entidadGridDTO.setDescripcion(descripcion);
                entidad.add(entidadGridDTO);
            }
        }
        return entidad;
    }

    @Override
    public List<JEntidad> findActivas() {
        TypedQuery<JEntidad> query = entityManager.createQuery("SELECT j FROM JEntidad j where j.activa = true", JEntidad.class);

        List<JEntidad> jEntidad = query.getResultList();


        return jEntidad;
    }

    @Override
    public List<String> findRolesDefinidos(List<Long> idEntidades) {
        String sql = "SELECT j.rolAdmin, j.rolAdminContenido, j.rolGestor, j.rolInformador FROM JEntidad j WHERE j.codigo IN (:idEntidades)";
        Query query = entityManager.createQuery(sql);
        query.setParameter("idEntidades", idEntidades);
        List<Object[]> jRoles = query.getResultList();
        List<String> roles = new ArrayList<>();
        if (jRoles != null) {
            for (Object[] jRol : jRoles) {
                roles.add((String) jRol[0]);
                roles.add((String) jRol[1]);
                roles.add((String) jRol[2]);
                roles.add((String) jRol[3]);
            }
        }
        return roles;
    }

    @Override
    public long countByFiltro(EntidadFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public boolean existeIdentificadorEntidad(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JEntidad.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador.toLowerCase());
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, EntidadFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JEntidad j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JEntidad j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, j.activa, j.rolAdmin, j.rolAdminContenido, " + " j.rolGestor, j.rolInformador, t.descripcion FROM JEntidad j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma WHERE t.idioma=:idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) LIKE :filtro " + " OR LOWER(j.identificador) LIKE :filtro " + " OR LOWER(t.descripcion) LIKE :filtro " + " OR LOWER(j.rolAdmin) LIKE :filtro " + " OR LOWER(j.rolAdminContenido) LIKE :filtro OR LOWER(j.rolGestor) LIKE :filtro " + " OR LOWER(j.rolInformador) LIKE :filtro)");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idioma", filtro.getIdioma());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }
        return query;
    }

    private String getOrden(String order) {
        if (order.equals("descripcion")) {
            return "t.descripcion";
        } else {
            return "j." + order;
        }
    }

    @Override
    public Optional<JEntidad> findById(String id) {
        TypedQuery<JEntidad> query = entityManager.createNamedQuery(JEntidad.FIND_BY_ID, JEntidad.class);
        query.setParameter("id", id);
        List<JEntidad> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<JEntidad> findAll() {
        TypedQuery<JEntidad> query = entityManager.createQuery("select p from JEntidad p ", JEntidad.class);
        List<JEntidad> result = query.getResultList();
        return result;
    }

    @Override
    public List<EntidadDTO> findPagedByFiltroRest(EntidadFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JEntidad> jentidades = query.getResultList();
        List<EntidadDTO> entidades = new ArrayList<>();
        if (jentidades != null) {
            for (JEntidad jentidad : jentidades) {
                EntidadDTO entidad = converter.createDTO(jentidad);

                entidades.add(entidad);
            }
        }
        return entidades;
    }

    @Override
    public String getIdiomaPorDefecto(Long idEntidad) {
        Query query = entityManager.createQuery("SELECT j.idiomaDefectoRest FROM JEntidad j where j.codigo = :idEntidad");
        query.setParameter("idEntidad", idEntidad);
        return (String) query.getSingleResult();
    }
}
