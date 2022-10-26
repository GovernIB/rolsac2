package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoLegitimacion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoLegitimacionTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
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

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jtipoLegitimacion.getDescripcion() == null || jtipoLegitimacion.getDescripcion().isEmpty()) {
            jtipoLegitimacion.setDescripcion(JTipoLegitimacionTraduccion.createInstance(idiomasRellenos));
            for (JTipoLegitimacionTraduccion jtrad : jtipoLegitimacion.getDescripcion()) {
                jtrad.setTipoLegitimacion(jtipoLegitimacion);
            }
        } else if(idiomasRellenos.size() >  jtipoLegitimacion.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoLegitimacionTraduccion> tradsAux = jtipoLegitimacion.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoLegitimacionTraduccion traduccion : jtipoLegitimacion.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoLegitimacionTraduccion trad = new JTipoLegitimacionTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoLegitimacion(jtipoLegitimacion);
                tradsAux.add(trad);
            }
            jtipoLegitimacion.setDescripcion(tradsAux);
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
