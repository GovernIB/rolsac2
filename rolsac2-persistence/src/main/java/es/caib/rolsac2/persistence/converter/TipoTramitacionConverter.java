package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.UsuarioUnidadAdministrativaDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

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

    @Named("createTipoTramitacionDTOs")
    default List<TipoTramitacionDTO> createTipoTramitacionDTOs(List<JTipoTramitacion> entities) {
        List<TipoTramitacionDTO> dtos = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> dtos.add(createDTO(e)));
        }
        return dtos;
    }
}
