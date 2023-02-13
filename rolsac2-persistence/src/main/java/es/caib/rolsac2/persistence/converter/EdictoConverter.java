package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import es.caib.rolsac2.service.model.EdictoGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EdictoConverter {

    @Mapping(target = "titulo", expression = "java(convierteMapToLiteral(dto.getEdictoTexto()))")
    @Mapping(target = "url", expression = "java(convierteMapToLiteral(dto.getEdictoUrl()))")
    @Mapping(source = "boletinTipo", target= "tipoBoletin")
    @Mapping(source = "boletinNumero", target = "numeroBoletin")
    @Mapping(target = "fechaBoletin", expression = "java(setFechaBoletin(dto.getBoletinFecha()))")
    @Mapping(source= "edictoNumero", target="numeroRegistro")
    @Mapping(target="codigoTabla", expression = "java(setCodigoTabla())")
    EdictoGridDTO edictoToEdictoGridDTO(Edicto dto);


    default Literal convierteMapToLiteral(Map<String, String> map) {
        Literal literal = Literal.createInstance();

        for(String key : map.keySet()) {
            Traduccion trad = new Traduccion();
            trad.setLiteral(map.get(key));
            trad.setIdioma(key);
            literal.add(trad);
        }

        return literal;
    }

    default String setCodigoTabla() {
        return UUID.randomUUID().toString();
    }

    default LocalDate setFechaBoletin(Date fecha) {
        if ( fecha != null ) {
            LocalDate date = LocalDateTime.ofInstant( fecha.toInstant(), ZoneId.systemDefault() ).toLocalDate();
            return date;
        }
        return null;
    }



}
