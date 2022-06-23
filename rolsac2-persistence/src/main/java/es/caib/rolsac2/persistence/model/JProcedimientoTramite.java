package es.caib.rolsac2.persistence.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-tram-sequence", sequenceName = "RS2_PRCTRM_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCTRM",
        indexes = {
                @Index(name = "RS2_PRCTRM_PK_I", columnList = "PRTA_CODIGO")
        }
)
public class JProcedimientoTramite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-tram-sequence")
    @Column(name = "PRTA_CODIGO", nullable = false)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}