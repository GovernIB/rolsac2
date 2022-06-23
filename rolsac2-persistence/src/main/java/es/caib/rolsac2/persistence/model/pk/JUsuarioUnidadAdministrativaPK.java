package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JUsuarioUnidadAdministrativaPK implements Serializable {
    private static final long serialVersionUID = -4971184161239060579L;
    @Column(name = "UAUS_CODUSER", nullable = false)
    private Integer usuario;

    @Column(name = "UAUS_CODUA", nullable = false)
    private Integer unidadAdministrativa;

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer uausCoduser) {
        this.usuario = uausCoduser;
    }

    public Integer getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Integer uausCodua) {
        this.unidadAdministrativa = uausCodua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        JUsuarioUnidadAdministrativaPK entity = (JUsuarioUnidadAdministrativaPK) o;
        return Objects.equals(this.usuario, entity.usuario) &&
                Objects.equals(this.unidadAdministrativa, entity.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, unidadAdministrativa);
    }

}