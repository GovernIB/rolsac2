package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Tema")
public class TemaDTO extends ModelApi {

    private Long codigo;

    private EntidadDTO entidad;

    private String identificador;

    private Long temaPadre;

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

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public Long getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(Long temaPadre) {
        this.temaPadre = temaPadre;
    }

    @Override
    public String toString() {
        return "TemaDTO{" +
                "id=" + codigo +
                '}';
    }
}
