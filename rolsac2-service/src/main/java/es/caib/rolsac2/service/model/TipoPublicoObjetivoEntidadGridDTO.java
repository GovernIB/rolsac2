package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Tipo Publico Objetivo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoPublicoObjetivoEntidadGrid")
public class TipoPublicoObjetivoEntidadGridDTO extends ModelApi {

    private Long codigo;
    private String tipo;
    private String identificador;

    private String entidad;

    private String descripcion;

    /**
     * Se utilizan la siguiente para el selector de procedimientos.
     **/
    private Long codigoProcWF;

    public TipoPublicoObjetivoEntidadGridDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
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

    public TipoPublicoObjetivoEntidadGridDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getEntidad() { return entidad; }

    public void setEntidad(String entidad) { this.entidad = entidad; }

    public Long getCodigoProcWF() {
        return codigoProcWF;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigoProcWF(Long codigoProcWF) {
        this.codigoProcWF = codigoProcWF;
    }


}
