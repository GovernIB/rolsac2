package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.service.model.PersonalDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JPersonal y PersonalDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {UnidadAdministrativaConverter.class})
public interface PersonalConverter extends Converter<JPersonal, PersonalDTO> {

    @Override
    @Mapping(target = "unidadAdministrativa", qualifiedByName = "createTreeDTO")
    PersonalDTO createDTO(JPersonal entity);

    @Override
    @Mapping(target = "unidadAdministrativa", ignore = true)
    JPersonal createEntity(PersonalDTO dto);

    @Override
    @Mapping(target = "unidadAdministrativa", ignore = true)
    void mergeEntity(@MappingTarget JPersonal entity, PersonalDTO dto);
}
