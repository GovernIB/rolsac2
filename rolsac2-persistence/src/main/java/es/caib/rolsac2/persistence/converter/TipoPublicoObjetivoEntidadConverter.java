package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JEdificio y EdificioDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {EntidadConverter.class, TipoPublicoObjetivoConverter.class})
public interface TipoPublicoObjetivoEntidadConverter
                extends Converter<JTipoPublicoObjetivoEntidad, TipoPublicoObjetivoEntidadDTO> {

    @Override
    TipoPublicoObjetivoEntidadDTO createDTO(JTipoPublicoObjetivoEntidad entity);

    @Override
    JTipoPublicoObjetivoEntidad createEntity(TipoPublicoObjetivoEntidadDTO dto);

    @Override
    void mergeEntity(@MappingTarget JTipoPublicoObjetivoEntidad entity, TipoPublicoObjetivoEntidadDTO dto);

}

