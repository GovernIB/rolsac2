package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "configuracion-sequence", sequenceName = "RS2_CNFGLO_SEQ", allocationSize = 1)
@Table(name = "RS2_CNFGLO", indexes = {@Index(name = "RS2_CNFGLO_PK_I", columnList = "CFG_CODIGO")})

@NamedQueries({@NamedQuery(name = JConfiguracionGlobal.FIND_BY_ID,
        query = "select p from JConfiguracionGlobal p where p.codigo = :id")})

public class JConfiguracionGlobal {

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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String cfgProp) {
        this.propiedad = cfgProp;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String cfgValor) {
        this.valor = cfgValor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String cfgDescr) {
        this.descripcion = cfgDescr;
    }

    public Boolean getNoModificable() {
        return noModificable;
    }

    public void setNoModificable(Boolean cfgNomod) {
        this.noModificable = cfgNomod;
    }

}
