package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JEdificioUnidadAdministrativaPK implements Serializable {
    private static final long serialVersionUID = 5291147853866863505L;
    @Column(name = "UAED_CODEDI", nullable = false)
    private Integer edificio;

    @Column(name = "UAED_CODUA", nullable = false)
    private Integer unidadAdministrativa;

    public Integer getEdificio() {
        return edificio;
    }

    public void setEdificio(Integer uaedCodedi) {
        this.edificio = uaedCodedi;
    }

    public Integer getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Integer uaedCodua) {
        this.unidadAdministrativa = uaedCodua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JEdificioUnidadAdministrativaPK entity = (JEdificioUnidadAdministrativaPK) o;
        return Objects.equals(this.edificio, entity.edificio) &&
                Objects.equals(this.unidadAdministrativa, entity.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edificio, unidadAdministrativa);
    }

}