package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dades de un documento ficha.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoDocumento")
public class ProcedimientoDocumentoDTO extends ModelApi implements Cloneable {

    /**
     * LOGGERR
     **/
    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoDocumentoDTO.class);

    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Código temporal para poder tratar con el dato
     **/
    private String codigoString;

    /*Título del documento*/
    private Literal titulo;

    /*URL del documento*/
    private Literal url;

    /*Descripción del documento*/
    private Literal descripcion;

    /*Fichero asociado al documento*/
    private DocumentoMultiIdioma documentos;

    /**
     * Procedimiento
     **/
    private ProcedimientoDTO procedimientoDTO;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ProcedimientoDTO getProcedimientoDTO() {
        return procedimientoDTO;
    }

    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }

    public Literal getTitulo() {
        return titulo;
    }

    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    public Literal getUrl() {
        return url;
    }

    public void setUrl(Literal url) {
        this.url = url;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public DocumentoMultiIdioma getDocumentos() {
        return documentos;
    }

    public void setDocumentos(DocumentoMultiIdioma documentos) {
        this.documentos = documentos;
    }

    public String getCodigoString() {
        if (codigo != null) {
            return codigo.toString();
        } else {
            return codigoString;
        }
    }

    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    public Object clone() {
        ProcedimientoDocumentoDTO obj = null;
        try {
            obj = (ProcedimientoDocumentoDTO) super.clone();
            if (this.titulo != null) {
                obj.titulo = (Literal) this.titulo.clone();
            }

            if (this.url != null) {
                obj.url = (Literal) this.url.clone();
            }

            if (this.descripcion != null) {
                obj.descripcion = (Literal) this.descripcion.clone();
            }

            if (documentos != null) {
                obj.documentos = (DocumentoMultiIdioma) documentos.clone();
            }
        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }

    @Override
    public String toString() {
        return "ProcedimientoDocumentoDTO{" +
                "id=" + codigo +
                '}';
    }

    public List<String> getTraduccionesSobrantes(List<String> idiomas) {
        List<String> idiomasSobrantes = new ArrayList<>();
        //Se va a mirar titulo y documentos, porque depende el caso, o es obligatorio el titulo o el documento
        if (this.getTitulo() != null) {
            for (String idiomaTitulo : this.getTitulo().getIdiomas()) {
                if (!idiomas.contains(idiomaTitulo) && !idiomasSobrantes.contains(idiomaTitulo)) {
                    idiomasSobrantes.add(idiomaTitulo);
                }
            }
        }

        if (this.getDocumentos() != null) {
            for (String idiomaDocumento : this.getDocumentos().getIdiomas()) {
                if (!idiomas.contains(idiomaDocumento) && !idiomasSobrantes.contains(idiomaDocumento)) {
                    idiomasSobrantes.add(idiomaDocumento);
                }
            }
        }
        return idiomasSobrantes;
    }

}
