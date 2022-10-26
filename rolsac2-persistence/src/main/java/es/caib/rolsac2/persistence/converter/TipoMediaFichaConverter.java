package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoMediaFicha;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMediaFichaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaFichaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoMediaFicha y TipoMediaFichaDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoMediaFichaConverter extends Converter<JTipoMediaFicha, TipoMediaFichaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoMediaFichaDTO createDTO(JTipoMediaFicha entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoMediaFicha,dto.getDescripcion()))")
    JTipoMediaFicha createEntity(TipoMediaFichaDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
///, ignore = true)
    void mergeEntity(@MappingTarget JTipoMediaFicha entity, TipoMediaFichaDTO dto);

    default List<JTipoMediaFichaTraduccion> convierteLiteralToTraduccion(
            JTipoMediaFicha jtipoMediaFicha, Literal descripcion
    ) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jtipoMediaFicha.getDescripcion() == null || jtipoMediaFicha.getDescripcion().isEmpty()) {
            jtipoMediaFicha.setDescripcion(JTipoMediaFichaTraduccion.createInstance(idiomasRellenos));
            for (JTipoMediaFichaTraduccion jtrad : jtipoMediaFicha.getDescripcion()) {
                jtrad.setTipoMediaFicha(jtipoMediaFicha);
            }
        } else if(idiomasRellenos.size() >  jtipoMediaFicha.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoMediaFichaTraduccion> tradsAux = jtipoMediaFicha.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoMediaFichaTraduccion traduccion : jtipoMediaFicha.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoMediaFichaTraduccion trad = new JTipoMediaFichaTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoMediaFicha(jtipoMediaFicha);
                tradsAux.add(trad);
            }
            jtipoMediaFicha.setDescripcion(tradsAux);
        }
        for (JTipoMediaFichaTraduccion traduccion : jtipoMediaFicha.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jtipoMediaFicha.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoMediaFichaTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoMediaFicha().getCodigo()).findFirst().orElse(null));
            for (JTipoMediaFichaTraduccion traduccion : traducciones) {
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
