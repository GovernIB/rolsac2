package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JFichaPublicoObjectivoPK implements Serializable {
    private static final long serialVersionUID = 3385582159822535478L;
    @Column(name = "FCPO_CODFCH", nullable = false)
    private Integer ficha;

    @Column(name = "FCPO_TIPOPO", nullable = false)
    private Integer tipoPublicoObjetivo;

    public Integer getFicha() {
        return ficha;
    }

    public void setFicha(Integer fcpoCodfch) {
        this.ficha = fcpoCodfch;
    }

    public Integer getTipoPublicoObjetivo() {
        return tipoPublicoObjetivo;
    }

    public void setTipoPublicoObjetivo(Integer fcpoTipopo) {
        this.tipoPublicoObjetivo = fcpoTipopo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JFichaPublicoObjectivoPK entity = (JFichaPublicoObjectivoPK) o;
        return Objects.equals(this.tipoPublicoObjetivo, entity.tipoPublicoObjetivo) &&
                Objects.equals(this.ficha, entity.ficha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoPublicoObjetivo, ficha);
    }

}