package es.caib.rolsac2.commons.plugins.indexacion.api.model;

public class ResultadoAccion {

    /**
     * Si ha ido correcto
     **/
    private boolean correcto;

    /**
     * En caso de correcto = false, el mensaje de error
     */
    private String mensaje;

    public ResultadoAccion(boolean correcto, String mensaje) {
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
}
