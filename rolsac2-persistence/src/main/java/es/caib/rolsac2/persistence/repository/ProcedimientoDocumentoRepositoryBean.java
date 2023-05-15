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

import es.caib.rolsac2.persistence.converter.ProcedimientoDocumentoConverter;
import es.caib.rolsac2.persistence.model.JProcedimientoDocumento;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class  ProcedimientoDocumentoRepositoryBean extends AbstractCrudRepository<JProcedimientoDocumento, Long>
        implements  ProcedimientoDocumentoRepository {

    protected ProcedimientoDocumentoRepositoryBean() {
        super(JProcedimientoDocumento.class);
    }

    @Override
    public Optional<JProcedimientoDocumento> findById(String id) {
        TypedQuery<JProcedimientoDocumento> query =
                entityManager.createNamedQuery(JProcedimientoDocumento.FIND_BY_ID, JProcedimientoDocumento.class);
        query.setParameter("id", id);
        List<JProcedimientoDocumento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<JProcedimientoDocumento> findByProcedimientoId(Long id) {
        TypedQuery<JProcedimientoDocumento> query = entityManager.createNamedQuery(JProcedimientoDocumento.FIND_BY_PROC_ID,
                JProcedimientoDocumento.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Inject
    private ProcedimientoDocumentoConverter converter;

    @Override
    public long countByFiltro(ProcedimientoDocumentoFiltro filtro) {
        return (long) getQuery(true, filtro, false).getSingleResult();
    }

    @Override
    public List<ProcedimientoDocumentoDTO> findPagedByFiltroRest(ProcedimientoDocumentoFiltro filtro) {
  	  	Query query = getQuery(false, filtro, true);
  		query.setFirstResult(filtro.getPaginaFirst());
  		query.setMaxResults(filtro.getPaginaTamanyo());

  		List<JProcedimientoDocumento> jentidades = query.getResultList();
  		List<ProcedimientoDocumentoDTO> entidades = new ArrayList<>();
  		if (jentidades != null) {
  			for (JProcedimientoDocumento jentidad : jentidades) {
  				ProcedimientoDocumentoDTO entidad = jentidad.toModel();

  				entidades.add(entidad);
  			}
  		}
  		return entidades;
    }

    private Query getQuery(boolean isTotal, ProcedimientoDocumentoFiltro filtro, boolean isRest) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("select count(j) from JProcedimientoDocumento j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else if (isRest) {
        	sql = new StringBuilder("SELECT j from JProcedimientoDocumento j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
      	  sql = new StringBuilder("SELECT j from JProcedimientoDocumento j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER(t.titulo) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro " );
        }

        if (filtro.isRellenoDocumento()) {
            sql.append(" and (t.fichero = :documento)");
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
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }

        if (filtro.isRellenoDocumento()) {
      	  query.setParameter("documento", filtro.getDocumento().getCodigo());
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
