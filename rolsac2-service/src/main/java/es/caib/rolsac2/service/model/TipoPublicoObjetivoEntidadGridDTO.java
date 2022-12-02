package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Tipo Publico Objetivo.
 *
 * @author Indra
 */
@Schema(name = "TipoPublicoObjetivoEntidadGrid")
public class TipoPublicoObjetivoEntidadGridDTO extends ModelApi {

    private Long codigo;
    private Literal tipo;
    private String identificador;

    private Literal descripcion;

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

    public Literal getTipo() {
        return tipo;
    }

    public void setTipo(Literal tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Long getCodigoProcWF() {
        return codigoProcWF;
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigoProcWF(Long codigoProcWF) {
        this.codigoProcWF = codigoProcWF;
    }


}
