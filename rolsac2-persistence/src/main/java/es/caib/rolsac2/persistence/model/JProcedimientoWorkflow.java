package es.caib.rolsac2.persistence.model;

import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoWorkflowTraduccion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@SequenceGenerator(name = "procedimiento-wf-sequence", sequenceName = "RS2_PRCWF_SEQ", allocationSize = 1)
@Table(name = "RS2_PRCWF", indexes = {@Index(name = "RS2_PRCWF_PK_I", columnList = "PRWF_CODIGO")})
public class JProcedimientoWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedimiento-wf-sequence")
    @Column(name = "PRWF_CODIGO", nullable = false)
    private Long codigo;

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

    @Column(name = "PRWF_FECPUB")
    private Date fechaPublicacion;

    @Column(name = "PRWF_FECCAD")
    private Date fechaCaducidad;
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

    @Column(name = "PROC_LOPDACT")
    private Boolean activoLOPD = true;

    @Column(name = "PROC_LOPDRESP", nullable = false)
    private String lopdResponsable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_DPTIPLEGI", nullable = false)
    private JTipoLegitimacion datosPersonalesLegitimacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_TIPPRO", nullable = false)
    private JTipoProcedimiento tipoProcedimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_TIPVIA", nullable = false)
    private JTipoVia tipoVia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_LSTDOC")
    private JListaDocumentos listaDocumentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRWF_LSLOPD")
    private JListaDocumentos listaDocumentosLOPD;

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
    private JTipoSilencioAdministrativo silencioAdministrativo;

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
    @JoinColumn(name = "PRWF_SVTPRE")
    private JTipoTramitacion tramiteElectronicoPlantilla;

    /**
     * PARA SERVICIO: TRAMITE ELECTRONICO ASOCIADO
     **/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRWF_SVTREL")
    private JTipoTramitacion tramiteElectronico;

    /**
     * PARA SERVICIO:Tramitación presencial
     */
    @Column(name = "PRWF_SVPRES", nullable = false, precision = 1, scale = 0)
    private Boolean tramitPresencial;

    /**
     * PARA SERVICIO:Tramitación electrónica
     */
    @Column(name = "PRWF_SVELEC", nullable = false, precision = 1, scale = 0)
    private Boolean tramitElectronica;

    /**
     * PARA SERVICIO:Tramitacion telefonica
     */
    @Column(name = "PRWF_SVTEL", nullable = false, precision = 1, scale = 0)
    private Boolean tramitTelefonica;

    @Column(name = "PRWF_COMUN", nullable = false, precision = 1, scale = 0)
    private int comun = 0;

    @Column(name = "PRWF_HABAPO", nullable = false, precision = 1, scale = 0)
    private boolean habilitadoApoderado;

    @Column(name = "PRWF_HABFUN", nullable = false)
    private String habilitadoFuncionario;
    /**
     * Traducciones
     */
    @OneToMany(mappedBy = "procedimientoWorkflow", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JProcedimientoWorkflowTraduccion> traducciones;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RS2_PRCTEM", joinColumns = {@JoinColumn(name = "PRTM_CODPRWF")}, inverseJoinColumns = {@JoinColumn(name = "PRTM_CODTEMA")})
    private Set<JTema> temas;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long id) {
        this.codigo = id;
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

    public JListaDocumentos getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(JListaDocumentos prwfLstdoc) {
        this.listaDocumentos = prwfLstdoc;
    }

    public JListaDocumentos getListaDocumentosLOPD() {
        return listaDocumentosLOPD;
    }

    public void setListaDocumentosLOPD(JListaDocumentos listaDocumentosLOPD) {
        this.listaDocumentosLOPD = listaDocumentosLOPD;
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

    public JTipoSilencioAdministrativo getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public void setSilencioAdministrativo(JTipoSilencioAdministrativo prwfPrtipsiad) {
        this.silencioAdministrativo = prwfPrtipsiad;
    }

    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Boolean prwfSvtasa) {
        this.tieneTasa = prwfSvtasa;
    }

    public JTipoTramitacion getTramiteElectronicoPlantilla() {
        return tramiteElectronicoPlantilla;
    }

    public void setTramiteElectronicoPlantilla(JTipoTramitacion prwfSvtpre) {
        this.tramiteElectronicoPlantilla = prwfSvtpre;
    }

    public JTipoLegitimacion getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(JTipoLegitimacion datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public JTipoVia getFinVia() {
        return finVia;
    }

    public void setFinVia(JTipoVia finVia) {
        this.finVia = finVia;
    }


    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Boolean getActivoLOPD() {
        return activoLOPD;
    }

    public void setActivoLOPD(Boolean datosLOPDActivo) {
        this.activoLOPD = datosLOPDActivo;
    }

    public String getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public List<JProcedimientoWorkflowTraduccion> getTraducciones() {
        return traducciones;
    }

    public JTipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(JTipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public JTipoVia getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(JTipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    public Boolean isTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(Boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public Boolean isTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(Boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public Boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(Boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public void setTraducciones(List<JProcedimientoWorkflowTraduccion> traducciones) {
        if (this.traducciones == null || this.traducciones.isEmpty()) {
            this.traducciones = traducciones;
        } else {
            this.traducciones.addAll(traducciones);
        }
    }

    public Boolean getTramitPresencial() {
        return tramitPresencial;
    }

    public Boolean getTramitElectronica() {
        return tramitElectronica;
    }

    public Boolean getTramitTelefonica() {
        return tramitTelefonica;
    }

    public int getComun() {
        return comun;
    }

    public void setComun(int comun) {
        this.comun = comun;
    }

    public boolean isHabilitadoApoderado() {
        return habilitadoApoderado;
    }

    public void setHabilitadoApoderado(boolean habilitadoApoderado) {
        this.habilitadoApoderado = habilitadoApoderado;
    }

    public String getHabilitadoFuncionario() {
        return habilitadoFuncionario;
    }

    public void setHabilitadoFuncionario(String habilitadoFuncionario) {
        this.habilitadoFuncionario = habilitadoFuncionario;
    }

    public Set<JTema> getTemas() {
        return temas;
    }

    public void setTemas(Set<JTema> temas) {
        this.temas = temas;
    }

    public JTipoTramitacion getTramiteElectronico() {
        return tramiteElectronico;
    }

    public void setTramiteElectronico(JTipoTramitacion tramiteElectronico) {
        this.tramiteElectronico = tramiteElectronico;
    }

    @Override
    public String toString() {
        return "JProcedimientoWorkflow{" + "codigo=" + codigo + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JProcedimientoWorkflow that = (JProcedimientoWorkflow) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}