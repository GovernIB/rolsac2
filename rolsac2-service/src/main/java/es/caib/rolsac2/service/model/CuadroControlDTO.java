package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CuadroControl")
public class CuadroControlDTO extends ModelApi {

    private String titulos;

    private String numeroTotal;

    private String numeroVisible;

    private String numeroNoVisible;

    public CuadroControlDTO() {

    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public String getNumeroTotal() {
        return numeroTotal;
    }

    public void setNumeroTotal(String numeroTotal) {
        this.numeroTotal = numeroTotal;
    }

    public String getNumeroVisible() {
        return numeroVisible;
    }

    public void setNumeroVisible(String numeroVisible) {
        this.numeroVisible = numeroVisible;
    }

    public String getNumeroNoVisible() {
        return numeroNoVisible;
    }

    public void setNumeroNoVisible(String numeroNoVisible) {
        this.numeroNoVisible = numeroNoVisible;
    }
}
