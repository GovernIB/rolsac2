package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JDocumentoNormativa;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


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


  protected DocumentoNormativaRepositoryBean() {
    super(JDocumentoNormativa.class);
  }


  private Query getQuery(boolean isTotal, PlatTramitElectronicaFiltro filtro) {

    StringBuilder sql;
    if(isTotal) {
      sql = new StringBuilder("SELECT count(j) FROM JPlatTramitElectronica j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma WHERE 1 = 1 ");
    } else {
      sql = new StringBuilder("SELECT j.codigo, j.identificador, j.codEntidad, t.descripcion, t.urlAcceso"
              + " FROM JPlatTramitElectronica j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma WHERE t.idioma=:idioma");
    }
    if (filtro.isRellenoTexto()) {
      sql.append(" and ( (cast(j.codigo as string)) like :filtro OR LOWER(j.identificador) LIKE :filtro " +
              " OR LOWER(j.codEntidad.identificador) LIKE :filtro  ) ");
    }
    if (filtro.getOrderBy() != null) {
      sql.append(" order by " + getOrden(filtro.getOrderBy()));
      sql.append(filtro.isAscendente() ? " asc " : " desc ");
    }
    Query query = entityManager.createQuery(sql.toString());

    if (filtro.isRellenoTexto()) {
      query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
    }
    if (filtro.isRellenoCodigo()) {
      query.setParameter("codigo", "%" + filtro.getCodigo());
    }
    if (filtro.isRellenoIdentificador()) {
      query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
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
  public List<JDocumentoNormativa> findDocumentosRelacionados(Long idNormativa) {
    TypedQuery<JDocumentoNormativa> query =
            entityManager.createNamedQuery(JDocumentoNormativa.FIND_BY_NORMATIVA, JDocumentoNormativa.class);
    query.setParameter("codigo", idNormativa);
    List<JDocumentoNormativa> result = query.getResultList();
    return result;
  }




}
