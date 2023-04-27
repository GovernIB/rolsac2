package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.api.externa.v1.utils.Utiles;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;

/**
 * Procediments.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Procedimientos", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_PROCEDIMIENTO)
public class Procedimientos extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(Procedimientos.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private long codigo;

	/**  **/
	@Schema(description = "destinatarios", type = SchemaType.STRING, required = false)
	private String destinatarios;

	/**  **/
	@Schema(description = "fechaActualizacion", required = false)
	private Calendar fechaActualizacion;

	/**  **/
	@Schema(description = "fechaCaducidad", required = false)
	private Calendar fechaCaducidad;

	/**  **/
	@Schema(description = "fechaPublicacion", required = false)
	private Calendar fechaPublicacion;

	/**  **/
	@Schema(description = "indicador", type = SchemaType.BOOLEAN, required = false)
	private boolean indicador;

	/**  **/
	@Schema(description = "dirElectronica", type = SchemaType.STRING, required = false)
	private String dirElectronica;

	/**  **/
	@Schema(description = "lugar", type = SchemaType.STRING, required = false)
	private String lugar;

	/**  **/
	@Schema(description = "nombre", type = SchemaType.STRING, required = false)
	private String nombre;

	/**  **/
	@Schema(description = "notificacion", type = SchemaType.STRING, required = false)
	private String notificacion;

	/**  **/
	@Schema(description = "observaciones", type = SchemaType.STRING, required = false)
	private String observaciones;

	/**  **/
	@Schema(description = "plazos", type = SchemaType.STRING, required = false)
	private String plazos;

	/**  **/
	@Schema(description = "recursos", type = SchemaType.STRING, required = false)
	private String recursos;

	/**  **/
	@Schema(description = "requisitos", type = SchemaType.STRING, required = false)
	private String requisitos;

	/**  **/
	@Schema(description = "resolucion", type = SchemaType.STRING, required = false)
	private String resolucion;

	/**  **/
	@Schema(description = "responsable", type = SchemaType.STRING, required = false)
	private String responsable;

	/**  **/
	@Schema(description = "resumen", type = SchemaType.STRING, required = false)
	private String resumen;

	/**  **/
	@Schema(description = "signatura", type = SchemaType.STRING, required = false)
	private String signatura;

	/**  **/
	@Schema(description = "signatura", type = SchemaType.BOOLEAN, required = false)
	private boolean taxa;

	/**  **/
	@Schema(description = "url", type = SchemaType.STRING, required = false)
	private String url;

	/**  **/
	@Schema(description = "validacion", type = SchemaType.INTEGER, required = false)
	private Integer validacion;

	/**  **/
	@Schema(description = "codigoSIA", type = SchemaType.STRING, required = false)
	private String codigoSIA;

	/**  **/
	@Schema(description = "estadoSIA", type = SchemaType.STRING, required = false)
	private String estadoSIA;

	/**  **/
	@Schema(description = "fechaSIA", required = false)
	private Calendar fechaSIA;

	@Schema(description = "tramite", type = SchemaType.STRING, required = false)
	private String tramite;

	@Schema(description = "version", type = SchemaType.INTEGER, required = false)
	private Long version;

	/*
	 * private java.lang.String resultat; private boolean ventanillaUnica;
	 */

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
//	/** servicioResponsable **/
//	@Schema(description = "linkServicioResponsable", required = false)
//	private Link linkServicioResponsable;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long servicioResponsable;

//	/** unidadAdministrativa **/
//	@Schema(description = "linkUnidadAdministrativa", required = false)
//	private Link linkUnidadAdministrativa;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long unidadAdministrativa;

//	/** organResolutori **/
//	@Schema(description = "linkOrganResolutori", required = false)
//	private Link linkOrganResolutori;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long organResolutori;

//	/** familia **/
//	@Schema(description = "linkFamilia", required = false)
//	private Link linkFamilia;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long familia;

	////// CASOS ESPECIALES, SE RELLENA LA SUBENTIDAD.
//	/** silencio **/
//	@Schema(description ="silencio", required = false)
//	private Silencis silencio;

//	/** iniciacion **/
//	@Schema(description ="iniciacion", required = false)
//	private Iniciacions iniciacion;

	/** es comun **/
	@Schema(description = "comun", type = SchemaType.BOOLEAN, required = false)
	private int comun;

//	@Schema
//	private LopdLegitimacion lopdLegitimacion;

//	/** Info Adicional **/
//	@Schema(description = "linkLopdInfoAdicional", required = false)
//	private Link linkLopdInfoAdicional;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private String lopdInfoAdicional;

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

	@Schema(description = "disponibleApoderadoHabilitado", type = SchemaType.BOOLEAN, required = false)
	private boolean disponibleApoderadoHabilitado;

	@Schema(description = "disponibleFuncionarioHabilitado", type = SchemaType.BOOLEAN, required = false)
	private boolean disponibleFuncionarioHabilitado;

	@Schema(description = "lopdCabecera", type = SchemaType.INTEGER, required = false)
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

	@Schema(description = "datosPersonalesLegitimacion", type = SchemaType.INTEGER, required = false)
    private Long datosPersonalesLegitimacion;
	@Schema(description = "uaResponsable", type = SchemaType.INTEGER, required = false)
    private Long uaResponsable;
	@Schema(description = "uaInstructor", type = SchemaType.INTEGER, required = false)
    private Long uaInstructor;
	@Schema(description = "iniciacion", type = SchemaType.INTEGER, required = false)
    private Long iniciacion;

	@Schema(description = "silencio", type = SchemaType.INTEGER, required = false)
    private Long silencio;
	@Schema(description = "tipoProcedimiento", type = SchemaType.INTEGER, required = false)
    private Long tipoProcedimiento;
	@Schema(description = "tipoVia", type = SchemaType.INTEGER, required = false)
    private Long tipoVia;
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
	@Schema(description = "datosPersonalesFinalidad", type = SchemaType.STRING, required = false)
    private String datosPersonalesFinalidad;
	@Schema(description = "datosPersonalesDestinatario", type = SchemaType.STRING, required = false)
    private String datosPersonalesDestinatario;
	@Schema(description = "terminoResolucion", type = SchemaType.STRING, required = false)
    private String terminoResolucion;
	@Schema(description = "mensajes", type = SchemaType.STRING, required = false)
    private String mensajes;
	@Schema(description = "usuarioAuditoria", type = SchemaType.STRING, required = false)
    private String usuarioAuditoria;
	@Schema(description = "pendienteIndexar", type = SchemaType.BOOLEAN, required = false)
    private boolean pendienteIndexar = false;
	@Schema(description = "pendienteMensajesGestor", type = SchemaType.BOOLEAN, required = false)
    private boolean pendienteMensajesGestor = false;
	@Schema(description = "pendienteMensajesSupervisor", type = SchemaType.BOOLEAN, required = false)
    private boolean pendienteMensajesSupervisor = false;

	/**
	 * Constructor
	 *
	 * @param elem
	 * @param urlBase
	 * @param idioma
	 * @param hateoasEnabled
	 */
	public Procedimientos(final ProcedimientoDTO elem, final String urlBase, final String idioma,
			final boolean hateoasEnabled) {
//		super(elem, urlBase, idioma, hateoasEnabled);
		if(elem != null) {
			this.codigo = elem.getCodigo();
			this.comun = elem.getComun();
			this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA().toString();
			this.codigoWF = elem.getCodigoWF();
			this.datosPersonalesDestinatario = elem.getDatosPersonalesDestinatario() == null ? null : elem.getDatosPersonalesDestinatario().getTraduccion(idioma);
			this.datosPersonalesFinalidad = elem.getDatosPersonalesFinalidad() == null ? null : elem.getDatosPersonalesFinalidad().getTraduccion(idioma);
			this.datosPersonalesLegitimacion = elem.getDatosPersonalesLegitimacion() == null ? null : elem.getDatosPersonalesLegitimacion().getCodigo();
			this.destinatarios = elem.getDestinatarios() == null ? null : elem.getDestinatarios().getTraduccion(idioma);
			this.estado = elem.getEstado() == null ? null : elem.getEstado().name();
			this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
			this.fechaActualizacion = elem.getFechaActualizacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaActualizacion());
			this.fechaCaducidad = elem.getFechaCaducidad() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaCaducidad());
			this.fechaSIA = elem.getFechaSIA() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaSIA());
			this.habilitadoApoderado = elem.isHabilitadoApoderado();
			this.habilitadoFuncionario = elem.getHabilitadoFuncionario();
			this.iniciacion = elem.getIniciacion() == null ? null : elem.getIniciacion().getCodigo();
			this.interno = elem.isInterno();
			this.lopdDerechos = elem.getLopdDerechos() == null ? null : elem.getLopdDerechos().getTraduccion(idioma);
			this.lopdDestinatario = elem.getLopdDestinatario() == null ? null : elem.getLopdDestinatario().getTraduccion(idioma);
			this.lopdFinalidad = elem.getLopdFinalidad() == null ? null : elem.getLopdFinalidad().getTraduccion(idioma);
			this.lopdInfoAdicional = elem.getLopdInfoAdicional() == null ? null : elem.getLopdInfoAdicional().getTraduccion(idioma);
			this.lopdResponsable = elem.getLopdResponsable();
			this.mensajes = elem.getMensajes();
			this.nombreProcedimientoWorkFlow = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccion(idioma);
			this.observaciones = elem.getObservaciones() == null ? null : elem.getObservaciones().getTraduccion(idioma);
			this.pendienteIndexar = elem.isPendienteIndexar();
			this.pendienteMensajesGestor = elem.isPendienteMensajesGestor();
			this.pendienteMensajesSupervisor = elem.isPendienteMensajesSupervisor();
			this.publicado = elem.isPublicado();
			this.requisitos = elem.getRequisitos() == null ? null : elem.getRequisitos().getTraduccion(idioma);
			this.responsable = elem.getResponsable();
			this.responsableEmail = elem.getResponsableEmail();
			this.responsableTelefono = elem.getResponsableTelefono();
			this.silencio = elem.getSilencio() == null ? null : elem.getSilencio().getCodigo();
			this.terminoResolucion = elem.getTerminoResolucion() == null ? null : elem.getTerminoResolucion().getTraduccion(idioma);
			this.tieneTasa = elem.isTieneTasa();
			this.tipo = elem.getTipo();
			this.tipoProcedimiento = elem.getTipoProcedimiento() == null ? null : elem.getTipoProcedimiento().getCodigo();
			this.tipoVia = elem.getTipoVia() == null ? null : elem.getTipoVia().getCodigo();
			this.uaInstructor = elem.getUaInstructor() == null ? null : elem.getUaInstructor().getCodigo();
			this.uaResponsable = elem.getUaResponsable() == null ? null : elem.getUaResponsable().getCodigo();
			this.usuarioAuditoria = elem.getUsuarioAuditoria();
			this.workflow = elem.getWorkflow() == null ? null : elem.getWorkflow().name();
		}


//		try {
//			// copiamos los datos que no tienen la misma estructura:
//			if (((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getSilencio() != null) {
//				this.silencio = new Silencis(((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getSilencio(), urlBase,
//						idioma, hateoasEnabled);
//			}
//
//			// copiamos los datos que no tienen la misma estructura:
//			if (((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getIniciacion() != null) {
//				this.iniciacion = new Iniciacions(((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getIniciacion(),
//						urlBase, idioma, hateoasEnabled);
//			}
//
//			// copiamos los datos que no tienen la misma estructura:
//			if (((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getLopdLegitimacion() != null) {
//				this.lopdLegitimacion = new LopdLegitimacion();
//				this.lopdLegitimacion.setIdentificador(
//						((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getLopdLegitimacion().getIdentificador());
//				this.lopdLegitimacion.setNombre(
//						((org.ibit.rol.sac.model.TraduccionLopdLegitimacion) ((org.ibit.rol.sac.model.ProcedimientoLocal) elem)
//								.getLopdLegitimacion().getTraduccion(idioma)).getNombre());
//			}
//
//			if (((org.ibit.rol.sac.model.ProcedimientoLocal) elem).isComun()) {
//				this.lopdResponsable = System.getProperty("es.caib.rolsac.lopd.responsable.comun." + idioma);
//			} else {
//				final String lopdResponsable = getUAByDir3(
//						((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getServicioResponsable(), idioma);
//				if (lopdResponsable != null) {
//					this.lopdResponsable = lopdResponsable;
//				}
//			}
//
//		} catch (final Exception e) {
//
//		}
	}

	public Procedimientos() {
		super();
	}

	public Procedimientos(final ProcedimientoGridDTO elem, final String urlBase, final String idioma,
			final boolean hateoasEnabled) {
//		super(elem, urlBase, idioma, hateoasEnabled);
		if(elem != null) {
			this.codigo = elem.getCodigo();
			this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA().toString();
			this.estado = elem.getEstado();
			this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA().toString();
			this.tipo = elem.getTipo();
			this.nombre = elem.getNombre();
			this.codigoWF = elem.getCodigoWFPub();
		}
	}

	@Override
	public void generaLinks(final String urlBase) {
//		linkServicioResponsable = this.generaLink(this.servicioResponsable, Constantes.ENTIDADUA, Constantes.URLUA,
//				urlBase, null);
//		linkUnidadAdministrativa = this.generaLink(this.unidadAdministrativa, Constantes.ENTIDADUA, Constantes.URLUA,
//				urlBase, null);
//		linkOrganResolutori = this.generaLink(this.organResolutori, Constantes.ENTIDADUA, Constantes.URLUA, urlBase,
//				null);
//		linkFamilia = this.generaLink(this.familia, Constantes.ENTIDADFAMILIA, Constantes.URLFAMILIA, urlBase, null);
//		linkLopdInfoAdicional = this.generaLinkArchivo(this.lopdInfoAdicional, urlBase, null);

	}

	@Override
	protected void addSetersInvalidos() {
		if (!SETTERS_INVALIDS.contains("setIniciacion")) {
			SETTERS_INVALIDS.add("setIniciacion");
		}

		if (!SETTERS_INVALIDS.contains("setSilencio")) {
			SETTERS_INVALIDS.add("setSilencio");
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
	 * @return the indicador
	 */
	public boolean isIndicador() {
		return indicador;
	}

	/**
	 * @param indicador the indicador to set
	 */
	public void setIndicador(final boolean indicador) {
		this.indicador = indicador;
	}

	/**
	 * @return the dirElectronica
	 */
	public java.lang.String getDirElectronica() {
		return dirElectronica;
	}

	/**
	 * @param dirElectronica the dirElectronica to set
	 */
	public void setDirElectronica(final java.lang.String dirElectronica) {
		this.dirElectronica = dirElectronica;
	}

	/**
	 * @return the lugar
	 */
	public java.lang.String getLugar() {
		return lugar;
	}

	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(final java.lang.String lugar) {
		this.lugar = lugar;
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

	/**
	 * @return the notificacion
	 */
	public java.lang.String getNotificacion() {
		return notificacion;
	}

	/**
	 * @param notificacion the notificacion to set
	 */
	public void setNotificacion(final java.lang.String notificacion) {
		this.notificacion = notificacion;
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
	 * @return the lopdCabecera
	 */
	public String getLopdCabecera() {
		return lopdCabecera;
	}

	/**
	 * @param lopdCabecera the lopdCabecera to set
	 */
	public void setLopdCabecera(final String lopdCabecera) {
		this.lopdCabecera = lopdCabecera;
	}

	/**
	 * @return the plazos
	 */
	public java.lang.String getPlazos() {
		return plazos;
	}

	/**
	 * @param plazos the plazos to set
	 */
	public void setPlazos(final java.lang.String plazos) {
		this.plazos = plazos;
	}

	/**
	 * @return the recursos
	 */
	public java.lang.String getRecursos() {
		return recursos;
	}

	/**
	 * @param recursos the recursos to set
	 */
	public void setRecursos(final java.lang.String recursos) {
		this.recursos = recursos;
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
	public java.lang.String getResolucion() {
		return resolucion;
	}

	/**
	 * @param resolucion the resolucion to set
	 */
	public void setResolucion(final java.lang.String resolucion) {
		this.resolucion = resolucion;
	}

	/**
	 * @return the responsable
	 */
	public java.lang.String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(final java.lang.String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the resumen
	 */
	public java.lang.String getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(final java.lang.String resumen) {
		this.resumen = resumen;
	}

	/**
	 * @return the signatura
	 */
	public java.lang.String getSignatura() {
		return signatura;
	}

	/**
	 * @param signatura the signatura to set
	 */
	public void setSignatura(final java.lang.String signatura) {
		this.signatura = signatura;
	}

	/**
	 * @return the taxa
	 */
	public boolean isTaxa() {
		return taxa;
	}

	/**
	 * @param taxa the taxa to set
	 */
	public void setTaxa(final boolean taxa) {
		this.taxa = taxa;
	}

	/**
	 * @return the url
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final java.lang.String url) {
		this.url = url;
	}

	/**
	 * @return the validacion
	 */
	public java.lang.Integer getValidacion() {
		return validacion;
	}

	/**
	 * @param validacion the validacion to set
	 */
	public void setValidacion(final java.lang.Integer validacion) {
		this.validacion = validacion;
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

//	/**
//	 * @return the linkServicioResponsable
//	 */
//	public Link getLinkServicioResponsable() {
//		return linkServicioResponsable;
//	}

//	/**
//	 * @param linkServicioResponsable the linkServicioResponsable to set
//	 */
//	public void setLinkServicioResponsable(final Link linkServicioResponsable) {
//		this.linkServicioResponsable = linkServicioResponsable;
//	}

	/**
	 * @return the servicioResponsable
	 */
	@XmlTransient
	public Long getServicioResponsable() {
		return servicioResponsable;
	}

	/**
	 * @param servicioResponsable the servicioResponsable to set
	 */
	public void setServicioResponsable(final Long servicioResponsable) {
		this.servicioResponsable = servicioResponsable;
	}

//	/**
//	 * @return the linkUnidadAdministrativa
//	 */
//	public Link getLinkUnidadAdministrativa() {
//		return linkUnidadAdministrativa;
//	}
//
//	/**
//	 * @param linkUnidadAdministrativa the linkUnidadAdministrativa to set
//	 */
//	public void setLinkUnidadAdministrativa(final Link linkUnidadAdministrativa) {
//		this.linkUnidadAdministrativa = linkUnidadAdministrativa;
//	}

	/**
	 * @return the unidadAdministrativa
	 */
	@XmlTransient
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	/**
	 * @param unidadAdministrativa the unidadAdministrativa to set
	 */
	public void setUnidadAdministrativa(final Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

//	/**
//	 * @return the linkOrganResolutori
//	 */
//	public Link getLinkOrganResolutori() {
//		return linkOrganResolutori;
//	}

//	/**
//	 * @param linkOrganResolutori the linkOrganResolutori to set
//	 */
//	public void setLinkOrganResolutori(final Link linkOrganResolutori) {
//		this.linkOrganResolutori = linkOrganResolutori;
//	}

	/**
	 * @return the organResolutori
	 */
	@XmlTransient
	public Long getOrganResolutori() {
		return organResolutori;
	}

	/**
	 * @param organResolutori the organResolutori to set
	 */
	public void setOrganResolutori(final Long organResolutori) {
		this.organResolutori = organResolutori;
	}

//	/**
//	 * @return the linkFamilia
//	 */
//	public Link getLinkFamilia() {
//		return linkFamilia;
//	}

//	/**
//	 * @param linkFamilia the linkFamilia to set
//	 */
//	public void setLinkFamilia(final Link linkFamilia) {
//		this.linkFamilia = linkFamilia;
//	}

	/**
	 * @return the familia
	 */
	@XmlTransient
	public Long getFamilia() {
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia(final Long familia) {
		this.familia = familia;
	}

	/**
	 * @return the tramite
	 */
	public java.lang.String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(final java.lang.String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the version
	 */
	public java.lang.Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(final java.lang.Long version) {
		this.version = version;
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

	/**
	 * @return the lopdInfoAdicional
	 */
	public String getLopdInfoAdicional() {
		return lopdInfoAdicional;
	}

	/**
	 * @param lopdInfoAdicional the lopdInfoAdicional to set
	 */
	public void setLopdInfoAdicional(final String lopdInfoAdicional) {
		this.lopdInfoAdicional = lopdInfoAdicional;
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

	/**
	 * @return the disponibleApoderadoHabilitado
	 */
	public boolean isDisponibleApoderadoHabilitado() {
		return disponibleApoderadoHabilitado;
	}

	/**
	 * @param disponibleApoderadoHabilitado the disponibleApoderadoHabilitado to set
	 */
	public void setDisponibleApoderadoHabilitado(boolean disponibleApoderadoHabilitado) {
		this.disponibleApoderadoHabilitado = disponibleApoderadoHabilitado;
	}

	/**
	 * @return the disponibleFuncionarioHabilitado
	 */
	public boolean isDisponibleFuncionarioHabilitado() {
		return disponibleFuncionarioHabilitado;
	}

	/**
	 * @param disponibleFuncionarioHabilitado the disponibleFuncionarioHabilitado to
	 *                                        set
	 */
	public void setDisponibleFuncionarioHabilitado(boolean disponibleFuncionarioHabilitado) {
		this.disponibleFuncionarioHabilitado = disponibleFuncionarioHabilitado;
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

	public Long getIniciacion() {
		return iniciacion;
	}

	public void setIniciacion(Long iniciacion) {
		this.iniciacion = iniciacion;
	}

	public Long getSilencio() {
		return silencio;
	}

	public void setSilencio(Long silencio) {
		this.silencio = silencio;
	}

	public Long getTipoProcedimiento() {
		return tipoProcedimiento;
	}

	public void setTipoProcedimiento(Long tipoProcedimiento) {
		this.tipoProcedimiento = tipoProcedimiento;
	}

	public Long getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(Long tipoVia) {
		this.tipoVia = tipoVia;
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

	public String getDatosPersonalesFinalidad() {
		return datosPersonalesFinalidad;
	}

	public void setDatosPersonalesFinalidad(String datosPersonalesFinalidad) {
		this.datosPersonalesFinalidad = datosPersonalesFinalidad;
	}

	public String getDatosPersonalesDestinatario() {
		return datosPersonalesDestinatario;
	}

	public void setDatosPersonalesDestinatario(String datosPersonalesDestinatario) {
		this.datosPersonalesDestinatario = datosPersonalesDestinatario;
	}

	public String getTerminoResolucion() {
		return terminoResolucion;
	}

	public void setTerminoResolucion(String terminoResolucion) {
		this.terminoResolucion = terminoResolucion;
	}

	public String getMensajes() {
		return mensajes;
	}

	public void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
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

}
