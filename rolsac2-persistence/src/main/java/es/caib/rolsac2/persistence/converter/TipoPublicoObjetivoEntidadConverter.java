package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoEntidadTraduccion;
import es.caib.rolsac2.service.model.*;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JEdificio y EdificioDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {EntidadConverter.class, TipoPublicoObjetivoConverter.class})
public interface TipoPublicoObjetivoEntidadConverter
                extends Converter<JTipoPublicoObjetivoEntidad, TipoPublicoObjetivoEntidadDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    TipoPublicoObjetivoEntidadDTO createDTO(JTipoPublicoObjetivoEntidad entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jTipoPublicoObjetivoEntidad, dto))")
    JTipoPublicoObjetivoEntidad createEntity(TipoPublicoObjetivoEntidadDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity, dto))")
    @Mapping(target = "tipo", ignore = true)
    void mergeEntity(@MappingTarget JTipoPublicoObjetivoEntidad entity, TipoPublicoObjetivoEntidadDTO dto);

    default List<JTipoPublicoObjetivoEntidadTraduccion> convierteLiteralToTraduccion(JTipoPublicoObjetivoEntidad jTipoPublicoObjetivoEntidad, TipoPublicoObjetivoEntidadDTO tipoPublicoObjetivoEntidadDTO) {
        List<String> idiomasPermitidos = List.of(tipoPublicoObjetivoEntidadDTO.getEntidad().getIdiomasPermitidos().split(";"));

        //Comprobamos si aún no se ha creado la entidad
        if (jTipoPublicoObjetivoEntidad.getTraducciones() == null || jTipoPublicoObjetivoEntidad.getTraducciones().isEmpty()) {
            jTipoPublicoObjetivoEntidad.setTraducciones(JTipoPublicoObjetivoEntidadTraduccion.createInstance(idiomasPermitidos));
            for (JTipoPublicoObjetivoEntidadTraduccion jTrad : jTipoPublicoObjetivoEntidad.getTraducciones()) {
                jTrad.setTipoPublicoObjetivoEntidad(jTipoPublicoObjetivoEntidad);
            }
        } else if (jTipoPublicoObjetivoEntidad.getTraducciones().size() < idiomasPermitidos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JTipoPublicoObjetivoEntidadTraduccion> tradsAux = jTipoPublicoObjetivoEntidad.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JTipoPublicoObjetivoEntidadTraduccion traduccion : jTipoPublicoObjetivoEntidad.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JTipoPublicoObjetivoEntidadTraduccion trad = new JTipoPublicoObjetivoEntidadTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoPublicoObjetivoEntidad(jTipoPublicoObjetivoEntidad);
                tradsAux.add(trad);
            }
            jTipoPublicoObjetivoEntidad.setTraducciones(tradsAux);
        }

        for (JTipoPublicoObjetivoEntidadTraduccion traduccion : jTipoPublicoObjetivoEntidad.getTraducciones()) {
            if (tipoPublicoObjetivoEntidadDTO.getDescripcion() != null) {

                traduccion.setDescripcion(tipoPublicoObjetivoEntidadDTO.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoPublicoObjetivoEntidad.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoPublicoObjetivoEntidadTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoPublicoObjetivoEntidad().getCodigo()).findFirst().orElse(null));

            for (JTipoPublicoObjetivoEntidadTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = "";
                if (nombreLiteral.equals("descripcion")) {
                    literal = traduccion.getDescripcion();
                }
                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }
}

