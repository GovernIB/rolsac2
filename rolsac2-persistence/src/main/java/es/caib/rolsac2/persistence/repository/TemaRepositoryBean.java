package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TemaConverter;
import es.caib.rolsac2.persistence.converter.TipoMateriaSIAConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;

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
@Local(TemaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TemaRepositoryBean extends AbstractCrudRepository<JTema, Long> implements TemaRepository {

    protected TemaRepositoryBean() {
        super(JTema.class);
    }

    @Inject
    private TemaConverter converter;


    @Inject
    private TipoMateriaSIAConverter converterSIA;

    @Override
    public Optional<JTema> findById(String id) {
        TypedQuery<JTema> query = entityManager.createNamedQuery(JTema.FIND_BY_ID, JTema.class);
        query.setParameter("id", id);
        List<JTema> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<TemaGridDTO> findPageByFiltro(TemaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTema = query.getResultList();
        List<TemaGridDTO> temaGridDTOS = new ArrayList<>();

        if (jTema != null) {
            for (Object[] tema : jTema) {
                TemaGridDTO temaGridDTO = new TemaGridDTO();
                temaGridDTO.setCodigo((Long) tema[0]);
                temaGridDTO.setEntidad(((JEntidad) tema[1]).getCodigo());
                temaGridDTO.setIdentificador((String) tema[2]);
                if (tema[3] != null) {
                    temaGridDTO.setTemaPadre(((JTema) tema[3]).getIdentificador());
                }
                if (tema[4] != null) {
                    temaGridDTO.setTipoMateriaSIA(converterSIA.createDTO((JTipoMateriaSIA) tema[4]));
                }
                temaGridDTOS.add(temaGridDTO);
            }
        }
        return temaGridDTOS;
    }

    private Query getQuery(boolean isTotal, TemaFiltro filtro, boolean isRest) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTema j LEFT OUTER JOIN j.temaPadre tp LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma " + " where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JTema j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.identificador, j.temaPadre, j.tipoMateriaSIA " + " FROM JTema j LEFT OUTER JOIN j.temaPadre tp " + " LEFT OUTER JOIN j.descripcion jd ON jd.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER (j.identificador) LIKE :filtro OR cast(j.codigo as string) like :filtro " + " OR LOWER (j.entidad.identificador) LIKE :filtro OR LOWER (j.temaPadre.identificador) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo =:idEntidad ");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo =:codigo ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER (j.identificador) LIKE :identificador ");
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
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        return query;
    }

    private String getOrden(String order) {
        return "j." + order;
    }

    @Override
    public List<JTema> getRoot(String idioma, Long entidadId) {
        TypedQuery<JTema> query = null;

        String sql = "SELECT j FROM JTema j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma " + " where j.temaPadre.codigo IS NULL AND j.entidad.codigo = :entidadId ";
        query = entityManager.createQuery(sql, JTema.class);
        query.setParameter("idioma", idioma);
        query.setParameter("entidadId", entidadId);
        return query.getResultList();
    }

    @Override
    public List<JTema> getHijos(Long idTema, String idioma) {
        TypedQuery<JTema> query = null;

        String sql = "SELECT j FROM JTema j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma " + "WHERE j.temaPadre.codigo = :idTema";

        query = entityManager.createQuery(sql, JTema.class);
        query.setParameter("idTema", idTema);
        query.setParameter("idioma", idioma);
        return query.getResultList();
    }

    @Override
    public List<JTema> getHijosTodosNiveles(String mathPath, String idioma) {
        TypedQuery<JTema> query = null;
        String sql = "SELECT j FROM JTema j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma " + "WHERE j.mathPath LIKE :mathPath";
        query = entityManager.createQuery(sql, JTema.class);
        query.setParameter("idioma", idioma);
        query.setParameter("mathPath", mathPath + "%");
        return query.getResultList();

    }

    @Override
    public Long getCountHijos(Long parentId) {
        TypedQuery<Long> query = null;

        String sql = "SELECT j FROM JTema j WHERE j.temaPadre.codigo = :idTema";

        query = entityManager.createQuery(sql, Long.class);
        query.setParameter("idTema", parentId);
        return query.getSingleResult();
    }

    @Override
    public long countByFiltro(TemaFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public Boolean checkIdentificador(String identificador, Long idEntidad) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTema.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        //query.setParameter("entidad", idEntidad);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }


    @Override
    public List<TemaDTO> findPagedByFiltroRest(TemaFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JTema> jtemas = query.getResultList();
        List<TemaDTO> temas = new ArrayList<>();
        if (jtemas != null) {
            for (JTema jtema : jtemas) {
                TemaDTO tema = converter.createDTO(jtema);

                temas.add(tema);
            }
        }
        return temas;
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {

        String sql;
        Query query;
        sql = "SELECT t FROM JTema j LEFT OUTER JOIN j.descripcion t where j.entidad.codigo = :entidad ";
        query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        List<JTemaTraduccion> jtraducciones = query.getResultList();
        if (jtraducciones != null) {
            for (JTemaTraduccion jtraduccion : jtraducciones) {
                entityManager.remove(jtraduccion);
            }
            entityManager.flush();
        }

        sql = "SELECT j FROM JTema j where j.entidad.codigo = :entidad ";
        query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        //int totalBorrados = query.executeUpdate();
        List<JTema> jtipos = query.getResultList();
        if (jtipos != null) {
            for (JTema jtipo : jtipos) {
                entityManager.remove(jtipo);
            }
            entityManager.flush();
        }

        entityManager.flush();
    }

    @Override
    public List<TemaGridDTO> getTemasByUas(List<Long> uas) {
        Query query = entityManager.createQuery("SELECT distinct j.temas FROM  JUnidadAdministrativa j WHERE  j.codigo IN (:uas) ");
        query.setParameter("uas", uas);
        List<JTema> jtemas = query.getResultList();
        List<TemaGridDTO> temas = new ArrayList<>();
        if (jtemas != null) {
            for (JTema jtema : jtemas) {
                TemaGridDTO temaGridDTO = new TemaGridDTO();
                temaGridDTO.setCodigo(jtema.getCodigo());
                temaGridDTO.setEntidad(jtema.getEntidad().getCodigo());
                temaGridDTO.setIdentificador(jtema.getIdentificador());
                if (jtema.getTemaPadre() != null) {
                    temaGridDTO.setTemaPadre(jtema.getTemaPadre().getIdentificador());
                }

                temas.add(temaGridDTO);
            }
        }
        return temas;
    }

    @Override
    public void actualizar(JTema jTema, JTipoMateriaSIA jTipoMateriaSIA) {
        if (jTipoMateriaSIA == null) {
            jTema.setTipoMateriaSIA(null);
        }
        entityManager.merge(jTema);
        if (jTipoMateriaSIA != null && (jTema.getTipoMateriaSIA() == null || jTipoMateriaSIA.getCodigo().compareTo(jTema.getTipoMateriaSIA().getCodigo()) != 0)) {
            jTema.setTipoMateriaSIA(jTipoMateriaSIA);
            entityManager.flush();
            entityManager.merge(jTema);
        }
    }
}
