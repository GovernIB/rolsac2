package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JTipoTramitacion y TipoTramitacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {PlatTramitElectronicaConverter.class})
public interface TipoTramitacionConverter extends Converter<JTipoTramitacion, TipoTramitacionDTO> {

    @Override
    @Mapping(target = "entidad", ignore = true)
    TipoTramitacionDTO createDTO(JTipoTramitacion entity);

    @Override
    @Mapping(target = "entidad", ignore = true)
    JTipoTramitacion createEntity(TipoTramitacionDTO dto);

    @Override
    @Mapping(target = "entidad", ignore = true)
    void mergeEntity(@MappingTarget JTipoTramitacion entity, TipoTramitacionDTO dto);
}
