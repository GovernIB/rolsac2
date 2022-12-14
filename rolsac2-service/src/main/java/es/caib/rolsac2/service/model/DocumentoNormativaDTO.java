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

    /*Código que se utiliza para seleccionar los docs en la tabla de normativa*/
    private String codigoTabla;

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

    public DocumentoNormativaDTO() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public NormativaDTO getNormativa() {
        return normativa;
    }

    public void setNormativa(NormativaDTO normativa) {
        this.normativa = normativa;
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

    public String getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(String codigoTabla) {
        this.codigoTabla = codigoTabla;
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
        return "DocumentoNormativaDTO{" +
                "codigo=" + codigo +
                ", normativaDTO=" + normativa +
                ", titulo=" + titulo +
                ", url=" + url +
                ", descripcion=" + descripcion +
                ", documentos=" + documentos +
                '}';
    }
}
