package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "plugin-sequence", sequenceName = "RS2_PLUGIN_SEQ", allocationSize = 1)
@Table(name = "RS2_PLUGIN",
        indexes = {
                @Index(name = "RS2_PLUGIN_PK_I", columnList = "PLUG_CODIGO")
        }
)
@NamedQueries({
        @NamedQuery(name = JPlugin.FIND_BY_ID,
                query = "select p from JPlugin p where p.codigo = :id"),
})
public class JPlugin extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_ID = "Plugin.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plugin-sequence")
    @Column(name = "PLUG_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Entidad
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PLUG_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Tipo
     **/
    @Column(name = "PLUG_TIPO", nullable = true, length = 3, unique = true)
    private String tipo;

    /**
     * Descripción
     **/
    @Column(name = "PLUG_DESC", nullable = false)
    private String descripcion;

    /**
     * NOMBRE CLASE PLUGIN
     */
    @Column(name = "PLUG_CLASSNAME", nullable = false, length = 1000)
    private String classname;

    /**
     * PREFIJO DE LAS PROPIEDADES
     */
    @Column(name = "PLUG_PREPRO",length = 100)
    private String prefijoPropiedades;
    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    @Column(name = "PLUG_PROPS", length = 4000)
    private String propiedades;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(JEntidad plugCodenti) {
        this.entidad = plugCodenti;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String plugDesc) {
        this.descripcion = plugDesc;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String plugClassname) {
        this.classname = plugClassname;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String plugProps) {
        this.propiedades = plugProps;
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
        JPlugin jPlugin = (JPlugin) o;
        return Objects.equals(codigo, jPlugin.codigo) && Objects.equals(entidad, jPlugin.entidad) && Objects.equals(tipo, jPlugin.tipo) && Objects.equals(descripcion, jPlugin.descripcion) && Objects.equals(classname, jPlugin.classname) && Objects.equals(prefijoPropiedades, jPlugin.prefijoPropiedades) && Objects.equals(propiedades, jPlugin.propiedades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, entidad, tipo, descripcion, classname, prefijoPropiedades, propiedades);
    }

    @Override
    public String toString() {
        return "JPlugin{" +
                "codigo=" + codigo +
                ", entidad=" + entidad +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", classname='" + classname + '\'' +
                ", prefijoPropiedades='" + prefijoPropiedades + '\'' +
                ", propiedades='" + propiedades + '\'' +
                '}';
    }
}