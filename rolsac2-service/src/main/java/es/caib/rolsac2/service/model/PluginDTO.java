package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Plugin dto.
 */
public class PluginDTO implements Serializable {
    private Long codigo;

    /**
     * Entidad
     **/
    private EntidadDTO entidad;

    /**
     * Descripcion
     **/
    private String descripcion;
    /**
     * Tipo
     **/
    private String tipo;

    /**
     * NOMBRE CLASE PLUGIN
     */
    private String classname;

    /**
     * PREFIJO PROPIEDADES
     */
    private String prefijoPropiedades;

    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    private List<Propiedad> propiedades;


    /**
     * Instancia un nuevo Plugin dto.
     *
     * @param id           id
     * @param entidad      entidad
     * @param descripcion  descripcion
     * @param classname    classname
     * @param propiedades  propiedades
     */
    public PluginDTO(Long id, EntidadDTO entidad, String descripcion, String classname, List<Propiedad> propiedades) {
        this.codigo = id;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.classname = classname;
        this.propiedades = propiedades;
    }

    /**
     * Instantiates a new Plugin dto.
     */
    public PluginDTO() {
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
     * Establece entidad.
     *
     * @param entidad  entidad
     */
    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
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
     * Establece classname.
     *
     * @param classname  classname
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * Establece propiedades.
     *
     * @param propiedades  propiedades
     */
    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
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
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public EntidadDTO getEntidad() {
        return entidad;
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
     * Obtiene classname.
     *
     * @return  classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * Obtiene propiedades.
     *
     * @return  propiedades
     */
    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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
    public void setPrefijoPropiedades(String prefijoPropiedades) { this.prefijoPropiedades = prefijoPropiedades; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginDTO pluginDTO = (PluginDTO) o;
        return Objects.equals(codigo, pluginDTO.codigo) && Objects.equals(entidad, pluginDTO.entidad) && Objects.equals(descripcion, pluginDTO.descripcion) && Objects.equals(tipo, pluginDTO.tipo) && Objects.equals(classname, pluginDTO.classname) && Objects.equals(prefijoPropiedades, pluginDTO.prefijoPropiedades) && Objects.equals(propiedades, pluginDTO.propiedades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, entidad, descripcion, tipo, classname, prefijoPropiedades, propiedades);
    }

    @Override
    public String toString() {
        return "PluginDTO{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", classname='" + classname + '\'' +
                ", prefijoPropiedades='" + prefijoPropiedades + '\'' +
                ", propiedades=" + propiedades +
                '}';
    }
}
