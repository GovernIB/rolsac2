package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "TipoVia")
public class TipoViaDTO extends ModelApi {

    private Long codigo;

    private String identificador;
    private Literal descripcion;

    public TipoViaDTO() {
        //Constructor vacio
    }

    public TipoViaDTO(Long id, String identificador, Literal descripcion) {
        this.codigo = id;
        this.identificador = identificador;
        this.descripcion = descripcion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoViaDTO that = (TipoViaDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, identificador, descripcion);
    }

    @Override
    public String toString() {
        return "TipoViaDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
