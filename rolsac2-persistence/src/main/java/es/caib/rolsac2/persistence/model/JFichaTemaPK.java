package es.caib.rolsac2.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JFichaTemaPK implements Serializable {
    private static final long serialVersionUID = 2568234528069291647L;
    @Column(name = "FCTE_CODFCH", nullable = false)
    private Integer ficha;

    @Column(name = "FCTE_CODTEM", nullable = false)
    private Integer tema;

    public Integer getFicha() {
        return ficha;
    }

    public void setFicha(Integer fcteCodfch) {
        this.ficha = fcteCodfch;
    }

    public Integer getTema() {
        return tema;
    }

    public void setTema(Integer fcteCodtem) {
        this.tema = fcteCodtem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JFichaTemaPK entity = (JFichaTemaPK) o;
        return Objects.equals(this.tema, entity.tema) &&
                Objects.equals(this.ficha, entity.ficha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tema, ficha);
    }

}