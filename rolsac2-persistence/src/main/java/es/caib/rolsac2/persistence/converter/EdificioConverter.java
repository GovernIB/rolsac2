package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEdificio;
import es.caib.rolsac2.persistence.model.traduccion.JEdificioTraduccion;
import es.caib.rolsac2.service.model.EdificioDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JEdificio y EdificioDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface EdificioConverter extends Converter<JEdificio, EdificioDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    EdificioDTO createDTO(JEdificio entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jEdificio,dto.getDescripcion()))")
    JEdificio createEntity(EdificioDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JEdificio entity, EdificioDTO dto);

    default List<JEdificioTraduccion> convierteLiteralToTraduccion(JEdificio jEdificio, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jEdificio.getDescripcion() == null || jEdificio.getDescripcion().isEmpty()) {
            jEdificio.setDescripcion(JEdificioTraduccion.createInstance(idiomasRellenos));
            for (JEdificioTraduccion jtrad : jEdificio.getDescripcion()) {
                jtrad.setEdificio(jEdificio);
            }
        } else if(idiomasRellenos.size() >  jEdificio.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JEdificioTraduccion> tradsAux = jEdificio.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JEdificioTraduccion traduccion : jEdificio.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JEdificioTraduccion trad = new JEdificioTraduccion();
                trad.setIdioma(idioma);
                trad.setEdificio(jEdificio);
                tradsAux.add(trad);
            }
            jEdificio.setDescripcion(tradsAux);
        }
        for (JEdificioTraduccion traduccion : jEdificio.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setNombre(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jEdificio.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JEdificioTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getEdificio().getCodigo()).findFirst().orElse(null));
            for (JEdificioTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getNombre());
                resultado.add(trad);
            }
        }

        return resultado;
    }
}
