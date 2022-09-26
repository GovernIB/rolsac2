package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JAfectacion;
import es.caib.rolsac2.service.model.AfectacionDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TipoAfectacionConverter.class})
public interface AfectacionConverter extends Converter<JAfectacion, AfectacionDTO>{

    @Override
    AfectacionDTO createDTO(JAfectacion entity);

    @Override
    JAfectacion createEntity(AfectacionDTO dto);

    @Override
    void mergeEntity(@MappingTarget JAfectacion entity, AfectacionDTO dto);
}
