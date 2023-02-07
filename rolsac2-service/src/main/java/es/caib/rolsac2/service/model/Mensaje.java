package es.caib.rolsac2.service.model;

import java.util.Date;

/**
 * Clase propiedad básica de codigo por valor.
 *
 * @author Indra
 */
public final class Mensaje extends ModelApi implements Comparable {

    /**
     * Código.
     */
    private String usuario;

    /**
     * Valor
     */
    private String fecha;

    /**
     * Fecha real
     */
    private Date fechaReal;
    /**
     * Orden.
     */
    private String mensaje;

    /**
     * Gestor
     */
    private Boolean admContenido;

    /**
     * Obtiene usuario.
     *
     * @return usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene fecha.
     *
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece fecha.
     *
     * @param fecha fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene mensaje.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece mensaje.
     *
     * @param mensaje mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean isAdmContenido() {
        return admContenido;
    }

    public void setAdmContenido(Boolean admContenido) {
        this.admContenido = admContenido;
    }

    public boolean esAdministradorContenido() {
        return admContenido != null && admContenido;
    }

    public Date getFechaReal() {
        return fechaReal;
    }

    public void setFechaReal(Date fechaReal) {
        this.fechaReal = fechaReal;
    }

    public Boolean getAdmContenido() {
        return admContenido;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        if (!(o instanceof Mensaje)) {
            return 1;
        }

        Mensaje mensaje2 = ((Mensaje) o);
        if (this.getFechaReal() == null && mensaje2.getFechaReal() != null) {
            return 1;
        }
        if (this.getFechaReal() != null && mensaje2.getFechaReal() == null) {
            return -1;
        }
        if (this.getFechaReal() == null && mensaje2.getFechaReal() == null) {
            return mensaje2.compareTo(this.getFecha());//this.getFecha().compareTo(((Mensaje) o).getFecha());
        } else {
            return mensaje2.getFechaReal().after(this.getFechaReal()) ? 1 : -1;
        }
    }
}
