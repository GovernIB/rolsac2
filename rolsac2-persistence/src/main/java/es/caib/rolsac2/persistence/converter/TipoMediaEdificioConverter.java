package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoMediaEdificio;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaEdificioTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaEdificioDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface TipoMediaEdificioConverter extends Converter<JTipoMediaEdificio, TipoMediaEdificioDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteTraduccionToLiteral(entity.getEntidad()))")
    TipoMediaEdificioDTO createDTO(JTipoMediaEdificio entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoMediaEdificio,dto.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteLiteralToTraduccion(jTipoMediaEdificio,dto.getEntidad()))")
    JTipoMediaEdificio createEntity(TipoMediaEdificioDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteLiteralToTraduccion(entity,dto.getEntidad()))")
    void mergeEntity(@MappingTarget JTipoMediaEdificio entity, TipoMediaEdificioDTO dto);

    default List<JTipoMediaEdificioTraduccion> convierteLiteralToTraduccion(
            JTipoMediaEdificio jtipoMediaEdificio, Literal descripcion
    ) {

        if (jtipoMediaEdificio.getDescripcion() == null || jtipoMediaEdificio.getDescripcion().isEmpty()) {
            jtipoMediaEdificio.setDescripcion(JTipoMediaEdificioTraduccion.createInstance());
            for (JTipoMediaEdificioTraduccion jtrad : jtipoMediaEdificio.getDescripcion()) {
                jtrad.setTipoMediaEdificio(jtipoMediaEdificio);
            }
        }
        for (JTipoMediaEdificioTraduccion traduccion : jtipoMediaEdificio.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoMediaEdificio.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoMediaEdificioTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoMediaEdificio().getCodigo()).findFirst().orElse(null));
            for (JTipoMediaEdificioTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }
}
