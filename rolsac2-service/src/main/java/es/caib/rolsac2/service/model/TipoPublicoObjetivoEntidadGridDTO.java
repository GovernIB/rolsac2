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

    private boolean empleadoPublico;

    /**
     * Instancia un nuevo Tipo publico objetivo entidad grid dto.
     */
    public TipoPublicoObjetivoEntidadGridDTO() {
    }


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
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
     * @param idString codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instancia un nuevo Tipo publico objetivo entidad grid dto.
     *
     * @param id id
     */
    public TipoPublicoObjetivoEntidadGridDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public Literal getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(Literal tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene codigo proc wf.
     *
     * @return codigo proc wf
     */
    public Long getCodigoProcWF() {
        return codigoProcWF;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece codigo proc wf.
     *
     * @param codigoProcWF codigo proc wf
     */
    public void setCodigoProcWF(Long codigoProcWF) {
        this.codigoProcWF = codigoProcWF;
    }

    /**
     * Obtiene si es empleado publico.
     *
     * @return empleadoPublico
     */
    public boolean isEmpleadoPublico() {
        return empleadoPublico;
    }

    /**
     * Establece el empleado publico
     *
     * @param empleadoPublico Si es empleado publico
     */
    public void setEmpleadoPublico(boolean empleadoPublico) {
        this.empleadoPublico = empleadoPublico;
    }
}
