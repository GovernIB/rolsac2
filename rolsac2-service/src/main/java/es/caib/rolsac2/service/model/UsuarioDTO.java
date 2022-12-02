package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "Usuario")
public class UsuarioDTO extends ModelApi {

    private Long codigo;

    private String identificador;

    private EntidadDTO entidad;

    private List<UnidadAdministrativaGridDTO> unidadesAdministrativas;

    private String nombre;

    private String email;

    private Literal observaciones;

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

    public List<UnidadAdministrativaGridDTO> getUnidadesAdministrativas() {
        return unidadesAdministrativas;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() {  return email; }

    public void setEmail(String email) { this.email = email; }

    public Literal getObservaciones() {  return observaciones; }

    public void setObservaciones(Literal observaciones) { this.observaciones = observaciones; }

    public void setUnidadesAdministrativas(List<UnidadAdministrativaGridDTO> unidadesAdministrativas) {
        this.unidadesAdministrativas = unidadesAdministrativas;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", entidad=" + entidad +
                ", usuarioUnidadAdministrativa=" + unidadesAdministrativas +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", observaciones=" + observaciones +
                '}';
    }
}
