package es.caib.rolsac2.service.model.auditoria;

/**
 * Valores campo.
 *
 * @author Indra
 */
public class AuditoriaValorCampo {

    /**
     * Idioma.
     */
    private AuditoriaIdioma idioma;

    /**
     * Valor anterior.
     */
    private String valorAnterior;

    /**
     * Valor nuevo.
     */
    private String valorNuevo;

    /**
     * Identificador elemento
     */
    private String elemento;

    /**
     * Constructor.
     *
     * @param idioma        idioma
     * @param valorAnterior valor anterior
     * @param valorNuevo    valor nuevo
     */
    public AuditoriaValorCampo(final AuditoriaIdioma idioma, final String valorAnterior, final String valorNuevo) {
        super();
        this.idioma = idioma;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
    }

    /**
     * Constructor.
     *
     * @param idioma        idioma
     * @param valorAnterior valor anterior
     * @param valorNuevo    valor nuevo
     */
    public AuditoriaValorCampo(final AuditoriaIdioma idioma, final String valorAnterior, final String valorNuevo, final String iElemento) {
        super();
        this.idioma = idioma;
        this.valorAnterior = valorAnterior;
        this.valorNuevo = valorNuevo;
        this.elemento = iElemento;
    }

    /**
     * Constructor.
     */
    public AuditoriaValorCampo() {
        super();
    }

    /**
     * Obtiene valorAnterior.
     *
     * @return valorAnterior
     */
    public String getValorAnterior() {
        return valorAnterior;
    }

    /**
     * Establece valorAnterior.
     *
     * @param valorAnterior valorAnterior a establecer
     */
    public void setValorAnterior(final String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    /**
     * Obtiene valorNuevo.
     *
     * @return valorNuevo
     */
    public String getValorNuevo() {
        return valorNuevo;
    }

    /**
     * Establece valorNuevo.
     *
     * @param valorNuevo valorNuevo a establecer
     */
    public void setValorNuevo(final String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    /**
     * Obtiene idioma.
     *
     * @return idioma
     */
    public AuditoriaIdioma getIdioma() {
        return idioma;
    }

    /**
     * Establece idioma.
     *
     * @param idioma idioma a establecer
     */
    public void setIdioma(final AuditoriaIdioma idioma) {
        this.idioma = idioma;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }
}
