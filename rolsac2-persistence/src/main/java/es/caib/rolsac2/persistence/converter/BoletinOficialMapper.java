package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JBoletinOficial;
import es.caib.rolsac2.service.model.BoletinOficialDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BoletinOficialMapper extends Converter<JBoletinOficial, BoletinOficialDTO>{
    @Override
    BoletinOficialDTO createDTO(JBoletinOficial entity);

    @Override
    JBoletinOficial createEntity(BoletinOficialDTO dto);

    @Override
    void mergeEntity(@MappingTarget JBoletinOficial entity, BoletinOficialDTO dto);

}
