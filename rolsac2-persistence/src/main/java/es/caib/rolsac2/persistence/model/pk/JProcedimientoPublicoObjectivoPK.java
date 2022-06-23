package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JProcedimientoPublicoObjectivoPK implements Serializable {
    private static final long serialVersionUID = -3845401494362339047L;
    @Column(name = "PRPO_CODPRWF", nullable = false)
    private Integer procedimiento;

    @Column(name = "PRPO_TIPPOBJ", nullable = false)
    private Integer tipoPublicoObjetivo;

    public Integer getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Integer prpoCodprwf) {
        this.procedimiento = prpoCodprwf;
    }

    public Integer getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    public void setTipoPublicoObjetivo(Integer prpoTippobj) {
        this.tipoPublicoObjetivo = prpoTippobj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JProcedimientoPublicoObjectivoPK entity = (JProcedimientoPublicoObjectivoPK) o;
        return Objects.equals(this.tipoPublicoObjetivo, entity.tipoPublicoObjetivo) &&
                Objects.equals(this.procedimiento, entity.procedimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoPublicoObjetivo, procedimiento);
    }

}