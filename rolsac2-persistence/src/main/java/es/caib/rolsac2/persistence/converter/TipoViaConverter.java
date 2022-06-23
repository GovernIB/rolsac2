package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoVia;
import es.caib.rolsac2.persistence.model.traduccion.JTipoViaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoVia y TipoViaDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoViaConverter extends Converter<JTipoVia, TipoViaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoViaDTO createDTO(JTipoVia entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoVia,dto.getDescripcion()))")
    JTipoVia createEntity(TipoViaDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
///, ignore = true)
    void mergeEntity(@MappingTarget JTipoVia entity, TipoViaDTO dto);

    default List<JTipoViaTraduccion> convierteLiteralToTraduccion(JTipoVia jtipoVia, Literal descripcion) {

        if (jtipoVia.getDescripcion() == null || jtipoVia.getDescripcion().isEmpty()) {
            jtipoVia.setDescripcion(JTipoViaTraduccion.createInstance());
            for (JTipoViaTraduccion jtrad : jtipoVia.getDescripcion()) {
                jtrad.setTipoVia(jtipoVia);
            }
        }
        for (JTipoViaTraduccion traduccion : jtipoVia.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoVia.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoViaTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoVia().getId()).findFirst().orElse(null));
            for (JTipoViaTraduccion traduccion : traducciones) {
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
