package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.service.model.PluginGridDTO;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Local(PluginRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PluginRepositoryBean extends AbstractCrudRepository<JPlugin, Long> implements PluginRepository {

    protected PluginRepositoryBean() {
        super(JPlugin.class);
    }

    @Override
    public Optional<JPlugin> findById(String id) {
        TypedQuery<JPlugin> query = entityManager.createNamedQuery(JPlugin.FIND_BY_ID, JPlugin.class);
        query.setParameter("id", id);
        List<JPlugin> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }

    @Override
    public List<PluginGridDTO> findPagedByFiltro(PluginFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jPlugins = query.getResultList();
        List<PluginGridDTO> plugin = new ArrayList<>();
        if (jPlugins != null) {
            for (Object[] jPlugin : jPlugins) {
                PluginGridDTO pluginGridDTO = new PluginGridDTO();
                pluginGridDTO.setCodigo((Long) jPlugin[0]);
                pluginGridDTO.setEntidad(((JEntidad) jPlugin[1]).getDescripcion(filtro.getIdioma()));
                pluginGridDTO.setDescripcion((String) jPlugin[2]);
                pluginGridDTO.setClassname((String) jPlugin[3]);
                pluginGridDTO.setPropiedades((String) jPlugin[4]);
                pluginGridDTO.setTipo((String) jPlugin[5]);
                pluginGridDTO.setPrefijoPropiedades((String) jPlugin[6]);

                plugin.add(pluginGridDTO);
            }
        }

        return plugin;
    }

    @Override
    public long countByFiltro(PluginFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    private Query getQuery(boolean isTotal, PluginFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JPlugin j where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.descripcion, j.classname, j.propiedades, j.tipo, j.prefijoPropiedades FROM JPlugin j where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and ( cast(j.id as string) like :filtro OR LOWER(j.descripcion) LIKE :filtro " +
                    " OR LOWER(j.classname) like :filtro OR LOWER(j.propiedades) like :filtro " +
                    " OR LOWER(j.tipo) like :filtro  ) ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by " + getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }
        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }

        return query;

    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public List<JPlugin> listPluginsByEntidad(Long idEntidad) {
        Query query = null;
        String sql = "SELECT p FROM JPlugin p where p.entidad.codigo =: idEntidad";
        query = entityManager.createQuery(sql, JPlugin.class);
        query.setParameter("idEntidad", idEntidad);
        return query.getResultList();
    }

    @Override
    public boolean existePluginTipo(Long codigoPlugin, String tipo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(p) FROM JPlugin p where p.tipo = :tipo ");
        if (codigoPlugin != null) {
            sql.append(" and p.codigo != :codigoPlugin");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("tipo", tipo);
        if (codigoPlugin != null) {
            query.setParameter("codigoPlugin", codigoPlugin);
        }
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }
}
