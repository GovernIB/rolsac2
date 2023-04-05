package es.caib.rolsac2.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.caib.rolsac2.persistence.converter.TipoMediaFichaConverter;
import es.caib.rolsac2.persistence.model.JTipoMediaFicha;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaFichaDTO;
import es.caib.rolsac2.service.model.TipoMediaFichaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;

@Stateless
@Local(TipoMediaFichaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoMediaFichaRepositoryBean extends AbstractCrudRepository<JTipoMediaFicha, Long> implements TipoMediaFichaRepository {

    @Inject
    private TipoMediaFichaConverter converter;

    protected TipoMediaFichaRepositoryBean() {
        super(JTipoMediaFicha.class);
    }

    @Override
    public List<TipoMediaFichaGridDTO> findPagedByFiltro(TipoMediaFichaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jtipoMediaFichaes = query.getResultList();
        List<TipoMediaFichaGridDTO> tipoMediaFichaes = new ArrayList<>();
        if (jtipoMediaFichaes != null) {
            for (Object[] jtipoMediaFicha : jtipoMediaFichaes) {
                TipoMediaFichaGridDTO tipoMediaFichaGrid = new TipoMediaFichaGridDTO();
                tipoMediaFichaGrid.setCodigo((Long) jtipoMediaFicha[0]);
                tipoMediaFichaGrid.setIdentificador((String) jtipoMediaFicha[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoMediaFicha[2]));
                tipoMediaFichaGrid.setDescripcion(literal);
                tipoMediaFichaes.add(tipoMediaFichaGrid);
            }
        }
        return tipoMediaFichaes;
    }

    @Override
    public long countByFiltro(TipoMediaFichaFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public boolean existeIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoMediaFicha.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        Long resultado = query.getSingleResult();
        return resultado > 0;
    }

    private Query getQuery(boolean isTotal, TipoMediaFichaFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoMediaFicha j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
        	sql = new StringBuilder("SELECT j FROM JTipoMediaFicha j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion FROM JTipoMediaFicha j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where t.idioma = :idioma");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro )");
        }
        if (filtro.isRellenoCodigo()) {
        	sql.append(" and j.codigo = :codigo ");
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

        if (filtro.isRellenoCodigo()) {
        	query.setParameter("codigo", filtro.getCodigo());
        }

        return query;
    }

    private String getOrden(String order) {
        //Se puede hacer un switch/if pero en este caso, con j.+order sobra
        if ("descripcion".equals(order)) {
            return "t." + order;
        }
        return "j." + order;
    }

    @Override
    public Optional<JTipoMediaFicha> findById(String id) {
        TypedQuery<JTipoMediaFicha> query = entityManager.createNamedQuery(JTipoMediaFicha.FIND_BY_ID,
                JTipoMediaFicha.class);
        query.setParameter("id", id);
        List<JTipoMediaFicha> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

	@Override
	public List<TipoMediaFichaDTO> findPagedByFiltroRest(TipoMediaFichaFiltro filtro) {
		Query query = getQuery(false, filtro, true);
		query.setFirstResult(filtro.getPaginaFirst());
		query.setMaxResults(filtro.getPaginaTamanyo());

		List<JTipoMediaFicha> jtipoMediaFichaes = query.getResultList();
		List<TipoMediaFichaDTO> tipoMediaFichaes = new ArrayList<>();
		if (jtipoMediaFichaes != null) {
			for (JTipoMediaFicha jtipoMediaFicha : jtipoMediaFichaes) {
				TipoMediaFichaDTO tipoMediaFicha = converter.createDTO(jtipoMediaFicha);

				tipoMediaFichaes.add(tipoMediaFicha);
			}
		}
		return tipoMediaFichaes;
	}
}
