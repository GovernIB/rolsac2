package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.traduccion.JUsuarioTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UsuarioDTO;

import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
                uses = {EntidadConverter.class, UsuarioUnidadAdministrativaConverter.class})
public interface UsuarioConverter extends Converter<JUsuario, UsuarioDTO> {

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", qualifiedByName = "createDTOs")
    @Mapping(target = "observaciones", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"observaciones\"))")
    UsuarioDTO createDTO(JUsuario entity);

    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    @Named("createDTOSinUsuarioUnidadAdministrativa")
    @Mapping(target ="observaciones", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"observaciones\"))" )
    UsuarioDTO createDTOSinUsuarioUnidadAdministrativa(JUsuario entity);

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jUsuario, dto))")
    JUsuario createEntity(UsuarioDTO dto);

    @Override
    @Mapping(target = "usuarioUnidadAdministrativa", ignore = true)
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity, dto))")
    void mergeEntity(@MappingTarget JUsuario entity, UsuarioDTO dto);

    default List<JUsuarioTraduccion> convierteLiteralToTraduccion(JUsuario jUsuario, UsuarioDTO usuarioDTO) {
        List<String> idiomasPermitidos = List.of(usuarioDTO.getEntidad().getIdiomasPermitidos().split(";"));

        //Comprobamos si aún no se ha creado la entidad
        if (jUsuario.getTraducciones() == null || jUsuario.getTraducciones().isEmpty()) {
            jUsuario.setTraducciones(JUsuarioTraduccion.createInstance(idiomasPermitidos));
            for (JUsuarioTraduccion jTrad : jUsuario.getTraducciones()) {
                jTrad.setUsuario(jUsuario);
            }
        } else if (jUsuario.getTraducciones().size() < idiomasPermitidos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JUsuarioTraduccion> tradsAux = jUsuario.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JUsuarioTraduccion traduccion : jUsuario.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JUsuarioTraduccion trad = new JUsuarioTraduccion();
                trad.setIdioma(idioma);
                trad.setUsuario(jUsuario);
                tradsAux.add(trad);
            }
            jUsuario.setTraducciones(tradsAux);
        }

        for (JUsuarioTraduccion traduccion : jUsuario.getTraducciones()) {
            if (usuarioDTO.getObservaciones() != null) {

                traduccion.setObservaciones(usuarioDTO.getObservaciones().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jUsuario.getTraducciones();
    }


    default Literal convierteTraduccionToLiteral(List<JUsuarioTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getUsuario().getCodigo()).findFirst().orElse(null));

            for (JUsuarioTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = "";
                if (nombreLiteral.equals("observaciones")) {
                    literal = traduccion.getObservaciones();
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }
}
