package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JNormativaUnidadAdministrativaPK implements Serializable {
    private static final long serialVersionUID = -8268731012864913289L;
    @Column(name = "UANO_CODUNA", nullable = false)
    private Integer unidadAdministrativa;

    @Column(name = "UANO_CODNORM", nullable = false)
    private Integer normativa;

    public Integer getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Integer uanoCoduna) {
        this.unidadAdministrativa = uanoCoduna;
    }

    public Integer getNormativa() {
        return normativa;
    }

    public void setNormativa(Integer uanoCodnorm) {
        this.normativa = uanoCodnorm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JNormativaUnidadAdministrativaPK entity = (JNormativaUnidadAdministrativaPK) o;
        return Objects.equals(this.normativa, entity.normativa) &&
                Objects.equals(this.unidadAdministrativa, entity.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normativa, unidadAdministrativa);
    }

}