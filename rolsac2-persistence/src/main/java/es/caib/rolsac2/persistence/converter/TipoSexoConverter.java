package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoSexo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoSexoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSexoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoSexo y TipoSexoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoSexoConverter extends Converter<JTipoSexo, TipoSexoDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoSexoDTO createDTO(JTipoSexo entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoSexo,dto.getDescripcion()))")
    JTipoSexo createEntity(TipoSexoDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
///, ignore = true)
    void mergeEntity(@MappingTarget JTipoSexo entity, TipoSexoDTO dto);

    default List<JTipoSexoTraduccion> convierteLiteralToTraduccion(JTipoSexo jtipoSexo, Literal descripcion) {

        if (jtipoSexo.getDescripcion() == null || jtipoSexo.getDescripcion().isEmpty()) {
            jtipoSexo.setDescripcion(JTipoSexoTraduccion.createInstance());
            for (JTipoSexoTraduccion jtrad : jtipoSexo.getDescripcion()) {
                jtrad.setTipoSexo(jtipoSexo);
            }
        }
        for (JTipoSexoTraduccion traduccion : jtipoSexo.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoSexo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoSexoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoSexo().getId()).findFirst().orElse(null));
            for (JTipoSexoTraduccion traduccion : traducciones) {
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
