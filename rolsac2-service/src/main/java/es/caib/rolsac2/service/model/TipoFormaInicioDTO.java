package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * El tipo Tipo forma inicio dto.
 *
 * @author Indra
 */
@Schema(name = "TipoFormaInicio")
public class TipoFormaInicioDTO extends ModelApi {

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
    // private Long descripcion;

    /**
     * Instancia un nuevo Tipo forma inicio dto.
     */
    public TipoFormaInicioDTO() {
    }

    /**
     * Instancia un nuevo Tipo forma inicio dto.
     *
     * @param id             id
     * @param identificador  identificador
     */
    public TipoFormaInicioDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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

    // public Long getDescripcion() {
    // return descripcion;
    // }
    //
    // public void setDescripcion(Long descripcion) {
    // this.descripcion = descripcion;
    // }

    @Override
    public String toString() {
        return "TipoFormaInicioDTO{" + "id=" + codigo + ", identificador='" + identificador + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoFormaInicioDTO that = (TipoFormaInicioDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
