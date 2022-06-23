package es.caib.rolsac2.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoAfectacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.Traduccion;

/**
 * Conversor entre JTipoAfectacion y TipoAfectacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoAfectacionConverter extends Converter<JTipoAfectacion, TipoAfectacionDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoAfectacionDTO createDTO(JTipoAfectacion entity);

    @Override
    @Mapping(target = "descripcion",
                    expression = "java(convierteLiteralToTraduccion(jTipoAfectacion,dto.getDescripcion()))")
    JTipoAfectacion createEntity(TipoAfectacionDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    /// , ignore = true)
    void mergeEntity(@MappingTarget JTipoAfectacion entity, TipoAfectacionDTO dto);

    default List<JTipoAfectacionTraduccion> convierteLiteralToTraduccion(JTipoAfectacion jtipoMateria,
                    Literal descripcion) {

        if (jtipoMateria.getDescripcion() == null || jtipoMateria.getDescripcion().isEmpty()) {
            jtipoMateria.setDescripcion(JTipoAfectacionTraduccion.createInstance());
            for (JTipoAfectacionTraduccion jtrad : jtipoMateria.getDescripcion()) {
                jtrad.setTipoAfectacion(jtipoMateria);
            }
        }
        for (JTipoAfectacionTraduccion traduccion : jtipoMateria.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            // traduccion.setTipoAfectacion(jTipoMateria);
        }
        return jtipoMateria.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoAfectacionTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoAfectacion().getId()).findFirst().orElse(null));
            for (JTipoAfectacionTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getId());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }
}
