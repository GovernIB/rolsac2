package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EntidadConverter.class})
public interface TemaConverter extends Converter<JTema, TemaDTO>{

    @Override
    @Mapping(target = "descripcion",
        expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    TemaDTO createDTO(JTema entity);

    @Mapping(target = "descripcion",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "temaPadre", expression = "java(createTreeDTOSinPadre(entity.getTemaPadre()))")
    @Named("createTreeDTO")
    TemaDTO createTreeDTO(JTema entity);

    @Mapping(target = "descripcion",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "temaPadre", ignore = true)
    @Named("createTreeDTOSinPadre")
    TemaDTO createTreeDTOSinPadre(JTema entity);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jTema,dto))")
    JTema createEntity(TemaDTO dto);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JTema entity, TemaDTO dto);

    default List<TemaDTO> createTreeDTOs(List<JTema> entities) {
        List<TemaDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createTreeDTO(e)));
        }
        return resultado;
    }

    default List<TemaDTO> createDTOs(List<JTema> entities) {
        List<TemaDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createTreeDTO(e)));
        }
        return resultado;
    }

    default List<JTemaTraduccion> convierteLiteralToTraduccion(JTema jTema, TemaDTO temaDTO) {
        if (jTema.getDescripcion() == null || jTema.getDescripcion().isEmpty()){
            jTema.setDescripcion(JTemaTraduccion.createInstance());
            for (JTemaTraduccion jTrad : jTema.getDescripcion()) {
                jTrad.setTema(jTema);
            }
        }
        for (JTemaTraduccion traduccion : jTema.getDescripcion()) {
            if (temaDTO.getDescripcion() != null) {
                traduccion.setDescripcion(temaDTO.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTema.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTemaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTema().getCodigo()).findFirst().orElse(null));

            for (JTemaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = traduccion.getDescripcion();

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }
}
