package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoProcedimiento;
import es.caib.rolsac2.persistence.model.traduccion.JTipoProcedimientoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoProcedimiento y TipoProcedimientoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TipoProcedimientoConverter.class})
public interface TipoProcedimientoConverter extends Converter<JTipoProcedimiento, TipoProcedimientoDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    @Mapping(ignore = true, target = "entidad")
    TipoProcedimientoDTO createDTO(JTipoProcedimiento entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoProcedimiento,dto.getDescripcion()))")
    @Mapping(ignore = true, target = "entidad")
    JTipoProcedimiento createEntity(TipoProcedimientoDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    ///, ignore = true)
    @Mapping(ignore = true, target = "entidad")
    void mergeEntity(@MappingTarget JTipoProcedimiento entity, TipoProcedimientoDTO dto);

    default List<JTipoProcedimientoTraduccion> convierteLiteralToTraduccion(JTipoProcedimiento jtipo, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for (String idioma : descripcion.getIdiomas()) {
            if (descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jtipo.getDescripcion() == null || jtipo.getDescripcion().isEmpty()) {
            jtipo.setDescripcion(JTipoProcedimientoTraduccion.createInstance(idiomasRellenos));
            for (JTipoProcedimientoTraduccion jtrad : jtipo.getDescripcion()) {
                jtrad.setTipoProcedimiento(jtipo);
            }
        } else if (idiomasRellenos.size() > jtipo.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoProcedimientoTraduccion> tradsAux = jtipo.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoProcedimientoTraduccion traduccion : jtipo.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoProcedimientoTraduccion trad = new JTipoProcedimientoTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoProcedimiento(jtipo);
                tradsAux.add(trad);
            }
            jtipo.setDescripcion(tradsAux);
        }
        for (JTipoProcedimientoTraduccion traduccion : jtipo.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoProcedimientoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoProcedimiento().getCodigo()).findFirst().orElse(null));
            for (JTipoProcedimientoTraduccion traduccion : traducciones) {
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
