package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "UsuarioUnidadAdministrativaPK")
public class UsuarioUnidadAdministrativaPKDTO extends ModelApi {

    private Long usuario;

    private Long unidadAdministrativa;

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioUnidadAdministrativaPKDTO that = (UsuarioUnidadAdministrativaPKDTO) o;
        return usuario.equals(that.usuario) && unidadAdministrativa.equals(that.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, unidadAdministrativa);
    }
}
