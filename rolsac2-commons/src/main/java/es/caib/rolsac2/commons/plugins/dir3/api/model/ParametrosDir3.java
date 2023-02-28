package es.caib.rolsac2.commons.plugins.dir3.api.model;

import java.util.Date;

public class ParametrosDir3 {

    private String codigo;

    private Date fechaActualizacion;

    private Date fechaSincronizacion;

    private Boolean denominacionCooficial;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public Boolean getDenominacionCooficial() {
        return denominacionCooficial;
    }

    public void setDenominacionCooficial(Boolean denominacionCooficial) {
        this.denominacionCooficial = denominacionCooficial;
    }
}
