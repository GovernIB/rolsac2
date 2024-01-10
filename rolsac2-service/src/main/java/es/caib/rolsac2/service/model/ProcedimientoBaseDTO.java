package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Dades d'un Procedimiento base.
 *
 * @author Indra
 */
@Schema(name = "ProcedimientoBase")
public class ProcedimientoBaseDTO extends ModelApi {
    /**
     * Codigo
     */
    private Long codigo;
    /*
      Codigo WF
     */
    private Long codigoWF;

    private String tipo;
    private TypeProcedimientoWorkflow workflow;
    private TypeProcedimientoEstado estado;
    private boolean interno;
    private boolean publicado;

    /**
     * Datos sia
     **/
    private Integer codigoSIA;
    private String estadoSIA;
    private Date fechaSIA;
    private String errorSIA;
    private Date fechaCaducidad;
    private Date fechaPublicacion;
    private Date fechaActualizacion;
    private TipoLegitimacionDTO datosPersonalesLegitimacion;
    private UnidadAdministrativaDTO uaResponsable;
    private UnidadAdministrativaDTO uaInstructor;
    private UnidadAdministrativaDTO uaCompetente;
    private TipoFormaInicioDTO iniciacion;
    private String responsable;
    private TipoSilencioAdministrativoDTO silencio;
    private TipoProcedimientoDTO tipoProcedimiento;
    private int comun;
    private TipoViaDTO tipoVia;
    private Boolean habilitadoApoderado;

    private String habilitadoFuncionario;

    private boolean tieneTasa = false;
    private boolean tramitElectronica = false;
    private boolean tramitPresencial = false;
    private boolean tramitTelefonica = false;
    private String responsableEmail;
    private String responsableTelefono;

    // Nombre
    private Literal nombreProcedimientoWorkFlow;

    /**
     * Nombre
     */
    private String nombre;

    // LOPD
    String lopdResponsable;
    Literal lopdInfoAdicional = new Literal();
    Literal lopdFinalidad = new Literal();
    Literal lopdDerechos = new Literal();
    Literal lopdCabecera = new Literal();

    /**
     * LITERALES
     **/
    private Literal requisitos;
    private Literal objeto;
    private Literal destinatarios;
    private Literal terminoResolucion;
    private Literal observaciones;
    private Literal keywords;

    /*** Lista de objetos **/
    private List<TipoPublicoObjetivoEntidadGridDTO> publicosObjetivo;
    private List<ProcedimientoDocumentoDTO> documentos;
    private List<ProcedimientoDocumentoDTO> documentosLOPD;
    private List<NormativaGridDTO> normativas;
    private List<TemaGridDTO> temas;

    /**
     * Auditoria y mensajes
     **/
    private String mensajes;
    private String usuarioAuditoria;

    /**
     * Pendientes de indexar o mensajes
     **/
    private boolean pendienteIndexar = false;
    private boolean pendienteMensajesGestor = false;
    private boolean pendienteMensajesSupervisor = false;

    /**
     * ESTA VARIABLE NO SE UTILIZA, SOLO PARA LA EVOLUCION
     **/
    //Opciones de Unidad Administrativa destino en la evolucion de division
    private Long opcionUAdestino;

    /**
     * Create instance
     *
     * @param idiomas
     * @return
     */
    public static ProcedimientoBaseDTO createInstance(List<String> idiomas) {
        ProcedimientoBaseDTO proc = new ProcedimientoBaseDTO();
        proc.setWorkflow(TypeProcedimientoWorkflow.MODIFICACION);
        proc.setEstado(TypeProcedimientoEstado.MODIFICACION);
        proc.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        proc.setLopdDerechos(Literal.createInstance(idiomas));
        proc.setLopdFinalidad(Literal.createInstance(idiomas));
        proc.setLopdInfoAdicional(Literal.createInstance(idiomas));
        proc.setRequisitos(Literal.createInstance(idiomas));
        proc.setObjeto(Literal.createInstance(idiomas));
        proc.setDestinatarios(Literal.createInstance(idiomas));
        proc.setTerminoResolucion(Literal.createInstance(idiomas));
        proc.setObservaciones(Literal.createInstance(idiomas));
        proc.setKeywords(Literal.createInstance(idiomas));
        proc.setPendienteIndexar(false);
        proc.setPendienteMensajesGestor(false);
        proc.setPendienteMensajesSupervisor(false);
        return proc;
    }

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
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene codigo sia.
     *
     * @return codigo sia
     */
    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * Obtiene estado sia.
     *
     * @return estado sia
     */
    public void setEstadoSIA(String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * Obtiene nombre procedimiento wf.
     *
     * @return nombre procedimiento wf
     */
    public Literal getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene requisitos.
     *
     * @return requisitos
     */
    public Literal getRequisitos() {
        return requisitos;
    }

    /**
     * Establece requisitos.
     *
     * @param requisitos requisitos
     */
    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Obtiene tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombreProcedimientoWorkFlow(Literal nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public TipoLegitimacionDTO getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(TipoLegitimacionDTO datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public Literal getLopdInfoAdicional() {
        return lopdInfoAdicional;
    }

    public void setLopdInfoAdicional(Literal lopdInfoAdicional) {
        this.lopdInfoAdicional = lopdInfoAdicional;
    }

    public Literal getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(Literal lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    public String getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public Literal getLopdDerechos() {
        return lopdDerechos;
    }

    public void setLopdDerechos(Literal lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public Literal getLopdCabecera() {
        return lopdCabecera;
    }

    public void setLopdCabecera(Literal lopdCabecera) {
        this.lopdCabecera = lopdCabecera;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public List<ProcedimientoDocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<ProcedimientoDocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    public List<NormativaGridDTO> getNormativas() {
        return normativas;
    }

    public void setNormativas(List<NormativaGridDTO> normativas) {
        this.normativas = normativas;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    public TipoFormaInicioDTO getIniciacion() {
        return iniciacion;
    }

    public void setIniciacion(TipoFormaInicioDTO iniciacion) {
        this.iniciacion = iniciacion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


    public List<TipoPublicoObjetivoEntidadGridDTO> getPublicosObjetivo() {
        return publicosObjetivo;
    }

    public void setPublicosObjetivo(List<TipoPublicoObjetivoEntidadGridDTO> publicosObjetivo) {
        this.publicosObjetivo = publicosObjetivo;
    }

    public Date getFechaSIA() {
        return fechaSIA;
    }

    public void setFechaSIA(Date fechaSIA) {
        this.fechaSIA = fechaSIA;
    }

    public String getErrorSIA() {
        return errorSIA;
    }

    public void setErrorSIA(String errorSIA) {
        this.errorSIA = errorSIA;
    }

    public TipoSilencioAdministrativoDTO getSilencio() {
        return silencio;
    }

    public void setSilencio(TipoSilencioAdministrativoDTO silencio) {
        this.silencio = silencio;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public String getEstadoSIA() {
        return estadoSIA;
    }

    public List<TemaGridDTO> getTemas() {
        return temas;
    }

    public void setTemas(List<TemaGridDTO> temas) {
        this.temas = temas;
    }

    /*
            public TypeProcedimientoEstado getEstado() {
                return estado;
            }

            public void setEstado(TypeProcedimientoEstado estado) {
                this.estado = estado;
            }
        */
    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public Long getCodigoWF() {
        return codigoWF;
    }

    public void setCodigoWF(Long codigoWF) {
        this.codigoWF = codigoWF;
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
 
    public TipoProcedimientoDTO getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimientoDTO tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public TypeProcedimientoWorkflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(TypeProcedimientoWorkflow workflow) {
        this.workflow = workflow;
    }

    public TypeProcedimientoEstado getEstado() {
        return estado;
    }

    public void setEstado(TypeProcedimientoEstado estado) {
        this.estado = estado;
    }

    public List<ProcedimientoDocumentoDTO> getDocumentosLOPD() {
        return documentosLOPD;
    }

    public void setDocumentosLOPD(List<ProcedimientoDocumentoDTO> documentosLOPD) {
        this.documentosLOPD = documentosLOPD;
    }

    public String getMensajes() {
        return mensajes;
    }

    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public TipoViaDTO getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(TipoViaDTO tipoVia) {
        this.tipoVia = tipoVia;
    }

    public int getComun() {
        return comun;
    }

    public void setComun(int comun) {
        this.comun = comun;
    }

    /**
     * Esta hecho asi para evitar problemas con jsf
     *
     * @return
     */
    public boolean esComun() {
        return this.comun == 1;
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

    public Literal getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(Literal terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    public Literal getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Literal observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean isHabilitadoApoderado() {
        return habilitadoApoderado;
    }

    public void setHabilitadoApoderado(Boolean habilitadoApoderado) {
        this.habilitadoApoderado = habilitadoApoderado;
    }

    public Boolean getHabilitadoApoderado() {
        return habilitadoApoderado;
    }

    public String getHabilitadoFuncionario() {
        return habilitadoFuncionario;
    }

    public void setHabilitadoFuncionario(String habilitadoFuncionario) {
        this.habilitadoFuncionario = habilitadoFuncionario;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public boolean isTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(boolean tieneTasa) {
        this.tieneTasa = tieneTasa;
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

    public boolean isPendienteIndexar() {
        return pendienteIndexar;
    }

    public void setPendienteIndexar(boolean pendienteIndexar) {
        this.pendienteIndexar = pendienteIndexar;
    }

    public boolean isPendienteMensajesGestor() {
        return pendienteMensajesGestor;
    }

    public void setPendienteMensajesGestor(boolean pendienteMensajesGestor) {
        this.pendienteMensajesGestor = pendienteMensajesGestor;
    }

    public boolean isPendienteMensajesSupervisor() {
        return pendienteMensajesSupervisor;
    }

    public void setPendienteMensajesSupervisor(boolean pendienteMensajesSupervisor) {
        this.pendienteMensajesSupervisor = pendienteMensajesSupervisor;
    }

    public UnidadAdministrativaDTO getUaCompetente() {
        return uaCompetente;
    }

    public void setUaCompetente(UnidadAdministrativaDTO uaCompetente) {
        this.uaCompetente = uaCompetente;
    }

    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public Literal getKeywords() {
        return keywords;
    }

    public void setKeywords(Literal keywords) {
        this.keywords = keywords;
    }

    public Long getOpcionUAdestino() {
        return opcionUAdestino;
    }

    public void setOpcionUAdestino(Long opcionUAdestino) {
        this.opcionUAdestino = opcionUAdestino;
    }

    @Override
    public String toString() {
        return "ProcedimientoBaseDTO{" + "codigo=" + codigo + ", codigoWF='" + codigoWF + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoBaseDTO that = (ProcedimientoBaseDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public void agregarDocumento(ProcedimientoDocumentoDTO doc) {
        if (documentos == null) {
            this.setDocumentos(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getDocumentos().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getDocumentos().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getDocumentos().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getDocumentos().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getDocumentos().add(doc);
        }
    }

    public void agregarDocumentoLOPD(ProcedimientoDocumentoDTO doc) {
        if (documentosLOPD == null) {
            this.setDocumentosLOPD(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getDocumentosLOPD().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getDocumentosLOPD().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getDocumentosLOPD().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getDocumentosLOPD().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getDocumentosLOPD().add(doc);
        }
    }


    public boolean isIndexable() {

        if (!this.isPublicado()) {
            return false;
        }

        if (this.uaInstructor == null) {
            return false;
        }
        //if (this.uaInstructor..getValidacion() != 1 ) {
        //    return false;
        //}
        return true;
    }
}
