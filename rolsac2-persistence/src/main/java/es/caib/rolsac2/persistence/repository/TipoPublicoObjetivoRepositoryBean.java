package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoPublicoObjetivoConverter;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoFiltro;

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
@Local(TipoPublicoObjetivoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoPublicoObjetivoRepositoryBean extends AbstractCrudRepository<JTipoPublicoObjetivo, Long>
        implements TipoPublicoObjetivoRepository {

    @Inject
    private TipoPublicoObjetivoConverter converter;

    protected TipoPublicoObjetivoRepositoryBean() {
        super(JTipoPublicoObjetivo.class);
    }

    @Override
    public List<TipoPublicoObjetivoGridDTO> findPagedByFiltro(TipoPublicoObjetivoFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTipoPublicoObjetivo = query.getResultList();
        List<TipoPublicoObjetivoGridDTO> tipoNormativa = new ArrayList<>();
        if (jTipoPublicoObjetivo != null) {
            for (Object[] jtipoNom : jTipoPublicoObjetivo) {
                TipoPublicoObjetivoGridDTO tipoNormativaGridDTO = new TipoPublicoObjetivoGridDTO();
                tipoNormativaGridDTO.setCodigo((Long) jtipoNom[0]);
                tipoNormativaGridDTO.setIdentificador((String) jtipoNom[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoNom[2]));
                tipoNormativaGridDTO.setDescripcion(literal);
                tipoNormativaGridDTO.setEmpleadoPublico((boolean) jtipoNom[3]);
                tipoNormativa.add(tipoNormativaGridDTO);
            }
        }
        return tipoNormativa;
    }

    @Override
    public long countByFiltro(TipoPublicoObjetivoFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private Query getQuery(boolean isTotal, TipoPublicoObjetivoFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder(
                    "SELECT count(j) FROM JTipoPublicoObjetivo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
        	sql = new StringBuilder("SELECT j FROM JTipoPublicoObjetivo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.identificador, t.descripcion, j.empleadoPublico FROM JTipoPublicoObjetivo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma  where 1 = 1  ");
        }
        // if (filtro.isRellenoIdUA()) {
        // sql.append(" and j.unidadAdministrativa = :ua");
        // }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.identificador) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro)");
        }
        if (filtro.isRellenoCodigo()) {
        	sql.append(" and j.codigo = :codigo ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
        // if (filtro.isRellenoIdUA()) {
        // query.setParameter("ua", filtro.getIdUA());
        // }
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
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        switch (order) {
            case "descripcion":
                return "t." + order;
            default:
                return "j." + order;
        }
    }

    @Override
    public Optional<JTipoPublicoObjetivo> findById(String id) {
        TypedQuery<JTipoPublicoObjetivo> query =
                entityManager.createNamedQuery(JTipoPublicoObjetivo.FIND_BY_ID, JTipoPublicoObjetivo.class);
        query.setParameter("id", id);
        List<JTipoPublicoObjetivo> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query =
                entityManager.createNamedQuery(JTipoPublicoObjetivo.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() >= 1L;
    }

    @Override
    public List<TipoPublicoObjetivoDTO> findAll() {
        TypedQuery<JTipoPublicoObjetivo> query =
                entityManager.createQuery("SELECT j FROM JTipoPublicoObjetivo j", JTipoPublicoObjetivo.class);
        return converter.createTipPubDTOs(query.getResultList());
    }

	@Override
	public List<TipoPublicoObjetivoDTO> findPagedByFiltroRest(TipoPublicoObjetivoFiltro filtro) {
		Query query = getQuery(false, filtro, true);
		query.setFirstResult(filtro.getPaginaFirst());
		query.setMaxResults(filtro.getPaginaTamanyo());

		List<JTipoPublicoObjetivo> jtipoPublicoObjetivoes = query.getResultList();
		List<TipoPublicoObjetivoDTO> tipoPublicoObjetivoes = new ArrayList<>();
		if (jtipoPublicoObjetivoes != null) {
			for (JTipoPublicoObjetivo jtipoPublicoObjetivo : jtipoPublicoObjetivoes) {
				TipoPublicoObjetivoDTO tipoPublicoObjetivo = converter.createDTO(jtipoPublicoObjetivo);

				tipoPublicoObjetivoes.add(tipoPublicoObjetivo);
			}
		}
		return tipoPublicoObjetivoes;
	}
}
