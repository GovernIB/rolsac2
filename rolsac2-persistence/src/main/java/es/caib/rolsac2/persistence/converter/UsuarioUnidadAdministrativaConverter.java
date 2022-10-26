package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUsuarioUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UsuarioUnidadAdministrativaPKConverter.class, UnidadAdministrativaConverter.class,
                UsuarioConverter.class})
public interface UsuarioUnidadAdministrativaConverter
        extends Converter<JUsuarioUnidadAdministrativa, UsuarioUnidadAdministrativaDTO> {


    @Override
    @Mapping(target = "usuario", qualifiedByName = "createDTOSinUsuarioUnidadAdministrativa")
    UsuarioUnidadAdministrativaDTO createDTO(JUsuarioUnidadAdministrativa entity);

    @Override
    JUsuarioUnidadAdministrativa createEntity(UsuarioUnidadAdministrativaDTO dto);

    @Override
    void mergeEntity(@MappingTarget JUsuarioUnidadAdministrativa entity, UsuarioUnidadAdministrativaDTO dto);

    @Named("createDTOs")
    default List<UsuarioUnidadAdministrativaDTO> createDTOs(List<JUsuarioUnidadAdministrativa> entities) {
        List<UsuarioUnidadAdministrativaDTO> dtos = new ArrayList<>();
        if (entities != null) {
            //TODO Falta ver si hay que añadir mas campos
            //entities.forEach(e -> dtos.add(createDTO(e)));
            for (JUsuarioUnidadAdministrativa entidad : entities) {
                UsuarioUnidadAdministrativaDTO usuarioUA = new UsuarioUnidadAdministrativaDTO();
                UnidadAdministrativaDTO unidadDTO = new UnidadAdministrativaDTO();
                unidadDTO.setCodigo(entidad.getUnidadAdministrativa().getCodigo());

                Literal nombre = new Literal();
                Literal url = new Literal();

                if (entidad.getUnidadAdministrativa().getTraducciones() != null) {
                    for (JUnidadAdministrativaTraduccion traduccion : entidad.getUnidadAdministrativa().getTraducciones()) {
                        nombre.add(new Traduccion(traduccion.getIdioma(), traduccion.getNombre()));
                        url.add(new Traduccion(traduccion.getIdioma(), traduccion.getUrl()));
                    }
                }
                unidadDTO.setNombre(nombre);
                unidadDTO.setUrl(url);
                EntidadDTO enti = new EntidadDTO();
                enti.setCodigo(entidad.getUnidadAdministrativa().getEntidad().getCodigo());
                enti.setActiva(entidad.getUnidadAdministrativa().getEntidad().getActiva());
                enti.setIdentificador(entidad.getUnidadAdministrativa().getEntidad().getIdentificador());
                enti.setIdiomasObligatorios(entidad.getUnidadAdministrativa().getEntidad().getIdiomasObligatorios());
                enti.setIdiomasPermitidos(entidad.getUnidadAdministrativa().getEntidad().getIdiomasPermitidos());
                enti.setIdiomaDefectoRest(entidad.getUnidadAdministrativa().getEntidad().getIdiomaDefectoRest());
                unidadDTO.setEntidad(enti);
                usuarioUA.setUnidadAdministrativa(unidadDTO);
                dtos.add(usuarioUA);
            }
        }
        return dtos;
    }
}
