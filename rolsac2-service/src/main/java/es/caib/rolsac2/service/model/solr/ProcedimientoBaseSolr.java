package es.caib.rolsac2.service.model.solr;

import es.caib.rolsac2.service.model.Constantes;

import java.util.Date;

/**
 * Clase de apoyo para solr.
 */
public class ProcedimientoBaseSolr {

    /**
     * Codigo PROC
     **/
    private Long codigo;

    /**
     * Fecha indexacion
     **/
    private Date fechaInicioIndexacion;
    /**
     * Fecha indexacion
     **/
    private Date fechaIndexacion;

    /**
     * Mensaje error
     **/
    private String mensajeError;

    /**
     * Tipo
     */
    private String tipo;

    /**
     * Indica si se mantiene para indexar.
     */
    private boolean mantenerIndexado;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getFechaIndexacion() {
        return fechaIndexacion;
    }

    public void setFechaIndexacion(Date fechaIndexacion) {
        this.fechaIndexacion = fechaIndexacion;
    }

    public Date getFechaInicioIndexacion() {
        return fechaInicioIndexacion;
    }

    public void setFechaInicioIndexacion(Date fechaInicioIndexacion) {
        this.fechaInicioIndexacion = fechaInicioIndexacion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public boolean isMantenerIndexado() {
        return mantenerIndexado;
    }

    public void setMantenerIndexado(boolean mantenerIndexado) {
        this.mantenerIndexado = mantenerIndexado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean esTipoProcedimiento() {
        return tipo != null && Constantes.PROCEDIMIENTO.equals(tipo);
    }

    public boolean esTipoServicio() {
        return tipo != null && Constantes.SERVICIO.equals(tipo);
    }

}
