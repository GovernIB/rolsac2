package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PluginDto implements Serializable {
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
     * NOMBRE CLASE PLUGIN
     */
    private String classname;

    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    private List<Propiedad> propiedades;


    public PluginDto(Long id, EntidadDTO entidad, String descripcion, String classname, List<Propiedad> propiedades) {
        this.codigo = id;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.classname = classname;
        this.propiedades = propiedades;
    }

    public PluginDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginDto entity = (PluginDto) o;
        return Objects.equals(this.codigo, entity.codigo) &&
                Objects.equals(this.entidad, entity.entidad) &&
                Objects.equals(this.descripcion, entity.descripcion) &&
                Objects.equals(this.classname, entity.classname) &&
                Objects.equals(this.propiedades, entity.propiedades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, entidad, descripcion, classname, propiedades);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + codigo + ", " +
                "entidad = " + entidad + ", " +
                "descripcion = " + descripcion + ", " +
                "classname = " + classname + ", " +
                "propiedades = " + propiedades + ")";
    }
}
