package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(name = "TipoMediaFicha")
public class TipoMediaFichaDTO {

    private Long codigo;

    @NotEmpty
    @Size(max = 50)
    private String identificador;
    private Literal descripcion;

    public TipoMediaFichaDTO() {
    }

    public TipoMediaFichaDTO(Long id, String identificador) {
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

    //todo toString()
}
