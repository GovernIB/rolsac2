package es.caib.rolsac2.service.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Traduccion.
 *
 * @author indra
 */

public class Traduccion implements Cloneable {

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

    private static final Logger LOG = LoggerFactory.getLogger(Traduccion.class);

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    public Object clone() {
        Traduccion trad = new Traduccion();
        trad.setLiteral(this.literal);
        trad.setCodigo(this.codigo);
        trad.setIdioma(this.idioma);
        return trad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traduccion that = (Traduccion) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(idioma, that.idioma) && Objects.equals(literal, that.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, idioma, literal);
    }


}
