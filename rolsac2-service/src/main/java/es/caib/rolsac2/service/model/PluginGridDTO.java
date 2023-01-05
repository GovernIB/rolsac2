package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * El tipo Plugin grid dto.
 */
@Schema(name = "PluginGrid")
public class PluginGridDTO extends ModelApi {

    private Long codigo;
    private String entidad;
    private String descripcion;
    private String classname;
    private String propiedades;
    private String tipo;

    private String prefijoPropiedades;

    /**
     * Instancia un nuevo Plugin grid dto.
     */
    public PluginGridDTO() {
    }

    /**
     * Instantiates a new Plugin grid dto.
     *
     * @param id  id
     */
    public PluginGridDTO(Long id) {
        this.codigo = id;
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
    public String getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * Obtiene descripcion.
     *
     * @return  descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param descripcion  descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene classname.
     *
     * @return  classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * Establece classname.
     *
     * @param classname  classname
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * Obtiene propiedades.
     *
     * @return  propiedades
     */
    public String getPropiedades() {
        return propiedades;
    }

    /**
     * Establece propiedades.
     *
     * @param propiedades  propiedades
     */
    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() { return tipo;  }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /**
     * Obtiene prefijo propiedades.
     *
     * @return  prefijo propiedades
     */
    public String getPrefijoPropiedades() { return prefijoPropiedades; }

    /**
     * Establece prefijo propiedades.
     *
     * @param prefijoPropiedades  prefijo propiedades
     */
    public void setPrefijoPropiedades(String prefijoPropiedades) {  this.prefijoPropiedades = prefijoPropiedades; }

    @Override
    public String toString() {
        return "PluginGridDTO{" +
                "id=" + codigo +
                ", entidad='" + entidad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", classname='" + classname + '\'' +
                ", propiedades='" + propiedades + '\'' +
                '}';
    }
}
