package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Dades d'un Personal.
 *
 * @author Indra
 */
@Schema(name = "PersonalGrid")
public class PersonalGridDTO extends ModelApi {

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
     * Email
     */
    private String email;

    /**
     * Instantiates a new Personal grid dto.
     */
    public PersonalGridDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia un nuevo Personal grid dto.
     *
     * @param id      id
     * @param nombre  nombre
     */
    public PersonalGridDTO(Long id, String nombre) {
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

    @Override
    public String toString() {
        return "PersonalDTO{" +
                "id=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", username=" + identificador +
                '}';
    }
}
