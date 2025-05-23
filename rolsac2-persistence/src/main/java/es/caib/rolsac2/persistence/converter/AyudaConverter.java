package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JAyuda;
import es.caib.rolsac2.persistence.model.traduccion.JAyudaTraduccion;
import es.caib.rolsac2.service.model.AyudaDTO;
import es.caib.rolsac2.service.model.AyudaGridDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class, UnidadAdministrativaConverter.class})
public interface AyudaConverter extends Converter<JAyuda, AyudaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"traducciones\"))")
    AyudaDTO createDTO(JAyuda entity);

    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"traducciones\"))")
    AyudaGridDTO createGridDTO(JAyuda entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jAyuda,dto.getDescripcion()))")
    JAyuda createEntity(AyudaDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JAyuda entity, AyudaDTO dto);


    default List<AyudaGridDTO> createGridDTOs(List<JAyuda> entities) {
        List<AyudaGridDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createGridDTO(e)));
        }
        return resultado;
    }


    default List<JAyudaTraduccion> convierteLiteralToTraduccion(JAyuda jAyuda, Literal descripcion) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jAyuda.getTraducciones() == null || jAyuda.getTraducciones().isEmpty()) {
            jAyuda.setTraducciones(JAyudaTraduccion.createInstance(idiomasRellenos));
            for (JAyudaTraduccion jTrad : jAyuda.getTraducciones()) {
                jTrad.setAyuda(jAyuda);
            }
        } else if (idiomasRellenos.size() > jAyuda.getTraducciones().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JAyudaTraduccion> tradsAux = jAyuda.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JAyudaTraduccion traduccion : jAyuda.getTraducciones()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JAyudaTraduccion trad = new JAyudaTraduccion();
                trad.setIdioma(idioma);
                trad.setAyuda(jAyuda);
                tradsAux.add(trad);
            }
            jAyuda.setTraducciones(tradsAux);
        }
        for (JAyudaTraduccion traduccion : jAyuda.getTraducciones()) {
            if (descripcion.getTraduccion(traduccion.getIdioma()) != null && !descripcion.getTraduccion(traduccion.getIdioma()).isEmpty()) {
                traduccion.setHtml(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jAyuda.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JAyudaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getAyuda().getCodigo()).findFirst().orElse(null));

            for (JAyudaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = traduccion.getHtml();

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }
}
