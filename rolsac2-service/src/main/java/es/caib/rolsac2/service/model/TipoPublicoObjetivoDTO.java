package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un TipoPublicoObjetivo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoPublicoObjetivo")
public class TipoPublicoObjetivoDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Empleado publico
     */
    private boolean empleadoPublico;

    /**
     * Instancia un nuevo Tipo publico objetivo dto.
     */
    public TipoPublicoObjetivoDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
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
     * @param idString codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instantiates a new Tipo publico objetivo dto.
     *
     * @param id id
     */
    public TipoPublicoObjetivoDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEmpleadoPublico() {
        return empleadoPublico;
    }

    public void setEmpleadoPublico(boolean empleadoPublico) {
        this.empleadoPublico = empleadoPublico;
    }

    @Override
    public String toString() {
        return "TipoPublicoObjetivoDTO{" +
                "id=" + codigo +
                ", identificador=" + identificador +
                ", descripcion=" + descripcion.toString() +
                ", empleadoPublico=" + empleadoPublico +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoPublicoObjetivoDTO that = (TipoPublicoObjetivoDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion, empleadoPublico);
    }
}
