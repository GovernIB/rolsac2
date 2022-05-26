package es.caib.rolsac2.back.model;


import es.caib.rolsac2.service.model.types.TypeModoAcceso;

/**
 * Resultado de ventana de dialogo.
 *
 * @author Indra
 */
public class DialogResult {

    /**
     * Modo de acceso al que se ha accedido dialogo.
     */
    private TypeModoAcceso modoAcceso;

    /**
     * Indica si se ha cancelado;
     */
    private boolean canceled;

    /**
     * Resultado dialogo.
     */
    private Object result;

    /**
     * Mensaje a mostrar.
     */
    private DialogResultMessage mensaje;

    /**
     * Obtiene el valor del resultado.
     *
     * @return el valor del resultado
     */
    public Object getResult() {
        return result;
    }

    /**
     * Establece el valor del resultado.
     *
     * @param result el nuevo valor del resultado
     */
    public void setResult(final Object result) {
        this.result = result;
    }

    /**
     * Verifica si se ha cancelado.
     *
     * @return true, si se ha cancelado
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Establece el valor de si se ha cancelado.
     *
     * @param canceled el nuevo valor de si se ha cancelado
     */
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * Obtiene el valor de modo de acceso.
     *
     * @return el valor de modo de acceso
     */
    public TypeModoAcceso getModoAcceso() {
        return modoAcceso;
    }

    /**
     * Establece el valor de modo de acceso.
     *
     * @param modoAcceso el nuevo valor de modo de acceso
     */
    public void setModoAcceso(final TypeModoAcceso modoAcceso) {
        this.modoAcceso = modoAcceso;
    }

    public DialogResultMessage getMensaje() {
        return mensaje;
    }

    public void setMensaje(final DialogResultMessage mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isAlta() {
        return this.getModoAcceso() != null && this.getModoAcceso() == TypeModoAcceso.ALTA;
    }

    public boolean isEdicion() {
        return this.getModoAcceso() != null && this.getModoAcceso() == TypeModoAcceso.EDICION;
    }
}
