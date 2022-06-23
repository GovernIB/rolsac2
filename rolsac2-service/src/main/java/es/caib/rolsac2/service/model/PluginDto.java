package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.Objects;

public class PluginDto implements Serializable {
    private final Integer id;

    /**
     * Entidad
     **/
    private final EntidadDTO entidad;

    /**
     * Descripcion
     **/
    private final String descripcion;
    /**
     * NOMBRE CLASE PLUGIN
     */
    private final String classname;

    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    private final String propiedades;

    public PluginDto(Integer id, EntidadDTO entidad, String descripcion, String classname, String propiedades) {
        this.id = id;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.classname = classname;
        this.propiedades = propiedades;
    }

    public Integer getId() {
        return id;
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

    public String getPropiedades() {
        return propiedades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginDto entity = (PluginDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.entidad, entity.entidad) &&
                Objects.equals(this.descripcion, entity.descripcion) &&
                Objects.equals(this.classname, entity.classname) &&
                Objects.equals(this.propiedades, entity.propiedades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entidad, descripcion, classname, propiedades);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "entidad = " + entidad + ", " +
                "descripcion = " + descripcion + ", " +
                "classname = " + classname + ", " +
                "propiedades = " + propiedades + ")";
    }
}
