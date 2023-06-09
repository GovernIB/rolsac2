package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.auditoria.Periodo;

public class EstadisticaFiltro extends AbstractFiltro {


    private Long codigo;

    private String idApp;

    private String tipo;

    private Periodo periodo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Boolean isRellenoCodigo() {
        return codigo != null;
    }

    public Boolean isRellenoApp() {
        return idApp != null;
    }

    public Boolean isRellenoTipo() {
        return tipo != null;
    }

    public Boolean isRellenoPeriodo() {
        return periodo != null && periodo.getFechaFin() != null && periodo.getFechaInicio() != null;
    }
    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
