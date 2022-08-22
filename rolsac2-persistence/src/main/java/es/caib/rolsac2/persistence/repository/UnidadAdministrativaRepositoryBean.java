package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
@Local(UnidadAdministrativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JUnidadAdministrativa, Long>
        implements UnidadAdministrativaRepository {

    protected UnidadAdministrativaRepositoryBean() {
        super(JUnidadAdministrativa.class);
    }

    @Override
    public Optional<JUnidadAdministrativa> findById(String id) {
        TypedQuery<JUnidadAdministrativa> query =
                entityManager.createNamedQuery(JUnidadAdministrativa.FIND_BY_ID, JUnidadAdministrativa.class);
        query.setParameter("id", id);
        List<JUnidadAdministrativa> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<UnidadAdministrativaGridDTO> findPagedByFiltro(UnidadAdministrativaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jUnidadAdministrativa = query.getResultList();
        List<UnidadAdministrativaGridDTO> unidadAdmin = new ArrayList<>();

        if (jUnidadAdministrativa != null) {
            for (Object[] jUnidadAdmin : jUnidadAdministrativa) {
                UnidadAdministrativaGridDTO unidadAdministrativaGridDTO = new UnidadAdministrativaGridDTO();
                unidadAdministrativaGridDTO.setCodigo((Long) jUnidadAdmin[0]);
                if (jUnidadAdmin[1] != null) {
                    unidadAdministrativaGridDTO
                            .setTipo(((JTipoUnidadAdministrativa) jUnidadAdmin[1]).getIdentificador());
                }
                if (jUnidadAdmin[2] != null) {
                    unidadAdministrativaGridDTO
                            .setNombrePadre(createLiteral((String) jUnidadAdmin[2], filtro.getIdioma()));
                }
                unidadAdministrativaGridDTO.setOrden((Integer) jUnidadAdmin[3]);
                unidadAdministrativaGridDTO.setNombre(createLiteral((String) jUnidadAdmin[4], filtro.getIdioma()));
                unidadAdministrativaGridDTO.setCodigoDIR3((String) jUnidadAdmin[5]);
                unidadAdmin.add(unidadAdministrativaGridDTO);
            }
        }
        return unidadAdmin;
    }


    private Query getQuery(boolean isTotal, UnidadAdministrativaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JUnidadAdministrativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma "
                            + " LEFT OUTER JOIN j.tipo  LEFT OUTER JOIN j.padre tp "
                            + " LEFT OUTER JOIN tp.descripcion tpd ON tpd.idioma=:idioma "
                            + " LEFT OUTER JOIN j.entidad je "
                            + " where 1 = 1 AND je.codigo=:codEnti");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.tipo, tpd.nombre, j.orden, t.nombre, j.codigoDIR3 "
                    + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma "
                    + " LEFT OUTER JOIN j.padre tp "
                    + " LEFT OUTER JOIN tp.descripcion tpd ON tpd.idioma=:idioma "
                    + " LEFT OUTER JOIN j.entidad je "
                    + " LEFT OUTER JOIN j.tipo where 1 = 1 AND je.codigo=:codEnti");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(j.tipo.identificador) LIKE :filtro "
                    + " OR LOWER(j.codigoDIR3) LIKE :filtro OR cast(j.id as string) like :filtro "
                    + " OR LOWER(t.nombre) LIKE :filtro OR LOWER(cast(j.orden as string)) LIKE :filtro "
                    + " OR LOWER(tpd.nombre) LIKE :filtro OR LOWER(cast(je.codigo as string)) LIKE :filtro ) ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoNombre()) {
            query.setParameter("nombre", "%" + filtro.getNombre().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        query.setParameter("codEnti", filtro.getCodEnti());


        return query;
    }

    @Override
    public List<JUnidadAdministrativa> getHijos(Long idUnitat, String idioma) {
        TypedQuery<JUnidadAdministrativa> query = null;

        String sql = "SELECT ua FROM JUnidadAdministrativa ua "
                + " LEFT OUTER JOIN ua.descripcion t ON t.idioma=:idioma WHERE ua.padre.codigo = :idUnidadPadre";

        query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("idUnidadPadre", idUnitat);
        query.setParameter("idioma", idioma);
        return query.getResultList();
    }

    @Override
    public List<JTipoUnidadAdministrativa> getTipo(Long idUnitat, String idioma) {
        TypedQuery<JTipoUnidadAdministrativa> query = null;
        String sql;
        if (idUnitat != null) {
            sql = "SELECT t FROM JUnidadAdministrativa j LEFT OUTER JOIN j.tipo t ON t.idioma=:idioma WHERE j.tipo.codigo=:idTipo ";
        } else {
            sql = "SELECT t FROM JUnidadAdministrativa j LEFT OUTER JOIN j.tipo t ON t.idioma=:idioma ";
        }
        query = entityManager.createQuery(sql, JTipoUnidadAdministrativa.class);

        query.setParameter("idTipo", idUnitat);
        query.setParameter("idioma", idioma);
        return query.getResultList();
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query =
                entityManager.createNamedQuery(JUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public JUnidadAdministrativa getRoot(String idioma, Long entidadId) {
        TypedQuery<JUnidadAdministrativa> query = null;

        String sql = "SELECT ua FROM JUnidadAdministrativa ua "
                + " LEFT OUTER JOIN ua.descripcion t ON t.idioma = :idioma "
                + " WHERE ua.padre.codigo IS NULL AND ua.entidad.codigo = :entidadId ORDER BY ua.orden DESC";

        query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("idioma", idioma);
        query.setParameter("entidadId", entidadId);
        return query.getSingleResult();
    }

    @Override
    public Long getCountHijos(Long parentId) {
        TypedQuery<Long> query = null;

        String sql = "SELECT COUNT(ua) FROM JUnidadAdministrativa ua WHERE ua.padre.codigo = :idUnidadPadre";

        query = entityManager.createQuery(sql, Long.class);
        query.setParameter("idUnidadPadre", parentId);
        return query.getSingleResult();
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public long countByFiltro(UnidadAdministrativaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private String getOrden(String order) {
        // return "j." + order;
        switch (order) {
            case "nombre":
            case "presentacion":
            case "url":
                return "t." + order;
            default:
                return "j." + order;
        }


    }

    @Override
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId) {
        return null;
    }
}
