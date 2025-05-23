package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JProcedimientoCategoriaPDUPK implements Serializable {
    private static final long serialVersionUID = 5291147853866863505L;
    @Column(name = "PRWF_CODIGO", nullable = false)
    private Long procedimiento;

    @Column(name = "CPDU_CODIGO", nullable = false)
    private Long categoriaPDU;

    public Long getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Long uaedCodedi) {
        this.procedimiento = uaedCodedi;
    }

    public Long getCategoriaPDU() {
        return categoriaPDU;
    }

    public void setCategoriaPDU(Long categoriaPDU) {
        this.categoriaPDU = categoriaPDU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JProcedimientoCategoriaPDUPK entity = (JProcedimientoCategoriaPDUPK) o;
        return Objects.equals(this.procedimiento, entity.procedimiento) &&
                Objects.equals(this.categoriaPDU, entity.categoriaPDU);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedimiento, categoriaPDU);
    }

}