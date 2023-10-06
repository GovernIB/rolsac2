package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

/**
 * La Clase J configuracion global.
 */
@Entity
@SequenceGenerator(name = "configuracion-sequence", sequenceName = "RS2_CNFGLO_SEQ", allocationSize = 1)
@Table(name = "RS2_CNFGLO", indexes = {@Index(name = "RS2_CNFGLO_PK_I", columnList = "CFG_CODIGO")})

@NamedQueries({@NamedQuery(name = JConfiguracionGlobal.FIND_BY_ID, query = "select p from JConfiguracionGlobal p where p.codigo = :id")})

public class JConfiguracionGlobal {

    /**
     * Consulta FIND_BY_ID.
     */
    public static final String FIND_BY_ID = "ConfiguracionGlobal.FIND_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracion-sequence")
    @Column(name = "CFG_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Propiedad
     **/
    @Column(name = "CFG_PROP", nullable = false, length = 100)
    private String propiedad;

    /**
     * Valor
     **/
    @Column(name = "CFG_VALOR", length = 500)
    private String valor;

    /**
     * Descripcion
     **/
    @Column(name = "CFG_DESCR", nullable = false)
    private String descripcion;

    /**
     * Indica si es no modificable.
     **/
    @Column(name = "CFG_NOMOD", nullable = false)
    private Boolean noModificable = false;

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param id id
     */
    public void setCodigo(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene propiedad.
     *
     * @return propiedad
     */
    public String getPropiedad() {
        return propiedad;
    }

    /**
     * Establece propiedad.
     *
     * @param cfgProp cfg prop
     */
    public void setPropiedad(String cfgProp) {
        this.propiedad = cfgProp;
    }

    /**
     * Obtiene valor.
     *
     * @return valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece valor.
     *
     * @param cfgValor cfg valor
     */
    public void setValor(String cfgValor) {
        this.valor = cfgValor;
    }

    /**
     * Obtiene descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece descripcion.
     *
     * @param cfgDescr cfg descr
     */
    public void setDescripcion(String cfgDescr) {
        this.descripcion = cfgDescr;
    }

    /**
     * Obtiene no modificable.
     *
     * @return no modificable
     */
    public Boolean getNoModificable() {
        return noModificable;
    }

    /**
     * Establece no modificable.
     *
     * @param cfgNomod cfg nomod
     */
    public void setNoModificable(Boolean cfgNomod) {
        this.noModificable = cfgNomod;
    }

}
