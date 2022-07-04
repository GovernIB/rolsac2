package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JTipoSilencioAdministrativoTraduccion;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "procedimiento-wf-sequence", sequenceName = "RS2_PRCWF_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCWF",
        indexes = {
                @Index(name = "RS2_PRCWF_PK_I", columnList = "PRWF_CODIGO")
        }
)
public class JProcedimientoWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-wf-sequence")
    @Column(name = "PRWF_CODIGO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_CODPROC")
    private JProcedimiento procedimiento;

    /**
     * WORFLOW (0: PUBLICADO / 1: EN MODIFICACION)
     */
    @Column(name = "PRWF_WF", nullable = false)
    private Boolean workflow = false;
    public final static boolean WORKFLOW_PUBLICADO = false;
    public final static boolean WORKFLOW_EN_MODIFICACION = true;

    @Column(name = "PRWF_WFESTADO", nullable = false, length = 1)
    private String estado;

    @Column(name = "PRWF_WFUSUA", length = 100)
    private String usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_CODUAR", nullable = false)
    private JUnidadAdministrativa uaResponsable;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_CODUAI", nullable = false)
    private JUnidadAdministrativa uaInstructor;

    /**
     * INDICA SI ES INTERNO (1) O EXTERNO (0). (DATO SIA)
     **/
    @Column(name = "PRWF_INTERNO", nullable = false)
    private Boolean interno = false;

    @Column(name = "PRWF_RSNOM", nullable = false)
    private String responsableNombre;

    @Column(name = "PRWF_RSEMA", length = 100)
    private String responsableEmail;

    @Column(name = "PRWF_RSTFNO", length = 9)
    private String responsableTelefono;

    @Column(name = "PROC_DPACTV", nullable = false)
    private Boolean datosPersonalesActivo = false;

    @Column(name = "PRWF_DPTIPLEGI", nullable = false)
    private Boolean datosPersonalesLegitimacion = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_LSTDOC")
    private JListaDocumentos listaDocumentos;

    /**
     * PARA PROC: CODIGO UA COMPETENTE
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_PRCODUAC")
    private JUnidadAdministrativa uaCompetente;

    /**
     * PARA PROC: FORMA INICIO
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_PRTIPINIC")
    private JTipoFormaInicio formaInicio;

    /**
     * PARA PROC: SILENCIO ADMINISTRATIVO
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_PRTIPSIAD")
    private JTipoSilencioAdministrativoTraduccion silencioAdministrativo;

    /**
     * PARA PROC: FIN VIA ADMINISTRATIVA
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_PRTIPFVIA")
    private JTipoVia finVia;


    /**
     * PARA SERVICIO: SI TIENE TASA
     **/
    @Column(name = "PRWF_SVTASA", nullable = false)
    private Boolean tieneTasa = false;

    /**
     * PARA SERVICIO: TRAMITE ELECTRONICO ASOCIADO
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_SVTPRE", nullable = false)
    private JTipoTramitacion tramiteElectronico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JProcedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(JProcedimiento prwfCodproc) {
        this.procedimiento = prwfCodproc;
    }

    public Boolean getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Boolean prwfWf) {
        this.workflow = prwfWf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String prwfWfestado) {
        this.estado = prwfWfestado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String prwfWfusua) {
        this.usuario = prwfWfusua;
    }

    public JUnidadAdministrativa getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(JUnidadAdministrativa prwfCoduar) {
        this.uaResponsable = prwfCoduar;
    }

    public JUnidadAdministrativa getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(JUnidadAdministrativa prwfCoduai) {
        this.uaInstructor = prwfCoduai;
    }

    public Boolean getInterno() {
        return interno;
    }

    public void setInterno(Boolean prwfInterno) {
        this.interno = prwfInterno;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String prwfRsnom) {
        this.responsableNombre = prwfRsnom;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String prwfRsema) {
        this.responsableEmail = prwfRsema;
    }

    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public void setResponsableTelefono(String prwfRstfno) {
        this.responsableTelefono = prwfRstfno;
    }

    public Boolean getDatosPersonalesActivo() {
        return datosPersonalesActivo;
    }

    public void setDatosPersonalesActivo(Boolean procDpactv) {
        this.datosPersonalesActivo = procDpactv;
    }

    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(JListaDocumentos prwfLstdoc) {
        this.listaDocumentos = prwfLstdoc;
    }

    public JUnidadAdministrativa getUaCompetente() {
        return uaCompetente;
    }

    public void setUaCompetente(JUnidadAdministrativa prwfPrcoduac) {
        this.uaCompetente = prwfPrcoduac;
    }

    public JTipoFormaInicio getFormaInicio() {
        return formaInicio;
    }

    public void setFormaInicio(JTipoFormaInicio prwfPrtipinic) {
        this.formaInicio = prwfPrtipinic;
    }

    public JTipoSilencioAdministrativoTraduccion getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public void setSilencioAdministrativo(JTipoSilencioAdministrativoTraduccion prwfPrtipsiad) {
        this.silencioAdministrativo = prwfPrtipsiad;
    }

    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Boolean prwfSvtasa) {
        this.tieneTasa = prwfSvtasa;
    }

    public JTipoTramitacion getTramiteElectronico() {
        return tramiteElectronico;
    }

    public void setTramiteElectronico(JTipoTramitacion prwfSvtpre) {
        this.tramiteElectronico = prwfSvtpre;
    }

    public Boolean getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(Boolean datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public JTipoVia getFinVia() {
        return finVia;
    }

    public void setFinVia(JTipoVia finVia) {
        this.finVia = finVia;
    }
}