package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.service.model.PluginDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface PluginConverter extends Converter<JPlugin, PluginDto> {

    @Override
    PluginDto createDTO(JPlugin entity);

    @Override
    JPlugin createEntity(PluginDto dto);

    @Override
    void mergeEntity(@MappingTarget JPlugin entity, PluginDto dto);
}
