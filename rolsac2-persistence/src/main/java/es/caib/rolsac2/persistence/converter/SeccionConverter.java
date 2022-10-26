package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JSeccion;
import es.caib.rolsac2.persistence.model.traduccion.JSeccionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.SeccionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EntidadConverter.class})
public interface SeccionConverter extends Converter<JSeccion, SeccionDTO> {

    @Override
    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    SeccionDTO createDTO(JSeccion entity);

    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "padre", expression = "java(createTreeDTOSinPadre(entity.getPadre()))")
    @Named("createTreeDTO")
    SeccionDTO createTreeDTO(JSeccion entity);

    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "padre", ignore = true)
    @Named("createTreeDTOSinPadre")
    SeccionDTO createTreeDTOSinPadre(JSeccion entity);

    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jSeccion,dto))")
    JSeccion createEntity(SeccionDTO dto);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JSeccion entity, SeccionDTO dto);

    default List<SeccionDTO> createDTOs(List<JSeccion> entities) {
        List<SeccionDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createDTO(e)));
        }
        return resultado;
    }

    default List<SeccionDTO> createTreeDTOs(List<JSeccion> entities) {
        List<SeccionDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createTreeDTO(e)));
        }
        return resultado;
    }

    default List<JSeccionTraduccion> convierteLiteralToTraduccion(
            JSeccion jSeccion, SeccionDTO seccionDTO) {

        if (jSeccion.getDescripcion() == null || jSeccion.getDescripcion().isEmpty()) {
            jSeccion.setDescripcion(JSeccionTraduccion.createInstance());
            for (JSeccionTraduccion jtrad : jSeccion.getDescripcion()) {
                jtrad.setSeccion(jSeccion);
            }
        }
        for (JSeccionTraduccion traduccion : jSeccion.getDescripcion()) {
            if (seccionDTO.getNombre() != null) {
                traduccion.setNombre(seccionDTO.getNombre().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jSeccion.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JSeccionTraduccion> traducciones,
                                                 String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getSeccion().getCodigo()).findFirst()
                    .orElse(null));
            for (JSeccionTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                if ("nombre".equals(nombreLiteral)) {
                    literal = traduccion.getNombre();
                } else {
                    literal = null;
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }

        return resultado;
    }
}
