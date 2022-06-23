package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.service.model.PersonalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JPersonal y PersonalDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface PersonalConverter extends Converter<JPersonal, PersonalDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "unidadAdministrativa", ignore = true)
    PersonalDTO createDTO(JPersonal entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "unidadAdministrativa", ignore = true)
    JPersonal createEntity(PersonalDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "unidadAdministrativa", ignore = true)
    void mergeEntity(@MappingTarget JPersonal entity, PersonalDTO dto);
}
