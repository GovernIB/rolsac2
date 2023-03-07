package es.caib.rolsac2.service.model.filtro;

import es.caib.rolsac2.service.model.EntidadDTO;

public class PluginFiltro extends AbstractFiltro {

    private String texto;
    private Long id;
    private String descripcion;
    private String classname;
    private String propiedades;
    private String tipo;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isRellenoDescripcion() {
        return descripcion != null && !descripcion.isEmpty();
    }

    public boolean isRellenoId() {
        return id != null;
    }

    public boolean isRellenoTexto() {
        return texto != null && !texto.isEmpty();
    }

    public boolean isRellenoClassname() {
        return classname != null && !classname.isEmpty();
    }

    public boolean isRellenoPropiedades() {
        return propiedades != null && !propiedades.isEmpty();
    }

    @Override
    protected String getDefaultOrder() {
        return "id";
    }
}
