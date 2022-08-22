package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Seccion")
public class SeccionDTO extends ModelApi {

    private Long codigo;

    private EntidadDTO entidad;

    private String identificador;

    private Long padre;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Long getPadre() {
        return padre;
    }

    public void setPadre(Long padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        return "SeccionDTO{" +
                "id=" + codigo +
                '}';
    }
}
