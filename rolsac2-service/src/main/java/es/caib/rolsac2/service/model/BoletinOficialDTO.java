package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Boletin")
public class BoletinOficialDTO extends ModelApi {

    private Long codigo;

    private String identificador;

    private String nombre;

    private String url;

    private Long normativa;


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

    public Long getNormativa() {
        return normativa;
    }

    public void setNormativa(Long normativa) {
        this.normativa = normativa;
    }

    @Override
    public String toString() {
        return "BoletinDTO{" +
                "id=" + codigo +
                '}';
    }
}
