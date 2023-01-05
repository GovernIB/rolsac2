package es.caib.rolsac2.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * La clase J plugin.
 */
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
    /**
     * La consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "Plugin.FIND_BY_ID";

    /**
     * Codigo
     **/
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
     * @param id  id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene entidad.
     *
     * @return  entidad
     */
    public JEntidad getEntidad() {
        return entidad;
    }

    /**
     * Establece entidad.
     *
     * @param plugCodenti  plug codenti
     */
    public void setEntidad(JEntidad plugCodenti) {
        this.entidad = plugCodenti;
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
     * @param plugDesc  plug desc
     */
    public void setDescripcion(String plugDesc) {
        this.descripcion = plugDesc;
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
     * @param plugClassname  plug classname
     */
    public void setClassname(String plugClassname) {
        this.classname = plugClassname;
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
     * @param plugProps  plug props
     */
    public void setPropiedades(String plugProps) {
        this.propiedades = plugProps;
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
                /*", entidad=" + entidad +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", classname='" + classname + '\'' +
                ", prefijoPropiedades='" + prefijoPropiedades + '\'' +
                ", propiedades='" + propiedades + '\'' +*/
                '}';
    }
}