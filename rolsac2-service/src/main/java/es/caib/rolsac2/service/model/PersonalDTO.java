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
public class PersonalDTO {
    private Long id;
    @NotEmpty
    @Size(max = 50)
    private String nombre;

    private String identificador;
    private String funciones;
    private String cargo;
    private String email;
    private Long unidadAdministrativa;

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

    public PersonalDTO(Long id, String nombre, Long idUnitat) {
        this.id = id;
        this.nombre = nombre;
        this.unidadAdministrativa = idUnitat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(Long unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
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

    @Override
    public String toString() {
        return "PersonalDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idUnitat=" + unidadAdministrativa +
                '}';
    }
}
