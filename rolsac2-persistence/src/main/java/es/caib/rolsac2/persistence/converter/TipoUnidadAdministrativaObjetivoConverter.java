package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JTipoUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.Traduccion;
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
@Mapper
public interface TipoUnidadAdministrativaObjetivoConverter extends Converter<JTipoUnidadAdministrativa, TipoUnidadAdministrativaDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoUnidadAdministrativaDTO createDTO(JTipoUnidadAdministrativa entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoUnidadAdministrativa,dto.getDescripcion(), dto.getCargoMasculino(), dto.getCargoFemenino(), dto.getTratamientoMasculino(), dto.getTratamientoFemenino()))")
    JTipoUnidadAdministrativa createEntity(TipoUnidadAdministrativaDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion(), dto.getCargoMasculino(), dto.getCargoFemenino(), dto.getTratamientoMasculino(), dto.getTratamientoFemenino()))")
    void mergeEntity(@MappingTarget JTipoUnidadAdministrativa entity, TipoUnidadAdministrativaDTO dto);


    default List<JTipoUnidadAdministrativaTraduccion> convierteLiteralToTraduccion(JTipoUnidadAdministrativa jTipoUnidadAdministrativa, Literal descripcion, Literal cargoMasculino, Literal cargoFemenino,
                                                                                   Literal tratamientoMasculino, Literal tratamientoFemenino) {

        if (jTipoUnidadAdministrativa.getDescripcion() == null || jTipoUnidadAdministrativa.getDescripcion().isEmpty()) {
            jTipoUnidadAdministrativa.setDescripcion(JTipoUnidadAdministrativaTraduccion.createInstance());
            for (JTipoUnidadAdministrativaTraduccion jtrad : jTipoUnidadAdministrativa.getDescripcion()) {
                jtrad.setTipoUnidadAdministrativa(jTipoUnidadAdministrativa);
            }
        }
        for (JTipoUnidadAdministrativaTraduccion traduccion : jTipoUnidadAdministrativa.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
            traduccion.setCargoMasculino(cargoMasculino.getTraduccion(traduccion.getIdioma()));
            traduccion.setCargoFemenino(cargoFemenino.getTraduccion(traduccion.getIdioma()));
            traduccion.setTratamientoMasculino(tratamientoMasculino.getTraduccion(traduccion.getIdioma()));
            traduccion.setTratamientoFemenino(tratamientoFemenino.getTraduccion(traduccion.getIdioma()));
        }
        return jTipoUnidadAdministrativa.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoUnidadAdministrativaTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoUnidadAdministrativa().getId()).findFirst().orElse(null));
            for (JTipoUnidadAdministrativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getId());
                trad.setIdioma(traduccion.getIdioma());
                trad.setLiteral(traduccion.getDescripcion());
                resultado.add(trad);
            }
        }

        return resultado;
    }
}
