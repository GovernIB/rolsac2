package es.caib.rolsac2.service.model;

import java.util.Objects;

/**
 * Documento Traduccion.
 *
 * @author indra
 */

public class DocumentoTraduccion {

    /**
     * Serial version UID.
     **/
    private static final long serialVersionUID = 1L;

    /**
     * codigo.
     */
    private Long codigo;

    /**
     * idioma.
     */
    private String idioma;

    /**
     * Fichero asociado al idioma.
     */
    private FicheroDTO ficheroDTO;

    /**
     * Constructor.
     **/
    public DocumentoTraduccion() {
    }

    /**
     * Constructor.
     *
     * @param iIdioma  ca/es/en
     * @param iFichero fichero
     */
    public DocumentoTraduccion(final String iIdioma, final FicheroDTO iFichero) {
        this.idioma = iIdioma;
        this.ficheroDTO = iFichero;
    }

    /**
     * Obtiene el valor de codigo.
     *
     * @return el valor de codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el valor de codigo.
     *
     * @param codigo el nuevo valor de codigo
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el valor de idioma.
     *
     * @return el valor de idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el valor de idioma.
     *
     * @param idioma el nuevo valor de idioma
     */
    public void setIdioma(final String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene el valor de ficheroDTO.
     *
     * @return el valor de ficheroDTO
     */
    public FicheroDTO getFicheroDTO() { return ficheroDTO; }

    /**
     * Establece el valor de ficheroDTO.
     *
     * @param ficheroDTO el nuevo valor de ficheroDTO
     */
    public void setFicheroDTO(final FicheroDTO ficheroDTO) {
        this.ficheroDTO = ficheroDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoTraduccion that = (DocumentoTraduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(idioma, that.idioma) && Objects.equals(ficheroDTO, that.ficheroDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, idioma, ficheroDTO);
    }
}
