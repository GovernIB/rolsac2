package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JProcedimientoTemaPK implements Serializable {
    private static final long serialVersionUID = 2711148029669764426L;
    @Column(name = "PRTM_CODTEMA", nullable = false)
    private Long tema;

    @Column(name = "PRTM_CODPRWF", nullable = false)
    private Long procedimiento;

    public Long getTema() {
        return tema;
    }

    public void setTema(Long prtmCodtema) {
        this.tema = prtmCodtema;
    }

    public Long getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Long prtmCodprwf) {
        this.procedimiento = prtmCodprwf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JProcedimientoTemaPK entity = (JProcedimientoTemaPK) o;
        return Objects.equals(this.tema, entity.tema) &&
                Objects.equals(this.procedimiento, entity.procedimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tema, procedimiento);
    }

}