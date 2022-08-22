package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JEntidad y EntidadDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface EntidadConverter extends Converter<JEntidad, EntidadDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    // @Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    EntidadDTO createDTO(JEntidad entity);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jEntidad,dto.getDescripcion()))")
    JEntidad createEntity(EntidadDTO dto);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JEntidad entity, EntidadDTO dto);

    default List<JEntidadTraduccion> convierteLiteralToTraduccion(JEntidad jEntidad, Literal descripcion) {
        if (jEntidad.getDescripcion() == null || jEntidad.getDescripcion().isEmpty()) {
            jEntidad.setDescripcion(JEntidadTraduccion.createInstance(jEntidad));
            for (JEntidadTraduccion jent : jEntidad.getDescripcion()) {
                jent.setEntidad(jEntidad);
            }
        }
        for (JEntidadTraduccion traduccion : jEntidad.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jEntidad.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JEntidadTraduccion> traducciones) {
        Literal resultado = Literal.createInstance();

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado.setCodigo(traducciones.stream().map(t -> t.getEntidad().getCodigo()).findFirst().orElse(null));
            for (JEntidadTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(Long.valueOf(traduccion.getCodigo()));
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }
        return resultado;
    }
}
