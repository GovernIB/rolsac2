package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Tema")
public class TemaGridDTO extends ModelApi {

    private Long codigo;
    private String entidad;
    private String identificador;
    private String temaPadre;

    public TemaGridDTO(){}

    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTemaPadre() {
        return temaPadre;
    }

    public void setTemaPadre(String temaPadre) {
        this.temaPadre = temaPadre;
    }

}
