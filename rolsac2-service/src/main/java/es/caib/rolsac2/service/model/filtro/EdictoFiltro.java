package es.caib.rolsac2.service.model.filtro;

import java.time.LocalDate;

public class EdictoFiltro extends AbstractFiltro{

    private String numeroBoletin;

    private String numeroRegistro;

    private LocalDate fechaBoletin;

    public String getNumeroBoletin() {
        return numeroBoletin;
    }

    public void setNumeroBoletin(String numeroBoletin) {
        this.numeroBoletin = numeroBoletin;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public LocalDate getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(LocalDate fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    @Override
    protected String getDefaultOrder() {
        return null;
    }
}
