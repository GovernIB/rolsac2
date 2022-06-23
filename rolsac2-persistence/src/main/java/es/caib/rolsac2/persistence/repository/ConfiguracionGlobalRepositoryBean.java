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

import es.caib.rolsac2.persistence.converter.ConfiguracionGlobalConverter;
import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;

/**
 * Implementación del repositorio de Configuración Global.
 *
 * @author jrodrigof
 */
@Stateless
@Local(ConfiguracionGlobalRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConfiguracionGlobalRepositoryBean extends AbstractCrudRepository<JConfiguracionGlobal, Long>
    implements ConfiguracionGlobalRepository {

  protected ConfiguracionGlobalRepositoryBean() {
    super(JConfiguracionGlobal.class);
  }

  @Inject
  private ConfiguracionGlobalConverter converter;

  @Override
  public List<ConfiguracionGlobalGridDTO> findPagedByFiltro(ConfiguracionGlobalFiltro filtro) {
    Query query = getQuery(false, filtro);
    query.setFirstResult(filtro.getPaginaFirst());
    query.setMaxResults(filtro.getPaginaTamanyo());

    List<Object[]> jConfiguracionesGlobal = query.getResultList();
    List<ConfiguracionGlobalGridDTO> configuracionGlobal = new ArrayList<>();
    if (jConfiguracionesGlobal != null) {
      for (Object[] jConfiguracionGlobal : jConfiguracionesGlobal) {
        ConfiguracionGlobalGridDTO configuracionGlobalGridDTO = new ConfiguracionGlobalGridDTO();
        configuracionGlobalGridDTO.setId((Long) jConfiguracionGlobal[0]);
        configuracionGlobalGridDTO.setPropiedad((String) jConfiguracionGlobal[1]);
        configuracionGlobalGridDTO.setValor((String) jConfiguracionGlobal[2]);
        configuracionGlobalGridDTO.setDescripcion((String) jConfiguracionGlobal[3]);
        configuracionGlobalGridDTO.setNoModificable((Boolean) jConfiguracionGlobal[4]);

        configuracionGlobal.add(configuracionGlobalGridDTO);
      }
    }
    return configuracionGlobal;
  }

  @Override
  public long countByFiltro(ConfiguracionGlobalFiltro filtro) {
    return (long) getQuery(true, filtro).getSingleResult();
  }

  private Query getQuery(boolean isTotal, ConfiguracionGlobalFiltro filtro) {

    StringBuilder sql;
    if (isTotal) {
      sql = new StringBuilder("SELECT count(j) FROM JConfiguracionGlobal j where 1 = 1 ");
    } else {
      sql = new StringBuilder(
          "SELECT j.id, j.propiedad, j.valor, j.descripcion, j.noModificable FROM JConfiguracionGlobal j where 1 = 1 ");
    }
    // if (filtro.isRellenoIdUA()) {
    // sql.append(" and j.unidadAdministrativa = :ua");
    // }
    if (filtro.isRellenoTexto()) {
      sql.append(
          " and ( cast(j.id as string) like :filtro OR LOWER(j.propiedad) LIKE :filtro OR LOWER(j.descripcion) LIKE :filtro "
              + " OR LOWER(cast(j.noModificable as string)) LIKE :filtro )");
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
  public Optional<JConfiguracionGlobal> findById(String id) {
    TypedQuery<JConfiguracionGlobal> query =
        entityManager.createNamedQuery(JConfiguracionGlobal.FIND_BY_ID, JConfiguracionGlobal.class);
    query.setParameter("id", id);
    List<JConfiguracionGlobal> result = query.getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
  }
}
