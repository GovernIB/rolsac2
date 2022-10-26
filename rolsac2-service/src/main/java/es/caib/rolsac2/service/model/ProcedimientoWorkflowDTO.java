package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un ProcedimientoWorkflow.
 */
@Schema(name = "ProcedimientoWorkflow")
public class ProcedimientoWorkflowDTO extends ModelApi {

    private Long codigo;

    private ProcedimientoDTO procedimiento;
    private Boolean workflow = false;
    private Integer estado;
    private boolean isInterna;
    private String usuario;
    private UnidadAdministrativaDTO uaResponsable;
    private UnidadAdministrativaDTO uaInstructor;
    private Boolean interno = false;
    private String responsableNombre;
    private String responsableEmail;
    private String responsableTelefono;
    private Boolean datosPersonalesActivo = false;
    private TipoLegitimacionDTO datosPersonalesLegitimacion;
    private ListaDocumentosDto listaDocumentos;
    private UnidadAdministrativaDTO uaCompetente;
    private TipoFormaInicioDTO formaInicio;
    private TipoSilencioAdministrativoDTO silencioAdministrativo;
    private TipoViaDTO finVia;
    private Boolean tieneTasa = false;
    private TipoTramitacionDTO tramiteElectronico;

    private Literal nombre;
    private Literal objeto;
    private Literal destinatarios;
    private Literal observaciones;
    private Literal datosPersonalesFinalidad;
    private Literal datosPersonalesDestinatario;
    private Long documentoLOPD;
    private Literal requisitos;
    private Literal terminoResolucion;


    public ProcedimientoWorkflowDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return the codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * @param idString the codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    public ProcedimientoWorkflowDTO(Long id) {
        this.codigo = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ProcedimientoDTO getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(ProcedimientoDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Boolean getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Boolean workflow) {
        this.workflow = workflow;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public UnidadAdministrativaDTO getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(UnidadAdministrativaDTO uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    public UnidadAdministrativaDTO getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(UnidadAdministrativaDTO uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public Boolean getInterno() {
        return interno;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    public Boolean getDatosPersonalesActivo() {
        return datosPersonalesActivo;
    }

    public void setDatosPersonalesActivo(Boolean datosPersonalesActivo) {
        this.datosPersonalesActivo = datosPersonalesActivo;
    }

    public TipoLegitimacionDTO getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(TipoLegitimacionDTO datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public ListaDocumentosDto getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(ListaDocumentosDto listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public UnidadAdministrativaDTO getUaCompetente() {
        return uaCompetente;
    }

    public void setUaCompetente(UnidadAdministrativaDTO uaCompetente) {
        this.uaCompetente = uaCompetente;
    }

    public TipoFormaInicioDTO getFormaInicio() {
        return formaInicio;
    }

    public void setFormaInicio(TipoFormaInicioDTO formaInicio) {
        this.formaInicio = formaInicio;
    }

    public TipoSilencioAdministrativoDTO getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public void setSilencioAdministrativo(TipoSilencioAdministrativoDTO silencioAdministrativo) {
        this.silencioAdministrativo = silencioAdministrativo;
    }

    public TipoViaDTO getFinVia() {
        return finVia;
    }

    public void setFinVia(TipoViaDTO finVia) {
        this.finVia = finVia;
    }

    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Boolean tieneTasa) {
        this.tieneTasa = tieneTasa;
    }

    public TipoTramitacionDTO getTramiteElectronico() {
        return tramiteElectronico;
    }

    public void setTramiteElectronico(TipoTramitacionDTO tramiteElectronico) {
        this.tramiteElectronico = tramiteElectronico;
    }

    public Literal getNombre() {
        return nombre;
    }

    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    public Literal getObjeto() {
        return objeto;
    }

    public void setObjeto(Literal objeto) {
        this.objeto = objeto;
    }

    public Literal getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(Literal destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Literal getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Literal observaciones) {
        this.observaciones = observaciones;
    }

    public Literal getDatosPersonalesFinalidad() {
        return datosPersonalesFinalidad;
    }

    public void setDatosPersonalesFinalidad(Literal datosPersonalesFinalidad) {
        this.datosPersonalesFinalidad = datosPersonalesFinalidad;
    }

    public Literal getDatosPersonalesDestinatario() {
        return datosPersonalesDestinatario;
    }

    public void setDatosPersonalesDestinatario(Literal datosPersonalesDestinatario) {
        this.datosPersonalesDestinatario = datosPersonalesDestinatario;
    }

    public Long getDocumentoLOPD() {
        return documentoLOPD;
    }

    public void setDocumentoLOPD(Long documentoLOPD) {
        this.documentoLOPD = documentoLOPD;
    }

    public Literal getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    public Literal getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(Literal terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    public boolean isInterna() {
        return isInterna;
    }

    public void setInterna(boolean interna) {
        isInterna = interna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProcedimientoWorkflowDTO that = (ProcedimientoWorkflowDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "ProcedimientoWorkflowDTO{" +
                "codigo=" + codigo +
                ", procedimiento=" + procedimiento +
                ", workflow=" + workflow +
                ", estado='" + estado + '\'' +
                ", usuario='" + usuario + '\'' +
                ", uaResponsable=" + uaResponsable +
                ", uaInstructor=" + uaInstructor +
                ", interno=" + interno +
                ", responsableNombre='" + responsableNombre + '\'' +
                ", responsableEmail='" + responsableEmail + '\'' +
                ", responsableTelefono='" + responsableTelefono + '\'' +
                ", datosPersonalesActivo=" + datosPersonalesActivo +
                ", datosPersonalesLegitimacion=" + datosPersonalesLegitimacion +
                ", listaDocumentos=" + listaDocumentos +
                ", uaCompetente=" + uaCompetente +
                ", formaInicio=" + formaInicio +
                ", silencioAdministrativo=" + silencioAdministrativo +
                ", finVia=" + finVia +
                ", tieneTasa=" + tieneTasa +
                ", tramiteElectronico=" + tramiteElectronico +
                ", nombre=" + nombre +
                ", objeto=" + objeto +
                ", destinatarios=" + destinatarios +
                ", observaciones=" + observaciones +
                ", datosPersonalesFinalidad=" + datosPersonalesFinalidad +
                ", datosPersonalesDestinatario=" + datosPersonalesDestinatario +
                ", documentoLOPD=" + documentoLOPD +
                ", requisitos=" + requisitos +
                ", terminoResolucion=" + terminoResolucion +
                '}';
    }
}
