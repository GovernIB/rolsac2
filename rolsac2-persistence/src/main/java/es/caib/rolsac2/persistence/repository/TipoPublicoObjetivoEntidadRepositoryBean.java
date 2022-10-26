package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Implementación del repositorio de Personal.
 *
 * @author jsegovia
 */
@Stateless
@Local(TipoPublicoObjetivoEntidadRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoPublicoObjetivoEntidadRepositoryBean extends AbstractCrudRepository<JTipoPublicoObjetivoEntidad, Long>
                implements TipoPublicoObjetivoEntidadRepository {

    protected TipoPublicoObjetivoEntidadRepositoryBean() {
        super(JTipoPublicoObjetivoEntidad.class);
    }

    // Aquí empieza TipoPublicoObjetivoEntidad
    @Override
    public List<TipoPublicoObjetivoEntidadGridDTO> findPagedByFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposPublicoObjetivoEntidad = query.getResultList();
        List<TipoPublicoObjetivoEntidadGridDTO> tipoPOEntidad = new ArrayList<>();
        if (jTiposPublicoObjetivoEntidad != null) {
            for (Object[] jTipoPublicoObjetivoEntidad : jTiposPublicoObjetivoEntidad) {
                TipoPublicoObjetivoEntidadGridDTO tipoPOGridDTO = new TipoPublicoObjetivoEntidadGridDTO();
                tipoPOGridDTO.setCodigo((Long) jTipoPublicoObjetivoEntidad[0]);
                tipoPOGridDTO.setEntidad(((String) jTipoPublicoObjetivoEntidad[1]));
                tipoPOGridDTO.setTipo(((String) jTipoPublicoObjetivoEntidad[2]));
                tipoPOGridDTO.setIdentificador((String) jTipoPublicoObjetivoEntidad[3]);

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
        return (long) getQuery(true, filtro).getSingleResult();

    }

    private Query getQuery(boolean isTotal, TipoPublicoObjetivoEntidadFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoPublicoObjetivoEntidad j "
                            + " LEFT OUTER JOIN j.tipo tp " + " LEFT OUTER JOIN j.entidad e "
                            + " LEFT OUTER JOIN tp.descripcion tt ON tt.idioma=:idioma "
                            + " LEFT OUTER JOIN e.descripcion te ON te.idioma=:idioma "
                            + " where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                            "SELECT j.codigo, te.descripcion, tt.descripcion, j.identificador FROM JTipoPublicoObjetivoEntidad j "
                                            + " LEFT OUTER JOIN j.tipo tp " + " LEFT OUTER JOIN j.entidad e "
                                            + " LEFT OUTER JOIN tp.descripcion tt ON tt.idioma=:idioma "
                                            + " LEFT OUTER JOIN e.descripcion te ON te.idioma=:idioma "
                                            + " where 1 = 1");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) like :filtro OR LOWER(j.identificador) LIKE :filtro "
                            + " OR LOWER(te.descripcion) LIKE :filtro " + " OR LOWER(tt.descripcion) LIKE :filtro)");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }

        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        return query;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }



    @Override
    public Optional<JTipoPublicoObjetivoEntidad> findById(String id) {
        TypedQuery<JTipoPublicoObjetivoEntidad> query = entityManager
                        .createNamedQuery(JTipoPublicoObjetivoEntidad.FIND_BY_ID, JTipoPublicoObjetivoEntidad.class);
        query.setParameter("id", id);
        List<JTipoPublicoObjetivoEntidad> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }


    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query =
                        entityManager.createNamedQuery(JTipoPublicoObjetivoEntidad.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() >= 1L;
    }



}
