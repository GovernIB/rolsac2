package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidadRaiz;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EntidadRaizFiltro;

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
@Local(EntidadRaizRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EntidadRaizRepositoryBean extends AbstractCrudRepository<JEntidadRaiz, Long> implements EntidadRaizRepository {

    protected EntidadRaizRepositoryBean() {
        super(JEntidadRaiz.class);
    }

    @Override
    public Optional<JEntidadRaiz> findById(String id) {
        TypedQuery<JEntidadRaiz> query = entityManager.createNamedQuery(JEntidadRaiz.FIND_BY_ID, JEntidadRaiz.class);
        query.setParameter("codigo", id);
        List<JEntidadRaiz> result = query.getResultList();

        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    private Query getQuery(boolean isTotal, EntidadRaizFiltro filtro) {
        StringBuilder sql;

        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JEntidadRaiz j LEFT OUTER JOIN j.ua ju LEFT OUTER JOIN ju.traducciones jut ON jut.idioma=:idioma WHERE 1 = 1 "
            );
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, jut.nombre, j.user, j.pwd FROM JEntidadRaiz j "
                            + " LEFT OUTER JOIN j.ua ju LEFT OUTER JOIN ju.traducciones jut ON jut.idioma=:idioma WHERE 1 = 1 "
            );
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) LIKE :filtro OR LOWER(jut.nombre) LIKE :filtro "
                    + " OR LOWER (j.user) LIKE :filtro OR LOWER(j.pwd) LIKE :filtro ) ");
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

    @Override
    public List<EntidadRaizGridDTO> findPageByFiltro(EntidadRaizFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jEntidadRaiz = query.getResultList();
        List<EntidadRaizGridDTO> entidadRaizGridDTOS = new ArrayList<>();

        if (jEntidadRaiz != null) {
            for (Object[] entidadRaiz : jEntidadRaiz) {
                EntidadRaizGridDTO entidadRaizGridDTO = new EntidadRaizGridDTO();
                entidadRaizGridDTO.setCodigo((Long) entidadRaiz[0]);
                entidadRaizGridDTO.setUa(createLiteral((String) entidadRaiz[1], filtro.getIdioma()));
                entidadRaizGridDTO.setUser((String) entidadRaiz[2]);
                entidadRaizGridDTO.setPwd((String) entidadRaiz[3]);
                entidadRaizGridDTOS.add(entidadRaizGridDTO);
            }
        }
        return entidadRaizGridDTOS;
    }

    @Override
    public long countByFiltro(EntidadRaizFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public EntidadRaizDTO getEntidadRaizByUA(Long codigoUA) {
        return getEntidadRaizByUARecursivo(codigoUA, 0);
    }

    private EntidadRaizDTO getEntidadRaizByUARecursivo(Long codigoUA, int iteracion) {
        if (codigoUA == null) {
            return null;
        }

        if (iteracion >= 10) {
            return null;
        }
        String sql = "SELECT J FROM JEntidadRaiz J WHERE J.ua.codigo = :codigoUA ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("codigoUA", codigoUA);
        List<JEntidadRaiz> resultados = query.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            EntidadRaizDTO entidadRaizGridDTO = new EntidadRaizDTO();
            entidadRaizGridDTO.setCodigo(resultados.get(0).getCodigo());
            UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
            ua.setCodigo(resultados.get(0).getUa().getCodigo());
            ua.setCodigoDIR3(resultados.get(0).getUa().getCodigoDIR3());
            entidadRaizGridDTO.setUa(ua);
            entidadRaizGridDTO.setUser(resultados.get(0).getUser());
            entidadRaizGridDTO.setPwd(resultados.get(0).getPwd());
            return entidadRaizGridDTO;
        } else {
            JUnidadAdministrativa jua = entityManager.find(JUnidadAdministrativa.class, codigoUA);
            if (jua != null && jua.getPadre() != null) {
                return getEntidadRaizByUARecursivo(codigoUA, (iteracion + 1));
            } else {
                return null;
            }
        }
    }

    private String getOrden(String orden) {
        switch (orden) {
            case "nombre":
                return "jut." + orden;
            default:
                return "j." + orden;
        }
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }
}
