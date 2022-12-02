package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoTramitacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoTramitacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoTramitacion y TipoTramitacionDTO. La implementacion se
 * generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        PlatTramitElectronicaConverter.class, EntidadConverter.class})
public interface TipoTramitacionConverter extends Converter<JTipoTramitacion, TipoTramitacionDTO> {
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    @Mapping(target = "url", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"url\"))")
    TipoTramitacionDTO createDTO(JTipoTramitacion entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jTipoTramitacion, dto))")
    JTipoTramitacion createEntity(TipoTramitacionDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JTipoTramitacion entity, TipoTramitacionDTO dto);

    /*
     * @Override
     *
     * @Mapping(target = "entidad", ignore = true) TipoTramitacionDTO
     * createDTO(JTipoTramitacion entity);
     *
     * @Override
     *
     * @Mapping(target = "entidad", ignore = true) JTipoTramitacion
     * createEntity(TipoTramitacionDTO dto);
     *
     * @Override
     *
     * @Mapping(target = "entidad", ignore = true) void mergeEntity(@MappingTarget
     * JTipoTramitacion entity, TipoTramitacionDTO dto);
     */

    @Named("createTipoTramitacionDTOs")
    default List<TipoTramitacionDTO> createTipoTramitacionDTOs(List<JTipoTramitacion> entities) {
        List<TipoTramitacionDTO> dtos = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> dtos.add(createDTO(e)));
        }
        return dtos;
    }

    default List<JTipoTramitacionTraduccion> convierteLiteralToTraduccion(JTipoTramitacion jTipoTramitacion,
                                                                          TipoTramitacionDTO dto) {

        List<String> idiomasPermitidos = List.of(dto.getEntidad().getIdiomasPermitidos().split(";"));

        if (jTipoTramitacion.getTraducciones() == null || jTipoTramitacion.getTraducciones().isEmpty()) {
            jTipoTramitacion.setTraducciones(JTipoTramitacionTraduccion.createInstance(idiomasPermitidos));
            for (JTipoTramitacionTraduccion jtrad : jTipoTramitacion.getTraducciones()) {
                jtrad.setTipoTramitacion(jTipoTramitacion);
            }
        } else if (jTipoTramitacion.getTraducciones().size() < idiomasPermitidos.size()) {
            // En caso de que se haya creado, comprobamos que tenga todas las traducciones
            // (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JTipoTramitacionTraduccion> tradsAux = jTipoTramitacion.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JTipoTramitacionTraduccion traduccion : jTipoTramitacion.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            // Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JTipoTramitacionTraduccion trad = new JTipoTramitacionTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoTramitacion(jTipoTramitacion);
                tradsAux.add(trad);
            }
            jTipoTramitacion.setTraducciones(tradsAux);
        }
        for (JTipoTramitacionTraduccion traduccion : jTipoTramitacion.getTraducciones()) {
            if (dto.getDescripcion() != null) {
                traduccion.setDescripcion(dto.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getUrl() != null) {
                traduccion.setUrl(dto.getUrl().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoTramitacion.getTraducciones();

    }

    default Literal convierteTraduccionToLiteral(List<JTipoTramitacionTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(
                    traducciones.stream().map(t -> t.getTipoTramitacion().getCodigo()).findFirst().orElse(null));
            for (JTipoTramitacionTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                switch (nombreLiteral) {
                    case "descripcion":
                        literal = traduccion.getDescripcion();
                        break;
                    case "url":
                        literal = traduccion.getUrl();
                        break;
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }

        return resultado;
    }
}
