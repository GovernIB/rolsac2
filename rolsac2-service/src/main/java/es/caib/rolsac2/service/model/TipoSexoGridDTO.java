package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "TipoSexoGrid")
public class TipoSexoGridDTO extends ModelApi {

    private Long codigo;
    private String identificador;
    private Literal descripcion;

    public TipoSexoGridDTO() {
    }

    public TipoSexoGridDTO(Long id, String identificador) {
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
        return "TipoSexoGridDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", descripcion=" + descripcion +
                '}';
    }
}
