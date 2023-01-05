package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

/**
 * El tipo Usuario dto.
 */
@Schema(name = "Usuario")
public class UsuarioDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Entidad
     */
    private EntidadDTO entidad;

    /**
     * Unidades administrativas
     */
    private List<UnidadAdministrativaGridDTO> unidadesAdministrativas;

    /**
     * Nombre
     */
    private String nombre;

    /**
     * Email
     */
    private String email;

    /**
     * Observaciones
     */
    private Literal observaciones;

    /**
     * Instancia un nuevo Usuario dto.
     */
    public UsuarioDTO() {
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene unidades administrativas.
     *
     * @return  unidades administrativas
     */
    public List<UnidadAdministrativaGridDTO> getUnidadesAdministrativas() {
        return unidadesAdministrativas;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public String getNombre() { return nombre; }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene email.
     *
     * @return  email
     */
    public String getEmail() {  return email; }

    /**
     * Establece email.
     *
     * @param email  email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Obtiene observaciones.
     *
     * @return  observaciones
     */
    public Literal getObservaciones() {  return observaciones; }

    /**
     * Establece observaciones.
     *
     * @param observaciones  observaciones
     */
    public void setObservaciones(Literal observaciones) { this.observaciones = observaciones; }

    /**
     * Establece unidades administrativas.
     *
     * @param unidadesAdministrativas  unidades administrativas
     */
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
