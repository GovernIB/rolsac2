package es.caib.rolsac2.service.model;

/**
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 */

public final class Mensaje extends ModelApi {

    /**
     * Código.
     */
    private String usuario;

    /**
     * Valor
     */
    private String fecha;

    /**
     * Orden.
     */
    private String mensaje;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
