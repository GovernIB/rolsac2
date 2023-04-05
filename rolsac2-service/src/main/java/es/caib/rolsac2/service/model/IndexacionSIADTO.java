package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

/**
 * Dades de una indexacion.
 *
 * @author Indra
 */
@Schema(name = "IndexacionSIA")
public class IndexacionSIADTO extends ModelApi {

    /**
     * Codigo
     **/
    private Long codigo;

    /**
     * Tipo
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
     * Estado
     */
    private Integer estado;

    /**
     * Mensaje error
     */
    private String mensajeError;

    /**
     * Los datos SIA para guardar
     **/
    private Integer codigoSIA;
    private Boolean estadoSIA;
    private Date fechaSIA;

    /**
     * Existe procedimiento
     **/
    private Integer existe;

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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public Date getFechaSIA() {
        return fechaSIA;
    }

    public void setFechaSIA(Date fechaSIA) {
        this.fechaSIA = fechaSIA;
    }

    public Integer getExiste() {
        return existe;
    }

    public void setExiste(Integer existe) {
        this.existe = existe;
    }

    @Override
    public String toString() {
        return "IndexacionDTO{" +
                "id=" + codigo +
                ", codElemento='" + codElemento + '\'' +
                '}';
    }
}
