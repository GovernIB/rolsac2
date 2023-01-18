/**
 *
 */
package es.caib.rolsac2.service.model.auditoria;

import java.io.Serializable;

/**
 * Valores detallados de AuditoriaDTO para mostrar en la interfaz gráfica
 *
 * @author Indra
 */
public class AuditoriaDetalle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String campo;
    private String valorAnterior;
    private String valorNuevo;

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the valorAnterior
     */
    public String getValorAnterior() {
        return valorAnterior;
    }

    /**
     * @param valorAnterior the valorAnterior to set
     */
    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    /**
     * @return the valorNuevo
     */
    public String getValorNuevo() {
        return valorNuevo;
    }

    /**
     * @param valorNuevo the valorNuevo to set
     */
    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }


}
