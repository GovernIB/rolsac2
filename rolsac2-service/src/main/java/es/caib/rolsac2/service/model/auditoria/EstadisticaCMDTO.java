package es.caib.rolsac2.service.model.auditoria;

import java.util.List;

public class EstadisticaCMDTO {

    private List<Long> valores;

    private List<String> diasSemana;

    private List<String> meses;

    public List<Long> getValores() {
        return valores;
    }

    public void setValores(List<Long> valores) {
        this.valores = valores;
    }

    public List<String> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<String> getMeses() {
        return meses;
    }

    public void setMeses(List<String> meses) {
        this.meses = meses;
    }
}
