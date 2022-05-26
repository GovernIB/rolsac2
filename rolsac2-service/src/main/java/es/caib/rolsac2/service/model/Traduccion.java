package es.caib.rolsac2.service.model;

/**
 * Traduccion.
 *
 * @author indra
 */

public class Traduccion {

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
     * literal.
     */
    private String literal;

    /**
     * Constructor.
     **/
    public Traduccion() {
    }

    /**
     * Constructor.
     *
     * @param iIdioma  ca/es/en
     * @param iLiteral literal
     */
    public Traduccion(final String iIdioma, final String iLiteral) {
        this.idioma = iIdioma;
        this.literal = iLiteral;
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
     * Obtiene el valor de literal.
     *
     * @return el valor de literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Establece el valor de literal.
     *
     * @param literal el nuevo valor de literal
     */
    public void setLiteral(final String literal) {
        this.literal = literal;
    }

}
