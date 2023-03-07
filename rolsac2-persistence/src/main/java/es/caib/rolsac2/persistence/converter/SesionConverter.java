package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JSesion;
import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoAfectacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.SesionDTO;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}
