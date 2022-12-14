package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JBoletinOficial;
import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.util.Utils;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(NormativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class NormativaRepositoryBean extends AbstractCrudRepository<JNormativa, Long>
        implements NormativaRepository {

    protected NormativaRepositoryBean() {
        super(JNormativa.class);
    }

    private Query getQuery(boolean isTotal, NormativaFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "select count(j) from JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma "
                            + " LEFT OUTER JOIN j.unidadesAdministrativas u"
                            + " where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT DISTINCT j.codigo, t.titulo, j.tipoNormativa, j.numero, j.boletinOficial, j.fechaAprobacion " +
                            "FROM JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma " +
                            "LEFT OUTER JOIN j.unidadesAdministrativas u WHERE 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(t.titulo) LIKE :filtro OR LOWER(j.tipoNormativa.identificador) LIKE :filtro " +
                    " OR LOWER(cast(j.numero as string)) LIKE :filtro OR LOWER(j.boletinOficial.nombre) LIKE :filtro " +
                    " OR LOWER(cast (j.fechaAprobacion as string)) LIKE :filtro ) ");
        }

        if (filtro.isRellenoIdUA()) {
            sql.append(" and ( u.codigo = :idUA OR u.padre.codigo = :idUA) ");
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

        if (filtro.isRellenoIdUA()) {
            query.setParameter("idUA", filtro.getIdUA());
        }

        return query;
    }

    @Override
    public Optional<JNormativa> findById(String id) {
        TypedQuery<JNormativa> query = entityManager.createNamedQuery(JNormativa.FIND_BY_ID, JNormativa.class);
        query.setParameter("codigo", id);
        List<JNormativa> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<NormativaGridDTO> findPagedByFiltro(NormativaFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jNormativas = query.getResultList();
        List<NormativaGridDTO> normativa = new ArrayList<>();

        if (jNormativas != null) {
            for (Object[] jNormativa : jNormativas) {
                NormativaGridDTO normativaGridDTO = new NormativaGridDTO();
                normativaGridDTO.setCodigo((Long) jNormativa[0]);
                normativaGridDTO.setTitulo(createLiteral((String) jNormativa[1], filtro.getIdioma()));
                normativaGridDTO.setTipoNormativa(((JTipoNormativa) jNormativa[2]).getIdentificador());
                normativaGridDTO.setNumero((String) jNormativa[3]);
                normativaGridDTO.setBoletinOficial(((JBoletinOficial) jNormativa[4]).getNombre());
                normativaGridDTO.setFechaAprobacion(Utils.dateToString((LocalDate) jNormativa[5]));

                normativa.add(normativaGridDTO);
            }
        }

        return normativa;
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public long countByFiltro(NormativaFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeTipoNormativa(Long codigoTipoNor) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JNormativa j where j.tipoNormativa.codigo = :codigoTipoNor ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoTipoNor", codigoTipoNor);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeBoletin(Long codigoBol) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JNormativa j where j.boletinOficial.codigo = :codigoBol ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoBol", codigoBol);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    private String getOrden(String order) {
        // return "j." + order;
        switch (order) {
            case "titulo":
                return "t." + order;
            default:
                return "j." + order;
        }
    }
}
