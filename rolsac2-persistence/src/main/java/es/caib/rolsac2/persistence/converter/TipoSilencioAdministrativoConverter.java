package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.persistence.model.JTipoSilencioAdministrativo;
import es.caib.rolsac2.persistence.model.traduccion.JTipoSilencioAdministrativoTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JTipoSilencioAdministrativo y  TipoSilencioAdministrativoDTO. La implementacion se generará automaticamente por MapStruct
 *
 * @author jsegovia
 */
@Mapper
public interface TipoSilencioAdministrativoConverter extends Converter<JTipoSilencioAdministrativo, TipoSilencioAdministrativoDTO> {

    // Els camps que no tenen exactament el mateix nom s'han de mapejar. En aquest cas, només quan
    // passam de Entity a DTO ens interessa agafar la clau forana de l'unitatOrganica.
    //@Mapping(target = "idUnitat", source = "unitatOrganica.id")
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion()))")
    TipoSilencioAdministrativoDTO createDTO(JTipoSilencioAdministrativo entity);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jTipoSilencioAdministrativo,dto.getDescripcion()))")
    JTipoSilencioAdministrativo createEntity(TipoSilencioAdministrativoDTO dto);

    //@Mapping(target = "unidadOrganica", ignore = true)
    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto.getDescripcion()))")
    void mergeEntity(@MappingTarget JTipoSilencioAdministrativo entity, TipoSilencioAdministrativoDTO dto);



    default List<JTipoSilencioAdministrativoTraduccion> convierteLiteralToTraduccion(JTipoSilencioAdministrativo jTipoSilencioAdministrativo, Literal descripcion) {

        if (jTipoSilencioAdministrativo.getDescripcion() == null || jTipoSilencioAdministrativo.getDescripcion().isEmpty()) {
            jTipoSilencioAdministrativo.setDescripcion(JTipoSilencioAdministrativoTraduccion.createInstance());
            for (JTipoSilencioAdministrativoTraduccion jtrad : jTipoSilencioAdministrativo.getDescripcion()) {
                jtrad.setTipoSilencioAdministrativo(jTipoSilencioAdministrativo);
            }
        }
        for (JTipoSilencioAdministrativoTraduccion traduccion : jTipoSilencioAdministrativo.getDescripcion()) {
            traduccion.setDescripcion(descripcion.getTraduccion(traduccion.getIdioma()));
        }
        return jTipoSilencioAdministrativo.getDescripcion();
    }

    default Literal convierteTraduccionToLiteral(List<JTipoSilencioAdministrativoTraduccion> traducciones) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getTipoSilencioAdministrativo().getId()).findFirst().orElse(null));
            for (JTipoSilencioAdministrativoTraduccion traduccion : traducciones) {
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
