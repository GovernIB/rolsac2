package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EntidadConverter.class, TipoUnidadAdministrativaObjetivoConverter.class,
                TipoSexoConverter.class})
public interface UnidadAdministrativaConverter extends Converter<JUnidadAdministrativa, UnidadAdministrativaDTO> {

    @Override
    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    @Mapping(target = "presentacion",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"presentacion\"))")
    @Mapping(target = "url",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"url\"))")
    @Mapping(target = "responsable",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"responsableCV\"))")
    UnidadAdministrativaDTO createDTO(JUnidadAdministrativa entity);

    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    @Mapping(target = "presentacion",
            ignore = true)
    @Mapping(target = "url",
            ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "responsableSexo", ignore = true)
    @Mapping(target = "padre", expression = "java(createTreeDTOSinPadre(entity.getPadre()))")
    @Named("createTreeDTO")
    UnidadAdministrativaDTO createTreeDTO(JUnidadAdministrativa entity);

    @Mapping(target = "nombre",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombre\"))")
    @Mapping(target = "presentacion",
            ignore = true)
    @Mapping(target = "url",
            ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "responsableSexo", ignore = true)
    @Mapping(target = "padre", ignore = true)
    @Named("createTreeDTOSinPadre")
    UnidadAdministrativaDTO createTreeDTOSinPadre(JUnidadAdministrativa entity);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jUnidadAdministrativa,dto))")
    JUnidadAdministrativa createEntity(UnidadAdministrativaDTO dto);

    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JUnidadAdministrativa entity, UnidadAdministrativaDTO dto);

    default List<UnidadAdministrativaDTO> createDTOs(List<JUnidadAdministrativa> entities) {
        List<UnidadAdministrativaDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createDTO(e)));
        }
        return resultado;
    }

    default List<UnidadAdministrativaDTO> createTreeDTOs(List<JUnidadAdministrativa> entities) {
        List<UnidadAdministrativaDTO> resultado = new ArrayList<>();
        if (entities != null) {
            entities.forEach(e -> resultado.add(createTreeDTO(e)));
        }
        return resultado;
    }


    default List<JUnidadAdministrativaTraduccion> convierteLiteralToTraduccion(
            JUnidadAdministrativa jUnidadAdministrativa, UnidadAdministrativaDTO unidadAdministrativa) {

        if (jUnidadAdministrativa.getDescripcion() == null || jUnidadAdministrativa.getDescripcion().isEmpty()) {
            jUnidadAdministrativa.setDescripcion(JUnidadAdministrativaTraduccion.createInstance());
            for (JUnidadAdministrativaTraduccion jtrad : jUnidadAdministrativa.getDescripcion()) {
                jtrad.setUnidadAdministrativa(jUnidadAdministrativa);
            }
        }
        for (JUnidadAdministrativaTraduccion traduccion : jUnidadAdministrativa.getDescripcion()) {
            if (unidadAdministrativa.getNombre() != null) {
                traduccion.setNombre(unidadAdministrativa.getNombre().getTraduccion(traduccion.getIdioma()));
            }
            if (unidadAdministrativa.getPresentacion() != null) {
                traduccion.setPresentacion(unidadAdministrativa.getPresentacion().getTraduccion(traduccion.getIdioma()));
            }
            if (unidadAdministrativa.getUrl() != null) {
                traduccion.setUrl(unidadAdministrativa.getUrl().getTraduccion(traduccion.getIdioma()));
            }
            if (unidadAdministrativa.getResponsable() != null) {
                traduccion.setResponsableCV(unidadAdministrativa.getResponsable().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jUnidadAdministrativa.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JUnidadAdministrativaTraduccion> traducciones,
                                                 String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getUnidadAdministrativa().getCodigo()).findFirst()
                    .orElse(null));
            for (JUnidadAdministrativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                switch (nombreLiteral) {
                    case "nombre":
                        literal = traduccion.getNombre();
                        break;
                    case "presentacion":
                        literal = traduccion.getPresentacion();
                        break;
                    case "url":
                        literal = traduccion.getUrl();
                        break;
                    case "responsableCV":
                        literal = traduccion.getResponsableCV();
                        break;
                    default:
                        literal = null;
                        break;
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }

        return resultado;
    }
}