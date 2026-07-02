package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.service.model.SesionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Conversor entre JSesion y SesionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface SesionConverter extends Converter<JSesion, SesionDTO> {

    @Override
    SesionDTO createDTO(JSesion entity);

    @Override
    JSesion createEntity(SesionDTO dto);

    @Override
    void mergeEntity(@MappingTarget JSesion entity, SesionDTO dto);


    List<SesionDTO> toDTOs(List<JSesion> entities);

}
