package es.caib.rolsac2.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.caib.rolsac2.persistence.converter.DocumentoNormativaConverter;
import es.caib.rolsac2.persistence.model.JDocumentoNormativa;
import es.caib.rolsac2.service.model.DocumentoNormativaDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;


/**
 * Implementación del repositorio de un documento de normativa
 *
 * @author Indra
 */
@Stateless
@Local(DocumentoNormativaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DocumentoNormativaRepositoryBean extends AbstractCrudRepository<JDocumentoNormativa, Long>
    implements DocumentoNormativaRepository {

    @Inject
    private DocumentoNormativaConverter converter;

  protected DocumentoNormativaRepositoryBean() {
    super(JDocumentoNormativa.class);
  }

  @Override
  public long countByFiltro(DocumentoNormativaFiltro filtro) {
      return (long) getQuery(true, filtro, false).getSingleResult();
  }

  private String getOrden(String order) {
    // Se puede hacer un switch/if pero en este caso, con j.+order sobra
    return "j." + order;
  }


  @Override
  public List<JDocumentoNormativa> findDocumentosRelacionados(Long idNormativa) {
    TypedQuery<JDocumentoNormativa> query =
            entityManager.createNamedQuery(JDocumentoNormativa.FIND_BY_NORMATIVA, JDocumentoNormativa.class);
    query.setParameter("codigo", idNormativa);
    List<JDocumentoNormativa> result = query.getResultList();
    return result;
  }


  @Override
  public List<DocumentoNormativaDTO> findPagedByFiltroRest(DocumentoNormativaFiltro filtro) {
	  	Query query = getQuery(false, filtro, true);
		query.setFirstResult(filtro.getPaginaFirst());
		query.setMaxResults(filtro.getPaginaTamanyo());

		List<JDocumentoNormativa> jentidades = query.getResultList();
		List<DocumentoNormativaDTO> entidades = new ArrayList<>();
		if (jentidades != null) {
			for (JDocumentoNormativa jentidad : jentidades) {
				DocumentoNormativaDTO entidad = converter.createDTO(jentidad);

				entidades.add(entidad);
			}
		}
		return entidades;
  }

  private Query getQuery(boolean isTotal, DocumentoNormativaFiltro filtro, boolean isRest) {

      StringBuilder sql;
      if (isTotal) {
          sql = new StringBuilder("select count(j) from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
      } else if (isRest) {
      	sql = new StringBuilder("SELECT j from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
      } else {
    	  sql = new StringBuilder("SELECT j from JDocumentoNormativa j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
      }

      if (filtro.isRellenoTexto()) {
          sql.append(" and (LOWER(t.titulo) LIKE :filtro OR LOWER(t.url) LIKE :filtro OR LOWER(t.descripcion) LIKE :filtro " );
      }

      if (filtro.isRellenoNormativa()) {
          sql.append(" and (j.normativa.codigo = :normativa) ");
      }

      if (filtro.isRellenoDocumento()) {
          sql.append(" and (t.documento.codigo = :documento)");
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

      if (filtro.isRellenoNormativa()) {
    	  query.setParameter("normativa", filtro.getNormativa().getCodigo());
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


}
