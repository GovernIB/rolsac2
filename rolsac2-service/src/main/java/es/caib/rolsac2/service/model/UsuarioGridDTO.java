package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * El tipo Usuario grid dto.
 */
@Schema(name = "UsuarioGrid")
public class UsuarioGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Numero
     */
    private Integer numero = 0;

	/**
     * Entidad
     */
    private String entidad;

    /**
     * Nombre
     */
    private String nombre;

    /**
     * Email
     */
    private String email;

    /**
     * Instancia un nuevo Usuario grid dto.
     */
    public UsuarioGridDTO() {

    }

    /**
     * Instancia un nuevo Usuario grid dto.
     *
     * @param id             id
     * @param identificador  identificador
     */
    public UsuarioGridDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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
    public String getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
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
    public String getEmail() { return email; }

    /**
     * Establece email.
     *
     * @param email  email
     */
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "UsuarioGridDTO{" +
                "codigo=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", entidad='" + entidad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioGridDTO that = (UsuarioGridDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador) && Objects.equals(entidad, that.entidad) && Objects.equals(nombre, that.nombre) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, entidad, nombre, email);
    }

    /**
     * Se hace a este nivel manualmente el clonar.
     *
     * @return
     */
    @Override
    public Object clone() {
        UsuarioGridDTO tipo = new UsuarioGridDTO();
        tipo.setCodigo(this.getCodigo());
        tipo.setIdentificador(this.getIdentificador());
        tipo.setNombre(this.getNombre());
        tipo.setEntidad(this.getEntidad());
        tipo.setEmail(this.getEmail());
        tipo.setIdString(this.getIdString());
        return tipo;
    }

    public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
