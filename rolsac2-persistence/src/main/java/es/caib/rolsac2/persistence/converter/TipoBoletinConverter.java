package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoBoletin;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TipoBoletinConverter extends Converter<JTipoBoletin, TipoBoletinDTO> {

    @Override
    TipoBoletinDTO createDTO(JTipoBoletin entity);

    @Override
    JTipoBoletin createEntity(TipoBoletinDTO dto);

    @Override
    void mergeEntity(@MappingTarget JTipoBoletin entity, TipoBoletinDTO dto);


}
