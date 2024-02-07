package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TemaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class, TipoMateriaSIAConverter.class})
public interface TemaConverter extends Converter<JTema, TemaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    TemaDTO createDTO(JTema entity);

    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "temaPadre", expression = "java(createTreeDTOSinPadre(entity.getTemaPadre()))")
    @Named("createTreeDTO")
    TemaDTO createTreeDTO(JTema entity);

    @Mapping(target = "entidad", source = "entidad.codigo")
    @Mapping(target = "temaPadre", source = "temaPadre.identificador")
    @Mapping(target = "mathPath", source = "mathPath")
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    TemaGridDTO createGridDTO(JTema entity);

    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "temaPadre", ignore = true)
    @Named("createTreeDTOSinPadre")
    TemaDTO createTreeDTOSinPadre(JTema entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTema,dto.getDescripcion()))")
    @Mapping(target = "mathPath", ignore = true)
    JTema createEntity(TemaDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    @Mapping(target = "mathPath", ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "temaPadre", ignore = true)
    @Mapping(target = "tipoMateriaSIA", ignore = true)
    void mergeEntity(@MappingTarget JTema entity, TemaDTO dto);

    default List<TemaDTO> createTreeDTOs(List<JTema> entities) {
        List<TemaDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createTreeDTO(e)));
        }
        return resultado;
    }

    default List<TemaGridDTO> createGridDTOs(List<JTema> entities) {
        List<TemaGridDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createGridDTO(e)));
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

    default List<JTemaTraduccion> convierteLiteralToTraduccion(JTema jTema, Literal descripcion) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jTema.getDescripcion() == null || jTema.getDescripcion().isEmpty()) {
            jTema.setDescripcion(JTemaTraduccion.createInstance(idiomasRellenos));
            for (JTemaTraduccion jTrad : jTema.getDescripcion()) {
                jTrad.setTema(jTema);
            }
        } else if (idiomasRellenos.size() > jTema.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTemaTraduccion> tradsAux = jTema.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTemaTraduccion traduccion : jTema.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTemaTraduccion trad = new JTemaTraduccion();
                trad.setIdioma(idioma);
                trad.setTema(jTema);
                tradsAux.add(trad);
            }
            jTema.setDescripcion(tradsAux);
        }
        for (JTemaTraduccion traduccion : jTema.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
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
