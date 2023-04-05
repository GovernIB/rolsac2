package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUnidadOrganica;
import es.caib.rolsac2.service.model.UnidadOrganicaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre JTipoAfectacion y TipoAfectacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface UnidadOrganicaConverter extends Converter<JUnidadOrganica, UnidadOrganicaDTO> {

    @Override
    @Mapping(source = "entidad.codigo", target="idEntidad")
    @Mapping(target = "denominacionDir3", expression = "java(convertDenominacionDir3(entity))")
    UnidadOrganicaDTO createDTO(JUnidadOrganica entity);

    @Override
    @Mapping(target = "entidad", ignore = true)
    JUnidadOrganica createEntity(UnidadOrganicaDTO dto);

    @Override
    @Mapping(target = "entidad", ignore = true)
    void mergeEntity(@MappingTarget JUnidadOrganica entity, UnidadOrganicaDTO dto);

    default String convertDenominacionDir3(JUnidadOrganica entity) {
        return entity.getDenominacion() + " (" + entity.getCodigoDir3() + ")";
    }

}
