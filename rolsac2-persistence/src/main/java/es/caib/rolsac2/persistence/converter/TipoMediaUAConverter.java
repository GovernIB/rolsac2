package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoMediaUA;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaUATraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaUADTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface TipoMediaUAConverter extends Converter<JTipoMediaUA, TipoMediaUADTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteTraduccionToLiteral(entity.getEntidad()))")
    TipoMediaUADTO createDTO(JTipoMediaUA entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoMediaUA,dto.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteLiteralToTraduccion(jTipoMediaUA,dto.getEntidad()))")
    JTipoMediaUA createEntity(TipoMediaUADTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
        //@Mapping(target = "entidad", expression = "java(convierteLiteralToTraduccion(entity,dto.getEntidad()))")
    void mergeEntity(@MappingTarget JTipoMediaUA entity, TipoMediaUADTO dto);

    default List<JTipoMediaUATraduccion> convierteLiteralToTraduccion(JTipoMediaUA jtipoMediaUA, Literal descripcion) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jtipoMediaUA.getDescripcion() == null || jtipoMediaUA.getDescripcion().isEmpty()) {
            jtipoMediaUA.setDescripcion(JTipoMediaUATraduccion.createInstance(idiomasRellenos));
            for (JTipoMediaUATraduccion jtrad : jtipoMediaUA.getDescripcion()) {
                jtrad.setTipoMediaUA(jtipoMediaUA);
            }
        } else if(idiomasRellenos.size() >  jtipoMediaUA.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoMediaUATraduccion> tradsAux = jtipoMediaUA.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoMediaUATraduccion traduccion : jtipoMediaUA.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoMediaUATraduccion trad = new JTipoMediaUATraduccion();
                trad.setIdioma(idioma);
                trad.setTipoMediaUA(jtipoMediaUA);
                tradsAux.add(trad);
            }
            jtipoMediaUA.setDescripcion(tradsAux);
        }
        for (JTipoMediaUATraduccion traduccion : jtipoMediaUA.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoMediaUA.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoMediaUATraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoMediaUA().getCodigo()).findFirst().orElse(null));
            for (JTipoMediaUATraduccion traduccion : traducciones) {
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
