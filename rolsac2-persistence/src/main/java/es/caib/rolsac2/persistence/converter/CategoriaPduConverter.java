package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JCategoriaPDU;
import es.caib.rolsac2.persistence.model.traduccion.JCategoriaPDUTraduccion;
import es.caib.rolsac2.service.model.CategoriaPDUDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface CategoriaPduConverter extends Converter<JCategoriaPDU, CategoriaPDUDTO> {

    @Override
    //@Mapping(target = "codigo", source = "codigo")
    //@Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    CategoriaPDUDTO createDTO(JCategoriaPDU entity);

    @Override
    //@Mapping(target = "codigo", source = "codigo")
    //@Mapping(target = "orden", source = "orden")
    //@Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jCategoriaPDU, dto.getDescripcion()))")
    JCategoriaPDU createEntity(CategoriaPDUDTO dto);

    @Override
    //@Mapping(target = "codigo", source = "codigo")
    //@Mapping(target = "orden", source = "orden")
    //@Mapping(target = "identificador", source = "identificador")
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JCategoriaPDU entity, CategoriaPDUDTO dto);

    default List<CategoriaPDUDTO> createDTOs(List<JCategoriaPDU> entities) {
        List<CategoriaPDUDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createDTO(e)));
        }
        return resultado;
    }

    default List<JCategoriaPDUTraduccion> convierteLiteralToTraduccion(JCategoriaPDU jCategoriaPDU, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jCategoriaPDU == null) {
            jCategoriaPDU = new JCategoriaPDU();
        }
        if (jCategoriaPDU.getDescripcion() == null || jCategoriaPDU.getDescripcion().isEmpty()) {
            jCategoriaPDU.setDescripcion(JCategoriaPDUTraduccion.createInstance(idiomasRellenos));
            for (JCategoriaPDUTraduccion jtrad : jCategoriaPDU.getDescripcion()) {
                jtrad.setCategoriaPDU(jCategoriaPDU);
            }
        } else if (idiomasRellenos.size() > jCategoriaPDU.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JCategoriaPDUTraduccion> tradsAux = jCategoriaPDU.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JCategoriaPDUTraduccion traduccion : jCategoriaPDU.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JCategoriaPDUTraduccion trad = new JCategoriaPDUTraduccion();
                trad.setIdioma(idioma);
                trad.setCategoriaPDU(jCategoriaPDU);
                tradsAux.add(trad);
            }
            jCategoriaPDU.setDescripcion(tradsAux);
        }
        for (JCategoriaPDUTraduccion traduccion : jCategoriaPDU.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jCategoriaPDU.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JCategoriaPDUTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getCategoriaPDU().getCodigo()).findFirst().orElse(null));
            for (JCategoriaPDUTraduccion traduccion : traducciones) {
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