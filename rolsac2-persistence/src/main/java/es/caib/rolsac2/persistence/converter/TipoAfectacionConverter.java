package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoAfectacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoAfectacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    void mergeEntity(@MappingTarget JTipoAfectacion entity, TipoAfectacionDTO dto);

    default List<JTipoAfectacionTraduccion> convierteLiteralToTraduccion(JTipoAfectacion jTipoAfectacion,
                                                                         Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }
        if (jTipoAfectacion.getDescripcion() == null || jTipoAfectacion.getDescripcion().isEmpty()) {
            jTipoAfectacion.setDescripcion(JTipoAfectacionTraduccion.createInstance(idiomasRellenos));
            for (JTipoAfectacionTraduccion jtrad : jTipoAfectacion.getDescripcion()) {
                jtrad.setTipoAfectacion(jTipoAfectacion);
            }
        } else if(idiomasRellenos.size() >  jTipoAfectacion.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoAfectacionTraduccion> tradsAux = jTipoAfectacion.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoAfectacionTraduccion traduccion : jTipoAfectacion.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoAfectacionTraduccion trad = new JTipoAfectacionTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoAfectacion(jTipoAfectacion);
                tradsAux.add(trad);
            }
            jTipoAfectacion.setDescripcion(tradsAux);
        }
        for (JTipoAfectacionTraduccion traduccion : jTipoAfectacion.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoAfectacion.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoAfectacionTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoAfectacion().getCodigo()).findFirst().orElse(null));
            for (JTipoAfectacionTraduccion traduccion : traducciones) {
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
