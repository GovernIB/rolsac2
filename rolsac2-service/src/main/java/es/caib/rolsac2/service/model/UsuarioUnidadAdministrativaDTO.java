package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "UsuarioUnidadAdministrativa")
public class UsuarioUnidadAdministrativaDTO extends ModelApi {
    private UsuarioUnidadAdministrativaPKDTO codigo;

    private UsuarioDTO usuario;

    private UnidadAdministrativaDTO unidadAdministrativa;

    public UsuarioUnidadAdministrativaPKDTO getCodigo() {
        return codigo;
    }

    public void setCodigo(UsuarioUnidadAdministrativaPKDTO codigo) {
        this.codigo = codigo;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioUnidadAdministrativaDTO that = (UsuarioUnidadAdministrativaDTO) o;
        return codigo.equals(that.codigo) && usuario.equals(that.usuario)
                && unidadAdministrativa.equals(that.unidadAdministrativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, usuario, unidadAdministrativa);
    }
}
