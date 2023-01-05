package es.caib.rolsac2.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

/**
 * Dades d'un ProcedimientoWorkflow.
 */
@Schema(name = "ProcedimientoWorkflow")
public class ProcedimientoWorkflowDTO extends ModelApi {

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Procedimiento
     */
    private ProcedimientoDTO procedimiento;

    /**
     * Workflow
     */
    private Boolean workflow = false;

    /**
     * Estadp
     */
    private String estado;

    /**
     * Es interna
     */
    private boolean isInterna;

    /**
     * Usuario
     */
    private String usuario;

    /**
     * Unidad administrativa responsable
     */
    private UnidadAdministrativaDTO uaResponsable;

    /**
     * Unidad administrativa instructora
     */
    private UnidadAdministrativaDTO uaInstructor;

    /**
     * Interno
     */
    private Boolean interno = false;

    /**
     * Nombre del responsable
     */
    private String responsableNombre;

    /**
     * Email del responsable
     */
    private String responsableEmail;

    /**
     * Telefono del responsable
     */
    private String responsableTelefono;

    /**
     * Datos del personal activo
     */
    private Boolean datosPersonalesActivo = false;

    /**
     * Datos personales de la legitimacion
     */
    private TipoLegitimacionDTO datosPersonalesLegitimacion;

    /**
     * Lista de documentos
     */
    private ListaDocumentosDto listaDocumentos;

    /**
     * Unidad administrativa competente
     */
    private UnidadAdministrativaDTO uaCompetente;

    /**
     * Forma de inicio
     */
    private TipoFormaInicioDTO formaInicio;

    /**
     * Silencio administrativo
     */
    private TipoSilencioAdministrativoDTO silencioAdministrativo;

    /**
     * Fin via
     */
    private TipoViaDTO finVia;

    /**
     * Tiene tasa
     */
    private Boolean tieneTasa = false;

    /**
     * Tramite electronico
     */
    private TipoTramitacionDTO tramiteElectronico;

    /**
     * Nombre
     */
    private Literal nombre;

    /**
     * Objeto
     */
    private Literal objeto;

    /**
     * Destinatarios
     */
    private Literal destinatarios;

    /**
     * Observaciones
     */
    private Literal observaciones;

    /**
     * Finalidad de los datos personales
     */
    private Literal datosPersonalesFinalidad;

    /**
     * Datos personales del destinatario
     */
    private Literal datosPersonalesDestinatario;

    /**
     * Documentos de la LOPD
     */
    private Long documentoLOPD;

    /**
     * Requisitos
     */
    private Literal requisitos;

    /**
     * Termino de la resolucion
     */
    private Literal terminoResolucion;


    /**
     * Instancia un nuevo Procedimiento workflow dto.
     */
    public ProcedimientoWorkflowDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return  codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString  codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Instantiates a new Procedimiento workflow dto.
     *
     * @param id  id
     */
    public ProcedimientoWorkflowDTO(Long id) {
        this.codigo = id;
    }

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
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return  procedimiento
     */
    public ProcedimientoDTO getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param procedimiento  procedimiento
     */
    public void setProcedimiento(ProcedimientoDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Obtiene workflow.
     *
     * @return  workflow
     */
    public Boolean getWorkflow() {
        return workflow;
    }

    /**
     * Establece workflow.
     *
     * @param workflow  workflow
     */
    public void setWorkflow(Boolean workflow) {
        this.workflow = workflow;
    }

    /**
     * Obtiene estado.
     *
     * @return  estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece estado.
     *
     * @param estado  estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene usuario.
     *
     * @return  usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece usuario.
     *
     * @param usuario  usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene ua responsable.
     *
     * @return  ua responsable
     */
    public UnidadAdministrativaDTO getUaResponsable() {
        return uaResponsable;
    }

    /**
     * Establece ua responsable.
     *
     * @param uaResponsable  ua responsable
     */
    public void setUaResponsable(UnidadAdministrativaDTO uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    /**
     * Obtiene ua instructor.
     *
     * @return  ua instructor
     */
    public UnidadAdministrativaDTO getUaInstructor() {
        return uaInstructor;
    }

    /**
     * Establece ua instructor.
     *
     * @param uaInstructor  ua instructor
     */
    public void setUaInstructor(UnidadAdministrativaDTO uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    /**
     * Obtiene interno.
     *
     * @return  interno
     */
    public Boolean getInterno() {
        return interno;
    }

    /**
     * Establece interno.
     *
     * @param interno  interno
     */
    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    /**
     * Obtiene responsable nombre.
     *
     * @return  responsable nombre
     */
    public String getResponsableNombre() {
        return responsableNombre;
    }

    /**
     * Establece responsable nombre.
     *
     * @param responsableNombre  responsable nombre
     */
    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    /**
     * Obtiene responsable email.
     *
     * @return  responsable email
     */
    public String getResponsableEmail() {
        return responsableEmail;
    }

    /**
     * Establece responsable email.
     *
     * @param responsableEmail  responsable email
     */
    public void setResponsableEmail(String responsableEmail) {
        this.responsableEmail = responsableEmail;
    }

    /**
     * Obtiene responsable telefono.
     *
     * @return  responsable telefono
     */
    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    /**
     * Establece responsable telefono.
     *
     * @param responsableTelefono  responsable telefono
     */
    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    /**
     * Obtiene datos personales activo.
     *
     * @return  datos personales activo
     */
    public Boolean getDatosPersonalesActivo() {
        return datosPersonalesActivo;
    }

    /**
     * Establece datos personales activo.
     *
     * @param datosPersonalesActivo  datos personales activo
     */
    public void setDatosPersonalesActivo(Boolean datosPersonalesActivo) {
        this.datosPersonalesActivo = datosPersonalesActivo;
    }

    /**
     * Obtiene datos personales legitimacion.
     *
     * @return  datos personales legitimacion
     */
    public TipoLegitimacionDTO getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    /**
     * Establece datos personales legitimacion.
     *
     * @param datosPersonalesLegitimacion  datos personales legitimacion
     */
    public void setDatosPersonalesLegitimacion(TipoLegitimacionDTO datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    /**
     * Obtiene lista documentos.
     *
     * @return  lista documentos
     */
    public ListaDocumentosDto getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * Establece lista documentos.
     *
     * @param listaDocumentos  lista documentos
     */
    public void setListaDocumentos(ListaDocumentosDto listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * Obtiene ua competente.
     *
     * @return  ua competente
     */
    public UnidadAdministrativaDTO getUaCompetente() {
        return uaCompetente;
    }

    /**
     * Establece ua competente.
     *
     * @param uaCompetente  ua competente
     */
    public void setUaCompetente(UnidadAdministrativaDTO uaCompetente) {
        this.uaCompetente = uaCompetente;
    }

    /**
     * Obtiene forma inicio.
     *
     * @return  forma inicio
     */
    public TipoFormaInicioDTO getFormaInicio() {
        return formaInicio;
    }

    /**
     * Establece forma inicio.
     *
     * @param formaInicio  forma inicio
     */
    public void setFormaInicio(TipoFormaInicioDTO formaInicio) {
        this.formaInicio = formaInicio;
    }

    /**
     * Obtiene silencio administrativo.
     *
     * @return  silencio administrativo
     */
    public TipoSilencioAdministrativoDTO getSilencioAdministrativo() {
        return silencioAdministrativo;
    }

    /**
     * Establece silencio administrativo.
     *
     * @param silencioAdministrativo  silencio administrativo
     */
    public void setSilencioAdministrativo(TipoSilencioAdministrativoDTO silencioAdministrativo) {
        this.silencioAdministrativo = silencioAdministrativo;
    }

    /**
     * Obtiene fin via.
     *
     * @return  fin via
     */
    public TipoViaDTO getFinVia() {
        return finVia;
    }

    /**
     * Establece fin via.
     *
     * @param finVia  fin via
     */
    public void setFinVia(TipoViaDTO finVia) {
        this.finVia = finVia;
    }

    /**
     * Obtiene tiene tasa.
     *
     * @return  tiene tasa
     */
    public Boolean getTieneTasa() {
        return tieneTasa;
    }

    /**
     * Establece tiene tasa.
     *
     * @param tieneTasa  tiene tasa
     */
    public void setTieneTasa(Boolean tieneTasa) {
        this.tieneTasa = tieneTasa;
    }

    /**
     * Obtiene tramite electronico.
     *
     * @return  tramite electronico
     */
    public TipoTramitacionDTO getTramiteElectronico() {
        return tramiteElectronico;
    }

    /**
     * Establece tramite electronico.
     *
     * @param tramiteElectronico  tramite electronico
     */
    public void setTramiteElectronico(TipoTramitacionDTO tramiteElectronico) {
        this.tramiteElectronico = tramiteElectronico;
    }

    /**
     * Obtiene nombre.
     *
     * @return  nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre  nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene objeto.
     *
     * @return  objeto
     */
    public Literal getObjeto() {
        return objeto;
    }

    /**
     * Establece objeto.
     *
     * @param objeto  objeto
     */
    public void setObjeto(Literal objeto) {
        this.objeto = objeto;
    }

    /**
     * Obtiene destinatarios.
     *
     * @return  destinatarios
     */
    public Literal getDestinatarios() {
        return destinatarios;
    }

    /**
     * Establece destinatarios.
     *
     * @param destinatarios  destinatarios
     */
    public void setDestinatarios(Literal destinatarios) {
        this.destinatarios = destinatarios;
    }

    /**
     * Obtiene observaciones.
     *
     * @return  observaciones
     */
    public Literal getObservaciones() {
        return observaciones;
    }

    /**
     * Establece observaciones.
     *
     * @param observaciones  observaciones
     */
    public void setObservaciones(Literal observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene datos personales finalidad.
     *
     * @return  datos personales finalidad
     */
    public Literal getDatosPersonalesFinalidad() {
        return datosPersonalesFinalidad;
    }

    /**
     * Establece datos personales finalidad.
     *
     * @param datosPersonalesFinalidad  datos personales finalidad
     */
    public void setDatosPersonalesFinalidad(Literal datosPersonalesFinalidad) {
        this.datosPersonalesFinalidad = datosPersonalesFinalidad;
    }

    /**
     * Obtiene datos personales destinatario.
     *
     * @return  datos personales destinatario
     */
    public Literal getDatosPersonalesDestinatario() {
        return datosPersonalesDestinatario;
    }

    /**
     * Establece datos personales destinatario.
     *
     * @param datosPersonalesDestinatario  datos personales destinatario
     */
    public void setDatosPersonalesDestinatario(Literal datosPersonalesDestinatario) {
        this.datosPersonalesDestinatario = datosPersonalesDestinatario;
    }

    /**
     * Obtiene documento lopd.
     *
     * @return  documento lopd
     */
    public Long getDocumentoLOPD() {
        return documentoLOPD;
    }

    /**
     * Establece documento lopd.
     *
     * @param documentoLOPD  documento lopd
     */
    public void setDocumentoLOPD(Long documentoLOPD) {
        this.documentoLOPD = documentoLOPD;
    }

    /**
     * Obtiene requisitos.
     *
     * @return  requisitos
     */
    public Literal getRequisitos() {
        return requisitos;
    }

    /**
     * Establece requisitos.
     *
     * @param requisitos  requisitos
     */
    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Obtiene termino resolucion.
     *
     * @return  termino resolucion
     */
    public Literal getTerminoResolucion() {
        return terminoResolucion;
    }

    /**
     * Establece termino resolucion.
     *
     * @param terminoResolucion  termino resolucion
     */
    public void setTerminoResolucion(Literal terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    /**
     * Is interna boolean.
     *
     * @return  boolean
     */
    public boolean isInterna() {
        return isInterna;
    }

    /**
     * Establece interna.
     *
     * @param interna  interna
     */
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
