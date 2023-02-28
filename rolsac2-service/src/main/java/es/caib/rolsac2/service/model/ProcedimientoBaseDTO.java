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
    private Boolean estadoSIA;
    private Date fechaSIA;
    private Date fechaCaducidad;
    private Date fechaPublicacion;
    private Date fechaActualizacion;
    private TipoLegitimacionDTO datosPersonalesLegitimacion;
    private UnidadAdministrativaDTO uaResponsable;
    private UnidadAdministrativaDTO uaInstructor;
    private TipoFormaInicioDTO iniciacion;
    private String responsable;
    private TipoSilencioAdministrativoDTO silencio;
    private TipoProcedimientoDTO tipoProcedimiento;
    private int comun;
    private List<TemaGridDTO> temas;
    private TipoViaDTO tipoVia;
    private boolean habilitadoApoderado;

    private String habilitadoFuncionario;

    private boolean tieneTasa = false;

    private String responsableEmail;
    private String responsableTelefono;

    // LOPD
    private String lopdResponsable;
    private Literal nombreProcedimientoWorkFlow;
    private Literal datosPersonalesFinalidad;
    private Literal datosPersonalesDestinatario;

    /**
     * LITERALES
     **/
    private Literal requisitos;
    private Literal objeto;
    private Literal destinatarios;
    private Literal terminoResolucion;
    private Literal observaciones;

    private Literal lopdFinalidad;
    private Literal lopdDestinatario;
    private Literal lopdDerechos;
    private Literal lopdInfoAdicional;

    /*** Lista de objetos **/
    private List<TipoPublicoObjetivoEntidadGridDTO> publicosObjetivo;
    private List<TipoMateriaSIAGridDTO> materiasSIA;
    private List<ProcedimientoDocumentoDTO> documentos;
    private List<ProcedimientoDocumentoDTO> documentosLOPD;
    private List<NormativaGridDTO> normativas;

    /**
     * Auditoria y mensajes
     **/
    private String mensajes;
    private String usuarioAuditoria;

    public static ProcedimientoBaseDTO createInstance(List<String> idiomas) {
        ProcedimientoBaseDTO proc = new ProcedimientoBaseDTO();
        proc.setWorkflow(TypeProcedimientoWorkflow.MODIFICACION);
        proc.setEstado(TypeProcedimientoEstado.MODIFICACION);
        proc.setNombreProcedimientoWorkFlow(Literal.createInstance(idiomas));
        proc.setDatosPersonalesDestinatario(Literal.createInstance(idiomas));
        proc.setDatosPersonalesFinalidad(Literal.createInstance(idiomas));
        proc.setRequisitos(Literal.createInstance(idiomas));
        proc.setObjeto(Literal.createInstance(idiomas));
        proc.setDestinatarios(Literal.createInstance(idiomas));
        proc.setTerminoResolucion(Literal.createInstance(idiomas));
        proc.setObservaciones(Literal.createInstance(idiomas));
        return proc;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setCodigoSIA(Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    public void setEstadoSIA(Boolean estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public Literal getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    public Literal getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombreProcedimientoWorkFlow(Literal nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public Literal getDatosPersonalesFinalidad() {
        return datosPersonalesFinalidad;
    }

    public void setDatosPersonalesFinalidad(Literal datosPersonalesFinalidad) {
        this.datosPersonalesFinalidad = datosPersonalesFinalidad;
    }

    public TipoLegitimacionDTO getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(TipoLegitimacionDTO datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public Literal getDatosPersonalesDestinatario() {
        return datosPersonalesDestinatario;
    }

    public void setDatosPersonalesDestinatario(Literal datosPersonalesDestinatario) {
        this.datosPersonalesDestinatario = datosPersonalesDestinatario;
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


    public TipoSilencioAdministrativoDTO getSilencio() {
        return silencio;
    }

    public void setSilencio(TipoSilencioAdministrativoDTO silencio) {
        this.silencio = silencio;
    }


    public String getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public Literal getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(Literal lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    public Literal getLopdDestinatario() {
        return lopdDestinatario;
    }

    public void setLopdDestinatario(Literal lopdDestinatario) {
        this.lopdDestinatario = lopdDestinatario;
    }

    public Literal getLopdDerechos() {
        return lopdDerechos;
    }

    public void setLopdDerechos(Literal lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public Literal getLopdInfoAdicional() {
        return lopdInfoAdicional;
    }

    public void setLopdInfoAdicional(Literal lopdInfoAdicional) {
        this.lopdInfoAdicional = lopdInfoAdicional;
    }

    public Integer getCodigoSIA() {
        return codigoSIA;
    }

    public Boolean getEstadoSIA() {
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


    public List<TipoMateriaSIAGridDTO> getMateriasSIA() {
        return materiasSIA;
    }

    public void setMateriasSIA(List<TipoMateriaSIAGridDTO> materiasSIA) {
        this.materiasSIA = materiasSIA;
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

    @Override
    public String toString() {
        return "ProcedimientoBaseDTO{" +
                "codigo=" + codigo +
                ", codigoWF='" + codigoWF + '\'' +
                '}';
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


}
