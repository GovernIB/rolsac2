package es.caib.rolsac2.service.model.util;

/**
 * Clase que representa el resultado de la comprobación de si un SIA cumple con los requisitos para ser enviado y es enviable.
 */
public class SiaCumpleEnviable {

    /**
     * Correcto
     **/
    private boolean correcto;

    /**
     * Mensaje de error o información adicional
     **/
    private String mensaje;

    /**
     * Constructor de la clase.
     *
     * @param correcto Indica si el SIA cumple con los requisitos para ser enviado.
     * @param mensaje  Mensaje de error o información adicional.
     */
    public SiaCumpleEnviable(boolean correcto, String mensaje) {
        this.correcto = correcto;
        this.mensaje = mensaje;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void addMensaje(String respuesta) {
        if (mensaje == null || mensaje.isEmpty()) {
            mensaje = " " + respuesta;
        } else {
            mensaje += " " + respuesta;
        }
    }
}
