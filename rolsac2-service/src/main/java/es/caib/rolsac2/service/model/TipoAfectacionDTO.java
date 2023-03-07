package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo afectacion dto.
 *
 * @author Indra
 */
@Schema(name = "TipoAfectacion")
public class TipoAfectacionDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    @NotEmpty
    @Size(max = 50)
    private String identificador;

    /**
     * Descripción
     */
    private Literal descripcion;

    /**
     * Instancia un nuevo Tipo afectacion dto.
     */
    public TipoAfectacionDTO() {
    }

    /**
     * Instantiates a new Tipo afectacion dto.
     *
     * @param id             id
     * @param identificador  identificador
     */
    public TipoAfectacionDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
    }

    public TipoAfectacionDTO(TipoAfectacionDTO otro) {
        if (otro != null) {
            this.codigo = otro.codigo;
            this.identificador = otro.identificador;
            this.descripcion = otro.descripcion == null ? null : (Literal) otro.descripcion.clone();
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
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public TipoAfectacionDTO clone() {
        return new TipoAfectacionDTO(this);
    }

    @Override
    public String toString() {
        return "TipoAfectacionDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAfectacionDTO that = (TipoAfectacionDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(identificador, that.identificador) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }
}
