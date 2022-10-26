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

import java.util.ArrayList;
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
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }
        if (jtipoMediaEdificio.getDescripcion() == null || jtipoMediaEdificio.getDescripcion().isEmpty()) {
            jtipoMediaEdificio.setDescripcion(JTipoMediaEdificioTraduccion.createInstance(idiomasRellenos));
            for (JTipoMediaEdificioTraduccion jtrad : jtipoMediaEdificio.getDescripcion()) {
                jtrad.setTipoMediaEdificio(jtipoMediaEdificio);
            }
        } else if(idiomasRellenos.size() >  jtipoMediaEdificio.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoMediaEdificioTraduccion> tradsAux = jtipoMediaEdificio.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoMediaEdificioTraduccion traduccion : jtipoMediaEdificio.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoMediaEdificioTraduccion trad = new JTipoMediaEdificioTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoMediaEdificio(jtipoMediaEdificio);
                tradsAux.add(trad);
            }
            jtipoMediaEdificio.setDescripcion(tradsAux);
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
