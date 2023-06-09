package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@Schema(name = "EstadisticaGridDTO")
public class EstadisticaGridDTO extends ModelApi {

    private Long codigo;

    private Date fechaUltAct;

    private Long contador;

    private Long codProc;

    private String tipo;

    private String identificadorApp;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getFechaUltAct() {
        return fechaUltAct;
    }

    public void setFechaUltAct(Date fechaUltAct) {
        this.fechaUltAct = fechaUltAct;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    public Long getCodProc() {
        return codProc;
    }

    public void setCodProc(Long codProc) {
        this.codProc = codProc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificadorApp() {
        return identificadorApp;
    }

    public void setIdentificadorApp(String identificadorApp) {
        this.identificadorApp = identificadorApp;
    }
}
