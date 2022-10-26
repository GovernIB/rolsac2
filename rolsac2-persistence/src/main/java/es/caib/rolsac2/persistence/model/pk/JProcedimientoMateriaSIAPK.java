package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JProcedimientoMateriaSIAPK implements Serializable {
    private static final long serialVersionUID = 8754334654034342054L;
    //procedimiento workflow
    @Column(name = "PRMS_CODPRWF", nullable = false)
    private Long procedimento;

    @Column(name = "PRMS_TIPMSIA", nullable = false)
    private Long tipoMateriaSIA;

    public Long getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Long prmsCodprwf) {
        this.procedimento = prmsCodprwf;
    }

    public Long getTipoMateriaSIA() {
        return tipoMateriaSIA;
    }

    public void setTipoMateriaSIA(Long prmsTipmsia) {
        this.tipoMateriaSIA = prmsTipmsia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JProcedimientoMateriaSIAPK entity = (JProcedimientoMateriaSIAPK) o;
        return Objects.equals(this.tipoMateriaSIA, entity.tipoMateriaSIA) &&
                Objects.equals(this.procedimento, entity.procedimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoMateriaSIA, procedimento);
    }

}