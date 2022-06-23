package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "plugin-sequence", sequenceName = "RS2_PLUGIN_SEQ", allocationSize = 1)
@Table(name = "RS2_PLUGIN",
        indexes = {
                @Index(name = "RS2_PLUGIN_PK_I", columnList = "PLUG_CODIGO")
        }
)
public class JPlugin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plugin-sequence")
    @Column(name = "PLUG_CODIGO", nullable = false)
    private Integer id;

    /**
     * Entidad
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PLUG_CODENTI", nullable = false)
    private JEntidad entidad;

    /**
     * Descripcion
     **/
    @Column(name = "PLUG_DESC", nullable = false)
    private String descripcion;

    /**
     * NOMBRE CLASE PLUGIN
     */
    @Column(name = "PLUG_CLASSNAME", nullable = false, length = 1000)
    private String classname;

    /**
     * PROPIEDADES PLUGIN (JSON)
     */
    @Column(name = "PLUG_PROPS", length = 4000)
    private String propiedades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}