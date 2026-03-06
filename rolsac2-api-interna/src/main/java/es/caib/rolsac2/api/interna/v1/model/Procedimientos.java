package es.caib.rolsac2.api.interna.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;

/**
 * Procediments.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "Procedimientos", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_PROCEDIMIENTO)
public class Procedimientos extends EntidadBase {

    private static final Logger LOG = LoggerFactory.getLogger(Procedimientos.class);

    /**
     * codigo
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private long codigo;

    /**
     *
     **/
    @Schema(description = "destinatarios", type = SchemaType.STRING, required = false)
    private String destinatarios;

    /**
     *
     **/
    @Schema(description = "fechaActualizacion", required = false)
    private Calendar fechaActualizacion;

    /**
     *
     **/
    @Schema(description = "fechaCaducidad", required = false)
    private Calendar fechaCaducidad;

    /**
     *
     **/
    @Schema(description = "fechaPublicacion", required = false)
    private Calendar fechaPublicacion;

    /**
     *
     **/
    @Schema(description = "observaciones", type = SchemaType.STRING, required = false)
    private String observaciones;

    /**
     *
     **/
    @Schema(description = "requisitos", type = SchemaType.STRING, required = false)
    private String requisitos;

    /**
     *
     **/
    @Schema(description = "codigoSIA", type = SchemaType.INTEGER, required = false)
    private Integer codigoSIA;

    /**
     *
     **/
    @Schema(description = "estadoSIA", type = SchemaType.STRING, required = false)
    private String estadoSIA;

    /**
     *
     **/
    @Schema(description = "fechaSIA", required = false)
    private Calendar fechaSIA;


    /*
     * private java.lang.String resultat; private boolean ventanillaUnica;
     */

    // -- LINKS--//
    // -- se duplican las entidades para poder generar la clase link en funcion de
    // la propiedad principal (sin "link_")
    /**
     * servicioResponsable
     **/
    @Schema(description = "linkUnidadAdministrativaResponsable", required = false)
    private Link linkUnidadAdministrativaResponsable;

    @Schema(hidden = true)
    @JsonIgnore
    @XmlTransient
    private Long uaResponsable;

    /**
     * unidadAdministrativa
     **/
    @Schema(description = "linkUnidadAdministrativaCompetente", required = false)
    private Link linkUnidadAdministrativaCompetente;
    @Schema(hidden = true)
    @JsonIgnore
    @XmlTransient
    private Long uaCompetente;

    /**
     * organResolutori
     **/
    @Schema(description = "linkUnidadAdministrativaInstructora", required = false)
    private Link linkUnidadAdministrativaInstructora;
    @Schema(hidden = true)
    @JsonIgnore
    @XmlTransient
    private Long uaInstructor;

    // CASOS ESPECIALES, SE RELLENA LA SUBENTIDAD.
    /** silencio **/
    //@Schema(description ="silencio", required = false)
    //private Silencio silencio;

    /** iniciacion **/
    //@Schema(description ="iniciacion", required = false)
    //private Iniciacion iniciacion;

    /**
     * es comun
     **/
    @Schema(description = "comun", type = SchemaType.INTEGER, required = false)
    private int comun;

    //	@Schema
    //	private LopdLegitimacion lopdLegitimacion;

    /**
     * Info Adicional
     **/
    @Schema(description = "linkLopdInfoAdicional", required = false)
    private Link linkLopdInfoAdicional;
    @Schema(description = "lopdResponsable", type = SchemaType.STRING, required = false)
    private String lopdResponsable;
    @Schema(description = "lopdFinalidad", type = SchemaType.STRING, required = false)
    private String lopdFinalidad;
    @Schema(description = "lopdDestinatario", type = SchemaType.STRING, required = false)
    private String lopdDestinatario;
    @Schema(description = "lopdDerechos", type = SchemaType.STRING, required = false)
    private String lopdDerechos;
    @Schema(description = "lopdCabecera", type = SchemaType.STRING, required = false)
    private String lopdCabecera;
    @Schema(description = "lopdLegitimacion", required = false)
    private Legitimacion lopdLegitimacion;

    @Schema(description = "objeto", type = SchemaType.STRING, required = false)
    private String objeto;

    @Schema(description = "codigoWF", type = SchemaType.INTEGER, required = false)
    private Long codigoWF;

    @Schema(description = "tipo", type = SchemaType.STRING, required = false)
    private String tipo;
    @Schema(description = "workflow", type = SchemaType.STRING, required = false)
    private TypeProcedimientoWorkflow workflow;
    @Schema(description = "estado", type = SchemaType.STRING, required = false)
    private TypeProcedimientoEstado estado;
    @Schema(description = "interno", type = SchemaType.INTEGER, required = false)
    private Integer interno;
    @Schema(description = "publicado", type = SchemaType.INTEGER, required = false)
    private Integer publicado;

    @Schema(description = "iniciacion", required = false)
    private Inicio iniciacion;

    @Schema(description = "silencio", required = false)
    private Silencio silencio;

    @Schema(description = "tipoProcedimiento", required = false)
    private TipoProcedimiento tipoProcedimiento;

    @Schema(description = "tipoVia", type = SchemaType.INTEGER, required = false)
    private Long tipoVia;

    @Schema(description = "habilitadoApoderado", type = SchemaType.INTEGER, required = false)
    private Integer habilitadoApoderado;

    @Schema(description = "habilitadoFuncionario", type = SchemaType.STRING, required = false)
    private String habilitadoFuncionario;

    @Schema(description = "tieneTasa", type = SchemaType.INTEGER, required = false)
    private Integer tieneTasa = 0;

    @Schema(description = "responsableEmail", type = SchemaType.STRING, required = false)
    private String responsableEmail;



    @Schema(description = "responsableTelefono", type = SchemaType.STRING, required = false)
    private String responsableTelefono;

    @Schema(description = "datosContacto", required = false)
    private DatosContacto datosContacto;

    @Schema(description = "nombreProcedimientoWorkFlow", type = SchemaType.STRING, required = false)
    private String nombreProcedimientoWorkFlow;

    @Schema(description = "terminoResolucion", type = SchemaType.STRING, required = false)
    private String terminoResolucion;

    @Schema(description = "tramitElectronica", type = SchemaType.INTEGER, required = false)
    private Integer tramitElectronica;

    @Schema(description = "tramitPresencial", type = SchemaType.INTEGER, required = false)
    private Integer tramitPresencial;

    @Schema(description = "tramitTelefonica", type = SchemaType.INTEGER, required = false)
    private Integer tramitTelefonica;
    //	@Schema(description = "mensajes", type = SchemaType.STRING, required = false)
    //    private String mensajes;
    //	@Schema(description = "usuarioAuditoria", type = SchemaType.STRING, required = false)
    //    private String usuarioAuditoria;
    //	@Schema(description = "pendienteIndexar", type = SchemaType.BOOLEAN, required = false)
    //    private boolean pendienteIndexar = false;
    //	@Schema(description = "pendienteMensajesGestor", type = SchemaType.BOOLEAN, required = false)
    //    private boolean pendienteMensajesGestor = false;
    //	@Schema(description = "pendienteMensajesSupervisor", type = SchemaType.BOOLEAN, required = false)
    //    private boolean pendienteMensajesSupervisor = false;

    @Schema(description = "activoLOPD", type = SchemaType.INTEGER, required = false)
    private Integer activoLOPD = 0;

    @Schema(description = "esPdu", type = SchemaType.INTEGER, required = false)
    private Integer esPdu;


    public Procedimientos() {
        super();
    }


    public Procedimientos(ProcedimientoDTO nodo, String urlBase, String idioma, boolean hateoasEnabled, final String idiomaPorDefecto) {

        super(nodo, urlBase, idioma, hateoasEnabled);

        try {
            // copiamos los datos que no tienen la misma estructura:
            if (nodo.getSilencio() != null) {
                this.silencio = new Silencio(nodo.getSilencio(), urlBase, idioma, hateoasEnabled);
            }

            // copiamos los datos que no tienen la misma estructura:
            if (nodo.getDatosPersonalesLegitimacion() != null) {
                this.lopdLegitimacion = new Legitimacion(nodo.getDatosPersonalesLegitimacion(), urlBase, idioma, hateoasEnabled);
            }

            // copiamos los datos que no tienen la misma estructura:
            if (nodo.getIniciacion() != null) {
                this.iniciacion = new Inicio(nodo.getIniciacion(), urlBase, idioma, hateoasEnabled);
            }

            if (nodo.getTipoProcedimiento() != null) {
                this.tipoProcedimiento = new TipoProcedimiento(nodo.getTipoProcedimiento(), urlBase, idioma, hateoasEnabled);
            }

            if (nodo.getLopdDerechos() != null) {
                this.lopdDerechos = nodo.getLopdDerechos().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (nodo.getLopdFinalidad() != null) {
                this.lopdFinalidad = nodo.getLopdFinalidad().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (nodo.getLopdDestinatario() != null) {
                this.lopdDestinatario = nodo.getLopdDestinatario().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (nodo.getLopdCabecera() != null) {
                this.lopdCabecera = nodo.getLopdCabecera().getTraduccionConValor(idioma, idiomaPorDefecto);
            }
            if (nodo.getDocumentosLOPD() != null && !nodo.getDocumentosLOPD().isEmpty()) {
                String descripcion = getDescripcion(nodo.getDocumentosLOPD().get(0), idioma, idiomaPorDefecto);
                Long codigoDoc = nodo.getDocumentosLOPD().get(0).getCodigo();
                linkLopdInfoAdicional = this.generaLinkArchivo(codigoDoc, urlBase, descripcion);
            }
            if (nodo.getUaInstructor() != null) {
                linkUnidadAdministrativaInstructora = this.generaLink(nodo.getUaInstructor().getCodigo(), Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, getDescripcionUA(nodo.getUaInstructor(), idioma, idiomaPorDefecto));
            }
            if (nodo.getUaCompetente() != null) {
                linkUnidadAdministrativaCompetente = this.generaLink(nodo.getUaCompetente().getCodigo(), Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, getDescripcionUA(nodo.getUaCompetente(), idioma, idiomaPorDefecto));
            }
            if (nodo.getUaResponsable() != null) {
                linkUnidadAdministrativaResponsable = this.generaLink(nodo.getUaResponsable().getCodigo(), Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, getDescripcionUA(nodo.getUaResponsable(), idioma, idiomaPorDefecto));
            }

            this.datosContacto = new DatosContacto();
            if (nodo.getUaResponsable() != null) {
                this.datosContacto.setServicioResponsable(getDescripcionUA(nodo.getUaResponsable(), idioma, idiomaPorDefecto));
            }
            this.datosContacto.setPersonaACargo(nodo.getResponsable());
            this.datosContacto.setEmailIncidencias(nodo.getIncidenciasEmail());
            this.esPdu = (nodo.getEstadoPdu() != null && nodo.getEstadoPdu().compareTo(1) == 0) ? 1 : 0;
        } catch (final Exception e) {
            LOG.error("Error generando procedimiento " + this.codigo, e);
        }
    }

    private String getDescripcion(ProcedimientoDocumentoDTO documentoLOPD, String idioma, String idiomaPorDefecto) {
        String descripcion = null;
        if (documentoLOPD.getDescripcion() != null) {
            descripcion = documentoLOPD.getDescripcion().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (documentoLOPD.getDescripcion() != null && descripcion == null) {
            descripcion = documentoLOPD.getDescripcion().getTraduccion();
        }
        return descripcion;
    }


    @Override
    public void generaLinks(final String urlBase) {

    }

    /**
     * Obtiene el nombre de la UA, primero el idioma y luego el idiomapordefecto
     *
     * @param ua               unidad administrativa
     * @param idioma           idioma
     * @param idiomaPorDefecto idioma por defecto
     * @return nombre
     */
    private String getDescripcionUA(UnidadAdministrativaDTO ua, String idioma, String idiomaPorDefecto) {
        String descripcion = null;
        if (ua.getNombre() != null) {
            descripcion = ua.getNombre().getTraduccionConValor(idioma, idiomaPorDefecto);
        }
        if (ua.getNombre() != null && descripcion == null) {
            descripcion = ua.getNombre().getTraduccion();
        }
        return descripcion;
    }

    @Override
    protected void addSetersInvalidos() {
        if (!SETTERS_INVALIDS.contains("setIniciacion")) {
            SETTERS_INVALIDS.add("setIniciacion");
        }

        if (!SETTERS_INVALIDS.contains("setSilencio")) {
            SETTERS_INVALIDS.add("setSilencio");
        }
        if (!SETTERS_INVALIDS.contains("setTipoProcedimiento")) {
            SETTERS_INVALIDS.add("setTipoProcedimiento");
        }

    }

    @Override
    public void setId(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the destinatarios
     */
    public java.lang.String getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param destinatarios the destinatarios to set
     */
    public void setDestinatarios(final java.lang.String destinatarios) {
        this.destinatarios = destinatarios;
    }

    /**
     * @return the fechaActualizacion
     */
    public java.util.Calendar getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(final java.util.Calendar fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the fechaCaducidad
     */
    public java.util.Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * @param fechaCaducidad the fechaCaducidad to set
     */
    public void setFechaCaducidad(final java.util.Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * @return the fechaPublicacion
     */
    public java.util.Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(final java.util.Calendar fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the observaciones
     */
    public java.lang.String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(final java.lang.String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the requisitos
     */
    public java.lang.String getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setRequisitos(final java.lang.String requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return the resolucion
     */

    /**
     * @return the codigoSIA
     */
    public java.lang.Integer getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * @param codigoSIA the codigoSIA to set
     */
    public void setCodigoSIA(final java.lang.Integer codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * @return the estadoSIA
     */
    public String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * @param estadoSIA the estadoSIA to set
     */
    public void setEstadoSIA(final String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    public String getLopdCabecera() {
        return lopdCabecera;
    }

    public void setLopdCabecera(String lopdCabecera) {
        this.lopdCabecera = lopdCabecera;
    }

    public Legitimacion getLopdLegitimacion() {
        return lopdLegitimacion;
    }

    public void setLopdLegitimacion(Legitimacion lopdLegitimacion) {
        this.lopdLegitimacion = lopdLegitimacion;
    }

    /**
     * @return the fechaSIA
     */
    public java.util.Calendar getFechaSIA() {
        return fechaSIA;
    }

    /**
     * @param fechaSIA the fechaSIA to set
     */
    public void setFechaSIA(final java.util.Calendar fechaSIA) {
        this.fechaSIA = fechaSIA;
    }

    /**
     * @return the comun
     */
    public int getComun() {
        return comun;
    }

    /**
     * @param comun the comun to set
     */
    public void setComun(final int comun) {
        this.comun = comun;
    }

    /**
     * @return the lopdResponsable
     */
    public String getLopdResponsable() {
        return lopdResponsable;
    }

    /**
     * @param lopdResponsable the lopdResponsable to set
     */
    public void setLopdResponsable(final String lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    /**
     * @return the lopdFinalidad
     */
    public String getLopdFinalidad() {
        return lopdFinalidad;
    }

    /**
     * @param lopdFinalidad the lopdFinalidad to set
     */
    public void setLopdFinalidad(final String lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    /**
     * @return the lopdDestinatario
     */
    public String getLopdDestinatario() {
        return lopdDestinatario;
    }

    /**
     * @param lopdDestinatario the lopdDestinatario to set
     */
    public void setLopdDestinatario(final String lopdDestinatario) {
        this.lopdDestinatario = lopdDestinatario;
    }

    /**
     * @return the lopdDerechos
     */
    public String getLopdDerechos() {
        return lopdDerechos;
    }

    /**
     * @param lopdDerechos the lopdDerechos to set
     */
    public void setLopdDerechos(final String lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public Long getCodigoWF() {
        return codigoWF;
    }

    public void setCodigoWF(Long codigoWF) {
        this.codigoWF = codigoWF;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Integer getInterno() {
        return interno;
    }

    public void setInterno(Integer interno) {
        this.interno = interno;
    }

    public Integer getPublicado() {
        return publicado;
    }

    public void setPublicado(Integer publicado) {
        this.publicado = publicado;
    }


    @XmlTransient
    public Long getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(Long uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    @XmlTransient
    public Long getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(Long uaInstructor) {
        this.uaInstructor = uaInstructor;
    }

    public Inicio getIniciacion() {
        return iniciacion;
    }

    public void setIniciacion(Inicio iniciacion) {
        this.iniciacion = iniciacion;
    }

    public Silencio getSilencio() {
        return silencio;
    }

    public void setSilencio(Silencio silencio) {
        this.silencio = silencio;
    }

    public Long getTipoVia() {
        return tipoVia;
    }

    public TipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public Integer getHabilitadoApoderado() {
        return habilitadoApoderado;
    }

    public void setHabilitadoApoderado(Integer habilitadoApoderado) {
        this.habilitadoApoderado = habilitadoApoderado;
    }

    public String getHabilitadoFuncionario() {
        return habilitadoFuncionario;
    }

    public void setHabilitadoFuncionario(String habilitadoFuncionario) {
        this.habilitadoFuncionario = habilitadoFuncionario;
    }

    public Integer getTieneTasa() {
        return tieneTasa;
    }

    public void setTieneTasa(Integer tieneTasa) {
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

    public String getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    public void setNombreProcedimientoWorkFlow(String nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public void setTipoVia(Long tipoVia) {
        this.tipoVia = tipoVia;
    }

    public Integer getActivoLOPD() {
        return activoLOPD;
    }

    public void setActivoLOPD(Integer activoLOPD) {
        this.activoLOPD = activoLOPD;
    }

    public String getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(String terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    //	public String getMensajes() {
    //		return mensajes;
    //	}
    //
    //	public void setMensajes(String mensajes) {
    //		this.mensajes = mensajes;
    //	}
    //
    //	public String getUsuarioAuditoria() {
    //		return usuarioAuditoria;
    //	}
    //
    //	public void setUsuarioAuditoria(String usuarioAuditoria) {
    //		this.usuarioAuditoria = usuarioAuditoria;
    //	}
    //
    //	public boolean isPendienteIndexar() {
    //		return pendienteIndexar;
    //	}
    //
    //	public void setPendienteIndexar(boolean pendienteIndexar) {
    //		this.pendienteIndexar = pendienteIndexar;
    //	}
    //
    //	public boolean isPendienteMensajesGestor() {
    //		return pendienteMensajesGestor;
    //	}
    //
    //	public void setPendienteMensajesGestor(boolean pendienteMensajesGestor) {
    //		this.pendienteMensajesGestor = pendienteMensajesGestor;
    //	}
    //
    //	public boolean isPendienteMensajesSupervisor() {
    //		return pendienteMensajesSupervisor;
    //	}
    //
    //	public void setPendienteMensajesSupervisor(boolean pendienteMensajesSupervisor) {
    //		this.pendienteMensajesSupervisor = pendienteMensajesSupervisor;
    //	}

    public Link getlinkUnidadAdministrativaResponsable() {
        return linkUnidadAdministrativaResponsable;
    }

    public void setlinkUnidadAdministrativaResponsable(Link linkUnidadAdministrativaResponsable) {
        this.linkUnidadAdministrativaResponsable = linkUnidadAdministrativaResponsable;
    }

    public void setlinkUnidadAdministrativaCompetente(Link linkUnidadAdministrativaResponsable) {
        this.linkUnidadAdministrativaResponsable = linkUnidadAdministrativaResponsable;
    }

    public Link getlinkUnidadAdministrativaCompetente() {
        return linkUnidadAdministrativaResponsable;
    }

    public Link getlinkUnidadAdministrativaInstructora() {
        return linkUnidadAdministrativaInstructora;
    }

    public void setlinkUnidadAdministrativaInstructora(Link linkUnidadAdministrativaInstructora) {
        this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
    }

    public Link getLinkLopdInfoAdicional() {
        return linkLopdInfoAdicional;
    }

    public void setLinkLopdInfoAdicional(Link linkLopdInfoAdicional) {
        this.linkLopdInfoAdicional = linkLopdInfoAdicional;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    @XmlTransient
    public Long getUaCompetente() {
        return uaCompetente;
    }

    public void setUaCompetente(Long uaCompetente) {
        this.uaCompetente = uaCompetente;
    }



    public void setLinkUnidadAdministrativaInstructora(Link linkUnidadAdministrativaInstructora) {
        this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
    }

    public Integer getTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(Integer tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public Integer getTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(Integer tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public Integer getTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(Integer tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public Integer getEsPdu() {
        return esPdu;
    }

    public void setEsPdu(Integer esPdu) {
        this.esPdu = esPdu;
    }

}