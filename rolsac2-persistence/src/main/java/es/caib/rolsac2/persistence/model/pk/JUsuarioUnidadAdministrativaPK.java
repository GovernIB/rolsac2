package es.caib.rolsac2.persistence.model.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class JUsuarioUnidadAdministrativaPK implements Serializable {
    private static final long serialVersionUID = -4971184161239060579L;
    @Column(name = "UAUS_CODUSER", nullable = false)
    private Long usuario;

    @Column(name = "UAUS_CODUA", nullable = false)
    private Long unidadAdministrativa;

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long uausCoduser) {
        this.usuario = uausCoduser;
    }

    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long uausCodua) {
        this.unidadAdministrativa = uausCodua;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        JUsuarioUnidadAdministrativaPK entity = (JUsuarioUnidadAdministrativaPK) o;
        return Objects.equals(this.usuario, entity.usuario)
                        && Objects.equals(this.unidadAdministrativa, entity.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, unidadAdministrativa);
    }

}
