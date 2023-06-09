package es.caib.rolsac2.service.model.auditoria;

import java.util.Date;

public class EstadisticaAccesoDTO {

    private Long codigo;

    private Date fechaCreacion;

    private Long contador;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }
}
