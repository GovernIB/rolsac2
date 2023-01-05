package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.Objects;

/**
 * Dades d'un TipoSilencioAdministrativo.
 *
 * @author jsegovia
 */
@Schema(name = "TipoSilencioAdministrativo")
public class TipoSilencioAdministrativoDTO extends ModelApi {

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
     * Segunda descripcion
     */
    private Object descripcion2;

    /**
     * Fecha de borrado
     */
    private Date fechaBorrar;

    /**
     * Instancia un nuevo Tipo silencio administrativo dto.
     */
    public TipoSilencioAdministrativoDTO() {
    }


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
     * Instantiates a new Tipo silencio administrativo dto.
     *
     * @param id  id
     */
    public TipoSilencioAdministrativoDTO(Long id) {
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
        return "TipoSilencioAdministrativo{" +
                "id=" + codigo +
                ", identificador=" + identificador +
                ", descripcion=" + descripcion.toString() +
                '}';
    }

    /**
     * Obtiene descripcion 2.
     *
     * @return  descripcion 2
     */
    public Object getDescripcion2() {
        return descripcion2;
    }

    /**
     * Establece descripcion 2.
     *
     * @param descripcion2  descripcion 2
     */
    public void setDescripcion2(Object descripcion2) {
        this.descripcion2 = descripcion2;
    }

    /**
     * Obtiene fecha borrar.
     *
     * @return  fecha borrar
     */
    public Date getFechaBorrar() {
        return fechaBorrar;
    }

    /**
     * Establece fecha borrar.
     *
     * @param fechaBorrar  fecha borrar
     */
    public void setFechaBorrar(Date fechaBorrar) {
        this.fechaBorrar = fechaBorrar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoSilencioAdministrativoDTO that = (TipoSilencioAdministrativoDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
