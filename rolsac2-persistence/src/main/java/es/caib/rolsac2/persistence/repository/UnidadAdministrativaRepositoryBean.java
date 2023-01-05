package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(UnidadAdministrativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JUnidadAdministrativa, Long>
        implements UnidadAdministrativaRepository {

    protected UnidadAdministrativaRepositoryBean() {
        super(JUnidadAdministrativa.class);
    }

    @Inject
    UnidadAdministrativaConverter uaConverter;

    @Override
    public UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot) {
        StringBuilder sql = new StringBuilder("SELECT j.codigo, j.identificador, t.nombre, jp " //" jp.codigo, jp.identificador, pt.nombre "
                + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma= :idioma "
                + " LEFT OUTER JOIN j.padre jp LEFT OUTER JOIN jp.traducciones pt ON pt.idioma = :idioma ");
        sql.append(" WHERE 1 = 1  ");
        if (idEntidadRoot != null) {
            sql.append(" and j.padre.codigo IS NULL AND j.entidad.codigo = :entidadId ");
        }
        if (id != null) {
            sql.append(" and j.codigo = :id ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (id != null) {
            query.setParameter("id", id);
        }
        query.setParameter("idioma", idioma);
        if (idEntidadRoot != null) {
            query.setParameter("entidadId", idEntidadRoot);
        }
        UnidadAdministrativaDTO uadto = null;
        List<Object[]> result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            Object[] jresultado = result.get(0);
            uadto = new UnidadAdministrativaDTO();
            uadto.setCodigo((Long) jresultado[0]);
            uadto.setIdentificador((String) jresultado[1]);
            Literal nombre = new Literal();
            nombre.add(new Traduccion(idioma, (String) jresultado[2]));
            if (jresultado[3] != null) {
                UnidadAdministrativaDTO uaPadre = getPadre(idioma, (JUnidadAdministrativa) jresultado[3]);
                List<UnidadAdministrativaDTO> hijos = getHijosSimple(uaPadre.getCodigo(), idioma, uaPadre);
                uaPadre.setHijos(hijos);
                uadto.setPadre(uaPadre);
            }
            uadto.setNombre(nombre);
            List<UnidadAdministrativaDTO> hijos = getHijosSimple(uadto.getCodigo(), idioma, uadto);
            uadto.setHijos(hijos);
        }
        return uadto;
    }

    private UnidadAdministrativaDTO getPadre(String idioma, JUnidadAdministrativa jUnidadAdministrativa) {
        if (jUnidadAdministrativa.getPadre() == null) {
            UnidadAdministrativaDTO uaPadre = jpaToDto(jUnidadAdministrativa, idioma);
            List<UnidadAdministrativaDTO> hijos = getHijosSimple(jUnidadAdministrativa.getCodigo(), idioma, uaPadre);
            uaPadre.setHijos(hijos);
            return uaPadre;
        } else {
            UnidadAdministrativaDTO uaPadre = getPadre(idioma, jUnidadAdministrativa.getPadre());
            UnidadAdministrativaDTO ua = jpaToDto(jUnidadAdministrativa, idioma);
            ua.setPadre(uaPadre);
            return ua;
        }
    }

    private UnidadAdministrativaDTO jpaToDto(JUnidadAdministrativa jUnidadAdministrativa, String idioma) {
        UnidadAdministrativaDTO uaPadre = new UnidadAdministrativaDTO();
        uaPadre.setCodigo(jUnidadAdministrativa.getCodigo());
        uaPadre.setIdentificador(jUnidadAdministrativa.getIdentificador());
        Literal nombrePadre = new Literal();
        if (jUnidadAdministrativa != null && jUnidadAdministrativa.getTraducciones() != null && !jUnidadAdministrativa.getTraducciones().isEmpty()) {
            for (JUnidadAdministrativaTraduccion trad : jUnidadAdministrativa.getTraducciones()) {
                if (trad != null && trad.getIdioma().equals(idioma)) {
                    nombrePadre.add(new Traduccion(idioma, trad.getNombre()));
                    break;
                }
            }
        }
        uaPadre.setNombre(nombrePadre);
        return uaPadre;
    }

    @Override
    public List<UnidadAdministrativaDTO> getHijosSimple(Long idPadre, String idioma, UnidadAdministrativaDTO padre) {

        StringBuilder sql = new StringBuilder("SELECT j.codigo, j.identificador, t.nombre "
                + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma= :idioma "
                + " LEFT OUTER JOIN j.padre jp LEFT OUTER JOIN jp.traducciones pt ON pt.idioma = :idioma ");
        sql.append(" WHERE j.padre.codigo = :idPadre ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idPadre", idPadre);
        query.setParameter("idioma", idioma);
        UnidadAdministrativaDTO uadto = null;
        List<Object[]> result = query.getResultList();
        List<UnidadAdministrativaDTO> hijos = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            for (Object[] jresultado : result) {
                uadto = new UnidadAdministrativaDTO();
                uadto.setCodigo((Long) jresultado[0]);
                uadto.setIdentificador((String) jresultado[1]);
                Literal nombre = new Literal();
                nombre.add(new Traduccion(idioma, (String) jresultado[2]));
                uadto.setNombre(nombre);
                uadto.setPadre(padre);
                hijos.add(uadto);
            }
        }
        return hijos;
    }

    @Override
    public String obtenerPadreDir3(Long codigoUA, String idioma) {
        return obtenerPadreDir3Recursivo(codigoUA, idioma);
    }

    public String obtenerPadreDir3Recursivo(Long codigoUA, String idioma) {
        StringBuilder sql = new StringBuilder(" SELECT j.codigo, j.codigoDIR3, t.nombre, j.padre.codigo  FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma= :idioma  ");
        sql.append(" WHERE j.codigo = :codigoUA");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoUA", codigoUA);
        query.setParameter("idioma", idioma);
        List<Object[]> result = query.getResultList();
        List<UnidadAdministrativaDTO> hijos = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            Object[] resultado = result.get(0);
            if (resultado[1] != null && !resultado[1].toString().isEmpty()) {
                return (String) resultado[2];
            } else if (resultado[3] != null && ((Long) resultado[3]) != null) {
                return obtenerPadreDir3Recursivo(((Long) resultado[3]), idioma);
            } else {
                return "";
            }
        } else {
            return "";
        }
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
                    "SELECT count(j) FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma "
                            + " LEFT OUTER JOIN j.tipo jtipo LEFT OUTER JOIN j.padre tp "
                            + " LEFT OUTER JOIN tp.traducciones tpd ON tpd.idioma=:idioma "
                            + " LEFT OUTER JOIN j.entidad je "
                            + " where 1 = 1 AND je.codigo=:codEnti");
        } else {
            sql = new StringBuilder("SELECT j.codigo, jtipo, tpd.nombre, j.orden, t.nombre, j.codigoDIR3 "
                    + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma "
                    + " LEFT OUTER JOIN j.padre tp "
                    + " LEFT OUTER JOIN tp.traducciones tpd ON tpd.idioma=:idioma "
                    + " LEFT OUTER JOIN j.entidad je "
                    + " LEFT OUTER JOIN j.tipo jtipo where 1 = 1 AND je.codigo=:codEnti");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(jtipo.identificador) LIKE :filtro "
                    + " OR LOWER(j.codigoDIR3) LIKE :filtro OR cast(j.id as string) like :filtro "
                    + " OR LOWER(t.nombre) LIKE :filtro OR LOWER(cast(j.orden as string)) LIKE :filtro "
                    + " OR LOWER(tpd.nombre) LIKE :filtro OR LOWER(cast(je.codigo as string)) LIKE :filtro ) ");
        }

        if (filtro.isRellenoIdUA()) {
            sql.append(" and ( j.codigo = :idUA OR j.padre.codigo = :idUA) ");
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
        if (filtro.isRellenoIdUA()) {
            query.setParameter("idUA", filtro.getIdUA());
        }
        query.setParameter("codEnti", filtro.getCodEnti());


        return query;
    }

    @Override
    public List<JUnidadAdministrativa> getHijos(Long idUnitat, String idioma) {
        TypedQuery<JUnidadAdministrativa> query = null;

        String sql = "SELECT ua FROM JUnidadAdministrativa ua "
                + " LEFT OUTER JOIN ua.traducciones t ON t.idioma=:idioma WHERE ua.padre.codigo = :idUnidadPadre";

        query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("idUnidadPadre", idUnitat);
        query.setParameter("idioma", idioma);
        return query.getResultList();
    }

    @Override
    public List<JTipoUnidadAdministrativa> getTipo(Long idUnitat, String idioma) {
        Query query = null;
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
            case "nombrePadre":
                return "tpd.nombre ";
            default:
                return "j." + order;
        }


    }

    @Override
    public List<UnidadAdministrativaDTO> getUnidadesAdministrativaByEntidadId(Long entidadId) {
        return null;
    }

    @Override
    public JUnidadAdministrativa findJUAById(UnidadAdministrativaDTO ua) {
        if (ua == null || ua.getCodigo() == null) {
            return null;
        }
        return entityManager.find(JUnidadAdministrativa.class, ua.getCodigo());
    }

    @Override
    public List<JUnidadAdministrativa> getUnidadesAdministrativaByUsuario(Long usuarioId) {
        TypedQuery<JUnidadAdministrativa> query = null;
        String sql = "SELECT a FROM JUnidadAdministrativa a LEFT OUTER JOIN a.usuarios b WHERE b.codigo= :usuarioId";
        query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("usuarioId", usuarioId);

        return query.getResultList();
    }

    @Override
    public List<JUnidadAdministrativa> getUnidadesAdministrativaByNormativa(Long normativaId) {
        TypedQuery<JUnidadAdministrativa> query = null;
        String sql = "SELECT a FROM JUnidadAdministrativa a LEFT OUTER JOIN a.normativas b WHERE b.codigo= :normativaId";
        query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("normativaId", normativaId);

        return query.getResultList();
    }

    @Override
    public UnidadAdministrativaGridDTO modelToGridDTO(JUnidadAdministrativa jUnidadAdministrativa) {
        UnidadAdministrativaGridDTO unidadAdministrativa = new UnidadAdministrativaGridDTO();
        unidadAdministrativa.setCodigo(jUnidadAdministrativa.getCodigo());
        unidadAdministrativa.setNombre(uaConverter.convierteTraduccionToLiteral(jUnidadAdministrativa.getTraducciones(), "nombre"));
        if (jUnidadAdministrativa.getTipo() != null) {
            unidadAdministrativa.setTipo(jUnidadAdministrativa.getTipo().getIdentificador());
        }
        if (jUnidadAdministrativa.getOrden() != null) {
            unidadAdministrativa.setOrden(jUnidadAdministrativa.getOrden());
        }
        if (jUnidadAdministrativa.getCodigoDIR3() != null) {
            unidadAdministrativa.setCodigoDIR3(jUnidadAdministrativa.getCodigoDIR3());
        }
        if (jUnidadAdministrativa.getPadre() != null) {
            unidadAdministrativa.setNombrePadre(uaConverter.convierteTraduccionToLiteral(jUnidadAdministrativa.getPadre().getTraducciones(), "nombre"));
        }
        return unidadAdministrativa;
    }

    @Override
    public boolean existeTipoSexo(Long codigoSex) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JUnidadAdministrativa j where j.responsableSexo.codigo = :codigoSex ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoSex", codigoSex);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<Long> getListaHijosRecursivo(Long codigoUA) {
        StringBuilder sql = new StringBuilder(
                "               SELECT t1.UNAD_CODIGO " +
                        "       FROM rs2_uniadm t1 " +
                        "  LEFT JOIN rs2_uniadm t2 ON t2.UNAD_CODIGO = t1.UNAD_UNADPADRE " +
                        " START WITH t1.UNAD_CODIGO = :codigoUA" +
                        " CONNECT BY PRIOR t1.UNAD_CODIGO = t1.UNAD_UNADPADRE");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("codigoUA", codigoUA);
        List<BigDecimal> resultados = query.getResultList();
        List<Long> resultList = resultados.stream().map(i -> i.longValue()).collect(Collectors.toList());
        return resultList;
    }
}
