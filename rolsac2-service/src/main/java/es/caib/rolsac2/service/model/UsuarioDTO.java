package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "Usuario")
public class UsuarioDTO extends ModelApi {

    private Long codigo;

    private String identificador;

    private EntidadDTO entidad;

    private List<UsuarioUnidadAdministrativaDTO> usuarioUnidadAdministrativa;

    public UsuarioDTO() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public List<UsuarioUnidadAdministrativaDTO> getUsuarioUnidadAdministrativa() {
        return usuarioUnidadAdministrativa;
    }

    public void setUsuarioUnidadAdministrativa(List<UsuarioUnidadAdministrativaDTO> usuarioUnidadAdministrativa) {
        this.usuarioUnidadAdministrativa = usuarioUnidadAdministrativa;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + ", entidad=" + entidad + '}';
    }
}
