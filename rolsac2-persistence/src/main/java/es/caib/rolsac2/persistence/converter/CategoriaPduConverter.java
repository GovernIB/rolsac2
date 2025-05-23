package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JCategoriaPdu;
import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.service.model.CategoriaPduDto;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CategoriaPduConverter extends Converter<JCategoriaPdu, CategoriaPduDto> {

    @Override
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "orden", source = "orden")
    @Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", source = "descripcion")
    CategoriaPduDto createDTO(JCategoriaPdu entity);

    @Override
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "orden", source = "orden")
    @Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", source = "descripcion")
    JCategoriaPdu createEntity(CategoriaPduDto dto);

    @Override
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "orden", source = "orden")
    @Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", source = "descripcion")
    void mergeEntity(@MappingTarget JCategoriaPdu entity, CategoriaPduDto dto);

    default List<CategoriaPduDto> createDTOs(List<JCategoriaPdu> entities) {
        List<CategoriaPduDto> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createDTO(e)));
        }
        return resultado;
    }
}