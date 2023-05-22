package es.caib.rolsac2.commons.plugins.email.api;

/**
 * Anexo email.
 *
 * @author Indra
 *
 */
public class AnexoEmail {

    /** Nombre fichero. */
    private String fileName;

    /** Content type. */
    private String contentType;

    /** Contenido. */
    private byte[] content;

    /**
     * Método de acceso a fileName.
     * 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Método para establecer fileName.
     * 
     * @param fileName
     *            fileName a establecer
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Método de acceso a contentType.
     * 
     * @return contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Método para establecer contentType.
     * 
     * @param contentType
     *            contentType a establecer
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Método de acceso a content.
     * 
     * @return content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Método para establecer content.
     * 
     * @param content
     *            content a establecer
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

}
