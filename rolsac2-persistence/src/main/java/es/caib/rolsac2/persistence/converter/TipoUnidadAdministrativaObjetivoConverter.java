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

import java.util.ArrayList;
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
            expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    @Mapping(target = "cargoMasculino",
            expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"cargoMasculino\"))")
    @Mapping(target = "cargoFemenino",
            expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"cargoFemenino\"))")
    @Mapping(target = "tratamientoMasculino",
            expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"tratamientoMasculino\"))")
    @Mapping(target = "tratamientoFemenino",
            expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"tratamientoFemenino\"))")
    TipoUnidadAdministrativaDTO createDTO(JTipoUnidadAdministrativa entity);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "traducciones",
            expression = "java(convierteLiteralToTraduccion(jTipoUnidadAdministrativa, dto))")
    JTipoUnidadAdministrativa createEntity(TipoUnidadAdministrativaDTO dto);

    // @Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "traducciones",
            expression = "java(convierteLiteralToTraduccion(entity,dto))")
    void mergeEntity(@MappingTarget JTipoUnidadAdministrativa entity, TipoUnidadAdministrativaDTO dto);


    default List<JTipoUnidadAdministrativaTraduccion> convierteLiteralToTraduccion(
            JTipoUnidadAdministrativa jTipoUnidadAdministrativa, TipoUnidadAdministrativaDTO dto) {

        List<String> idiomasPermitidos = List.of(dto.getEntidad().getIdiomasPermitidos().split(";"));

        if (jTipoUnidadAdministrativa.getTraducciones() == null
                || jTipoUnidadAdministrativa.getTraducciones().isEmpty()) {
            jTipoUnidadAdministrativa.setTraducciones(JTipoUnidadAdministrativaTraduccion.createInstance(idiomasPermitidos));
            for (JTipoUnidadAdministrativaTraduccion jtrad : jTipoUnidadAdministrativa.getTraducciones()) {
                jtrad.setTipoUnidadAdministrativa(jTipoUnidadAdministrativa);
            }
        } else if (jTipoUnidadAdministrativa.getTraducciones().size() < idiomasPermitidos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JTipoUnidadAdministrativaTraduccion> tradsAux = jTipoUnidadAdministrativa.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JTipoUnidadAdministrativaTraduccion traduccion : jTipoUnidadAdministrativa.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JTipoUnidadAdministrativaTraduccion trad = new JTipoUnidadAdministrativaTraduccion();
                trad.setIdioma(idioma);
                trad.setTipoUnidadAdministrativa(jTipoUnidadAdministrativa);
                tradsAux.add(trad);
            }
            jTipoUnidadAdministrativa.setTraducciones(tradsAux);
        }
        for (JTipoUnidadAdministrativaTraduccion traduccion : jTipoUnidadAdministrativa.getTraducciones()) {
            if (dto.getDescripcion() != null) {
                traduccion.setDescripcion(dto.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getCargoMasculino() != null) {
                traduccion.setCargoMasculino(dto.getCargoMasculino().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getCargoFemenino() != null) {
                traduccion.setCargoFemenino(dto.getCargoFemenino().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getTratamientoMasculino() != null) {
                traduccion.setTratamientoMasculino(dto.getTratamientoMasculino().getTraduccion(traduccion.getIdioma()));
            }
            if (dto.getTratamientoFemenino() != null) {
                traduccion.setTratamientoFemenino(dto.getTratamientoFemenino().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jTipoUnidadAdministrativa.getTraducciones();
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
