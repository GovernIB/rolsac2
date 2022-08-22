package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "PluginGrid")
public class PluginGridDTO extends ModelApi {

    private Long codigo;
    private String entidad;
    private String descripcion;
    private String classname;
    private String propiedades;

    public PluginGridDTO() {
    }

    public PluginGridDTO(Long id) {
        this.codigo = id;
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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

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
