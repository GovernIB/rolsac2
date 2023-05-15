package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.service.model.PluginGridDTO;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;

import java.util.List;
import java.util.Optional;

public interface PluginRepository extends CrudRepository<JPlugin, Long> {
    Optional<JPlugin> findById(String id);

    List<PluginGridDTO> findPagedByFiltro(PluginFiltro filtro);

    long countByFiltro(PluginFiltro filtro);

    List<JPlugin> listPluginsByEntidad(Long idEntidad);

    boolean existePluginTipo(Long codigoPlugin, String tipo);

    boolean existePluginTipoByEntidad(Long idEntidad, String tipo);

    void deleteByUA(Long id);
}
