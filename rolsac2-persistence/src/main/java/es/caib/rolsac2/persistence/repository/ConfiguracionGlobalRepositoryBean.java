package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.ConfiguracionGlobalConverter;
import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;

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
                configuracionGlobalGridDTO.setCodigo((Long) jConfiguracionGlobal[0]);
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

    @Override
    public ConfiguracionGlobalGridDTO findByPropiedad(String propiedad) {
        ConfiguracionGlobalFiltro filtro = new ConfiguracionGlobalFiltro();
        filtro.setPropiedad(propiedad);
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jConfiguracionesGlobal = query.getResultList();
        if (jConfiguracionesGlobal != null && !jConfiguracionesGlobal.isEmpty()) {
            Object[] jConfiguracionGlobal = jConfiguracionesGlobal.get(0);
            ConfiguracionGlobalGridDTO configuracionGlobalGridDTO = new ConfiguracionGlobalGridDTO();
            configuracionGlobalGridDTO.setCodigo((Long) jConfiguracionGlobal[0]);
            configuracionGlobalGridDTO.setPropiedad((String) jConfiguracionGlobal[1]);
            configuracionGlobalGridDTO.setValor((String) jConfiguracionGlobal[2]);
            //configuracionGlobalGridDTO.setDescripcion((String) jConfiguracionGlobal[3]);
            //configuracionGlobalGridDTO.setNoModificable((Boolean) jConfiguracionGlobal[4]);
            return configuracionGlobalGridDTO;
        }
        return null;
    }

    private Query getQuery(boolean isTotal, ConfiguracionGlobalFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JConfiguracionGlobal j where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.propiedad, j.valor, j.descripcion, j.noModificable FROM JConfiguracionGlobal j where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.codigo as string) like :filtro OR LOWER(j.propiedad) LIKE :filtro "
                    + " OR LOWER(j.valor) LIKE :filtro OR LOWER(j.descripcion) LIKE :filtro "
                    + " OR LOWER(cast(j.noModificable as string)) LIKE :filtro )");
        }
        if (filtro.isRellenoPropiedad()) {
            sql.append(" and  j.propiedad like :propiedad ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto() + "%");
        }
        if (filtro.isRellenoPropiedad()) {
            query.setParameter("propiedad", filtro.getPropiedad());
        }
        return query;
    }

    private String getOrden(String order) {
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
