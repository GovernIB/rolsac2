package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
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

    public TipoFormaInicioDTO() {
    }

    public TipoFormaInicioDTO(Long id, String identificador) {
        this.codigo = id;
        this.identificador = identificador;
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

    public Literal getDescripcion() {
        return descripcion;
    }

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
