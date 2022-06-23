package es.caib.rolsac2.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import es.caib.rolsac2.persistence.model.JConfiguracionGlobal;
import es.caib.rolsac2.service.model.ConfiguracionGlobalDTO;

/**
 * Conversor entre JConfiguracionGlobal y ConfiguracionGlobalDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jrodrigof
 */
@Mapper
public interface ConfiguracionGlobalConverter extends Converter<JConfiguracionGlobal, ConfiguracionGlobalDTO> {

  // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
  // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
  // @Mapping(target = "idUnitat", source = "unitatOrganica.id")
  @Override
  ConfiguracionGlobalDTO createDTO(JConfiguracionGlobal entity);

  // @Mapping(target = "unidadOrganica", ignore = true)
  @Override
  JConfiguracionGlobal createEntity(ConfiguracionGlobalDTO dto);

  // @Mapping(target = "unidadOrganica", ignore = true)
  @Override
  void mergeEntity(@MappingTarget JConfiguracionGlobal entity, ConfiguracionGlobalDTO dto);
}
