package es.caib.rolsac2.persistence.converter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;

/**
 * Conversor entre JTipoTramitacion y TipoTramitacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {PlatTramitElectronicaConverter.class})
public interface TipoTramitacionConverter extends Converter<JTipoTramitacion, TipoTramitacionDTO> {

    @Override
    TipoTramitacionDTO createDTO(JTipoTramitacion entity);

    @Override
    JTipoTramitacion createEntity(TipoTramitacionDTO dto);

    @Override
    void mergeEntity(@MappingTarget JTipoTramitacion entity, TipoTramitacionDTO dto);
}
