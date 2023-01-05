package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "ServicioGrid")
public class ServicioGridDTO extends ModelApi {
    private Long codigo;

    private Long codigoWFMod;

    private Long codigoWFPub;
    private String tipo;

    private String estado;
    private Boolean estadoSIA;
    public LocalDate siaFecha;
    private String codigoDir3SIA;
    private Integer codigoSIA;
    private String nombre;

    private LocalDate fecha;

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

    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public LocalDate getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(LocalDate fechaSIA) {
        this.siaFecha = fechaSIA;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        if (estado == null) {
            return "";
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCodigoWFMod() {
        return codigoWFMod;
    }

    public void setCodigoWFMod(Long codigoWFMod) {
        this.codigoWFMod = codigoWFMod;
    }

    public Long getCodigoWFPub() {
        return codigoWFPub;
    }

    public void setCodigoWFPub(Long codigoWFPub) {
        this.codigoWFPub = codigoWFPub;
    }

    public boolean tieneDatosPublicados() {
        return this.codigoWFPub != null;
    }

    public boolean tieneDatosEnModificacion() {
        return this.codigoWFMod != null;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", codigoSIA='" + codigoSIA + '\'' +
                '}';
    }
}
