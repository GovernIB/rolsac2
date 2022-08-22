package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(name = "TipoBoletin")
public class TipoBoletinDTO extends ModelApi {

    private Long codigo;
    @NotEmpty
    @Size(max = 50)
    private String identificador;
    private String nombre;
    private String url;

    public TipoBoletinDTO() {
    }

    public TipoBoletinDTO(Long id, String identificador, String nombre, String url) {
        this.codigo = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.url = url;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TipoBoletinDTO{" +
                "id=" + codigo +
                ", identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
