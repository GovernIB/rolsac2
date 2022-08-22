package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.pk.JUsuarioUnidadAdministrativaPK;
import es.caib.rolsac2.service.model.UsuarioUnidadAdministrativaPKDTO;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UsuarioUnidadAdministrativaPKConverter
                extends Converter<JUsuarioUnidadAdministrativaPK, UsuarioUnidadAdministrativaPKDTO> {
    @Override
    UsuarioUnidadAdministrativaPKDTO createDTO(JUsuarioUnidadAdministrativaPK entity);

    @Override
    JUsuarioUnidadAdministrativaPK createEntity(UsuarioUnidadAdministrativaPKDTO dto);

    @Override
    void mergeEntity(@MappingTarget JUsuarioUnidadAdministrativaPK entity, UsuarioUnidadAdministrativaPKDTO dto);
}
