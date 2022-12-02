package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPlugin;
import es.caib.rolsac2.service.model.PluginDTO;
import es.caib.rolsac2.service.model.Propiedad;
import es.caib.rolsac2.service.utils.UtilJSON;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.inject.Named;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface PluginConverter extends Converter<JPlugin, PluginDTO> {

    @Override
    @Mapping(target = "propiedades", expression = "java(convierteJSONtoLista(entity.getPropiedades()))")
    PluginDTO createDTO(JPlugin entity);

    @Override
    @Mapping(target = "prefijoPropiedades", source = "prefijoPropiedades")
    @Mapping(target = "propiedades", expression = "java(convierteListaToJSON(dto.getPropiedades()))")
    JPlugin createEntity(PluginDTO dto);

    @Override
    @Mapping(target = "prefijoPropiedades", source = "prefijoPropiedades")
    @Mapping(target = "propiedades", expression = "java(convierteListaToJSON(dto.getPropiedades()))")
    void mergeEntity(@MappingTarget JPlugin entity, PluginDTO dto);

    default List<Propiedad> convierteJSONtoLista(String json) {
    	return (List<Propiedad>) UtilJSON.fromListJSON(json, Propiedad.class);
    }

    default String convierteListaToJSON(List<Propiedad> lista) {
    	return UtilJSON.toJSON(lista);
    }

    @Named("createDTOs")
    default List<PluginDTO> createDTOs(List<JPlugin> jPlugins) {
        if(jPlugins != null) {
            List<PluginDTO> plugins = new ArrayList<>();
            for(JPlugin jPlugin : jPlugins) {
                PluginDTO plugin = createDTO(jPlugin);
                plugins.add(plugin);
            }
            return plugins;
        } else {
            return null;
        }
    }
}
