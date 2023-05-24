package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoPublicoObjetivoEntidadConverter;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoEntidadTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;

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
@Local(TipoPublicoObjetivoEntidadRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoPublicoObjetivoEntidadRepositoryBean extends AbstractCrudRepository<JTipoPublicoObjetivoEntidad, Long> implements TipoPublicoObjetivoEntidadRepository {

    @Inject
    private TipoPublicoObjetivoEntidadConverter converter;

    protected TipoPublicoObjetivoEntidadRepositoryBean() {
        super(JTipoPublicoObjetivoEntidad.class);
    }

    // Aquí empieza TipoPublicoObjetivoEntidad
    @Override
    public List<TipoPublicoObjetivoEntidadGridDTO> findPagedByFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposPublicoObjetivoEntidad = query.getResultList();
        List<TipoPublicoObjetivoEntidadGridDTO> tipoPOEntidad = new ArrayList<>();

        if (jTiposPublicoObjetivoEntidad != null) {
            for (Object[] jTipoPublicoObjetivoEntidad : jTiposPublicoObjetivoEntidad) {
                TipoPublicoObjetivoEntidadGridDTO tipoPOGridDTO = new TipoPublicoObjetivoEntidadGridDTO();
                tipoPOGridDTO.setCodigo((Long) jTipoPublicoObjetivoEntidad[0]);
                tipoPOGridDTO.setIdentificador((String) jTipoPublicoObjetivoEntidad[1]);
                tipoPOGridDTO.setTipo(createLiteral((String) jTipoPublicoObjetivoEntidad[2], filtro.getIdioma()));
                tipoPOGridDTO.setDescripcion(createLiteral((String) jTipoPublicoObjetivoEntidad[3], filtro.getIdioma()));
                tipoPOGridDTO.setEmpleadoPublico((boolean) jTipoPublicoObjetivoEntidad[4]);

                tipoPOEntidad.add(tipoPOGridDTO);
            }
        }
        return tipoPOEntidad;
    }

    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }

    @Override
    public long countByFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();

    }

    private Query getQuery(boolean isTotal, TipoPublicoObjetivoEntidadFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoPublicoObjetivoEntidad j  " + " LEFT OUTER JOIN j.tipo te " + " LEFT OUTER JOIN te.descripcion tt ON tt.idioma=:idioma " + " LEFT OUTER JOIN j.traducciones tp ON tp.idioma=:idioma " + " WHERE tp.idioma=:idioma and tt.idioma=:idioma ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JTipoPublicoObjetivoEntidad j  " + " LEFT OUTER JOIN j.tipo te " + " LEFT OUTER JOIN te.descripcion tt ON tt.idioma=:idioma " + " LEFT OUTER JOIN j.traducciones tp ON tp.idioma=:idioma " + " WHERE tp.idioma=:idioma and tt.idioma=:idioma ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, tt.descripcion, tp.descripcion, te.empleadoPublico FROM JTipoPublicoObjetivoEntidad j" + " LEFT OUTER JOIN j.tipo te" + " LEFT OUTER JOIN te.descripcion tt ON tt.idioma=:idioma" + " LEFT OUTER JOIN j.traducciones tp ON tp.idioma=:idioma" + " WHERE tp.idioma=:idioma and tt.idioma=:idioma ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) like :filtro OR LOWER(j.identificador) LIKE :filtro " + " OR LOWER(tp.descripcion) LIKE :filtro " + " OR LOWER(tt.descripcion) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo = :idEntidad ");
        }
        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo = :codigo ");
        }
        if (filtro.isRellenoCodigoTipo()) {
            sql.append(" and te.codigo = :codigoTipo ");
        }
        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER(j.identificador) LIKE :identificador ");
        }
        if (filtro.isRellenoTraducciones()) {
            sql.append(" and LOWER(tp.descripcion) LIKE :traducciones ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
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
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }

        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        if (filtro.isRellenoTraducciones()) {
            query.setParameter("traducciones", filtro.getTraducciones());
        }

        if (filtro.isRellenoCodigoTipo()) {
            query.setParameter("codigoTipo", filtro.getCodigoTipo());
        }

        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador() + "%");
        }

        return query;
    }

    private String getOrden(String order) {
        //return "j." + order;
        switch (order) {
            case "codigo":
            case "identificador":
                return "j." + order;
            case "tipo":
                return "tt.descripcion";
            default:
                return "tp.descripcion";
        }
    }


    @Override
    public Optional<JTipoPublicoObjetivoEntidad> findById(String id) {
        TypedQuery<JTipoPublicoObjetivoEntidad> query = entityManager.createNamedQuery(JTipoPublicoObjetivoEntidad.FIND_BY_ID, JTipoPublicoObjetivoEntidad.class);
        query.setParameter("codigo", id);
        List<JTipoPublicoObjetivoEntidad> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }


    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoPublicoObjetivoEntidad.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() >= 1L;
    }

    @Override
    public boolean existePublicoObjetivo(Long codigoPO) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JTipoPublicoObjetivoEntidad j where j.tipo.codigo = :codigoPO ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoPO", codigoPO);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public List<TipoPublicoObjetivoEntidadDTO> findPagedByFiltroRest(TipoPublicoObjetivoEntidadFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JTipoPublicoObjetivoEntidad> jtipoPublicoObjetivoes = query.getResultList();
        List<TipoPublicoObjetivoEntidadDTO> tipoPublicoObjetivoes = new ArrayList<>();
        if (jtipoPublicoObjetivoes != null) {
            for (JTipoPublicoObjetivoEntidad jtipoPublicoObjetivo : jtipoPublicoObjetivoes) {
                TipoPublicoObjetivoEntidadDTO tipoPublicoObjetivo = converter.createDTO(jtipoPublicoObjetivo);

                tipoPublicoObjetivoes.add(tipoPublicoObjetivo);
            }
        }
        return tipoPublicoObjetivoes;
    }

    @Override
    public List<JTipoPublicoObjetivoEntidad> findPageByEntidad(Long idEntidad) {
        String sql = "SELECT j FROM JTipoPublicoObjetivoEntidad j WHERE j.entidad.codigo = :idEntidad";

        Query query = entityManager.createQuery(sql, JTipoPublicoObjetivoEntidad.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public void deleteByEntidad(Long idEntidad) {
        String sqlTrad = "SELECT TRAD FROM JTipoPublicoObjetivoEntidadTraduccion trad INNER JOIN trad.tipoPublicoObjetivoEntidad j where j.entidad.codigo = :entidad ";
        Query queryTrad = entityManager.createQuery(sqlTrad);
        queryTrad.setParameter("entidad", idEntidad);
        List<JTipoPublicoObjetivoEntidadTraduccion> jtrads = queryTrad.getResultList();
        if (jtrads != null) {
            for (JTipoPublicoObjetivoEntidadTraduccion jtrad : jtrads) {
                entityManager.remove(jtrad);
            }
        }
        entityManager.flush();


        String sql = "SELECT j FROM JTipoPublicoObjetivoEntidad j where j.entidad.codigo = :entidad ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("entidad", idEntidad);
        List<JTipoPublicoObjetivoEntidad> jtipos = query.getResultList();
        if (jtipos != null) {
            for (JTipoPublicoObjetivoEntidad jtipo : jtipos) {
                entityManager.remove(jtipo);
            }
        }
    }
}
