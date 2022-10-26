package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoTramiteTraduccion;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-tram-sequence", sequenceName = "RS2_PRCTRM_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCTRM", indexes = {@Index(name = "RS2_PRCTRM_PK_I", columnList = "PRTA_CODIGO")})
@NamedQueries({
        @NamedQuery(name = JProcedimientoTramite.FIND_BY_ID,
                query = "select p from JProcedimientoTramite p where p.codigo = :id"),
        @NamedQuery(name = JProcedimientoTramite.FIND_BY_PROC_ID,
                query = "select p from JProcedimientoTramite p where p.procedimiento.procedimiento.codigo = :id")
})

public class JProcedimientoTramite {
    public static final String FIND_BY_ID = "ProcedimientoTramite.FIND_BY_ID";
    public static final String FIND_BY_PROC_ID = "ProcedimientoTramite.FIND_BY_PROC_ID";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-sequence")
    @Column(name = "PRTA_CODIGO", nullable = false)
    private Long codigo;

    /**
     * Unidad administrativa competente
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODUAC", nullable = false)
    private JUnidadAdministrativa unidadAdministrativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_CODPRWF", nullable = false)
    private JProcedimientoWorkflow procedimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRTA_TRMPRE", nullable = false)
    private JTipoTramitacion tipoTramitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRTA_LSTDOC")
    private JListaDocumentos listaDocumentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LSDO_CODIGO")
    private JListaDocumentos listaModelos;

    @Column(name = "PRTA_TASA", nullable = false)
    private Boolean tasaAsociada = false;

    @Column(name = "PRTA_FECPUB")
    private Date fechaPublicacion;

    @Column(name = "PRTA_FECINI")
    private Date fechaInicio;

    @Column(name = "PRTA_FECCIE")
    private Date fechaCierre;

    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "procedimientoTramite", fetch = FetchType.EAGER, cascade = CascadeType.ALL,
                    orphanRemoval = true)
    private List<JProcedimientoTramiteTraduccion> traducciones;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
    }

    public JUnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(JUnidadAdministrativa prtaCoduac) {
        this.unidadAdministrativa = prtaCoduac;
    }

    public JProcedimientoWorkflow getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimientoWorkflow prtaCodprwf) {
        this.procedimiento = prtaCodprwf;
    }

    public JTipoTramitacion getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(JTipoTramitacion prtaTrmpre) {
        this.tipoTramitacion = prtaTrmpre;
    }

    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(JListaDocumentos prtaLstdoc) {
        this.listaDocumentos = prtaLstdoc;
    }

    public JListaDocumentos getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(JListaDocumentos lsdoCodigo) {
        this.listaModelos = lsdoCodigo;
    }

    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    public void setTasaAsociada(Boolean prtaTasa) {
        this.tasaAsociada = prtaTasa;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public List<JProcedimientoTramiteTraduccion> getTraducciones() {
        return traducciones;
    }

    public void setTraducciones(List<JProcedimientoTramiteTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }
}
