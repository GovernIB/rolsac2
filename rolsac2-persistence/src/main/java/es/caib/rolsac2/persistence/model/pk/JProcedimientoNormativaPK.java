package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JProcedimientoNormativaPK implements Serializable {
    private static final long serialVersionUID = 5291147853866863505L;
    @Column(name = "PRWF_CODIGO", nullable = false)
    private Long procedimiento;

    @Column(name = "NORM_CODIGO", nullable = false)
    private Long normativa;

    public Long getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Long uaedCodedi) {
        this.procedimiento = uaedCodedi;
    }

    public Long getNormativa() {
        return normativa;
    }

    public void setNormativa(Long uaedCodua) {
        this.normativa = uaedCodua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JProcedimientoNormativaPK entity = (JProcedimientoNormativaPK) o;
        return Objects.equals(this.procedimiento, entity.procedimiento) &&
                Objects.equals(this.normativa, entity.normativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedimiento, normativa);
    }

}