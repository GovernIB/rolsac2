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

    /**
     * Obtiene codigo.
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene procedimiento dto.
     *
     * @return the procedimiento dto
     */
    public ProcedimientoDTO getProcedimientoDTO() {
        return procedimientoDTO;
    }

    /**
     * Establece procedimiento dto.
     *
     * @param procedimientoDTO the procedimiento dto
     */
    public void setProcedimientoDTO(ProcedimientoDTO procedimientoDTO) {
        this.procedimientoDTO = procedimientoDTO;
    }

    /**
     * Obtiene titulo.
     *
     * @return the titulo
     */
    public Literal getTitulo() {
        return titulo;
    }

    /**
     * Establece titulo.
     *
     * @param titulo the titulo
     */
    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene url.
     *
     * @return the url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Establece url.
     *
     * @param url the url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene descripcion.
     *
     * @return the descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene documentos.
     *
     * @return the documentos
     */
    public DocumentoMultiIdioma getDocumentos() {
        return documentos;
    }

    /**
     * Establece documentos.
     *
     * @param documentos the documentos
     */
    public void setDocumentos(DocumentoMultiIdioma documentos) {
        this.documentos = documentos;
    }

    /**
     * Obtiene codigo string.
     *
     * @return the codigo string
     */
    public String getCodigoString() {
        if (codigo != null) {
            return codigo.toString();
        } else {
            return codigoString;
        }
    }

    /**
     * Establece codigo string.
     *
     * @param codigoString the codigo string
     */
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

    /**
     * Obtiene traducciones sobrantes.
     *
     * @param idiomas the idiomas
     * @return the traducciones sobrantes
     */
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
