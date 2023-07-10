package es.caib.rolsac2.api.externa.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.api.externa.v1.utils.Utiles;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.ServicioGridDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;

/**
 * Serveis.
 *
 * @author Indra
 */
@XmlRootElement
@Schema(name = "Servicios", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Servicios extends EntidadBase {

    private static final Logger LOG = LoggerFactory.getLogger(Servicios.class);

    /**
     * codigo
     **/
    @Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
    private long codigo;

    //	@Schema(description = "codigoServicio", type = SchemaType.STRING, required = false)
    //	private String codigoServicio; // en el modelo se llama codigo

    @Schema(description = "codigoSIA", type = SchemaType.STRING, required = false)
    private String codigoSIA;

    //	@Schema(description = "correo", type = SchemaType.STRING, required = false)
    //	private String correo;

    @Schema(description = "destinatarios", type = SchemaType.STRING, required = false)
    private String destinatarios;

    @Schema(description = "estadoSIA", type = SchemaType.STRING, required = false)
    private String estadoSIA;

    @Schema(description = "fechaActualizacion", required = false)
    private Calendar fechaActualizacion;

    //	@Schema(description = "fechaDespublicacion", required = false)
    //	private Calendar fechaDespublicacion;

    @Schema(description = "fechaPublicacion", required = false)
    private Calendar fechaPublicacion;

    @Schema(description = "fechaSIA", required = false)
    private Calendar fechaSIA;


    @Schema(description = "nombre", type = SchemaType.STRING, required = false)
    private String nombre;

    @Schema(description = "objeto", type = SchemaType.STRING, required = false)
    private String objeto;

    @Schema(description = "observaciones", type = SchemaType.STRING, required = false)
    private String observaciones;

    @Schema(description = "requisitos", type = SchemaType.STRING, required = false)
    private String requisitos;

    /**
     * es comun
     **/
    @Schema(description = "comun", type = SchemaType.INTEGER, required = false)
    private Integer comun;

    @Schema(description = "lopdResponsable", type = SchemaType.STRING, required = false)
    private String lopdResponsable;

    @Schema(description = "lopdFinalidad", type = SchemaType.STRING, required = false)
    private String lopdFinalidad;

    @Schema(description = "lopdDestinatario", type = SchemaType.STRING, required = false)
    private String lopdDestinatario;

    @Schema(description = "lopdDerechos", type = SchemaType.STRING, required = false)
    private String lopdDerechos;

    //	@Schema(description = "lopdCabecera", type = SchemaType.STRING, required = false)
    //	private String lopdCabecera;

    @Schema(description = "codigoWF", type = SchemaType.STRING, required = false)
    private Long codigoWF;

    @Schema(description = "tipo", type = SchemaType.STRING, required = false)
    private String tipo;
    @Schema(description = "workflow", type = SchemaType.STRING, required = false)
    private String workflow;
    @Schema(description = "estado", type = SchemaType.STRING, required = false)
    private String estado;
    @Schema(description = "interno", type = SchemaType.BOOLEAN, required = false)
    private boolean interno;
    @Schema(description = "publicado", type = SchemaType.BOOLEAN, required = false)
    private boolean publicado;
    @Schema(description = "fechaCaducidad", required = false)
    private Calendar fechaCaducidad;
    @Schema(description = "datosPersonalesLegitimacion", type = SchemaType.INTEGER, required = false)
    private Long datosPersonalesLegitimacion;
    @Schema(description = "uaResponsable", type = SchemaType.INTEGER, required = false)
    private Long uaResponsable;
    @Schema(description = "uaInstructor", type = SchemaType.INTEGER, required = false)
    private Long uaInstructor;
    @Schema(description = "habilitadoApoderado", type = SchemaType.BOOLEAN, required = false)
    private boolean habilitadoApoderado;
    @Schema(description = "habilitadoFuncionario", type = SchemaType.STRING, required = false)
    private String habilitadoFuncionario;
    @Schema(description = "tieneTasa", type = SchemaType.BOOLEAN, required = false)
    private boolean tieneTasa = false;
    @Schema(description = "responsableEmail", type = SchemaType.STRING, required = false)
    private String responsableEmail;
    @Schema(description = "responsableTelefono", type = SchemaType.STRING, required = false)
    private String responsableTelefono;
    @Schema(description = "nombreProcedimientoWorkFlow", type = SchemaType.STRING, required = false)
    private String nombreProcedimientoWorkFlow;
    @Schema(description = "terminoResolucion", type = SchemaType.STRING, required = false)
    private String terminoResolucion;
    @Schema(description = "tramitPresencial", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitPresencial;
    @Schema(description = "tramitElectronica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitElectronica;
    @Schema(description = "tramitTelefonica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitTelefonica;
    @Schema(description = "activoLOPD", type = SchemaType.BOOLEAN, required = false)
    private boolean activoLOPD;
    @Schema(description = "siaFecha", required = false)
    public Calendar siaFecha;

    @Schema(description = "link_tipoTramitacion", required = false)
    private Link link_tipoTramitacion;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long tipoTramitacion;

    @Schema(description = "link_plantillaSel", required = false)
    private Link link_plantillaSel;
    @JsonIgnore
    @Schema(hidden = true)
    @XmlTransient
    private Long plantillaSel;

    /**
     * Constructor
     *
     * @param elem
     * @param urlBase
     * @param idioma
     * @param hateoasEnabled
     */
    public Servicios(final ServicioDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
        if (elem != null) {
            this.codigo = elem.getCodigo();
            this.comun = elem.getComun();
            this.activoLOPD = elem.isActivoLOPD();
            this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA().toString();
            this.codigoWF = elem.getCodigoWF();
            this.nombre = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccion(idioma);
            this.datosPersonalesLegitimacion = elem.getDatosPersonalesLegitimacion() == null ? null : elem.getDatosPersonalesLegitimacion().getCodigo();
            this.destinatarios = elem.getDestinatarios() == null ? null : elem.getDestinatarios().getTraduccion(idioma);
            this.estado = elem.getEstado() == null ? null : elem.getEstado().name();
            this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
            this.fechaPublicacion = elem.getFechaPublicacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaPublicacion());
            this.fechaActualizacion = elem.getFechaActualizacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaActualizacion());
            this.fechaCaducidad = elem.getFechaCaducidad() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaCaducidad());
            this.fechaSIA = elem.getFechaSIA() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaSIA());
            this.habilitadoApoderado = elem.isHabilitadoApoderado();
            this.habilitadoFuncionario = elem.getHabilitadoFuncionario();
            this.interno = elem.isInterno();
            this.lopdResponsable = elem.getLopdResponsable();
            this.nombreProcedimientoWorkFlow = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccion(idioma);
            this.objeto = elem.getObjeto() == null ? null : elem.getObjeto().getTraduccion(idioma);
            this.observaciones = elem.getObservaciones() == null ? null : elem.getObservaciones().getTraduccion(idioma);
            this.publicado = elem.isPublicado();
            this.requisitos = elem.getRequisitos() == null ? null : elem.getRequisitos().getTraduccion(idioma);
            this.responsableEmail = elem.getResponsableEmail();
            this.responsableTelefono = elem.getResponsableTelefono();
            this.terminoResolucion = elem.getTerminoResolucion() == null ? null : elem.getTerminoResolucion().getTraduccion(idioma);
            this.tieneTasa = elem.isTieneTasa();
            this.tipo = elem.getTipo();
            this.tramitElectronica = elem.isTramitElectronica();
            this.tramitTelefonica = elem.isTramitTelefonica();
            this.tramitPresencial = elem.isTramitPresencial();
            this.uaInstructor = elem.getUaInstructor() == null ? null : elem.getUaInstructor().getCodigo();
            this.uaResponsable = elem.getUaResponsable() == null ? null : elem.getUaResponsable().getCodigo();
            this.workflow = elem.getWorkflow() == null ? null : elem.getWorkflow().name();
            if (this.tramitElectronica) {
                this.tipoTramitacion = elem.getTipoTramitacion() == null ? null : elem.getTipoTramitacion().getCodigo();
                this.plantillaSel = elem.getPlantillaSel() == null ? null : elem.getPlantillaSel().getCodigo();
            }
            this.hateoasEnabled = hateoasEnabled;

            generaLinks(urlBase);
        }
    }

    public Servicios() {
        super();
    }

    public Servicios(ServicioGridDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
        if (elem != null) {
            this.codigo = elem.getCodigo();
            this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA().toString();
            this.estado = elem.getEstado();
            this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
            this.tipo = elem.getTipo();
            this.nombre = elem.getNombre();
            if (elem.getSiaFecha() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(elem.getSiaFecha());
                this.siaFecha = calendar;
            }
            this.codigoWF = elem.getCodigoWFPub();
        }
    }

    @Override
    public void generaLinks(String urlBase) {
        link_tipoTramitacion = this.generaLink(this.tipoTramitacion, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase, null);
        link_plantillaSel = this.generaLink(this.plantillaSel, Constantes.ENTIDAD_TIPO_TRAMITACION, Constantes.URL_TIPO_TRAMITACION, urlBase, null);
    }

    @Override
    protected void addSetersInvalidos() {
        if (!SETTERS_INVALIDS.contains("setCodigo")) {
            SETTERS_INVALIDS.add("setCodigo");
        }

        if (!SETTERS_INVALIDS.contains("setPlataforma")) {
            SETTERS_INVALIDS.add("setPlataforma");
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
     * @return the codigoSIA
     */
    public java.lang.String getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * @param codigoSIA the codigoSIA to set
     */
    public void setCodigoSIA(final java.lang.String codigoSIA) {
        this.codigoSIA = codigoSIA;
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
     * @return the estadoSIA
     */
    public java.lang.String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * @param estadoSIA the estadoSIA to set
     */
    public void setEstadoSIA(final java.lang.String estadoSIA) {
        this.estadoSIA = estadoSIA;
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

    //	/**
    //	 * @return the fechaDespublicacion
    //	 */
    //	public java.util.Calendar getFechaDespublicacion() {
    //		return fechaDespublicacion;
    //	}

    //	/**
    //	 * @param fechaDespublicacion the fechaDespublicacion to set
    //	 */
    //	public void setFechaDespublicacion(final java.util.Calendar fechaDespublicacion) {
    //		this.fechaDespublicacion = fechaDespublicacion;
    //	}

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
     * @return the nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final java.lang.String nombre) {
        this.nombre = nombre;
    }

    //	/**
    //	 * @return the nombreResponsable
    //	 */
    //	public java.lang.String getNombreResponsable() {
    //		return nombreResponsable;
    //	}
    //
    //	/**
    //	 * @param nombreResponsable the nombreResponsable to set
    //	 */
    //	public void setNombreResponsable(final java.lang.String nombreResponsable) {
    //		this.nombreResponsable = nombreResponsable;
    //	}

    /**
     * @return the objeto
     */
    public java.lang.String getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(final java.lang.String objeto) {
        this.objeto = objeto;
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

    //	/**
    //	 * @return the telefono
    //	 */
    //	public java.lang.String getTelefono() {
    //		return telefono;
    //	}
    //
    //	/**
    //	 * @param telefono the telefono to set
    //	 */
    //	public void setTelefono(final java.lang.String telefono) {
    //		this.telefono = telefono;
    //	}

    //	/**
    //	 * @return the tramiteId
    //	 */
    //	public java.lang.String getTramiteId() {
    //		return tramiteId;
    //	}

    //	/**
    //	 * @param tramiteId the tramiteId to set
    //	 */
    //	public void setTramiteId(final java.lang.String tramiteId) {
    //		this.tramiteId = tramiteId;
    //	}

    //	/**
    //	 * @return the tasaUrl
    //	 */
    //	public java.lang.String getTasaUrl() {
    //		return tasaUrl;
    //	}
    //
    //	/**
    //	 * @param tasaUrl the tasaUrl to set
    //	 */
    //	public void setTasaUrl(final java.lang.String tasaUrl) {
    //		this.tasaUrl = tasaUrl;
    //	}
    //
    //	/**
    //	 * @return the tramiteVersion
    //	 */
    //	public java.lang.String getTramiteVersion() {
    //		return tramiteVersion;
    //	}

    //	/**
    //	 * @param tramiteVersion the tramiteVersion to set
    //	 */
    //	public void setTramiteVersion(final java.lang.String tramiteVersion) {
    //		this.tramiteVersion = tramiteVersion;
    //	}

    //	/**
    //	 * @return the validacion
    //	 */
    //	public java.lang.Integer getValidacion() {
    //		return validacion;
    //	}
    //
    //	/**
    //	 * @param validacion the validacion to set
    //	 */
    //	public void setValidacion(final java.lang.Integer validacion) {
    //		this.validacion = validacion;
    //	}
    //
    //	/**
    //	 * @return the linkServicioResponsable
    //	 */
    //	public Link getLinkServicioResponsable() {
    //		return linkServicioResponsable;
    //	}
    //
    //	/**
    //	 * @param linkServicioResponsable the linkServicioResponsable to set
    //	 */
    //	public void setLinkServicioResponsable(final Link linkServicioResponsable) {
    //		this.linkServicioResponsable = linkServicioResponsable;
    //	}
    //
    //	/**
    //	 * @return the servicioResponsable
    //	 */
    //	public Long getServicioResponsable() {
    //		return servicioResponsable;
    //	}
    //
    //	/**
    //	 * @param servicioResponsable the servicioResponsable to set
    //	 */
    //	public void setServicioResponsable(final Long servicioResponsable) {
    //		this.servicioResponsable = servicioResponsable;
    //	}

    //	/**
    //	 * @return the linkOrganoInstructor
    //	 */
    //	public Link getLinkOrganoInstructor() {
    //		return linkOrganoInstructor;
    //	}

    //	/**
    //	 * @param linkOrganoInstructor the linkOrganoInstructor to set
    //	 */
    //	public void setLinkOrganoInstructor(final Link linkOrganoInstructor) {
    //		this.linkOrganoInstructor = linkOrganoInstructor;
    //	}

    //	/**
    //	 * @return the organoInstructor
    //	 */
    //	public Long getOrganoInstructor() {
    //		return organoInstructor;
    //	}
    //
    //	/**
    //	 * @param organoInstructor the organoInstructor to set
    //	 */
    //	public void setOrganoInstructor(final Long organoInstructor) {
    //		this.organoInstructor = organoInstructor;
    //	}

    /**
     * @return the comun
     */
    public Integer getComun() {
        return comun;
    }

    /**
     * @param comun the comun to set
     */
    public void setComun(final Integer comun) {
        this.comun = comun;
    }

    //	/**
    //	 * @return the id
    //	 */
    //	public java.lang.Long getId() {
    //		return id;
    //	}

    //	/**
    //	 * @return the telematico
    //	 */
    //	public boolean getTelematico() {
    //		return telematico;
    //	}

    //	/**
    //	 * @param telematico the telematico to set
    //	 */
    //	public void setTelematico(final boolean telematico) {
    //		this.telematico = telematico;
    //	}

    //	/**
    //	 * @return the parametros
    //	 */
    //	public java.lang.String getParametros() {
    //		return parametros;
    //	}

    //	/**
    //	 * @param parametros the parametros to set
    //	 */
    //	public void setParametros(final java.lang.String parametros) {
    //		this.parametros = parametros;
    //	}

    //	/**
    //	 * @return the urlTramiteExterno
    //	 */
    //	public java.lang.String getUrlTramiteExterno() {
    //		return urlTramiteExterno;
    //	}

    //	/**
    //	 * @param urlTramiteExterno the urlTramiteExterno to set
    //	 */
    //	public void setUrlTramiteExterno(final java.lang.String urlTramiteExterno) {
    //		this.urlTramiteExterno = urlTramiteExterno;
    //	}

    //	/**
    //	 * @return the linkLopdInfoAdicional
    //	 */
    //	public Link getLinkLopdInfoAdicional() {
    //		return linkLopdInfoAdicional;
    //	}

    //	/**
    //	 * @param linkLopdInfoAdicional the linkLopdInfoAdicional to set
    //	 */
    //	public void setLinkLopdInfoAdicional(final Link linkLopdInfoAdicional) {
    //		this.linkLopdInfoAdicional = linkLopdInfoAdicional;
    //	}

    //	/**
    //	 * @return the lopdInfoAdicional
    //	 */
    //	public String getLopdInfoAdicional() {
    //		return lopdInfoAdicional;
    //	}
    //
    //	/**
    //	 * @param lopdInfoAdicional the lopdInfoAdicional to set
    //	 */
    //	public void setLopdInfoAdicional(final String lopdInfoAdicional) {
    //		this.lopdInfoAdicional = lopdInfoAdicional;
    //	}

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

    //	/**
    //	 * @return the lopdCabecera
    //	 */
    //	public String getLopdCabecera() {
    //		return lopdCabecera;
    //	}
    //
    //	/**
    //	 * @param lopdCabecera the lopdCabecera to set
    //	 */
    //	public void setLopdCabecera(final String lopdCabecera) {
    //		this.lopdCabecera = lopdCabecera;
    //	}

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

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Long getDatosPersonalesLegitimacion() {
        return datosPersonalesLegitimacion;
    }

    public void setDatosPersonalesLegitimacion(Long datosPersonalesLegitimacion) {
        this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
    }

    public Long getUaResponsable() {
        return uaResponsable;
    }

    public void setUaResponsable(Long uaResponsable) {
        this.uaResponsable = uaResponsable;
    }

    public Long getUaInstructor() {
        return uaInstructor;
    }

    public void setUaInstructor(Long uaInstructor) {
        this.uaInstructor = uaInstructor;
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

    public String getNombreProcedimientoWorkFlow() {
        return nombreProcedimientoWorkFlow;
    }

    public void setNombreProcedimientoWorkFlow(String nombreProcedimientoWorkFlow) {
        this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
    }

    public String getTerminoResolucion() {
        return terminoResolucion;
    }

    public void setTerminoResolucion(String terminoResolucion) {
        this.terminoResolucion = terminoResolucion;
    }

    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public boolean isActivoLOPD() {
        return activoLOPD;
    }

    public void setActivoLOPD(boolean activoLOPD) {
        this.activoLOPD = activoLOPD;
    }

    public Link getLink_tipoTramitacion() {
        return link_tipoTramitacion;
    }

    public void setLink_tipoTramitacion(Link link_tipoTramitacion) {
        this.link_tipoTramitacion = link_tipoTramitacion;
    }

    public Long getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(Long tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    public Link getLink_plantillaSel() {
        return link_plantillaSel;
    }

    public void setLink_plantillaSel(Link link_plantillaSel) {
        this.link_plantillaSel = link_plantillaSel;
    }

    public Long getPlantillaSel() {
        return plantillaSel;
    }

    public void setPlantillaSel(Long plantillaSel) {
        this.plantillaSel = plantillaSel;
    }
}
