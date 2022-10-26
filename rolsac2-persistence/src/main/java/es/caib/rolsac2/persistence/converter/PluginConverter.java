package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PluginDto;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.utils.UtilJSON;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface PluginConverter extends Converter<JPlugin, PluginDto> {

    @Override
    @Mapping(target = "propiedades", expression = "java(convierteJSONtoLista(entity.getPropiedades()))")
    PluginDto createDTO(JPlugin entity);

    @Override
    @Mapping(target = "propiedades", expression = "java(convierteListaToJSON(dto.getPropiedades()))")
    JPlugin createEntity(PluginDto dto);

    @Override
    @Mapping(target = "propiedades", expression = "java(convierteListaToJSON(dto.getPropiedades()))")
    void mergeEntity(@MappingTarget JPlugin entity, PluginDto dto);

    default List<Propiedad> convierteJSONtoLista(String json) {
    	return (List<Propiedad>) UtilJSON.fromListJSON(json, Propiedad.class);
    }

    default String convierteListaToJSON(List<Propiedad> lista) {
    	return UtilJSON.toJSON(lista);
    }
}
