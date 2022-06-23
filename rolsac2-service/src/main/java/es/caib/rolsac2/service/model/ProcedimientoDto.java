package es.caib.rolsac2.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ProcedimientoDto implements Serializable {
    private Long id;
    private Long idWF;

    /**
     * PROCEDIMIENTO O SERVICIO
     * TypeProcedimiento : Procedimiento(P) o Servicio(S)
     **/
    private String tipo;

    private Integer codigoSIA;

    private Boolean estadoSIA;

    private Date siaFecha;

    private String codigoDir3SIA;
    private Boolean workflow;
    private String estado;
    private String usuario;
    private UnidadAdministrativaDTO uaResponsable;
    private UnidadAdministrativaDTO uaInstructor;
    private Boolean interno;
    private String responsableNombre;
    private String responsableEmail;
    private String responsableTelefono;
    private Boolean datosPersonalesActivo;
    private Boolean datosPersonalesLegitimacion;
    private ListaDocumentosDto listaDocumentos;
    private UnidadAdministrativaDTO uaCompetente;
    private TipoFormaInicioDTO formaInicio;
    private TipoSilencioAdministrativoDTO silencioAdministrativo;
    private TipoViaDTO finVia;
    private Boolean tieneTasa;
    private TipoTramitacionDTO tramiteElectronico;

    public ProcedimientoDto(Long id, Boolean workflow, String estado, String usuario, UnidadAdministrativaDTO uaResponsable, UnidadAdministrativaDTO uaInstructor, Boolean interno, String responsableNombre, String responsableEmail, String responsableTelefono, Boolean datosPersonalesActivo, Boolean datosPersonalesLegitimacion, ListaDocumentosDto listaDocumentos, UnidadAdministrativaDTO uaCompetente, TipoFormaInicioDTO formaInicio, TipoSilencioAdministrativoDTO silencioAdministrativo, TipoViaDTO finVia, Boolean tieneTasa, TipoTramitacionDTO tramiteElectronico) {
        this.id = id;
        this.workflow = workflow;
        this.estado = estado;
        this.usuario = usuario;
        this.uaResponsable = uaResponsable;
        this.uaInstructor = uaInstructor;
        this.interno = interno;
        this.responsableNombre = responsableNombre;
        this.responsableEmail = responsableEmail;
        this.responsableTelefono = responsableTelefono;
        this.datosPersonalesActivo = datosPersonalesActivo;
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
        this.listaDocumentos = listaDocumentos;
        this.uaCompetente = uaCompetente;
        this.formaInicio = formaInicio;
        this.silencioAdministrativo = silencioAdministrativo;
        this.finVia = finVia;
        this.tieneTasa = tieneTasa;
        this.tramiteElectronico = tramiteElectronico;
    }

    public Long getId() {
        return id;
    }

    public Boolean getWorkflow() {
        return workflow;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public UnidadAdministrativaDTO getUaResponsable() {
        return uaResponsable;
    }

    public UnidadAdministrativaDTO getUaInstructor() {
        return uaInstructor;
    }

    public Boolean getInterno() {
        return interno;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public Boolean getDatosPersonalesActivo() {
        return datosPersonalesActivo;
    }

    public Boolean getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public ListaDocumentosDto getListaDocumentos() {
        return listaDocumentos;
    }

    public UnidadAdministrativaDTO getUaCompetente() {
        return uaCompetente;
    }

    public TipoFormaInicioDTO getFormaInicio() {
        return formaInicio;
    }

    public TipoSilencioAdministrativoDTO getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    public TipoViaDTO getFinVia() {
        return finVia;
    }

    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    public TipoTramitacionDTO getTramiteElectronico() {
        return tramiteElectronico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public Boolean getEstadoSIA() {
        return estadoSIA;
    }

    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public Date getSiaFecha() {
        return siaFecha;
    }

    public void setSiaFecha(Date siaFecha) {
        this.siaFecha = siaFecha;
    }

    public String getCodigoDir3SIA() {
        return codigoDir3SIA;
    }

    public void setCodigoDir3SIA(String codigoDir3SIA) {
        this.codigoDir3SIA = codigoDir3SIA;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdWF() {
        return idWF;
    }

    public void setIdWF(Long idWF) {
        this.idWF = idWF;
    }

    public void setWorkflow(Boolean workflow) {
        this.workflow = workflow;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setUaResponsable(UnidadAdministrativaDTO uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    public void setUaInstructor(UnidadAdministrativaDTO uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    public void setDatosPersonalesActivo(Boolean datosPersonalesActivo) {
        this.datosPersonalesActivo = datosPersonalesActivo;
    }

    public void setDatosPersonalesLegitimacion(Boolean datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public void setListaDocumentos(ListaDocumentosDto listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public void setUaCompetente(UnidadAdministrativaDTO uaCompetente) {
        this.uaCompetente = uaCompetente;
    }

    public void setFormaInicio(TipoFormaInicioDTO formaInicio) {
        this.formaInicio = formaInicio;
    }

    public void setSilencioAdministrativo(TipoSilencioAdministrativoDTO silencioAdministrativo) {
        this.silencioAdministrativo = silencioAdministrativo;
    }

    public void setFinVia(TipoViaDTO finVia) {
        this.finVia = finVia;
    }

    public void setTieneTasa(Boolean tieneTasa) {
        this.tieneTasa = tieneTasa;
    }

    public void setTramiteElectronico(TipoTramitacionDTO tramiteElectronico) {
        this.tramiteElectronico = tramiteElectronico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoDto entity = (ProcedimientoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.workflow, entity.workflow) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.usuario, entity.usuario) &&
                Objects.equals(this.uaResponsable, entity.uaResponsable) &&
                Objects.equals(this.uaInstructor, entity.uaInstructor) &&
                Objects.equals(this.interno, entity.interno) &&
                Objects.equals(this.responsableNombre, entity.responsableNombre) &&
                Objects.equals(this.responsableEmail, entity.responsableEmail) &&
                Objects.equals(this.responsableTelefono, entity.responsableTelefono) &&
                Objects.equals(this.datosPersonalesActivo, entity.datosPersonalesActivo) &&
                Objects.equals(this.datosPersonalesLegitimacion, entity.datosPersonalesLegitimacion) &&
                Objects.equals(this.listaDocumentos, entity.listaDocumentos) &&
                Objects.equals(this.uaCompetente, entity.uaCompetente) &&
                Objects.equals(this.formaInicio, entity.formaInicio) &&
                Objects.equals(this.silencioAdministrativo, entity.silencioAdministrativo) &&
                Objects.equals(this.finVia, entity.finVia) &&
                Objects.equals(this.tieneTasa, entity.tieneTasa) &&
                Objects.equals(this.tramiteElectronico, entity.tramiteElectronico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workflow, estado, usuario, uaResponsable, uaInstructor, interno, responsableNombre, responsableEmail, responsableTelefono, datosPersonalesActivo, datosPersonalesLegitimacion, listaDocumentos, uaCompetente, formaInicio, silencioAdministrativo, finVia, tieneTasa, tramiteElectronico);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "workflow = " + workflow + ", " +
                "estado = " + estado + ", " +
                "usuario = " + usuario + ", " +
                "uaResponsable = " + uaResponsable + ", " +
                "uaInstructor = " + uaInstructor + ", " +
                "interno = " + interno + ", " +
                "responsableNombre = " + responsableNombre + ", " +
                "responsableEmail = " + responsableEmail + ", " +
                "responsableTelefono = " + responsableTelefono + ", " +
                "datosPersonalesActivo = " + datosPersonalesActivo + ", " +
                "datosPersonalesLegitimacion = " + datosPersonalesLegitimacion + ", " +
                "listaDocumentos = " + listaDocumentos + ", " +
                "uaCompetente = " + uaCompetente + ", " +
                "formaInicio = " + formaInicio + ", " +
                "silencioAdministrativo = " + silencioAdministrativo + ", " +
                "finVia = " + finVia + ", " +
                "tieneTasa = " + tieneTasa + ", " +
                "tramiteElectronico = " + tramiteElectronico + ")";
    }
}
