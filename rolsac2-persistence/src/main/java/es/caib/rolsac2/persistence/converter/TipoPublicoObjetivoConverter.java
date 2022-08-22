package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoPublicoObjetivo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoPublicoObjetivoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoPublicoObjetivo y  TipoPublicoObjetivoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface TipoPublicoObjetivoConverter extends Converter<JTipoPublicoObjetivo, TipoPublicoObjetivoDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoPublicoObjetivoDTO createDTO(JTipoPublicoObjetivo entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoPublicoObjetivo,dto.getDescripcion()))")
    JTipoPublicoObjetivo createEntity(TipoPublicoObjetivoDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JTipoPublicoObjetivo entity, TipoPublicoObjetivoDTO dto);

    default List<JTipoPublicoObjetivoTraduccion> convierteLiteralToTraduccion(JTipoPublicoObjetivo jTipoPublicoObjetivo, Literal descripcion) {

        if (jTipoPublicoObjetivo.getDescripcion() == null || jTipoPublicoObjetivo.getDescripcion().isEmpty()) {
            jTipoPublicoObjetivo.setDescripcion(JTipoPublicoObjetivoTraduccion.createInstance());
            for (JTipoPublicoObjetivoTraduccion jtrad : jTipoPublicoObjetivo.getDescripcion()) {
                jtrad.setTipoPublicoObjetivo(jTipoPublicoObjetivo);
            }
        }
        for (JTipoPublicoObjetivoTraduccion traduccion : jTipoPublicoObjetivo.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoPublicoObjetivo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoPublicoObjetivoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoPublicoObjetivo().getCodigo()).findFirst().orElse(null));
            for (JTipoPublicoObjetivoTraduccion traduccion : traducciones) {
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
