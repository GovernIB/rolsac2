package es.caib.rolsac2.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

import es.caib.rolsac2.persistence.model.JTipoFormaInicio;
import es.caib.rolsac2.persistence.model.traduccion.JTipoFormaInicioTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.Traduccion;

/**
 * Conversor entre JTipoFormaInicio y TipoFormaInicioDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoFormaInicioConverter extends Converter<JTipoFormaInicio, TipoFormaInicioDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoFormaInicioDTO createDTO(JTipoFormaInicio entity);

    @Override
    @Mapping(target = "descripcion",
                    expression = "java(convierteLiteralToTraduccion(jTipoFormaInicio,dto.getDescripcion()))")
    JTipoFormaInicio createEntity(TipoFormaInicioDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    /// , ignore = true)
    void mergeEntity(@MappingTarget JTipoFormaInicio entity, TipoFormaInicioDTO dto);

    default List<JTipoFormaInicioTraduccion> convierteLiteralToTraduccion(JTipoFormaInicio jTipoFormaInicio,
                    Literal descripcion) {

        if (jTipoFormaInicio.getDescripcion() == null || jTipoFormaInicio.getDescripcion().isEmpty()) {
            jTipoFormaInicio.setDescripcion(JTipoFormaInicioTraduccion.createInstance());
            for (JTipoFormaInicioTraduccion jtrad : jTipoFormaInicio.getDescripcion()) {
                jtrad.setTipoFormaInicio(jTipoFormaInicio);
            }
        }
        for (JTipoFormaInicioTraduccion traduccion : jTipoFormaInicio.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            // traduccion.setTipoFormaInicio(jTipoMateria);
        }
        return jTipoFormaInicio.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoFormaInicioTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(
                            traducciones.stream().map(t -> t.getTipoFormaInicio().getId()).findFirst().orElse(null));
            for (JTipoFormaInicioTraduccion traduccion : traducciones) {
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
