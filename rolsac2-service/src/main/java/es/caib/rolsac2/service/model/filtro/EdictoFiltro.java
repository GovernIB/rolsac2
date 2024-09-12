package es.caib.rolsac2.service.model.filtro;

import java.util.Date;

public class EdictoFiltro extends AbstractFiltro {

    private String numeroBoletin;

    private String numeroRegistro;

    private Date fechaBoletin;

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

    public Date getFechaBoletin() {
        return fechaBoletin;
    }

    public void setFechaBoletin(Date fechaBoletin) {
        this.fechaBoletin = fechaBoletin;
    }

    @Override
    protected String getDefaultOrder() {
        return null;
    }
}
