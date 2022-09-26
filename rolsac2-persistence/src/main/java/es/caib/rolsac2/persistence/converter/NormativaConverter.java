package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;


@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EntidadConverter.class, BoletinOficialMapper.class, TipoNormativaConverter.class})
public interface NormativaConverter extends Converter<JNormativa, NormativaDTO>{

    @Override
    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    NormativaDTO createDTO(JNormativa entity);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jNormativa,dto.getNombre()))")
    JNormativa createEntity(NormativaDTO dto);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(entity,dto.getNombre()))")
    void mergeEntity(@MappingTarget JNormativa entity, NormativaDTO dto);

    default Literal convierteTraduccionToLiteral(List<JNormativaTraduccion> traducciones, String nombreLiteral){
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getNormativa().getCodigo()).findFirst()
                    .orElse(null));
            for (JNormativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                if ("nombre".equals(nombreLiteral)) {
                    literal = traduccion.getTitulo();
                } else {
                    literal = null;
                }
                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }

    default List<JNormativaTraduccion> convierteLiteralToTraduccion(
            JNormativa jNormativa, Literal nombre){
        if ( jNormativa.getDescripcion() == null ||jNormativa.getDescripcion().isEmpty()) {
            jNormativa.setDescripcion(JNormativaTraduccion.createInstance());
            for (JNormativaTraduccion jtrad : jNormativa.getDescripcion()) {
                jtrad.setNormativa(jNormativa);
            }
        }
        for (JNormativaTraduccion traduccion : jNormativa.getDescripcion()) {
            if (nombre != null) {
                traduccion.setTitulo(nombre.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jNormativa.getDescripcion();
    }
}
