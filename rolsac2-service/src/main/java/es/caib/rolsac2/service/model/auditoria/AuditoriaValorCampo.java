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
     * Constructor.
     *
     * @param campo         campo
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



}
