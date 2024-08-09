package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.NormativaConverter;
import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.util.Utils;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Stateless
@Local(NormativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class NormativaRepositoryBean extends AbstractCrudRepository<JNormativa, Long> implements NormativaRepository {

    @Inject
    private NormativaConverter converter;

    protected NormativaRepositoryBean() {
        super(JNormativa.class);
    }

    private Query getQuery(boolean isTotal, NormativaFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("select count(j) from JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma  where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j from JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT DISTINCT j.codigo, t.titulo, j.tipoNormativa, j.numero, j.boletinOficial, j.fechaAprobacion, j.vigente FROM JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma WHERE 1 = 1 ");
            //sql = new StringBuilder("SELECT DISTINCT j.codigo, t.titulo, j.tipoNormativa, j.numero, j.boletinOficial, j.fechaAprobacion, j.vigente FROM JNormativa j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma  WHERE 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(t.titulo) LIKE :filtro OR LOWER(j.tipoNormativa.identificador) LIKE :filtro " + " OR LOWER(cast(j.numero as string)) LIKE :filtro OR LOWER(j.boletinOficial.nombre) LIKE :filtro " + " OR LOWER(cast (j.fechaAprobacion as string)) LIKE :filtro ) ");
        }

        if (filtro.isRellenoNombre()) {
            sql.append(" and (LOWER(t.titulo) LIKE :filtro ) ");
        }

        if (filtro.isRellenoEntidad()) {
            sql.append(" AND j.entidad.codigo = :idEntidad");
        }

        if ((filtro.isRellenoHijasActivas() && !filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            sql.append(" AND j.codigo IN (select ua.normativa.codigo from JNormativaUnidadAdministrativa ua where ua.unidadAdministrativa.codigo IN (:idUAs) ) "); // (u.codigo IN (:idUAs) ) ");
        } else if ((filtro.isRellenoHijasActivas() && filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            sql.append(" AND j.codigo IN (select ua.normativa.codigo from JNormativaUnidadAdministrativa ua where (ua.unidadAdministrativa.codigo IN (:idUAs) OR ua.unidadAdministrativa.codigo IN (:idUAsAux)) ) ");
            //sql.append(" AND (u.codigo IN (:idUAs) OR u.codigo IN (:idUAsAux))");
        } else if (filtro.isRellenoIdUA()) {
            sql.append(" AND j.codigo IN (select ua.normativa.codigo from JNormativaUnidadAdministrativa ua where ua.unidadAdministrativa.codigo IN (:idUA) ) ");
            //sql.append(" and ( u.codigo = :idUA) ");
        }

        if (filtro.isRellenoTipoNormativa()) {
            sql.append(" and (j.tipoNormativa.codigo = :tipoNormativa) ");
        }

        if (filtro.isRellenoTipoBoletin()) {
            sql.append(" and (j.boletinOficial.codigo = :boletinOficial)");
        }

        if (filtro.isRellenoFechaAprobacion()) {
            sql.append(" and (j.fechaAprobacion = :fechaAprobacion) ");
        }

        if (filtro.isRellenoFechaBoletin()) {
            sql.append(" and (j.fechaBoletin = :fechaBoletin) ");
        }

        if (filtro.isRellenoNumero()) {
            sql.append(" and (j.numero = :numero) ");
        }
        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
        }

        if (filtro.isRellenoVigente()) {
            if (filtro.getVigente()) {
                sql.append(" and j.vigente is true ");
            } /* Sólo se tiene en cuenta si vigente es true: else {
                sql.append(" and j.vigente is false");
            } */
        }

        if (filtro.isRellenoSoloValidas()) {
            if (filtro.getSoloValidas()) {
                //Se pone 66 en el tipo que es el desconocido
                sql.append(" and j.fechaAprobacion > :fechaMinima and j.tipoNormativa.codigo != 66 ");
            }
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
            query.setParameter("filtro", "%" + filtro.getNombre().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if ((filtro.isRellenoHijasActivas() && !filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            query.setParameter("idUAs", filtro.getIdUAsHijas());
        } else if ((filtro.isRellenoHijasActivas() && filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            query.setParameter("idUAs", filtro.getIdUAsHijas());
            query.setParameter("idUAsAux", filtro.getIdsUAsHijasAux());
        } else if (filtro.isRellenoIdUA()) {
            query.setParameter("idUA", filtro.getIdUA());
        }

        if (filtro.isRellenoTipoNormativa()) {
            query.setParameter("tipoNormativa", filtro.getTipoNormativa().getCodigo());
        }

        if (filtro.isRellenoTipoBoletin()) {
            query.setParameter("boletinOficial", filtro.getTipoBoletin().getCodigo());
        }

        if (filtro.isRellenoFechaAprobacion()) {
            query.setParameter("fechaAprobacion", filtro.getFechaAprobacion());
        }

        if (filtro.isRellenoFechaBoletin()) {
            query.setParameter("fechaBoletin", filtro.getFechaBoletin());
        }

        if (filtro.isRellenoNumero()) {
            query.setParameter("numero", filtro.getNumero());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        if (filtro.isRellenoSoloValidas()) {
            if (filtro.getSoloValidas()) {
                //Se pone la fecha de 1960 ya que todas las fechas tendrían que ser superior
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 1960);
                LocalDate date = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault()).toLocalDate();
                query.setParameter("fechaMinima", date);
            }
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
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
    public List<NormativaDTO> findByFiltro(NormativaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JNormativa> jNormativas = query.getResultList();
        List<NormativaDTO> normativa = new ArrayList<>();

        if (jNormativas != null) {
            for (JNormativa jNormativa : jNormativas) {
                NormativaDTO normativaDTO = converter.createDTO(jNormativa);
                normativa.add(normativaDTO);
            }
        }

        return normativa;
    }

    @Override
    public List<NormativaGridDTO> findPagedByFiltro(NormativaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jNormativas = query.getResultList();
        List<NormativaGridDTO> normativa = new ArrayList<>();

        if (jNormativas != null) {
            for (Object[] jNormativa : jNormativas) {
                NormativaGridDTO normativaGridDTO = new NormativaGridDTO();
                normativaGridDTO.setCodigo((Long) jNormativa[0]);
                normativaGridDTO.setTitulo(createLiteral((String) jNormativa[1], filtro.getIdioma()));
                normativaGridDTO.setTipoNormativa(((JTipoNormativa) jNormativa[2]).getDescripcion(filtro.getIdioma()));
                normativaGridDTO.setNumero((String) jNormativa[3]);
                normativaGridDTO.setBoletinOficial(((JTipoBoletin) jNormativa[4]).getNombre());
                normativaGridDTO.setFechaAprobacion(Utils.dateToString((LocalDate) jNormativa[5]));
                normativaGridDTO.setVigente((Boolean) jNormativa[6]);

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
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public Long countByEntidad(Long entidadId) {
        String sql = "SELECT count(a) FROM JNormativa a LEFT OUTER JOIN a.entidad b WHERE b.codigo= :entidadId";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("entidadId", entidadId);
        return query.getSingleResult();
    }

    @Override
    public Long countByUa(Long uaId) {
        String sql = "SELECT count(a) FROM JNormativa a LEFT OUTER JOIN a.unidadesAdministrativas b WHERE b.codigo= :uaId";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("uaId", uaId);
        return query.getSingleResult();
    }

    @Override
    public Long countAll() {
        String sql = "SELECT count(a) FROM JNormativa a WHERE 1 = 1 ";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean existeTipoNormativa(Long codigoTipoNor) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JNormativa j where j.tipoNormativa.codigo = :codigoTipoNor ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoTipoNor", codigoTipoNor);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeBoletin(Long codigoBol) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JNormativa j where j.boletinOficial.codigo = :codigoBol ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoBol", codigoBol);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public Pagina<IndexacionDTO> getNormativasParaIndexacion(Long idEntidad) {
        String sql = "SELECT j.codigo FROM JNormativa j WHERE J.entidad.codigo = :entidad ";
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
                indexacionDTO.setTipo(TypeIndexacion.NORMATIVA.toString());
                indexacionDTO.setCodElemento(dato);
                indexacionDTO.setFechaCreacion(new Date());
                indexacionDTO.setAccion(1); //Indexar
                indexacionDTOS.add(indexacionDTO);
            }
            resultado = new Pagina<>(indexacionDTOS, indexacionDTOS.size());
        }
        return resultado;
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

    @Override
    public List<NormativaDTO> findPagedByFiltroRest(NormativaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JNormativa> jnormativas = query.getResultList();
        List<NormativaDTO> normativas = new ArrayList<>();
        if (jnormativas != null) {
            for (JNormativa jnormativa : jnormativas) {
                NormativaDTO normativa = converter.createDTO(jnormativa);
                normativas.add(normativa);
            }
        }
        return normativas;
    }

    @Override
    public List<JNormativa> findByEntidad(Long idEntidad) {
        String sql = "SELECT j FROM JNormativa j WHERE j.entidad.codigo = :idEntidad";
        Query query = entityManager.createQuery(sql, JNormativa.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public NormativaDTO getNormativaBaja(Long codigoUA) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JNormativaUnidadAdministrativa j where j.normativa.codigo = :codigoUA ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoUA", codigoUA);
        Long resultado = (Long) query.getSingleResult();
        //O hay 1 normativa o hay ninguna
        if (resultado.compareTo(1l) == 0) {
            sql = new StringBuilder("SELECT j.normativa FROM JNormativaUnidadAdministrativa j where j.normativa.codigo = :codigoUA ");
            query = entityManager.createQuery(sql.toString());
            query.setParameter("codigoUA", codigoUA);
            JNormativa jnorma = (JNormativa) query.getResultList().get(0);
            return converter.createDTO(jnorma);
        } else {
            return null;
        }
    }

    @Override
    public List<NormativaDTO> getNormativaByUas(List<Long> uas, String idioma) {
        Query query = entityManager.createQuery("SELECT distinct JNORM.codigo, t.titulo  FROM  JNormativaUnidadAdministrativa j INNER JOIN j.normativa JNORM LEFT OUTER JOIN JNORM.descripcion t ON t.idioma=:idioma WHERE  j.unidadAdministrativa.codigo IN (:uas)  ");
        query.setParameter("uas", uas);
        query.setParameter("idioma", idioma);
        List<Object[]> jnormativas = query.getResultList();
        List<NormativaDTO> normativas = new ArrayList<>();
        if (jnormativas != null) {
            for (Object[] jnormativa : jnormativas) {
                NormativaDTO normativa = new NormativaDTO();
                normativa.setCodigo((Long) jnormativa[0]);
                Literal nombre = new Literal();
                List<Traduccion> traducciones = new ArrayList<>();
                traducciones.add(new Traduccion(idioma, (String) jnormativa[1]));
                nombre.setTraducciones(traducciones);
                normativa.setTitulo(nombre);
                normativas.add(normativa);
            }
        }
        return normativas;
    }

    /**
     * Actualiza la unidad administrativa de una normativa
     *
     * @param idUAs
     * @param codigoUAfusion
     */
    @Override
    public void actualizarUA(List<Long> idUAs, Long codigoUAfusion) {

        Query query = entityManager.createQuery("SELECT distinct j.normativa FROM  JNormativaUnidadAdministrativa j WHERE  j.unidadAdministrativa.codigo IN (:uas) ");
        query.setParameter("uas", idUAs);
        List<JNormativa> jnormativas = query.getResultList();
        if (jnormativas != null) {
            JUnidadAdministrativa jua = entityManager.find(JUnidadAdministrativa.class, codigoUAfusion);
            jua.setNormativas(new HashSet<>(jnormativas));
            entityManager.merge(jua);
        }

    }

    /**
     * Actualiza la unidad administrativa de una normativa
     *
     * @param codigoNormativa
     * @param codigoUAOrigen
     * @param codigoUADestino
     */
    @Override
    public void evolucionarNorm(Long codigoNormativa, Long codigoUAOrigen, Long codigoUADestino) {

        JNormativa jNormativa = entityManager.find(JNormativa.class, codigoNormativa);
        JUnidadAdministrativa juaDestino = entityManager.find(JUnidadAdministrativa.class, codigoUADestino);
        if (jNormativa.getUnidadesAdministrativas() == null) {
            jNormativa.setUnidadesAdministrativas(new HashSet<>());
            jNormativa.getUnidadesAdministrativas().add(juaDestino);
        } else {
            JUnidadAdministrativa jUnidadAdministrativa = entityManager.find(JUnidadAdministrativa.class, codigoUAOrigen);
            if (jNormativa.getUnidadesAdministrativas().contains(jUnidadAdministrativa)) {
                jNormativa.getUnidadesAdministrativas().remove(jUnidadAdministrativa);
            }
            jNormativa.getUnidadesAdministrativas().add(juaDestino);
        }
        entityManager.merge(jNormativa);

    }


    @Override
    public String obtenerIdiomaEntidad(Long codigoNorm) {
        Query query = entityManager.createQuery("select j.entidad.idiomaDefectoRest from JNormativa j where j.codigo = :codigoNorm ");
        query.setParameter("codigoNorm", codigoNorm);
        return (String) query.getSingleResult();
    }

}
