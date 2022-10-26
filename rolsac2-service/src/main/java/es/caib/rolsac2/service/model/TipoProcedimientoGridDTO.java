package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "TipoProcedimientoGrid")
public class TipoProcedimientoGridDTO extends ModelApi {

    private Long codigo;
    private String identificador;
    private Literal descripcion;

    public TipoProcedimientoGridDTO() {
    }

    public TipoProcedimientoGridDTO(Long id, String identificador) {
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

    @Override
    public String toString() {
        return "TipoProcedimientoGridDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
