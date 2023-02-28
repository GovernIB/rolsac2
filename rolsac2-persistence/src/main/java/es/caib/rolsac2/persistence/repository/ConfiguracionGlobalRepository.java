package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de las operaciones básicas sobre Configuración Global
 *
 * @author jrodrigof
 */
public interface ConfiguracionGlobalRepository extends CrudRepository<JConfiguracionGlobal, Long> {

    Optional<JConfiguracionGlobal> findById(String id);

    List<ConfiguracionGlobalGridDTO> findPagedByFiltro(ConfiguracionGlobalFiltro filtro);

    long countByFiltro(ConfiguracionGlobalFiltro filtro);

    /**
     * Obtiene una configuracion global según la propiedad
     *
     * @param propiedad
     * @return
     */
    ConfiguracionGlobalGridDTO findByPropiedad(String propiedad);
}
