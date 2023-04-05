package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEntidadRaiz;
import es.caib.rolsac2.service.model.EntidadRaizDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {UnidadAdministrativaConverter.class})
public interface EntidadRaizConverter extends Converter<JEntidadRaiz, EntidadRaizDTO> {

    @Override
    @Mapping(target = "ua", qualifiedByName = "createTreeDTO")
    EntidadRaizDTO createDTO(JEntidadRaiz entity);

    @Override
    @Mapping(target = "ua", ignore = true)
    JEntidadRaiz createEntity(EntidadRaizDTO dto);

    @Override
    @Mapping(target = "ua", ignore = true)
    void mergeEntity(@MappingTarget JEntidadRaiz entity, EntidadRaizDTO dto);
}
