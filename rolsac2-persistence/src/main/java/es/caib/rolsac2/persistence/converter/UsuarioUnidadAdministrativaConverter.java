package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.service.model.UsuarioUnidadAdministrativaDTO;

import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {UsuarioUnidadAdministrativaPKConverter.class, UnidadAdministrativaConverter.class,
                                UsuarioConverter.class})
public interface UsuarioUnidadAdministrativaConverter
                extends Converter<JUsuarioUnidadAdministrativa, UsuarioUnidadAdministrativaDTO> {


    @Override
    @Mapping(target = "usuario", qualifiedByName = "createDTOSinUsuarioUnidadAdministrativa")
    UsuarioUnidadAdministrativaDTO createDTO(JUsuarioUnidadAdministrativa entity);

    @Override
    JUsuarioUnidadAdministrativa createEntity(UsuarioUnidadAdministrativaDTO dto);

    @Override
    void mergeEntity(@MappingTarget JUsuarioUnidadAdministrativa entity, UsuarioUnidadAdministrativaDTO dto);

    @Named("createDTOs")
    default List<UsuarioUnidadAdministrativaDTO> createDTOs(List<JUsuarioUnidadAdministrativa> entities) {
        List<UsuarioUnidadAdministrativaDTO> dtos = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> dtos.add(createDTO(e)));
        }
        return dtos;
    }
}
