package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Usuario unidad administrativa dto.
 */
@Schema(name = "UsuarioUnidadAdministrativa")
public class UsuarioUnidadAdministrativaDTO extends ModelApi {
    /**
     * Codigo
     */
    private UsuarioUnidadAdministrativaPKDTO codigo;

    /**
     * Usuario
     */
    private UsuarioDTO usuario;

    /**
     * Unidad administrativa
     */
    private UnidadAdministrativaDTO unidadAdministrativa;

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public UsuarioUnidadAdministrativaPKDTO getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(UsuarioUnidadAdministrativaPKDTO codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene usuario.
     *
     * @return  usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario  usuario
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa  unidad administrativa
     */
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
