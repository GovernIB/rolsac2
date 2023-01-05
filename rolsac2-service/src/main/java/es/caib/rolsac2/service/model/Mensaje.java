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

    /**
     * Obtiene usuario.
     *
     * @return  usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario  usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene fecha.
     *
     * @return  fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param fecha  fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene mensaje.
     *
     * @return  mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece mensaje.
     *
     * @param mensaje  mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
