package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.service.model.EntidadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JEntidad y  EntidadDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface EntidadConverter extends Converter<JEntidad, EntidadDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    EntidadDTO createDTO(JEntidad entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    JEntidad createEntity(EntidadDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    void mergeEntity(@MappingTarget JEntidad entity, EntidadDTO dto);
}
