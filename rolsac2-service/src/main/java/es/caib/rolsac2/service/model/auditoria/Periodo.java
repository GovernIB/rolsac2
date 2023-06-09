package es.caib.rolsac2.service.model.auditoria;

import java.util.Date;

public class Periodo {

    public Periodo() {}

    public Periodo(Date fechaInicio, Date fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Object clone() {
        Date newFechaInicio = new Date(fechaInicio.getTime());
        Date newFechaFin = new Date(fechaFin.getTime());
        return new Periodo(newFechaInicio, newFechaFin);
    }

    private Date fechaInicio;
    private Date fechaFin;

    /** Data d'inici del periode */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /** Data de fi del periode */
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean contains(Date fecha) {
        return (fechaInicio.compareTo(fecha) <= 0) && (fechaFin.compareTo(fecha) >= 0);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Periodo)) return false;

        final Periodo periodo = (Periodo) o;

        if (!fechaFin.equals(periodo.fechaFin)) return false;
        if (!fechaInicio.equals(periodo.fechaInicio)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = fechaInicio.hashCode();
        result = 29 * result + fechaFin.hashCode();
        return result;
    }

    public String toString() {
        return "(" + fechaInicio + "," + fechaFin + ")";
    }
}
