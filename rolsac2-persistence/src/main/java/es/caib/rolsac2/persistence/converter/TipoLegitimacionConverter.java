package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoLegitimacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoLegitimacion y TipoLegitimacionDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoLegitimacionConverter extends Converter<JTipoLegitimacion, TipoLegitimacionDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoLegitimacionDTO createDTO(JTipoLegitimacion entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoLegitimacion,dto.getDescripcion()))")
    JTipoLegitimacion createEntity(TipoLegitimacionDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
///, ignore = true)
    void mergeEntity(@MappingTarget JTipoLegitimacion entity, TipoLegitimacionDTO dto);

    default List<JTipoLegitimacionTraduccion> convierteLiteralToTraduccion(
            JTipoLegitimacion jtipoLegitimacion, Literal descripcion
    ) {

        if (jtipoLegitimacion.getDescripcion() == null || jtipoLegitimacion.getDescripcion().isEmpty()) {
            jtipoLegitimacion.setDescripcion(JTipoLegitimacionTraduccion.createInstance());
            for (JTipoLegitimacionTraduccion jtrad : jtipoLegitimacion.getDescripcion()) {
                jtrad.setTipoLegitimacion(jtipoLegitimacion);
            }
        }
        for (JTipoLegitimacionTraduccion traduccion : jtipoLegitimacion.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoLegitimacion.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoLegitimacionTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(
                    traducciones.stream().map(t -> t.getTipoLegitimacion().getCodigo()).findFirst().orElse(null));
            for (JTipoLegitimacionTraduccion traduccion : traducciones) {
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
