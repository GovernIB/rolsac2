package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Dades d'un Procedimiento.
 *
 * @author Indra
 */
@Schema(name = "Procedimiento")
public class ProcedimientoGridDTO extends ModelApi {
    private Long codigo;
    private String tipo;
    private Boolean estadoSIA;
    public LocalDate siaFecha;
    private String codigoDir3SIA;
    private Integer codigoSIA;

    private String nombre;

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

    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", codigoSIA='" + codigoSIA + '\'' +
                '}';
    }
}
