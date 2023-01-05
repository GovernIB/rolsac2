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
    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Nombre
     */
    @NotEmpty
    @Size(max = 50)
    private String nombre;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Funciones
     */
    private String funciones;

    /**
     * Cargo
     */
    private String cargo;

    /**
     * Email
     */
    private String email;

    /**
     * Unidad administrativa
     */
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

    /**
     * Instantiates a new Personal dto.
     */
    public PersonalDTO() {
    }

    /**
     * Instantiates a new Personal dto.
     *
     * @param id      id
     * @param nombre  nombre
     */
    public PersonalDTO(Long id, String nombre) {
        this.codigo = id;
        this.nombre = nombre;
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
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * Obtiene funciones.
     *
     * @return  funciones
     */
    public String getFunciones() {
        return funciones;
    }

    /**
     * Establece funciones.
     *
     * @param funciones  funciones
     */
    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    /**
     * Obtiene cargo.
     *
     * @return  cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece cargo.
     *
     * @param cargo  cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene email.
     *
     * @return  email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece email.
     *
     * @param email  email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene telefono fijo.
     *
     * @return  telefono fijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * Establece telefono fijo.
     *
     * @param telefonoFijo  telefono fijo
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * Obtiene telefono movil.
     *
     * @return  telefono movil
     */
    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    /**
     * Establece telefono movil.
     *
     * @param telefonoMovil  telefono movil
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    /**
     * Obtiene telefono exterior fijo.
     *
     * @return  telefono exterior fijo
     */
    public String getTelefonoExteriorFijo() {
        return telefonoExteriorFijo;
    }

    /**
     * Establece telefono exterior fijo.
     *
     * @param telefonoExteriorFijo  telefono exterior fijo
     */
    public void setTelefonoExteriorFijo(String telefonoExteriorFijo) {
        this.telefonoExteriorFijo = telefonoExteriorFijo;
    }

    /**
     * Obtiene telefono exterior movil.
     *
     * @return  telefono exterior movil
     */
    public String getTelefonoExteriorMovil() {
        return telefonoExteriorMovil;
    }

    /**
     * Establece telefono exterior movil.
     *
     * @param telefonoExteriorMovil  telefono exterior movil
     */
    public void setTelefonoExteriorMovil(String telefonoExteriorMovil) {
        this.telefonoExteriorMovil = telefonoExteriorMovil;
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
    public String toString() {
        return "PersonalDTO{" + "id=" + codigo + ", nombre='" + nombre + '\'' + '}';
    }
}
