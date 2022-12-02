package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoFormaInicioConverter;
import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

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
 * Implementación del repositorio de tipo de forma de inicio.
 *
 * @author Indra
 */
@Stateless
@Local(TipoFormaInicioRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoFormaInicioRepositoryBean extends AbstractCrudRepository<JTipoFormaInicio, Long>
        implements TipoFormaInicioRepository {

    protected TipoFormaInicioRepositoryBean() {
        super(JTipoFormaInicio.class);
    }

    @Inject
    private TipoFormaInicioConverter converter;

    @Override
    public List<TipoFormaInicioGridDTO> findPagedByFiltro(TipoFormaInicioFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTiposFormaInicio = query.getResultList();
        List<TipoFormaInicioGridDTO> tiposFormaInicio = new ArrayList<>();
        if (jTiposFormaInicio != null) {
            for (Object[] jTipoFormaInicio : jTiposFormaInicio) {
                TipoFormaInicioGridDTO formaInicioGrid = new TipoFormaInicioGridDTO();
                formaInicioGrid.setCodigo((Long) jTipoFormaInicio[0]);
                formaInicioGrid.setIdentificador((String) jTipoFormaInicio[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jTipoFormaInicio[2]));
                formaInicioGrid.setDescripcion(literal);

                tiposFormaInicio.add(formaInicioGrid);
            }
        }
        return tiposFormaInicio;
    }

    @Override
    public long countByFiltro(TipoFormaInicioFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoFormaInicio.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoFormaInicioFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoFormaInicio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            // sql = new StringBuilder("SELECT j.codigo, j.identificador, j.descripcion FROM JTipoFormaInicio j where 1 = 1
            // ");
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoFormaInicio j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) LIKE :filtro OR LOWER(j.identificador) LIKE :filtro  OR LOWER(t.descripcion) LIKE :filtro)");
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

        return query;
    }

    private String getOrden(String order) {
        if ("descripcion".equalsIgnoreCase(order)) {
            return "t." + order;
        } else {
            return "j." + order;
        }
    }

    @Override
    public Optional<JTipoFormaInicio> findById(String id) {
        TypedQuery<JTipoFormaInicio> query =
                entityManager.createNamedQuery(JTipoFormaInicio.FIND_BY_ID, JTipoFormaInicio.class);
        query.setParameter("id", id);
        List<JTipoFormaInicio> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<TipoFormaInicioDTO> findAllTipoFormaInicio() {
        TypedQuery query =
                entityManager.createQuery("SELECT j FROM JTipoFormaInicio j", JTipoFormaInicio.class);
        List<JTipoFormaInicio> jTipoFormaInicios = query.getResultList();
        List<TipoFormaInicioDTO> tipoFormaInicioDTOS = new ArrayList<>();
        if (jTipoFormaInicios != null) {
            for (JTipoFormaInicio jTipoFormaInicio : jTipoFormaInicios) {
                tipoFormaInicioDTOS.add(this.converter.createDTO(jTipoFormaInicio));
            }
        }
        return tipoFormaInicioDTOS;
    }
}
