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

import es.caib.rolsac2.persistence.converter.ProcedimientoTramiteConverter;
import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class  ProcedimientoTramiteRepositoryBean extends AbstractCrudRepository<JProcedimientoTramite, Long>
        implements  ProcedimientoTramiteRepository {

    @Inject
    private ProcedimientoTramiteConverter converter;

    protected ProcedimientoTramiteRepositoryBean() {
        super(JProcedimientoTramite.class);
    }

    @Override
    public Optional<JProcedimientoTramite> findById(String id) {
        TypedQuery<JProcedimientoTramite> query =
                entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_ID, JProcedimientoTramite.class);
        query.setParameter("id", id);
        List<JProcedimientoTramite> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<JProcedimientoTramite> findByProcedimientoId(Long id) {
        TypedQuery<JProcedimientoTramite> query = entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_PROC_ID,
                JProcedimientoTramite.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

	@Override
	public List<ProcedimientoTramiteDTO> findPagedByFiltroRest(ProcedimientoTramiteFiltro filtro) {
		Query query = getQuery(false, filtro, true);
  		query.setFirstResult(filtro.getPaginaFirst());
  		query.setMaxResults(filtro.getPaginaTamanyo());

  		List<JProcedimientoTramite> jentidades = query.getResultList();
  		List<ProcedimientoTramiteDTO> entidades = new ArrayList<>();
  		if (jentidades != null) {
  			for (JProcedimientoTramite jentidad : jentidades) {
  				ProcedimientoTramiteDTO entidad = converter.createDTO(jentidad);

  				entidades.add(entidad);
  			}
  		}
  		return entidades;
	}

	@Override
	public long countByFiltro(ProcedimientoTramiteFiltro filtro) {
		return (long) getQuery(true, filtro, false).getSingleResult();
	}

	 private Query getQuery(boolean isTotal, ProcedimientoTramiteFiltro filtro, boolean isRest) {
	        StringBuilder sql;
	        if (isTotal) {
	            sql = new StringBuilder("select count(j) from JProcedimientoTramite j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
	        } else if (isRest) {
	        	sql = new StringBuilder("SELECT j from JProcedimientoTramite j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
	        } else {
	      	  sql = new StringBuilder("SELECT j from JProcedimientoTramite j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
	        }

	        if (filtro.isRellenoTexto()) {
	            sql.append(" and (LOWER(t.requisitos) LIKE :filtro "
	            		+ "OR LOWER(t.nombre) LIKE :filtro "
	            		+ "OR LOWER(t.documentacion) LIKE :filtro "
	            		+ "OR LOWER(t.observacion) LIKE :filtro "
	            		+ "OR LOWER(t.terminoMaximo) LIKE :filtro "
	            		);
	        }

	        if (filtro.isRellenoCodigo()) {
	        	sql.append(" and j.codigo = :codigo ");
	        }

//			"\"orden\":0," 					+ Constantes.SALTO_LINEA +
//			"\"fase\":0," 					+ Constantes.SALTO_LINEA +
//			"\"fechaPublicacion\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
//			"\"fechaCierre\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +
//			"\"codigoUnidadAdministrativa\":0," + Constantes.SALTO_LINEA +
//			"\"codigoProcedimiento\":0," + Constantes.SALTO_LINEA +
//			"\"codigoTipoTramitacion\":0," + Constantes.SALTO_LINEA +
//			"\"fechaInicio\":\"DD/MM/YYYY\"," + Constantes.SALTO_LINEA +

	        if (filtro.getOrderBy() != null) {
	            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
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

	        if (filtro.getOrderBy() != null) {
	            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
	            sql.append(filtro.isAscendente() ? " asc " : " desc ");
	        }

	        return query;
	    }

	 private String getOrden(String order) {
	        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
	        return "j." + order;
	    }
}
