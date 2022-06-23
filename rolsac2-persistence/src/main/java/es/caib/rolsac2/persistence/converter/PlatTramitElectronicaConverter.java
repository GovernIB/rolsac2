package es.caib.rolsac2.persistence.converter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

import es.caib.rolsac2.persistence.model.JPlatTramitElectronica;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;

/**
 * Conversor entre JPlatTramitElectronica y PlatTramitElectronicaDTO. La implementacion se generará automaticamente por
 * MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface PlatTramitElectronicaConverter extends Converter<JPlatTramitElectronica, PlatTramitElectronicaDTO> {

    @Override
    PlatTramitElectronicaDTO createDTO(JPlatTramitElectronica entity);

    @Override
    JPlatTramitElectronica createEntity(PlatTramitElectronicaDTO dto);

    @Override
    void mergeEntity(@MappingTarget JPlatTramitElectronica entity, PlatTramitElectronicaDTO dto);

    default List<PlatTramitElectronicaDTO> toDTOs(List<JPlatTramitElectronica> entities) {
        return entities.stream().map(e -> createDTO(e)).collect(Collectors.toList());
    }
}
