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

import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

/**
 * Implementación del repositorio de Personal.
 *
 * @author jsegovia
 */
@Stateless
@Local(EntidadRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EntidadRepositoryBean extends AbstractCrudRepository<JEntidad, Long> implements EntidadRepository {

  protected EntidadRepositoryBean() {
    super(JEntidad.class);
  }

  @Inject
  private EntidadConverter converter;

  @Override
  public List<EntidadGridDTO> findPagedByFiltro(EntidadFiltro filtro) {
    Query query = getQuery(false, filtro);
    query.setFirstResult(filtro.getPaginaFirst());
    query.setMaxResults(filtro.getPaginaTamanyo());

    List<Object[]> jEntidades = query.getResultList();
    List<EntidadGridDTO> entidad = new ArrayList<>();
    if (jEntidades != null) {
      for (Object[] jEntidad : jEntidades) {
        EntidadGridDTO entidadGridDTO = new EntidadGridDTO();
        entidadGridDTO.setId((Long) jEntidad[0]);
        entidadGridDTO.setDir3((String) jEntidad[1]);
        entidadGridDTO.setActiva((Boolean) jEntidad[2]);
        entidadGridDTO.setRolAdmin((String) jEntidad[3]);
        entidadGridDTO.setRolAdminContenido((String) jEntidad[4]);
        entidadGridDTO.setRolGestor((String) jEntidad[5]);
        entidadGridDTO.setRolInformador((String) jEntidad[6]);
        entidad.add(entidadGridDTO);
      }
    }
    return entidad;
  }

  @Override
  public List<EntidadDTO> findActivas() {
    TypedQuery<JEntidad> query =
        entityManager.createQuery("SELECT j FROM JEntidad j where j.activa = true", JEntidad.class);

    List<JEntidad> jEntidad = query.getResultList();
    List<EntidadDTO> entidad = new ArrayList<>();
    if (jEntidad != null) {
      for (JEntidad jentidad : jEntidad) {
        entidad.add(this.converter.createDTO(jentidad));
      }
    }
    return entidad;
  }

  @Override
  public long countByFiltro(EntidadFiltro filtro) {
    return (long) getQuery(true, filtro).getSingleResult();
  }

  private Query getQuery(boolean isTotal, EntidadFiltro filtro) {

    StringBuilder sql;
    if (isTotal) {
      sql = new StringBuilder("SELECT count(j) FROM JEntidad j where 1 = 1 ");
    } else {
      sql = new StringBuilder("SELECT j.id, j.dir3, j.activa, j.rolAdmin, j.rolAdminContenido, "
          + " j.rolGestor, j.rolInformador, j.logo FROM JEntidad j where 1 = 1 ");
    }
    // if (filtro.isRellenoIdUA()) {
    // sql.append(" and j.unidadAdministrativa = :ua");
    // }
    if (filtro.isRellenoTexto()) {
      sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.dir3) LIKE :filtro "
          + " OR LOWER(cast(j.activa as string)) LIKE :filtro OR LOWER(j.rolAdmin) LIKE :filtro "
          + " OR LOWER(j.rolAdminContenido) LIKE :filtro OR LOWER(j.rolGestor) LIKE :filtro "
          + " OR LOWER(j.rolInformador) LIKE :filtro)");
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
      query.setParameter("filtro", "%" + filtro.getTexto() + "%");
    }
    return query;
  }

  private String getOrden(String order) {
    // Se puede hacer un switch/if pero en este caso, con j.+order sobra
    return "j." + order;
  }

  @Override
  public Optional<JEntidad> findById(String id) {
    TypedQuery<JEntidad> query = entityManager.createNamedQuery(JEntidad.FIND_BY_ID, JEntidad.class);
    query.setParameter("id", id);
    List<JEntidad> result = query.getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
  }
}
