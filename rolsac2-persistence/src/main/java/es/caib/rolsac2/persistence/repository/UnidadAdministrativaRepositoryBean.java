package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.PathUA;
import es.caib.rolsac2.persistence.converter.UnidadAdministrativaConverter;
import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(UnidadAdministrativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UnidadAdministrativaRepositoryBean extends AbstractCrudRepository<JUnidadAdministrativa, Long> implements UnidadAdministrativaRepository {

    @Inject
    private UnidadAdministrativaConverter converter;

    @Inject
    private UnidadAdministrativaAuditoriaRepository uaAuditoriaRepository;

    protected UnidadAdministrativaRepositoryBean() {
        super(JUnidadAdministrativa.class);
    }

    @Inject
    UnidadAdministrativaConverter uaConverter;

    @Override
    public UnidadAdministrativaDTO findUASimpleByID(Long id, String idioma, Long idEntidadRoot) {
        StringBuilder sql = new StringBuilder("SELECT j.codigo, j.identificador, t.nombre, jp " //" jp.codigo, jp.identificador, pt.nombre "
                + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma= :idioma " + " LEFT OUTER JOIN j.padre jp LEFT OUTER JOIN jp.traducciones pt ON pt.idioma = :idioma ");
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

        StringBuilder sql = new StringBuilder("SELECT j.codigo, j.identificador, t.nombre " + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma= :idioma " + " LEFT OUTER JOIN j.padre jp LEFT OUTER JOIN jp.traducciones pt ON pt.idioma = :idioma ");
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
        TypedQuery<JUnidadAdministrativa> query = entityManager.createNamedQuery(JUnidadAdministrativa.FIND_BY_ID, JUnidadAdministrativa.class);
        query.setParameter("id", id);
        List<JUnidadAdministrativa> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<UnidadAdministrativaGridDTO> findPagedByFiltro(UnidadAdministrativaFiltro filtro) {
        Query query = null;

        query = getQuery(false, filtro, false);

        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jUnidadAdministrativa = query.getResultList();
        List<UnidadAdministrativaGridDTO> unidadAdmin = new ArrayList<>();

        if (jUnidadAdministrativa != null) {
            for (Object[] jUnidadAdmin : jUnidadAdministrativa) {
                UnidadAdministrativaGridDTO unidadAdministrativaGridDTO = new UnidadAdministrativaGridDTO();
                unidadAdministrativaGridDTO.setCodigo((Long) jUnidadAdmin[0]);
                if (jUnidadAdmin[1] != null) {
                    unidadAdministrativaGridDTO.setTipo(((JTipoUnidadAdministrativa) jUnidadAdmin[1]).getIdentificador());
                }
                if (jUnidadAdmin[2] != null) {
                    unidadAdministrativaGridDTO.setNombrePadre(createLiteral((String) jUnidadAdmin[2], filtro.getIdioma()));
                }
                unidadAdministrativaGridDTO.setOrden((Integer) jUnidadAdmin[3]);
                unidadAdministrativaGridDTO.setNombre(createLiteral((String) jUnidadAdmin[4], filtro.getIdioma()));
                unidadAdministrativaGridDTO.setCodigoDIR3((String) jUnidadAdmin[5]);
                unidadAdmin.add(unidadAdministrativaGridDTO);
            }
        }
        return unidadAdmin;
    }

    private Query getQuery(boolean isTotal, UnidadAdministrativaFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " LEFT OUTER JOIN j.tipo jtipo LEFT OUTER JOIN j.padre tp " + " LEFT OUTER JOIN tp.traducciones tpd ON tpd.idioma=:idioma LEFT OUTER JOIN j.entidad je ");

            if (filtro.isRellenoCodigoNormativa()) {
                sql.append(new StringBuilder(" LEFT OUTER JOIN j.normativas jn ON jn.codigo = :codigoNormativa "));
            }

            sql.append(new StringBuilder(" where 1 = 1  "));
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " LEFT OUTER JOIN j.tipo jtipo LEFT OUTER JOIN j.padre tp " + " LEFT OUTER JOIN tp.traducciones tpd ON tpd.idioma=:idioma LEFT OUTER JOIN j.entidad je ");

            if (filtro.isRellenoCodigoNormativa()) {
                sql.append(new StringBuilder(" LEFT OUTER JOIN j.normativas jn ON jn.codigo = :codigoNormativa "));
            }

            sql.append(new StringBuilder(" where 1 = 1  "));

        } else {
            sql = new StringBuilder("SELECT j.codigo, jtipo, tpd.nombre, j.orden, t.nombre, j.codigoDIR3 " + " FROM JUnidadAdministrativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " LEFT OUTER JOIN j.padre tp " + " LEFT OUTER JOIN tp.traducciones tpd ON tpd.idioma=:idioma " + " LEFT OUTER JOIN j.entidad je " + " LEFT OUTER JOIN j.tipo jtipo where 1 = 1 AND je.codigo=:codEnti");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(jtipo.identificador) LIKE :filtro " + " OR LOWER(j.codigoDIR3) LIKE :filtro OR cast(j.id as string) like :filtro " + " OR LOWER(t.nombre) LIKE :filtro OR LOWER(cast(j.orden as string)) LIKE :filtro " + " OR LOWER(tpd.nombre) LIKE :filtro OR LOWER(cast(je.codigo as string)) LIKE :filtro ) ");
        }

        if (filtro.isRellenoIdUA()) {
            if (filtro.getIdUA().longValue() == -1) {
                sql.append(" and j.padre is null ");
            } else {
                sql.append(" and ( j.codigo = :idUA OR j.padre.codigo = :idUA) ");
            }
        }

        //        if (filtro.isRellenoCodigoNormativa()) {
        //            sql.append(" and  j.normativas in (:codigoNormativa) ");
        //        }

        if (filtro.isRellenoCodigoDIR3()) {
            sql.append(" and LOWER(j.codigoDIR3) LIKE :codigoDIR3 ");
        }

        if (filtro.isRellenoCodEnti()) {
            sql.append(" and je.codigo=:codEnti ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER(jtipo.identificador) LIKE :identificador ");
        }

        if (filtro.isRellenoNombre()) {
            sql.append(" and LOWER(t.nombre) LIKE :nombre ");
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
        if (filtro.isRellenoNombre()) {
            query.setParameter("nombre", "%" + filtro.getNombre().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoCodigoDIR3()) {
            query.setParameter("codigoDIR3", "%" + filtro.getCodigoDIR3().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdUA() && filtro.getIdUA().longValue() != -1) {
            query.setParameter("idUA", filtro.getIdUA());
        }
        if (filtro.isRellenoCodEnti()) {
            query.setParameter("codEnti", filtro.getCodEnti());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }
        if (filtro.isRellenoCodigoNormativa()) {
            query.setParameter("codigoNormativa", filtro.getCodigoNormativa());
        }

        return query;
    }

    @Override
    public List<JUnidadAdministrativa> getHijos(Long idUnitat, String idioma) {
        TypedQuery<JUnidadAdministrativa> query = null;

        String sql = "SELECT ua FROM JUnidadAdministrativa ua " + " LEFT OUTER JOIN ua.traducciones t ON t.idioma=:idioma WHERE ua.padre.codigo = :idUnidadPadre";

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
        TypedQuery<Long> query = entityManager.createNamedQuery(JUnidadAdministrativa.COUNT_BY_IDENTIFICADOR, Long.class);
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
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public long countByFiltroEntidad(Long entidadId) {
        String sql = "SELECT count(a) FROM JUnidadAdministrativa a LEFT OUTER JOIN a.entidad b WHERE b.codigo= :entidadId";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("entidadId", entidadId);
        return query.getSingleResult();
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
    public List<JUnidadAdministrativa> getUnidadesAdministrativaByEntidadId(Long entidadId, String idioma) {
        String sql = "SELECT ua FROM JUnidadAdministrativa ua " + " LEFT OUTER JOIN ua.traducciones t ON t.idioma=:idioma WHERE ua.entidad.codigo=:entidadId";

        Query query = entityManager.createQuery(sql, JUnidadAdministrativa.class);
        query.setParameter("entidadId", entidadId);
        query.setParameter("idioma", idioma);
        return query.getResultList();
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
        unidadAdministrativa.setIdEntidad(jUnidadAdministrativa.getEntidad().getCodigo());
        unidadAdministrativa.setNombre(uaConverter.convierteTraduccionToLiteral(jUnidadAdministrativa.getTraducciones(), "nombre"));
        unidadAdministrativa.setIdentificador(jUnidadAdministrativa.getIdentificador());
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
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JUnidadAdministrativa j where j.responsableSexo.codigo = :codigoSex ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoSex", codigoSex);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<Long> getListaHijosRecursivo(Long codigoUA) {
        StringBuilder sql = new StringBuilder("               SELECT t1.UNAD_CODIGO " + "       FROM rs2_uniadm t1 " + "  LEFT JOIN rs2_uniadm t2 ON t2.UNAD_CODIGO = t1.UNAD_UNADPADRE " + " START WITH t1.UNAD_CODIGO = :codigoUA" + " CONNECT BY PRIOR t1.UNAD_CODIGO = t1.UNAD_UNADPADRE");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("codigoUA", codigoUA);
        List<BigDecimal> resultados = query.getResultList();
        List<Long> resultList = resultados.stream().map(i -> i.longValue()).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public PathUA getPath(UnidadAdministrativaGridDTO ua) {
        PathUA pathUA = new PathUA();
        List<String> ruta = getRuta(ua, 0);
        pathUA.setPath(ruta);
        return pathUA;
    }

    @Override
    public UnidadOrganicaDTO obtenerUnidadRaiz(Long idEntidad) {
        String sql = "SELECT p.codigoDIR3, t.nombre FROM JUnidadAdministrativa p LEFT OUTER JOIN p.traducciones t ON t.idioma = :idioma " + "WHERE p.padre is null AND p.entidad.codigo = :idEntidad";
        Query query = entityManager.createQuery(sql);
        //Lo recuperamos en catalán ya que el organigrama DIR3 viene en catalán también
        query.setParameter("idioma", "ca");
        query.setParameter("idEntidad", idEntidad);
        Object[] resultado = (Object[]) query.getSingleResult();
        UnidadOrganicaDTO unidadOrganicaDTO = new UnidadOrganicaDTO();
        unidadOrganicaDTO.setCodigoDir3((String) resultado[0]);
        unidadOrganicaDTO.setDenominacion((String) resultado[1]);
        return unidadOrganicaDTO;
    }

    @Override
    public List<UnidadOrganicaDTO> obtenerUnidadesHijas(String codigoDir3, Long idEntidad) {
        String sql = "SELECT p.codigoDIR3, p.padre.codigoDIR3, t.nombre, p.version FROM JUnidadAdministrativa p LEFT OUTER JOIN p.traducciones t ON t.idioma = :idioma " + "WHERE p.padre.codigoDIR3 = :codigoDir3 AND p.entidad.codigo=:idEntidad";
        Query query = entityManager.createQuery(sql);
        //Lo recuperamos en catalán ya que el organigrama DIR3 viene en catalán también
        query.setParameter("idioma", "ca");
        query.setParameter("codigoDir3", codigoDir3);
        query.setParameter("idEntidad", idEntidad);
        List<Object[]> resultado = query.getResultList();
        List<UnidadOrganicaDTO> unidades = new ArrayList<>();
        for (Object[] result : resultado) {
            UnidadOrganicaDTO unidad = new UnidadOrganicaDTO();
            unidad.setCodigoDir3((String) result[0]);
            unidad.setCodigoDir3Padre((String) result[1]);
            unidad.setDenominacion((String) result[2]);
            unidad.setVersion((Integer) result[3]);
            unidad.setDenominacionDir3(unidad.getDenominacion() + " (" + unidad.getCodigoDir3() + ")");
            unidades.add(unidad);
        }
        return unidades;
    }

    /**
     * Metodo recursivo para sacar el pathUA
     *
     * @param ua          Unidad administrativa
     * @param profundidad Para evitar problemas de bucle eterno
     * @return
     */
    /**
     * Metodo recursivo para sacar el pathUA
     *
     * @param ua          Unidad administrativa
     * @param profundidad Para evitar problemas de bucle eterno
     * @return
     */
    private List<String> getRuta(UnidadAdministrativaGridDTO ua, int profundidad) {
        List<String> ruta = new ArrayList<>();
        if (profundidad == 20) {
            //Limite de una profundidad de 20
            ruta.add(ua.getCodigo().toString());
            return ruta;
        }
        if (ua.esRaiz() || (ua.getPadre() == null || ua.getPadre().getCodigo() == null)) {
            ruta.add(ua.getCodigo().toString());
        } else {
            JUnidadAdministrativa jpadre = entityManager.find(JUnidadAdministrativa.class, ua.getPadre().getCodigo());
            UnidadAdministrativaGridDTO padre = new UnidadAdministrativaGridDTO();
            padre.setCodigo(jpadre.getCodigo());
            if (jpadre.getPadre() != null) {
                UnidadAdministrativaGridDTO abuelo = new UnidadAdministrativaGridDTO();
                abuelo.setCodigo(jpadre.getPadre().getCodigo());
                padre.setPadre(abuelo);
            }
            profundidad++;
            ruta = getRuta(padre, profundidad);
            ruta.add(ua.getCodigo().toString());
        }
        return ruta;
    }

    @Override
    public Pagina<IndexacionDTO> getUAsParaIndexacion(Long idEntidad) {
        String sql = "SELECT j.codigo FROM JUnidadAdministrativa j WHERE J.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        List<Long> datos = query.getResultList();
        Pagina<IndexacionDTO> resultado = null;
        if (datos == null || datos.isEmpty()) {
            resultado = new Pagina<>(new ArrayList<>(), 0);
        } else {
            List<IndexacionDTO> indexacionDTOS = new ArrayList<>();
            for (Long dato : datos) {
                IndexacionDTO indexacionDTO = new IndexacionDTO();
                indexacionDTO.setTipo(TypeIndexacion.UNIDAD_ADMINISTRATIVA.toString());
                indexacionDTO.setCodElemento(dato);
                indexacionDTO.setFechaCreacion(new Date());
                indexacionDTO.setAccion(1); //Indexar
                indexacionDTOS.add(indexacionDTO);
            }
            resultado = new Pagina<>(indexacionDTOS, indexacionDTOS.size());
        }
        return resultado;
    }

    @Override
    public boolean isVisibleUA(UnidadAdministrativaDTO uaInstructor) {
        return true;
    }

    @Override
    public String obtenerCodigoDIR3(Long codigoUA) {
        if (codigoUA == null) {
            return null;
        }
        return getCodigoDIR3r(codigoUA, 0);
    }

    private String getCodigoDIR3r(Long codigo, int intentos) {
        if (intentos >= 10) {
            return null;
        }
        JUnidadAdministrativa jua = entityManager.find(JUnidadAdministrativa.class, codigo);
        if (jua.getCodigoDIR3() != null && !jua.getCodigoDIR3().isEmpty()) {
            return jua.getCodigoDIR3();
        } else if (jua.getPadre() != null) {
            return getCodigoDIR3r(jua.getPadre().getCodigo(), (intentos + 1));
        } else {
            return null;
        }
    }

    @Override
    public List<UnidadAdministrativaDTO> findPagedByFiltroRest(UnidadAdministrativaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JUnidadAdministrativa> jentidades = query.getResultList();
        List<UnidadAdministrativaDTO> entidades = new ArrayList<>();
        if (jentidades != null) {
            for (JUnidadAdministrativa jentidad : jentidades) {
                UnidadAdministrativaDTO entidad = converter.createDTO(jentidad);

                entidades.add(entidad);
            }
        }
        return entidades;
    }

    @Override
    public void deleteUA(Long id) {
        uaAuditoriaRepository.borrarAuditoriasByIdUA(id);
        JUnidadAdministrativa jua = entityManager.find(JUnidadAdministrativa.class, id);
        entityManager.remove(jua);
        entityManager.flush();
    }
}
