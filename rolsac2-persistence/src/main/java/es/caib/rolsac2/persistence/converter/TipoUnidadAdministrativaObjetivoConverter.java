package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JTipoUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JPersonal y PersonalDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class})
public interface TipoUnidadAdministrativaObjetivoConverter
        extends Converter<JTipoUnidadAdministrativa, TipoUnidadAdministrativaDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    // @Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"descripcion\"))")
    @Mapping(target = "cargoMasculino",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"cargoMasculino\"))")
    @Mapping(target = "cargoFemenino",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"cargoFemenino\"))")
    @Mapping(target = "tratamientoMasculino",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"tratamientoMasculino\"))")
    @Mapping(target = "tratamientoFemenino",
            expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"tratamientoFemenino\"))")
    TipoUnidadAdministrativaDTO createDTO(JTipoUnidadAdministrativa entity);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(jTipoUnidadAdministrativa,dto.getDescripcion(), dto.getCargoMasculino(), dto.getCargoFemenino(), dto.getTratamientoMasculino(), dto.getTratamientoFemenino()))")
    JTipoUnidadAdministrativa createEntity(TipoUnidadAdministrativaDTO dto);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion",
            expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion(), dto.getCargoMasculino(), dto.getCargoFemenino(), dto.getTratamientoMasculino(), dto.getTratamientoFemenino()))")
    void mergeEntity(@MappingTarget JTipoUnidadAdministrativa entity, TipoUnidadAdministrativaDTO dto);


    default List<JTipoUnidadAdministrativaTraduccion> convierteLiteralToTraduccion(
            JTipoUnidadAdministrativa jTipoUnidadAdministrativa, Literal descripcion, Literal cargoMasculino,
            Literal cargoFemenino, Literal tratamientoMasculino, Literal tratamientoFemenino) {

        if (jTipoUnidadAdministrativa.getDescripcion() == null
                || jTipoUnidadAdministrativa.getDescripcion().isEmpty()) {
            jTipoUnidadAdministrativa.setDescripcion(JTipoUnidadAdministrativaTraduccion.createInstance());
            for (JTipoUnidadAdministrativaTraduccion jtrad : jTipoUnidadAdministrativa.getDescripcion()) {
                jtrad.setTipoUnidadAdministrativa(jTipoUnidadAdministrativa);
            }
        }
        for (JTipoUnidadAdministrativaTraduccion traduccion : jTipoUnidadAdministrativa.getDescripcion()) {
            if (descripcion != null) {
                traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            }
            if (cargoMasculino != null) {
                traduccion.setCargoMasculino(cargoMasculino.getTraduccion(traduccion.getIdioma()));
            }
            if (cargoFemenino != null) {
                traduccion.setCargoFemenino(cargoFemenino.getTraduccion(traduccion.getIdioma()));
            }
            if (tratamientoMasculino != null) {
                traduccion.setTratamientoMasculino(tratamientoMasculino.getTraduccion(traduccion.getIdioma()));
            }
            if (tratamientoFemenino != null) {
                traduccion.setTratamientoFemenino(tratamientoFemenino.getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoUnidadAdministrativa.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoUnidadAdministrativaTraduccion> traducciones,
                                                 String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoUnidadAdministrativa().getCodigo()).findFirst()
                    .orElse(null));
            for (JTipoUnidadAdministrativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                switch (nombreLiteral) {
                    case "descripcion":
                        literal = traduccion.getDescripcion();
                        break;
                    case "cargoMasculino":
                        literal = traduccion.getCargoMasculino();
                        break;
                    case "cargoFemenino":
                        literal = traduccion.getCargoFemenino();
                        break;
                    case "tratamientoMasculino":
                        literal = traduccion.getTratamientoMasculino();
                        break;
                    case "tratamientoFemenino":
                        literal = traduccion.getTratamientoFemenino();
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
