package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JNormativa;
import es.caib.rolsac2.persistence.model.traduccion.JNormativaTraduccion;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {EntidadConverter.class, TipoBoletinConverter.class, TipoNormativaConverter.class, DocumentoNormativaConverter.class})
public interface NormativaConverter extends Converter<JNormativa, NormativaDTO> {

    @Override
    @Mapping(target = "titulo", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"titulo\"))")
    @Mapping(target = "urlBoletin", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"urlBoletin\"))")
    @Mapping(target = "nombreResponsable", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"nombreResponsable\"))")
    @Mapping(target = "documentosNormativa", ignore = true)
    @Mapping(target = "unidadesAdministrativas", ignore = true)
    @Mapping(target = "afectaciones", ignore = true)
    NormativaDTO createDTO(JNormativa entity);

    @Mapping(target = "titulo", expression = "java(convierteTraduccionToLiteral(entity.getDescripcion(), \"titulo\"))")
    @Mapping(target = "tipoNormativa", source = "tipoNormativa.identificador")
    @Mapping(target = "boletinOficial", source = "boletinOficial.nombre")
    NormativaGridDTO createGridDTO(JNormativa entity);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(jNormativa,dto))")
    @Mapping(target = "documentosNormativa", ignore = true)
    @Mapping(target = "unidadesAdministrativas", ignore = true)
    @Mapping(target = "afectaciones", ignore = true)
    JNormativa createEntity(NormativaDTO dto);

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteLiteralToTraduccion(entity,dto))")
    @Mapping(target = "documentosNormativa", ignore = true)
    @Mapping(target = "unidadesAdministrativas", ignore = true)
    @Mapping(target = "afectaciones", ignore = true)
    void mergeEntity(@MappingTarget JNormativa entity, NormativaDTO dto);

    default Literal convierteTraduccionToLiteral(List<JNormativaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getNormativa().getCodigo()).findFirst().orElse(null));
            for (JNormativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = null;

                if ("titulo".equals(nombreLiteral)) {
                    literal = traduccion.getTitulo();
                } else if ("urlBoletin".equals(nombreLiteral)) {
                    literal = traduccion.getUrlBoletin();
                } else if ("nombreResponsable".equals(nombreLiteral)) {
                    literal = traduccion.getNombreResponsable();
                } else {
                    literal = null;
                }
                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }

    default List<JNormativaTraduccion> convierteLiteralToTraduccion(JNormativa jNormativa, NormativaDTO normativaDTO) {
        List<String> idiomasPermitidos = List.of(jNormativa.getEntidad().getIdiomasPermitidos().split(";"));
        //Comprobamos si aún no se ha creado la entidad
        if (jNormativa.getDescripcion() == null || jNormativa.getDescripcion().isEmpty()) {
            jNormativa.setDescripcion(JNormativaTraduccion.createInstance(idiomasPermitidos));
            for (JNormativaTraduccion jTrad : jNormativa.getDescripcion()) {
                jTrad.setNormativa(jNormativa);
            }
        } else if (jNormativa.getDescripcion().size() < idiomasPermitidos.size()) {
            //            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JNormativaTraduccion> tradsAux = jNormativa.getDescripcion();
            List<String> idiomasNuevos = new ArrayList<>(idiomasPermitidos);

            for (JNormativaTraduccion traduccion : jNormativa.getDescripcion()) {
                if (idiomasPermitidos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JNormativaTraduccion trad = new JNormativaTraduccion();
                trad.setIdioma(idioma);
                trad.setNormativa(jNormativa);
                tradsAux.add(trad);
            }
            jNormativa.setDescripcion(tradsAux);
        }

        for (JNormativaTraduccion traduccion : jNormativa.getDescripcion()) {
            if (normativaDTO.getTitulo() != null) {

                traduccion.setTitulo(normativaDTO.getTitulo().getTraduccion(traduccion.getIdioma()));
            }

            if (normativaDTO.getUrlBoletin() != null) {
                traduccion.setUrlBoletin(normativaDTO.getUrlBoletin().getTraduccion(traduccion.getIdioma()));
            }

            if (normativaDTO.getNombreResponsable() != null) {
                traduccion.setNombreResponsable(normativaDTO.getNombreResponsable().getTraduccion(traduccion.getIdioma()));
            }
        }
        return jNormativa.getDescripcion();
    }

}
