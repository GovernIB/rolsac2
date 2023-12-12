package es.caib.rolsac2.service.model.exportar;

/**
 * Para cuando se exporta datos a csv.
 */
public class ExportarCampos {

    /**
     * Literal
     **/
    private String literal;

    /**
     * Campo
     **/
    private String campo;

    /**
     * Nombre campo
     **/
    private String nombreCampo;

    /**
     * Opcion seleccionada.
     */
    private boolean seleccionado;

    /**
     * Peso del campo (para cuando se imprime en pdf).
     */
    private int peso = 1;

    /**
     * Constructor.
     *
     * @param literal literal
     *                * @param campo    campo
     *                * @param nombreCampo nombreCampo
     */
    public ExportarCampos(String literal, String campo, String nombreCampo) {
        this.literal = literal;
        this.campo = campo;
        this.nombreCampo = nombreCampo;
        this.seleccionado = false;
    }


    /**
     * Constructor.
     *
     * @param literal      literal
     * @param campo        campo
     * @param nombreCampo  nombreCampo
     * @param seleccionado seleccionado
     */
    public ExportarCampos(String literal, String campo, String nombreCampo, boolean seleccionado) {
        this.literal = literal;
        this.campo = campo;
        this.nombreCampo = nombreCampo;
        this.seleccionado = seleccionado;
        this.peso = 1;
    }

    /**
     * Constructor.
     *
     * @param literal      literal
     * @param campo        campo
     * @param nombreCampo  nombreCampo
     * @param seleccionado seleccionado
     * @param peso         peso
     */
    public ExportarCampos(String literal, String campo, String nombreCampo, boolean seleccionado, int peso) {
        this.literal = literal;
        this.campo = campo;
        this.nombreCampo = nombreCampo;
        this.seleccionado = seleccionado;
        this.peso = peso;
    }

    /**
     * Constructor para clonacion.
     *
     * @param otro otro
     */
    public ExportarCampos(ExportarCampos otro) {
        this.literal = otro.literal;
        this.campo = otro.campo;
        this.nombreCampo = otro.nombreCampo;
        this.seleccionado = otro.seleccionado;
        this.peso = otro.peso;
    }

    /**
     * Obtiene literal.
     *
     * @return
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Setea literal.
     *
     * @param literal
     */
    public void setLiteral(String literal) {
        this.literal = literal;
    }

    /**
     * Obtiene campo.
     *
     * @return
     */
    public String getCampo() {
        return campo;
    }

    /**
     * Setea campo.
     *
     * @param campo
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * Obtiene nombre campo.
     *
     * @return
     */
    public String getNombreCampo() {
        return nombreCampo;
    }

    /**
     * Setea nombre campo.
     *
     * @param nombreCampo
     */
    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    /**
     * Obtiene seleccionado.
     *
     * @return
     */
    public boolean isSeleccionado() {
        return seleccionado;
    }

    /**
     * Setea seleccionado.
     *
     * @param seleccionado
     */
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * Obtiene peso.
     *
     * @return
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Setea peso.
     *
     * @param peso
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "ExportarCampos{" + "literal='" + literal + '\'' + ", campo='" + campo + '\'' + ", nombreCampo='" + nombreCampo + '\'' + ", seleccionado=" + seleccionado + '}';
    }


    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public ExportarCampos clone() {
        return new ExportarCampos(this);
    }
}
