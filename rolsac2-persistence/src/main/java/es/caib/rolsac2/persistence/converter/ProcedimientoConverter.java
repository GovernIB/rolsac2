package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JProcedimiento;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {NormativaConverter.class, UnidadAdministrativaConverter.class, TipoPublicoObjetivoConverter.class,
        TipoPublicoObjetivoConverter.class, TipoSilencioAdministrativoConverter.class, TipoFormaInicioConverter.class,
        TipoLegitimacionConverter.class})
public interface ProcedimientoConverter extends Converter<JProcedimiento, ProcedimientoDTO>{

    @Override
    ProcedimientoDTO createDTO(JProcedimiento entity);

    @Override
    JProcedimiento createEntity(ProcedimientoDTO dto);

    @Override
    void mergeEntity(@MappingTarget JProcedimiento entity, ProcedimientoDTO dto);


}
