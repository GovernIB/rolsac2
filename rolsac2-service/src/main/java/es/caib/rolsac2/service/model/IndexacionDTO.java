package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

/**
 * Dades de una indexacion.
 *
 * @author Indra
 */
@Schema(name = "Indexacion")
public class IndexacionDTO extends ModelApi {

    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Tipo.
     */
    private String tipo;

    /**
     * Entidad
     */
    private EntidadDTO entidad;

    /**
     * Codigo elemento
     **/
    private Long codElemento;

    /**
     * Fecha creacion.
     */
    private Date fechaCreacion;

    /**
     * Fecha intento indexacion.
     */
    private Date fechaIntentoIndexacion;

    /**
     * Accion.
     */
    private Integer accion;

    /**
     * Mensaje error
     */
    private String mensajeError;

    /**
     * Obtiene el codigo
     *
     * @return
     */
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public Long getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Long codElemento) {
        this.codElemento = codElemento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaIntentoIndexacion() {
        return fechaIntentoIndexacion;
    }

    public void setFechaIntentoIndexacion(Date fechaIntentoIndexacion) {
        this.fechaIntentoIndexacion = fechaIntentoIndexacion;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public String toString() {
        return "IndexacionDTO{" +
                "id=" + codigo +
                ", tipo='" + tipo + '\'' +
                ", codElemento='" + codElemento + '\'' +
                ", accion='" + accion + '\'' +
                '}';
    }
}
