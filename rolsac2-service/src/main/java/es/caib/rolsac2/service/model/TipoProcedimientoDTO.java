package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "TipoProcedimiento")
public class TipoProcedimientoDTO extends ModelApi {

    private Long codigo;
    @NotEmpty
    @Size(max = 50)
    private String identificador;
    private Literal descripcion;
    private EntidadDTO entidad;

    public TipoProcedimientoDTO() {
    }

    //todo descripcion
    public TipoProcedimientoDTO(Long id, String identificador) {
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

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoProcedimientoDTO that = (TipoProcedimientoDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }

    @Override
    public String toString() {
        return "TipoProcedimientoDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
