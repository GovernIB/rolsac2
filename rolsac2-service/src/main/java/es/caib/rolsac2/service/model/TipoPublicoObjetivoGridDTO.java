package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un Tipo Publico Objetivo.
 *
 * @author Indra
 */
@Schema(name = "TipoPublicoObjetivoGrid")
public class TipoPublicoObjetivoGridDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Identificador
     */
    private String identificador;

    /**
     * Descripcion
     */
    private Literal descripcion;

    /**
     * Empleado publico.
     */
    private boolean empleadoPublico;

    /**
     * Instancia un nuevo Tipo publico objetivo grid dto.
     */
    public TipoPublicoObjetivoGridDTO() {
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
     * Establece id string.
     *
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia un nuevo Tipo publico objetivo grid dto.
     *
     * @param id the id
     */
    public TipoPublicoObjetivoGridDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene identificador.
     *
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador the identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return the descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEmpleadoPublico() {
        return empleadoPublico;
    }

    public void setEmpleadoPublico(boolean empleadoPublico) {
        this.empleadoPublico = empleadoPublico;
    }

    @Override
    public String toString() {
        return "TipoPublicoObjetivoGridDTO{" + "id=" + codigo + ", identificador=" + identificador + ", descripcion=" + descripcion.toString() + ", empleadoPublico=" + empleadoPublico + '}';
    }
}
