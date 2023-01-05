package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades d'un TipoPublicoObjetivoEntitat.
 */
@Schema(name = "TipoPublicoObjetivoEntidad")
public class TipoPublicoObjetivoEntidadDTO extends ModelApi {

    private Long codigo;
    private EntidadDTO entidad;
    private TipoPublicoObjetivoDTO tipo;
    private String identificador;

    private Literal descripcion;


    /**
     * Instancia un nuevo Tipo publico objetivo entidad dto.
     */
    public TipoPublicoObjetivoEntidadDTO() {}


    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
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
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instantiates a new Tipo publico objetivo entidad dto.
     *
     * @param id  id
     */
    public TipoPublicoObjetivoEntidadDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param cod  cod
     */
    public void setEntidad(EntidadDTO cod) {
        this.entidad = cod;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public TipoPublicoObjetivoDTO getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(TipoPublicoObjetivoDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene identificador.
     *
     * @return  identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece identificador.
     *
     * @param identificador  identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoPublicoObjetivoEntidadDTO{" + "id=" + codigo + ", entidad=" + entidad + ", tipo=" + tipo
                        + ", identificador=" + identificador + "}";
    }
}
