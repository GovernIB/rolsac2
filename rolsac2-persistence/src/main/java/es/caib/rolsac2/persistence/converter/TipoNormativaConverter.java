package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoNormativa;
import es.caib.rolsac2.persistence.model.traduccion.JTipoMateriaSIATraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoNormativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre J TipoNormativa y  TipoNormativaDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface TipoNormativaConverter extends Converter<JTipoNormativa, TipoNormativaDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoNormativaDTO createDTO(JTipoNormativa entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoNormativa,dto.getDescripcion()))")
    JTipoNormativa createEntity(TipoNormativaDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JTipoNormativa entity, TipoNormativaDTO dto);

    default List<JTipoNormativaTraduccion> convierteLiteralToTraduccion(JTipoNormativa jTipoNormativa, Literal descripcion) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        //Comprobamos si aún no se ha creado la entidad
        if (jTipoNormativa.getDescripcion() == null || jTipoNormativa.getDescripcion().isEmpty()) {
            jTipoNormativa.setDescripcion(JTipoNormativaTraduccion.createInstance(idiomasRellenos));
            for (JTipoNormativaTraduccion jtrad : jTipoNormativa.getDescripcion()) {
                jtrad.setTipoNormativa(jTipoNormativa);
            }
        } else if(idiomasRellenos.size() >  jTipoNormativa.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JTipoNormativaTraduccion> tradsAux = jTipoNormativa.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JTipoNormativaTraduccion traduccion : jTipoNormativa.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JTipoNormativaTraduccion trad = new JTipoNormativaTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoNormativa(jTipoNormativa);
                tradsAux.add(trad);
            }
            jTipoNormativa.setDescripcion(tradsAux);
        }
        for (JTipoNormativaTraduccion traduccion : jTipoNormativa.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoNormativa.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoNormativaTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoNormativa().getCodigo()).findFirst().orElse(null));
            for (JTipoNormativaTraduccion traduccion : traducciones) {
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
