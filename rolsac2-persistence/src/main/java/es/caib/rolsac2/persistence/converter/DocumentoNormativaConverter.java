package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JDocumentoNormativa;
import es.caib.rolsac2.persistence.model.JDocumentoNormativaTraduccion;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conversor entre JDocumentoNormativa y DocumentoNormativaDTO. La implementacion se generará automaticamente por
 * MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = NormativaConverter.class)
public interface DocumentoNormativaConverter extends Converter<JDocumentoNormativa, DocumentoNormativaDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    @Mapping(target = "url", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"url\"))")
    @Mapping(target = "titulo", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"titulo\"))")
    @Mapping(target = "documentos", expression = "java(convierteDocsToModel(entity.getTraducciones()))")
    DocumentoNormativaDTO createDTO(JDocumentoNormativa entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jDocumentoNormativa,dto))")
    JDocumentoNormativa createEntity(DocumentoNormativaDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity ,dto))")
    void mergeEntity(@MappingTarget JDocumentoNormativa entity, DocumentoNormativaDTO dto);

    default List<JDocumentoNormativaTraduccion> convierteLiteralToTraduccion(JDocumentoNormativa jDocumentoNormativa, DocumentoNormativaDTO documentoNormativaDTO) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : documentoNormativaDTO.getDocumentos().getIdiomas()) {
            if(documentoNormativaDTO.getDocumentos().getTraduccion(idioma) != null ) {
                idiomasRellenos.add(idioma);
            }
        }

        //Comprobamos si aún no se ha creado
        if (jDocumentoNormativa.getTraducciones() == null || jDocumentoNormativa.getTraducciones().isEmpty()) {
            jDocumentoNormativa.setTraducciones(JDocumentoNormativaTraduccion.createInstance(idiomasRellenos));
            for (JDocumentoNormativaTraduccion jTrad : jDocumentoNormativa.getTraducciones()) {
                jTrad.setDocumentoNormativa(jDocumentoNormativa);
            }
        } else if (jDocumentoNormativa.getTraducciones().size() < idiomasRellenos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JDocumentoNormativaTraduccion> tradsAux = jDocumentoNormativa.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JDocumentoNormativaTraduccion traduccion : jDocumentoNormativa.getTraducciones()) {
                if (idiomasRellenos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JDocumentoNormativaTraduccion trad = new JDocumentoNormativaTraduccion();
                trad.setIdioma(idioma);
                trad.setDocumentoNormativa(jDocumentoNormativa);
                tradsAux.add(trad);
            }
            jDocumentoNormativa.setTraducciones(tradsAux);
        }

        for (JDocumentoNormativaTraduccion traduccion : jDocumentoNormativa.getTraducciones()) {
            if (documentoNormativaDTO.getDescripcion() != null) {

                traduccion.setDescripcion(documentoNormativaDTO.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
            if (documentoNormativaDTO.getUrl() != null) {

                traduccion.setUrl(documentoNormativaDTO.getUrl().getTraduccion(traduccion.getIdioma()));
            }
            if (documentoNormativaDTO.getTitulo() != null) {
                traduccion.setTitulo(documentoNormativaDTO.getTitulo().getTraduccion(traduccion.getIdioma()));
            }
            if (documentoNormativaDTO.getDocumentos() != null) {
                traduccion.setDocumento(ficheroDTOToJFicheroExterno(documentoNormativaDTO.getDocumentos().getTraduccion(traduccion.getIdioma())));
            }
        }
        return jDocumentoNormativa.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JDocumentoNormativaTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getDocumentoNormativa().getCodigo()).findFirst().orElse(null));

            for (JDocumentoNormativaTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = "";
                if (nombreLiteral.equals("descripcion")) {
                    literal = traduccion.getDescripcion();
                } else if (nombreLiteral.equals("url")) {
                    literal = traduccion.getUrl();
                } else if (nombreLiteral.equals("titulo")) {
                    literal = traduccion.getTitulo();
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }

    default DocumentoMultiIdioma convierteDocsToModel(List<JDocumentoNormativaTraduccion> jDocumentoNormativaTraducciones) {
        DocumentoMultiIdioma resultado = null;
        if (Objects.nonNull(jDocumentoNormativaTraducciones) && !jDocumentoNormativaTraducciones.isEmpty()) {
            resultado = new DocumentoMultiIdioma();
            resultado.setCodigo(jDocumentoNormativaTraducciones.stream().map(t -> t.getDocumentoNormativa().getCodigo()).findFirst().orElse(null));

            for (JDocumentoNormativaTraduccion traduccion : jDocumentoNormativaTraducciones) {
                DocumentoTraduccion trad = new DocumentoTraduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                trad.setFicheroDTO(jFicheroToFicheroDTO(traduccion.getDocumento()));

                resultado.add(trad);
            }
        }
        return resultado;
    }

    default JFicheroExterno ficheroDTOToJFicheroExterno(FicheroDTO ficheroDTO) {
        if (ficheroDTO == null) {
            return null;
        }

        JFicheroExterno jFicheroExterno = new JFicheroExterno();

        jFicheroExterno.setCodigo(ficheroDTO.getCodigo());
        jFicheroExterno.setFilename(ficheroDTO.getFilename());
        if (ficheroDTO.getTipo() != null) {
            jFicheroExterno.setTipo(ficheroDTO.getTipo().name());
        }

        return jFicheroExterno;
    }

    default FicheroDTO jFicheroToFicheroDTO(JFicheroExterno jFicheroExterno) {
        if ( jFicheroExterno == null ) {
            return null;
        }

        FicheroDTO ficheroDTO = new FicheroDTO();

        ficheroDTO.setCodigo( jFicheroExterno.getCodigo() );
        ficheroDTO.setFilename( jFicheroExterno.getFilename() );
        if ( jFicheroExterno.getTipo() != null ) {
            ficheroDTO.setTipo( TypeFicheroExterno.fromString(jFicheroExterno.getTipo()) );
        }

        return ficheroDTO;
    }
}
