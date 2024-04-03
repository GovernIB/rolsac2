package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.JTema;
import es.caib.rolsac2.persistence.model.JUnidadAdministrativa;
import es.caib.rolsac2.persistence.model.JUsuario;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTemaTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JUnidadAdministrativaTraduccion;
import es.caib.rolsac2.service.model.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class, TipoUnidadAdministrativaObjetivoConverter.class, TipoSexoConverter.class})
public interface UnidadAdministrativaConverter extends Converter<JUnidadAdministrativa, UnidadAdministrativaDTO> {

    @Override
    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "abreviatura", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"abreviatura\"))")
    @Mapping(target = "presentacion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"presentacion\"))")
    @Mapping(target = "url", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"url\"))")
    @Mapping(target = "responsable", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"responsableCV\"))")
    @Mapping(target = "usuariosUnidadAdministrativa", expression = "java(convertUsuariostoDTO(entity.getUsuarios()))")
    @Mapping(target = "temas", expression = "java(convertTematoDTO(entity.getTemas()))")
    @Mapping(target = "normativas", expression = "java(convertNormativasDTO(entity.getNormativas()))")
    @Mapping(target = "padre", expression = "java(convertSencillo(entity.getPadre(), true))")
    UnidadAdministrativaDTO createDTO(JUnidadAdministrativa entity);

    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "presentacion", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "responsableSexo", ignore = true)
    @Mapping(target = "abreviatura", ignore = true)
    @Mapping(target = "padre", expression = "java(createTreeDTOSinPadre(entity.getPadre()))")
    @Named("createTreeDTO")
    UnidadAdministrativaDTO createTreeDTO(JUnidadAdministrativa entity);

    @Mapping(target = "nombre", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"nombre\"))")
    @Mapping(target = "presentacion", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "responsableSexo", ignore = true)
    @Mapping(target = "abreviatura", ignore = true)
    @Mapping(target = "padre", ignore = true)
    @Named("createTreeDTOSinPadre")
    UnidadAdministrativaDTO createTreeDTOSinPadre(JUnidadAdministrativa entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jUnidadAdministrativa,dto))")
    @Mapping(target = "padre", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "temas", ignore = true)
    @Mapping(target = "normativas", ignore = true)
    JUnidadAdministrativa createEntity(UnidadAdministrativaDTO dto);

    @Override
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "responsableSexo", ignore = true)
    @Mapping(target = "padre", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "temas", ignore = true)
    @Mapping(target = "normativas", ignore = true)
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


    default List<JUnidadAdministrativaTraduccion> convierteLiteralToTraduccion(JUnidadAdministrativa jUnidadAdministrativa, UnidadAdministrativaDTO unidadAdministrativa) {
        List<String> idiomasPermitidos = List.of(unidadAdministrativa.getEntidad().getIdiomasPermitidos().split(";"));

        if (jUnidadAdministrativa.getTraducciones() == null || jUnidadAdministrativa.getTraducciones().isEmpty()) {
            jUnidadAdministrativa.setTraducciones(JUnidadAdministrativaTraduccion.createInstance(idiomasPermitidos));
            for (JUnidadAdministrativaTraduccion jtrad : jUnidadAdministrativa.getTraducciones()) {
                jtrad.setUnidadAdministrativa(jUnidadAdministrativa);
            }
        } else if (jUnidadAdministrativa.getTraducciones().size() < idiomasPermitidos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JUnidadAdministrativaTraduccion> tradsAux = jUnidadAdministrativa.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JUnidadAdministrativaTraduccion traduccion : jUnidadAdministrativa.getTraducciones()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JUnidadAdministrativaTraduccion trad = new JUnidadAdministrativaTraduccion();
                trad.setIdioma(idioma);
                trad.setUnidadAdministrativa(jUnidadAdministrativa);
                tradsAux.add(trad);
            }
            jUnidadAdministrativa.setTraducciones(tradsAux);
        }

        for (JUnidadAdministrativaTraduccion traduccion : jUnidadAdministrativa.getTraducciones()) {
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
            if (unidadAdministrativa.getAbreviatura() != null) {
                traduccion.setAbreviatura(unidadAdministrativa.getAbreviatura().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jUnidadAdministrativa.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JUnidadAdministrativaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getUnidadAdministrativa().getCodigo()).findFirst().orElse(null));
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
                    case "abreviatura":
                        literal = traduccion.getAbreviatura();
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

    default List<UsuarioGridDTO> convertUsuariostoDTO(Set<JUsuario> usuarios) {
        List<UsuarioGridDTO> usuariosUA = new ArrayList<>();
        if (usuarios != null) {
            for (JUsuario usuario : usuarios) {
                UsuarioGridDTO usuarioGridDTO = new UsuarioGridDTO();
                usuarioGridDTO.setCodigo(usuario.getCodigo());
                usuarioGridDTO.setNombre(usuario.getNombre());
                usuarioGridDTO.setIdentificador(usuario.getIdentificador());
                usuarioGridDTO.setEmail(usuario.getEmail());
                usuariosUA.add(usuarioGridDTO);
            }
        }

        return usuariosUA;
    }

    default List<TemaGridDTO> convertTematoDTO(Set<JTema> temas) {
        List<TemaGridDTO> temasDTO = new ArrayList<>();
        if (temas != null) {
            for (JTema tema : temas) {
                TemaGridDTO temaGridDTO = new TemaGridDTO();
                temaGridDTO.setCodigo(tema.getCodigo());
                temaGridDTO.setIdentificador(tema.getIdentificador());
                temaGridDTO.setEntidad(tema.getEntidad().getCodigo());
                temaGridDTO.setMathPath(tema.getMathPath());

                Literal resultado = null;
                if (tema.getDescripcion() != null) {
                    resultado = new Literal();
                    resultado.setCodigo(tema.getDescripcion().stream().map(t -> t.getTema().getCodigo()).findFirst().orElse(null));
                    for (JTemaTraduccion trad : tema.getDescripcion()) {
                        Traduccion traduccion = new Traduccion();
                        traduccion.setCodigo(trad.getCodigo());
                        traduccion.setIdioma(trad.getIdioma());
                        traduccion.setLiteral(trad.getDescripcion());
                        resultado.add(traduccion);
                    }
                }
                temaGridDTO.setDescripcion(resultado);
                if (tema.getTemaPadre() != null) {
                    temaGridDTO.setTemaPadre(tema.getTemaPadre().getIdentificador());
                }
                temasDTO.add(temaGridDTO);
            }
        }

        return temasDTO;
    }

    /**
     * Convierte una junidad administrativa a un DTO sencillo
     *
     * @param junidad
     * @param incluirPadre
     * @return
     */
    default UnidadAdministrativaDTO convertSencillo(JUnidadAdministrativa junidad, boolean incluirPadre) {

        if (junidad == null) {
            return null;
        }
        UnidadAdministrativaDTO unidadAdministrativaDTO = new UnidadAdministrativaDTO();
        unidadAdministrativaDTO.setCodigo(junidad.getCodigo());
        unidadAdministrativaDTO.setCodigoDIR3(junidad.getCodigoDIR3());
        unidadAdministrativaDTO.setIdentificador(junidad.getIdentificador());
        Literal nombre = new Literal();
        for (JUnidadAdministrativaTraduccion trad : junidad.getTraducciones()) {
            nombre.add(new Traduccion(trad.getIdioma(), trad.getNombre()));
        }
        unidadAdministrativaDTO.setNombre(nombre);
        unidadAdministrativaDTO.setOrden(junidad.getOrden());
        if (incluirPadre && junidad.getPadre() != null) {
            unidadAdministrativaDTO.setPadre(convertSencillo(junidad.getPadre(), false));
        }
        return unidadAdministrativaDTO;
    }

    default List<NormativaDTO> convertNormativasDTO(Set<JNormativa> normativas) {
        List<NormativaDTO> normativasDTO = new ArrayList<>();
        if (normativas != null) {
            for (JNormativa normativa : normativas) {
                NormativaDTO normativaDTO = new NormativaDTO();
                normativaDTO.setCodigo(normativa.getCodigo());

                Literal resultado = null;
                if (normativa.getDescripcion() != null) {
                    resultado = new Literal();
                    for (JNormativaTraduccion trad : normativa.getDescripcion()) {
                        Traduccion traduccion = new Traduccion();
                        traduccion.setCodigo(trad.getCodigo());
                        traduccion.setIdioma(trad.getIdioma());
                        traduccion.setLiteral(trad.getTitulo());
                        resultado.add(traduccion);
                    }
                }
                normativaDTO.setTitulo(resultado);
                normativasDTO.add(normativaDTO);
            }
        }

        return normativasDTO;
    }
}
