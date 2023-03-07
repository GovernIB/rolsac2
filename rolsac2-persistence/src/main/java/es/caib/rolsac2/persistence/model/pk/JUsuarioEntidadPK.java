package es.caib.rolsac2.persistence.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JUsuarioEntidadPK implements Serializable {
    private static final long serialVersionUID = -4971184161239060579L;
    @Column(name = "USEN_CODUSER", nullable = false)
    private Long usuario;

    @Column(name = "USEN_CODENTI", nullable = false)
    private Long entidad;

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long uausCoduser) {
        this.usuario = uausCoduser;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JUsuarioEntidadPK that = (JUsuarioEntidadPK) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(entidad, that.entidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, entidad);
    }
}
