package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoMateriaSIA;
import es.caib.rolsac2.persistence.model.traduccion.JPlatTramitElectronicaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMateriaSIATraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoMateriaSIA y TipoMateriaSIADTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface TipoMateriaSIAConverter extends Converter<JTipoMateriaSIA, TipoMateriaSIADTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoMateriaSIADTO createDTO(JTipoMateriaSIA entity);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jTipoMateriaSIA,dto.getDescripcion()))")
    JTipoMateriaSIA createEntity(TipoMateriaSIADTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
        /// , ignore = true)
    void mergeEntity(@MappingTarget JTipoMateriaSIA entity, TipoMateriaSIADTO dto);

    default List<JTipoMateriaSIATraduccion> convierteLiteralToTraduccion(JTipoMateriaSIA jtipoMateria,
                                                                         Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        //Comprobamos si aún no se ha creado la entidad
        if (jtipoMateria.getDescripcion() == null || jtipoMateria.getDescripcion().isEmpty()) {
            jtipoMateria.setDescripcion(JTipoMateriaSIATraduccion.createInstance(idiomasRellenos));
            for (JTipoMateriaSIATraduccion jtrad : jtipoMateria.getDescripcion()) {
                jtrad.setTipoMateriaSIA(jtipoMateria);
            }
        } else if(idiomasRellenos.size() >  jtipoMateria.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoMateriaSIATraduccion> tradsAux = jtipoMateria.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoMateriaSIATraduccion traduccion : jtipoMateria.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoMateriaSIATraduccion trad = new JTipoMateriaSIATraduccion();
                trad.setIdioma(idioma);
                trad.setTipoMateriaSIA(jtipoMateria);
                tradsAux.add(trad);
            }
            jtipoMateria.setDescripcion(tradsAux);
        }

        for (JTipoMateriaSIATraduccion traduccion : jtipoMateria.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jtipoMateria.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoMateriaSIATraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoMateriaSIA().getCodigo()).findFirst().orElse(null));
            for (JTipoMateriaSIATraduccion traduccion : traducciones) {
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
