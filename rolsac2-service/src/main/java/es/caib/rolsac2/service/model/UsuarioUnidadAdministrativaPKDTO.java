package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Usuario unidad administrativa pkdto.
 */
@Schema(name = "UsuarioUnidadAdministrativaPK")
public class UsuarioUnidadAdministrativaPKDTO extends ModelApi {

    /**
     * Usuario
     */
    private Long usuario;

    /**
     * Unidad administrativa
     */
    private Long unidadAdministrativa;

    /**
     * Obtiene usuario.
     *
     * @return  usuario
     */
    public Long getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario  usuario
     */
    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return  unidad administrativa
     */
    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa  unidad administrativa
     */
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
