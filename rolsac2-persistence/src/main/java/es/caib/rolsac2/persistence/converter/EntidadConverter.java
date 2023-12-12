package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JEntidad;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.traduccion.JEntidadTraduccion;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Conversor entre JEntidad y EntidadDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author Indra
 */
@Mapper
public interface EntidadConverter extends Converter<JEntidad, EntidadDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    // @Mapping(target = "idUnitat", source = "unitatOrganica.id")

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"descripcion\" ))")
    @Mapping(target = "lopdFinalidad", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"lopdFinalidad\" ))")
    @Mapping(target = "lopdDestinatario", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"lopdDestinatario\" ))")
    @Mapping(target = "lopdDerechos", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"lopdDerechos\" ))")
    @Mapping(target = "lopdCabecera", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"lopdCabecera\" ))")
    @Mapping(target = "lopdPlantilla", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"lopdPlantilla\" ))")
    @Mapping(target = "uaComun", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(),\"uaComun\" ))")
    @Mapping(target = "logo", expression = "java(jFicheroExternoToFicheroDTO(entity.getLogo()))")
    @Mapping(target = "cssPersonalizado", expression = "java(jFicheroExternoToFicheroDTO(entity.getCssPersonalizado()))")
    EntidadDTO createDTO(JEntidad entity);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entidad.getDescripcion(),\"descripcion\" ))")
    EntidadGridDTO createGridDTO(JEntidad entidad);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jEntidad,dto))")
    JEntidad createEntity(EntidadDTO dto);


    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "cssPersonalizado", ignore = true)
    void mergeEntity(@MappingTarget JEntidad entity, EntidadDTO dto);

    default List<JEntidadTraduccion> convierteLiteralToTraduccion(JEntidad jEntidad, EntidadDTO dto) {
        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        List<String> idiomasPermitidos = List.of(dto.getIdiomasPermitidos().split(";"));

        //Comprobamos si aún no se ha creado la entidad
        if (jEntidad.getDescripcion() == null || jEntidad.getDescripcion().isEmpty()) {
            jEntidad.setDescripcion(JEntidadTraduccion.createInstance(idiomasPermitidos));
            for (JEntidadTraduccion jTrad : jEntidad.getDescripcion()) {
                jTrad.setEntidad(jEntidad);
            }
        } else if (jEntidad.getDescripcion().size() < idiomasPermitidos.size()) {
            //            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JEntidadTraduccion> tradsAux = jEntidad.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JEntidadTraduccion traduccion : jEntidad.getDescripcion()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JEntidadTraduccion trad = new JEntidadTraduccion();
                trad.setIdioma(idioma);
                trad.setEntidad(jEntidad);
                tradsAux.add(trad);
            }
            jEntidad.setDescripcion(tradsAux);
        }

        for (JEntidadTraduccion traduccion : jEntidad.getDescripcion()) {
            if (dto.getDescripcion() != null) {
                traduccion.setDescripcion(dto.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getLopdDerechos() != null) {
                traduccion.setLopdDerechos(dto.getLopdDerechos().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getLopdFinalidad() != null) {
                traduccion.setLopdFinalidad(dto.getLopdFinalidad().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getLopdDestinatario() != null) {
                traduccion.setLopdDestinatario(dto.getLopdDestinatario().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getLopdCabecera() != null) {
                traduccion.setLopdCabecera(dto.getLopdCabecera().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getLopdPlantilla() != null) {
                traduccion.setLopdPlantilla(dto.getLopdPlantilla().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getUaComun() != null) {
                traduccion.setUaComun(dto.getUaComun().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jEntidad.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JEntidadTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = Literal.createInstance();

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado.setCodigo(traducciones.stream().map(t -> t.getEntidad().getCodigo()).findFirst().orElse(null));
            for (JEntidadTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(Long.valueOf(traduccion.getCodigo()));
                trad.setIdioma(traduccion.getIdioma());
                if (nombreLiteral == "descripcion") {
                    trad.setLiteral(traduccion.getDescripcion());
                } else if (nombreLiteral == "lopdFinalidad") {
                    trad.setLiteral(traduccion.getLopdFinalidad());
                } else if (nombreLiteral == "lopdDestinatario") {
                    trad.setLiteral(traduccion.getLopdDestinatario());
                } else if (nombreLiteral == "lopdDerechos") {
                    trad.setLiteral(traduccion.getLopdDerechos());
                } else if (nombreLiteral == "lopdCabecera") {
                    trad.setLiteral(traduccion.getLopdCabecera());
                } else if (nombreLiteral == "lopdPlantilla") {
                    trad.setLiteral(traduccion.getLopdPlantilla());
                } else if (nombreLiteral == "uaComun") {
                    trad.setLiteral(traduccion.getUaComun());
                }
                resultado.add(trad);
            }
        }
        return resultado;
    }

    default FicheroDTO jFicheroExternoToFicheroDTO(JFicheroExterno jFicheroExterno) {
        if (jFicheroExterno == null) {
            return null;
        }

        FicheroDTO ficheroDTO = new FicheroDTO();

        ficheroDTO.setCodigo(jFicheroExterno.getCodigo());
        ficheroDTO.setFilename(jFicheroExterno.getFilename());
        if (jFicheroExterno.getTipo() != null) {
            ficheroDTO.setTipo(TypeFicheroExterno.fromString(jFicheroExterno.getTipo()));
        }
        ficheroDTO.setReferencia(jFicheroExterno.getReferencia());

        return ficheroDTO;
    }

    default List<EntidadDTO> toDTOs(List<JEntidad> entities) {
        return entities.stream().map(e -> createDTO(e)).collect(Collectors.toList());
    }
}
