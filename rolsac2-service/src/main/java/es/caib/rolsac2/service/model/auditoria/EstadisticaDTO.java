package es.caib.rolsac2.service.model.auditoria;

import es.caib.rolsac2.service.model.ModelApi;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(name = "EstadisticaDTO")
public class EstadisticaDTO extends ModelApi {
    private Long codigo;

    private Long codProcedimiento;

    private Long codUa;

    private String tipo;

    private String identificadorApp;

    private List<EstadisticaAccesoDTO> accesos;

    public EstadisticaDTO() {

    }

    public EstadisticaDTO(EstadisticaDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.codProcedimiento = otro.codProcedimiento;
            this.codUa = otro.codUa;
            this.tipo = otro.tipo;
            this.identificadorApp = otro.identificadorApp;
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodProcedimiento() {
        return codProcedimiento;
    }

    public void setCodProcedimiento(Long codProcedimiento) {
        this.codProcedimiento = codProcedimiento;
    }

    public Long getCodUa() {
        return codUa;
    }

    public void setCodUa(Long codUa) {
        this.codUa = codUa;
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

    public List<EstadisticaAccesoDTO> getAccesos() {
        return accesos;
    }

    public void setAccesos(List<EstadisticaAccesoDTO> accesos) {
        this.accesos = accesos;
    }

    @Override
    public EstadisticaDTO clone() {
        return new EstadisticaDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaDTO that = (EstadisticaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "EstadisticaDTO{" +
                "codigo=" + codigo +
                '}';
    }
}
