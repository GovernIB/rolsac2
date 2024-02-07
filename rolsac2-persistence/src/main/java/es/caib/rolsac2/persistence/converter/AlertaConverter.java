package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JAlerta;
import es.caib.rolsac2.persistence.model.traduccion.JAlertaTraduccion;
import es.caib.rolsac2.service.model.AlertaDTO;
import es.caib.rolsac2.service.model.AlertaGridDTO;
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
public interface AlertaConverter extends Converter<JAlerta, AlertaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"traducciones\"))")
    @Mapping(target = "unidadAdministrativa", ignore = true)
    AlertaDTO createDTO(JAlerta entity);

    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"traducciones\"))")
    AlertaGridDTO createGridDTO(JAlerta entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jAlerta,dto.getDescripcion()))")
    @Mapping(target = "unidadAdministrativa", ignore = true)
    JAlerta createEntity(AlertaDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    @Mapping(target = "unidadAdministrativa", ignore = true)
    void mergeEntity(@MappingTarget JAlerta entity, AlertaDTO dto);


    default List<AlertaGridDTO> createGridDTOs(List<JAlerta> entities) {
        List<AlertaGridDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createGridDTO(e)));
        }
        return resultado;
    }


    default List<JAlertaTraduccion> convierteLiteralToTraduccion(JAlerta jAlerta, Literal descripcion) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jAlerta.getTraducciones() == null || jAlerta.getTraducciones().isEmpty()) {
            jAlerta.setTraducciones(JAlertaTraduccion.createInstance(idiomasRellenos));
            for (JAlertaTraduccion jTrad : jAlerta.getTraducciones()) {
                jTrad.setAlerta(jAlerta);
            }
        } else if (idiomasRellenos.size() > jAlerta.getTraducciones().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JAlertaTraduccion> tradsAux = jAlerta.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JAlertaTraduccion traduccion : jAlerta.getTraducciones()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JAlertaTraduccion trad = new JAlertaTraduccion();
                trad.setIdioma(idioma);
                trad.setAlerta(jAlerta);
                tradsAux.add(trad);
            }
            jAlerta.setTraducciones(tradsAux);
        }
        for (JAlertaTraduccion traduccion : jAlerta.getTraducciones()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jAlerta.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JAlertaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getAlerta().getCodigo()).findFirst().orElse(null));

            for (JAlertaTraduccion traduccion : traducciones) {
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
