package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JIndexacion;
import es.caib.rolsac2.service.model.IndexacionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JIndexacion y IndexacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface IndexacionConverter extends Converter<JIndexacion, IndexacionDTO> {

    @Override
    @Mapping(target = "entidad", ignore = true)
    IndexacionDTO createDTO(JIndexacion entity);

    @Override
    @Mapping(target = "entidad", ignore = true)
    JIndexacion createEntity(IndexacionDTO dto);

    @Override
    @Mapping(target = "entidad", ignore = true)
    void mergeEntity(@MappingTarget JIndexacion entity, IndexacionDTO dto);

}
