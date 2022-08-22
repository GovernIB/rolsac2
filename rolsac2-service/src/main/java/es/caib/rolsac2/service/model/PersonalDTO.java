package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Dades d'un Personal.
 *
 * @author Indra
 */
@Schema(name = "Personal")
public class PersonalDTO extends ModelApi {
    private Long codigo;
    @NotEmpty
    @Size(max = 50)
    private String nombre;

    private String identificador;
    private String funciones;
    private String cargo;
    private String email;
    private UnidadAdministrativaDTO unidadAdministrativa;

    /**
     * Telefono Fijo
     **/
    private String telefonoFijo;

    /**
     * Telefono movil
     **/
    private String telefonoMovil;

    /**
     * Telefono Exterior fijo
     **/
    private String telefonoExteriorFijo;

    /**
     * Telefono Exterior movil
     **/
    private String telefonoExteriorMovil;

    public PersonalDTO() {
    }

    public PersonalDTO(Long id, String nombre) {
        this.codigo = id;
        this.nombre = nombre;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoExteriorFijo() {
        return telefonoExteriorFijo;
    }

    public void setTelefonoExteriorFijo(String telefonoExteriorFijo) {
        this.telefonoExteriorFijo = telefonoExteriorFijo;
    }

    public String getTelefonoExteriorMovil() {
        return telefonoExteriorMovil;
    }

    public void setTelefonoExteriorMovil(String telefonoExteriorMovil) {
        this.telefonoExteriorMovil = telefonoExteriorMovil;
    }

    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    @Override
    public String toString() {
        return "PersonalDTO{" + "id=" + codigo + ", nombre='" + nombre + '\'' + '}';
    }
}
