package es.caib.rolsac2.persistence.repository;

import java.util.List;
import java.util.Optional;

import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.ConfiguracionGlobalDTO;
import es.caib.rolsac2.service.model.ConfiguracionGlobalGridDTO;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.EntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.ConfiguracionGlobalFiltro;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;

/**
 * Interface de las operaciones básicas sobre Configuración Global
 *
 * @author jrodrigof
 */
public interface ConfiguracionGlobalRepository extends CrudRepository<JConfiguracionGlobal, Long> {

    Optional<JConfiguracionGlobal> findById(String id);

    List<ConfiguracionGlobalGridDTO> findPagedByFiltro(ConfiguracionGlobalFiltro filtro);

    long countByFiltro(ConfiguracionGlobalFiltro filtro);
}
