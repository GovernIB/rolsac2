package es.caib.rolsac2.persistence.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.persistence.model.JProcedimientoDocumento;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.service.model.DocumentoMultiIdioma;
import es.caib.rolsac2.service.model.DocumentoTraduccion;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;

/**
 * Conversor entre JProcedimientoDocumento y ProcedimientoDocumentoDTO. La implementacion se generará automaticamente por
 * MapStruct
 *
 * @author Indra
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = NormativaConverter.class)
public interface ProcedimientoDocumentoConverter extends Converter<JProcedimientoDocumento, ProcedimientoDocumentoDTO> {

    @Override
    @Mapping(target = "descripcion", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"descripcion\"))")
    @Mapping(target = "url", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"url\"))")
    @Mapping(target = "titulo", expression = "java(convierteTraduccionToLiteral(entity.getTraducciones(), \"titulo\"))")
    @Mapping(target = "documentos", expression = "java(convierteDocsToModel(entity.getTraducciones()))")
    ProcedimientoDocumentoDTO createDTO(JProcedimientoDocumento entity);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(jProcedimientoDocumento,dto))")
    JProcedimientoDocumento createEntity(ProcedimientoDocumentoDTO dto);

    @Override
    @Mapping(target = "traducciones", expression = "java(convierteLiteralToTraduccion(entity ,dto))")
    void mergeEntity(@MappingTarget JProcedimientoDocumento entity, ProcedimientoDocumentoDTO dto);

    default List<JProcedimientoDocumentoTraduccion> convierteLiteralToTraduccion(JProcedimientoDocumento jProcedimientoDocumento, ProcedimientoDocumentoDTO documentoNormativaDTO) {

        //Iteramos sobre el literal para ver que idiomas se han rellenado
        List<String> idiomasRellenos = new ArrayList<>();
        for(String idioma : documentoNormativaDTO.getTitulo().getIdiomas()) {
            if(documentoNormativaDTO.getTitulo().getTraduccion(idioma) != null && !documentoNormativaDTO.getTitulo().getTraduccion(idioma).equals("") ) {
                idiomasRellenos.add(idioma);
            }
        }

        //Comprobamos si aún no se ha creado
        if (jProcedimientoDocumento.getTraducciones() == null || jProcedimientoDocumento.getTraducciones().isEmpty()) {
            jProcedimientoDocumento.setTraducciones(JProcedimientoDocumentoTraduccion.createInstance(idiomasRellenos));
            for (JProcedimientoDocumentoTraduccion jTrad : jProcedimientoDocumento.getTraducciones()) {
                jTrad.setDocumento(jProcedimientoDocumento);
            }
        } else if (jProcedimientoDocumento.getTraducciones().size() < idiomasRellenos.size()) {
            //En caso de que se haya creado, comprobamos que tenga todas las traducciones (pueden haberse dado de alta idiomas nuevos en entidad)
            List<JProcedimientoDocumentoTraduccion> tradsAux = jProcedimientoDocumento.getTraducciones();
            List<String> idiomasNuevos = new ArrayList<>(idiomasRellenos);

            for (JProcedimientoDocumentoTraduccion traduccion : jProcedimientoDocumento.getTraducciones()) {
                if (idiomasRellenos.contains(traduccion.getIdioma())) {
                    idiomasNuevos.remove(traduccion.getIdioma());
                }
            }
            //Añadimos a la lista de traducciones los nuevos valores

            for (String idioma : idiomasNuevos) {
                JProcedimientoDocumentoTraduccion trad = new JProcedimientoDocumentoTraduccion();
                trad.setIdioma(idioma);
                trad.setDocumento(jProcedimientoDocumento);
                tradsAux.add(trad);
            }
            jProcedimientoDocumento.setTraducciones(tradsAux);
        }

        for (JProcedimientoDocumentoTraduccion traduccion : jProcedimientoDocumento.getTraducciones()) {
            if (documentoNormativaDTO.getDescripcion() != null) {

                traduccion.setDescripcion(documentoNormativaDTO.getDescripcion().getTraduccion(traduccion.getIdioma()));
            }
//            if (documentoNormativaDTO.getUrl() != null) {
//
//                traduccion.setUrl(documentoNormativaDTO.getUrl().getTraduccion(traduccion.getIdioma()));
//            }
            if (documentoNormativaDTO.getTitulo() != null) {
                traduccion.setTitulo(documentoNormativaDTO.getTitulo().getTraduccion(traduccion.getIdioma()));
            }
            if (documentoNormativaDTO.getDocumentos() != null) {
            	JFicheroExterno fichero = ficheroDTOToJFicheroExterno(documentoNormativaDTO.getDocumentos().getTraduccion(traduccion.getIdioma()));
                traduccion.setFichero(fichero == null ? null : fichero.getCodigo());
            }
        }
        return jProcedimientoDocumento.getTraducciones();
    }

    default Literal convierteTraduccionToLiteral(List<JProcedimientoDocumentoTraduccion> traducciones, String nombreLiteral) {
        Literal resultado = null;

        if (Objects.nonNull(traducciones) && !traducciones.isEmpty()) {
            resultado = new Literal();
            resultado.setCodigo(traducciones.stream().map(t -> t.getDocumento().getCodigo()).findFirst().orElse(null));

            for (JProcedimientoDocumentoTraduccion traduccion : traducciones) {
                Traduccion trad = new Traduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());

                String literal = "";
                if (nombreLiteral.equals("descripcion")) {
                    literal = traduccion.getDescripcion();
//                } else if (nombreLiteral.equals("url")) {
//                    literal = traduccion.getUrl();
                } else if (nombreLiteral.equals("titulo")) {
                    literal = traduccion.getTitulo();
                }

                trad.setLiteral(literal);

                resultado.add(trad);
            }
        }
        return resultado;
    }

    default DocumentoMultiIdioma convierteDocsToModel(List<JProcedimientoDocumentoTraduccion> jProcedimientoDocumentoTraducciones) {
        DocumentoMultiIdioma resultado = null;
        if (Objects.nonNull(jProcedimientoDocumentoTraducciones) && !jProcedimientoDocumentoTraducciones.isEmpty()) {
            resultado = new DocumentoMultiIdioma();
            resultado.setCodigo(jProcedimientoDocumentoTraducciones.stream().map(t -> t.getDocumento().getCodigo()).findFirst().orElse(null));

            for (JProcedimientoDocumentoTraduccion traduccion : jProcedimientoDocumentoTraducciones) {
                DocumentoTraduccion trad = new DocumentoTraduccion();
                trad.setCodigo(traduccion.getCodigo());
                trad.setIdioma(traduccion.getIdioma());
                trad.setFichero(traduccion.getFichero());

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
