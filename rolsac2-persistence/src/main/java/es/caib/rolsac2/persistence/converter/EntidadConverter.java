package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JEntidad y EntidadDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface EntidadConverter extends Converter<JEntidad, EntidadDTO>{

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    // @Mapping(target = "idUnitat", source = "unitatOrganica.id")

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    @Mapping(target = "logo", expression = "java(jFicheroExternoToFicheroDTO(entity.getLogo()))")
    EntidadDTO createDTO(JEntidad entity);

    // @Mapping(target = "unidadOrganica", ignore = true)

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jEntidad,dto.getDescripcion()))")
    JEntidad createEntity(EntidadDTO dto);


    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    @Mapping(target = "logo", ignore = true)
    void mergeEntity(@MappingTarget JEntidad entity, EntidadDTO dto);

    default List<JEntidadTraduccion> convierteLiteralToTraduccion(JEntidad jEntidad, Literal descripcion) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : descripcion.getIdiomas()) {
            if(descripcion.getTraduccion(idioma) != null && !descripcion.getTraduccion(idioma).isEmpty()) {
                idiomasRellenos.add(idioma);
            }
        }

        if (jEntidad.getDescripcion() == null || jEntidad.getDescripcion().isEmpty()) {
            jEntidad.setDescripcion(JEntidadTraduccion.createInstance(idiomasRellenos));
            for (JEntidadTraduccion jent : jEntidad.getDescripcion()) {
                jent.setEntidad(jEntidad);
            }
        } else if(idiomasRellenos.size() >  jEntidad.getDescripcion().size()) {
            //En caso de que no se haya creado, comprobamos que tenga todas las traducciones (pueden haberse añadido nuevos idiomas)
            List<JEntidadTraduccion> tradsAux = jEntidad.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JEntidadTraduccion traduccion : jEntidad.getDescripcion()) {
                if (idiomasNuevos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            for (String idioma : idiomasNuevos) {
                JEntidadTraduccion trad = new JEntidadTraduccion();
                trad.setIdioma(idioma);
                trad.setEntidad(jEntidad);
                tradsAux.add(trad);
            }
            jEntidad.setDescripcion(tradsAux);
        }
        for (JEntidadTraduccion traduccion : jEntidad.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jEntidad.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JEntidadTraduccion> traducciones) {
        Literal resultado = Literal.createInstance();

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado.setCodigo(traducciones.stream().map(t -> t.getEntidad().getCodigo()).findFirst().orElse(null));
            for (JEntidadTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(Long.valueOf(traduccion.getCodigo()));
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }
        return resultado;
    }

    default FicheroDTO jFicheroExternoToFicheroDTO(JFicheroExterno jFicheroExterno) {
        if ( jFicheroExterno == null ) {
            return null;
        }

        FicheroDTO ficheroDTO = new FicheroDTO();

        ficheroDTO.setCodigo( jFicheroExterno.getCodigo() );
        ficheroDTO.setFilename( jFicheroExterno.getFilename() );
        if ( jFicheroExterno.getTipo() != null ) {
            ficheroDTO.setTipo( TypeFicheroExterno.fromString(jFicheroExterno.getTipo()) );
        }

        return ficheroDTO;
    }
}
