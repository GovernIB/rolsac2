package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.TipoSilencioAdministrativoConverter;
import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoSilencioAdministrativoFiltro;

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
@Local(TipoSilencioAdministrativoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoSilencioAdministrativoRepositoryBean extends AbstractCrudRepository<JTipoSilencioAdministrativo, Long>
        implements TipoSilencioAdministrativoRepository {

    @Inject
    private TipoSilencioAdministrativoConverter converter;

    protected TipoSilencioAdministrativoRepositoryBean() {
        super(JTipoSilencioAdministrativo.class);
    }

    @Override
    public List<TipoSilencioAdministrativoGridDTO> findPagedByFiltro(TipoSilencioAdministrativoFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jTipoSilencioAdministrativo = query.getResultList();
        List<TipoSilencioAdministrativoGridDTO> tipoSilencioAdministrativo = new ArrayList<>();
        if (jTipoSilencioAdministrativo != null) {
            for (Object[] jtipoNom : jTipoSilencioAdministrativo) {
                TipoSilencioAdministrativoGridDTO tipoSilencioAdministrativoGridDTO = new TipoSilencioAdministrativoGridDTO();
                tipoSilencioAdministrativoGridDTO.setCodigo((Long) jtipoNom[0]);
                tipoSilencioAdministrativoGridDTO.setIdentificador((String) jtipoNom[1]);
                Literal literal = new Literal();
                literal.add(new Traduccion(filtro.getIdioma(), (String) jtipoNom[2]));
                tipoSilencioAdministrativoGridDTO.setDescripcion(literal);

                tipoSilencioAdministrativo.add(tipoSilencioAdministrativoGridDTO);
            }
        }
        return tipoSilencioAdministrativo;
    }

    @Override
    public long countByFiltro(TipoSilencioAdministrativoFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    private Query getQuery(boolean isTotal, TipoSilencioAdministrativoFiltro filtro, boolean isRest) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JTipoSilencioAdministrativo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1  ");
        } else if (isRest) {
        	sql = new StringBuilder(
                    "SELECT j FROM JTipoSilencioAdministrativo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.identificador, t.descripcion FROM JTipoSilencioAdministrativo j LEFT OUTER JOIN j.descripcion t ON t.idioma=:idioma where 1 = 1  ");
        }
        //        if (filtro.isRellenoIdUA()) {
        //            sql.append(" and j.unidadAdministrativa = :ua");
        //        }
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
        //        if (filtro.isRellenoIdUA()) {
        //            query.setParameter("ua", filtro.getIdUA());
        //        }
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
        switch (order) {
            case "descripcion":
                return "t." + order;
            default:
                return "j." + order;
        }
    }

    @Override
    public Optional<JTipoSilencioAdministrativo> findById(String id) {
        TypedQuery<JTipoSilencioAdministrativo> query = entityManager.createNamedQuery(JTipoSilencioAdministrativo.FIND_BY_ID, JTipoSilencioAdministrativo.class);
        query.setParameter("id", id);
        List<JTipoSilencioAdministrativo> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public Boolean checkIdentificador(String identificador) {
        TypedQuery<Long> query = entityManager.createNamedQuery(JTipoSilencioAdministrativo.COUNT_BY_IDENTIFICADOR, Long.class);
        query.setParameter("identificador", identificador);
        return query.getSingleResult().longValue() == 1L;
    }

    @Override
    public List<TipoSilencioAdministrativoDTO> findAllTipoSilencio() {
        TypedQuery query =
                entityManager.createQuery("SELECT j FROM JTipoSilencioAdministrativo j", JTipoSilencioAdministrativo.class);
        List<JTipoSilencioAdministrativo> jTipoSilencioAdministrativos = query.getResultList();
        List<TipoSilencioAdministrativoDTO> tipoSilencioAdministrativoDTOS = new ArrayList<>();
        if (jTipoSilencioAdministrativos != null) {
            for (JTipoSilencioAdministrativo jTipoSilencioAdministrativo : jTipoSilencioAdministrativos) {
                tipoSilencioAdministrativoDTOS.add(this.converter.createDTO(jTipoSilencioAdministrativo));
            }
        }
        return tipoSilencioAdministrativoDTOS;
    }

	@Override
	public List<TipoSilencioAdministrativoDTO> findPagedByFiltroRest(TipoSilencioAdministrativoFiltro filtro) {
		Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<JTipoSilencioAdministrativo> jtipoSilencioAdministrativoes = query.getResultList();
        List<TipoSilencioAdministrativoDTO> tipoSilencioAdministrativoes = new ArrayList<>();
        if (jtipoSilencioAdministrativoes != null) {
            for (JTipoSilencioAdministrativo jtipoSilencioAdministrativo : jtipoSilencioAdministrativoes) {
                TipoSilencioAdministrativoDTO tipoSilencioAdministrativo = converter.createDTO(jtipoSilencioAdministrativo);

                tipoSilencioAdministrativoes.add(tipoSilencioAdministrativo);
            }
        }
        return tipoSilencioAdministrativoes;
	}
}
