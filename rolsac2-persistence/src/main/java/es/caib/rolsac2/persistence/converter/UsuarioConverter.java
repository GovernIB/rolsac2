package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.service.model.UsuarioDTO;

import org.mapstruct.*;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {EntidadConverter.class, UsuarioUnidadAdministrativaConverter.class})
public interface UsuarioConverter extends Converter<JUsuario, UsuarioDTO> {

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", qualifiedByName = "createDTOs")
    UsuarioDTO createDTO(JUsuario entity);

    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    @Named("createDTOSinUsuarioUnidadAdministrativa")
    UsuarioDTO createDTOSinUsuarioUnidadAdministrativa(JUsuario entity);

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    JUsuario createEntity(UsuarioDTO dto);

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    void mergeEntity(@MappingTarget JUsuario entity, UsuarioDTO dto);
}
