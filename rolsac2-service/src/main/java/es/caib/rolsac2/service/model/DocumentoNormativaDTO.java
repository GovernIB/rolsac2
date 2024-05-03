package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;


/**
 * DTO para los documentos de una entidad.
 */
@Schema(name = "DocumentoNormativa")
public class DocumentoNormativaDTO extends ModelApi {

    /* Código del documento*/
    private Long codigo;

    /**
     * Codigo Temporal (NO SE GUARDA )
     **/
    private Long codigoTemporal;

    /*Normativa asociada al documento*/
    private NormativaDTO normativa;

    /*Título del documento*/
    private Literal titulo;

    /*URL del documento*/
    private Literal url;

    /*Descripción del documento*/
    private Literal descripcion;

    /*Fichero asociado al documento*/
    private DocumentoMultiIdioma documentos;

    /**
     * Instantiates a new Documento normativa dto.
     */
    public DocumentoNormativaDTO() {
    }

    /**
     * Obtiene codigo.
     *
     * @return el codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Sets codigo.
     *
     * @param codigo el codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets codigo temporal.
     *
     * @return the codigo temporal
     */
    public Long getCodigoTemporal() {
        return codigoTemporal;
    }

    /**
     * Sets codigo temporal.
     *
     * @param codigoTemporal the codigo temporal
     */
    public void setCodigoTemporal(Long codigoTemporal) {
        this.codigoTemporal = codigoTemporal;
    }

    /**
     * Obtiene normativa.
     *
     * @return la normativa
     */
    public NormativaDTO getNormativa() {
        return normativa;
    }

    /**
     * Sets normativa.
     *
     * @param normativa la normativa
     */
    public void setNormativa(NormativaDTO normativa) {
        this.normativa = normativa;
    }

    /**
     * Obtiene titulo.
     *
     * @return el titulo
     */
    public Literal getTitulo() {
        return titulo;
    }

    /**
     * Sets titulo.
     *
     * @param titulo el titulo
     */
    public void setTitulo(Literal titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene url.
     *
     * @return la url
     */
    public Literal getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url la url
     */
    public void setUrl(Literal url) {
        this.url = url;
    }

    /**
     * Obtiene descripcion.
     *
     * @return la descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Sets descripcion.
     *
     * @param descripcion la descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene documentos.
     *
     * @return los documentos
     */
    public DocumentoMultiIdioma getDocumentos() {
        return documentos;
    }

    /**
     * Establece documentos.
     *
     * @param documentos los documentos
     */
    public void setDocumentos(DocumentoMultiIdioma documentos) {
        this.documentos = documentos;
    }

    public String getCodigoString() {
        if (codigo != null) {
            return codigo.toString();
        }
        if (codigoTemporal != null) {
            return codigoTemporal.toString();
        }
        return "";
    }

    public void setCodigoString(String codigoString) {
        //Vacio
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoNormativaDTO that = (DocumentoNormativaDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(normativa, that.normativa) && Objects.equals(titulo, that.titulo) && Objects.equals(url, that.url) && Objects.equals(descripcion, that.descripcion) && Objects.equals(documentos, that.documentos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, normativa, titulo, url, descripcion, documentos);
    }

    @Override
    public String toString() {
        return "DocumentoNormativaDTO{" + "codigo=" + codigo + ", normativaDTO=" + normativa + ", titulo=" + titulo + ", url=" + url + ", descripcion=" + descripcion + ", documentos=" + documentos + '}';
    }
}
