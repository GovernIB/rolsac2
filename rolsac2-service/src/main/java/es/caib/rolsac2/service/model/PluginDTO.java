package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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


    public PluginDTO(Long id, EntidadDTO entidad, String descripcion, String classname, List<Propiedad> propiedades) {
        this.codigo = id;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.classname = classname;
        this.propiedades = propiedades;
    }

    public PluginDTO() {
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public Long getCodigo() {
        return codigo;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getClassname() {
        return classname;
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrefijoPropiedades() { return prefijoPropiedades; }

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
