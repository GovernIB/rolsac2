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
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;

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
	@Schema(description = "observaciones", type = SchemaType.STRING, required = false)
	private String observaciones;

	/**  **/
	@Schema(description = "requisitos", type = SchemaType.STRING, required = false)
	private String requisitos;

	/**  **/
	@Schema(description = "codigoSIA", type = SchemaType.INTEGER, required = false)
	private Integer codigoSIA;

	/**  **/
	@Schema(description = "estadoSIA", type = SchemaType.BOOLEAN, required = false)
	private boolean estadoSIA;

	/**  **/
	@Schema(description = "fechaSIA", required = false)
	private Calendar fechaSIA;

	@Schema(description = "responsable", type = SchemaType.STRING, required = false)
	private String responsable;
	/*
	 * private java.lang.String resultat; private boolean ventanillaUnica;
	 */

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** servicioResponsable **/
	@Schema(description = "linkUnidadAdministrativaResponsable", required = false)
	private Link linkUnidadAdministrativaResponsable;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long uaResponsable;

	/** unidadAdministrativa **/
	@Schema(description = "linkUnidadAdministrativaCompetente", required = false)
	private Link linkUnidadAdministrativaCompetente;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long uaCompetente;

	/** organResolutori **/
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

	/** es comun **/
	@Schema(description = "comun", type = SchemaType.BOOLEAN, required = false)
	private int comun;

//	@Schema
//	private LopdLegitimacion lopdLegitimacion;

	/** Info Adicional **/
	@Schema(description = "linkLopdInfoAdicional", required = false)
	private Link linkLopdInfoAdicional;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long lopdInfoAdicional;

	@Schema(description = "lopdResponsable", type = SchemaType.STRING, required = false)
	private String lopdResponsable;

	@Schema(description = "lopdFinalidad", type = SchemaType.STRING, required = false)
	private String lopdFinalidad;

	@Schema(description = "lopdDestinatario", type = SchemaType.STRING, required = false)
	private String lopdDestinatario;

	@Schema(description = "lopdDerechos", type = SchemaType.STRING, required = false)
	private String lopdDerechos;

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
	@Schema(description = "interno", type = SchemaType.BOOLEAN, required = false)
    private boolean interno;
	@Schema(description = "publicado", type = SchemaType.BOOLEAN, required = false)
    private boolean publicado;

	@Schema(description = "datosPersonalesLegitimacion", type = SchemaType.INTEGER, required = false)
    private Long datosPersonalesLegitimacion;
	@Schema(description = "iniciacion", required = false)
    private Inicio iniciacion;

	@Schema(description = "silencio", required = false)
    private Silencio silencio;

	@Schema(description = "tipoProcedimiento", required = false)
    private TipoProcedimiento tipoProcedimiento;
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
	@Schema(description = "tramitElectronica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitElectronica;
	@Schema(description = "tramitPresencial", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitPresencial;
	@Schema(description = "tramitTelefonica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitTelefonica;
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
	@Schema(description = "activoLOPD", type = SchemaType.BOOLEAN, required = false)
    private boolean activoLOPD = false;

//	/**
//	 * Constructor
//	 *
//	 * @param elem
//	 * @param urlBase
//	 * @param idioma
//	 * @param hateoasEnabled
//	 */
//	public Procedimientos(final ProcedimientoDTO elem, final String urlBase, final String idioma,
//			final boolean hateoasEnabled) {
////		super(elem, urlBase, idioma, hateoasEnabled);
//		if(elem != null) {
//			this.codigo = elem.getCodigo();
//			this.comun = elem.getComun();
//			this.codigoSIA = elem.getCodigoSIA() == null ? null : elem.getCodigoSIA();
//			this.codigoWF = elem.getCodigoWF();
//			this.datosPersonalesDestinatario = elem.getDatosPersonalesDestinatario() == null ? null : elem.getDatosPersonalesDestinatario().getTraduccion(idioma);
//			this.datosPersonalesFinalidad = elem.getDatosPersonalesFinalidad() == null ? null : elem.getDatosPersonalesFinalidad().getTraduccion(idioma);
//			this.datosPersonalesLegitimacion = elem.getDatosPersonalesLegitimacion() == null ? null : elem.getDatosPersonalesLegitimacion().getCodigo();
//			this.destinatarios = elem.getDestinatarios() == null ? null : elem.getDestinatarios().getTraduccion(idioma);
//			this.estado = elem.getEstado() == null ? null : elem.getEstado();
//			this.estadoSIA = elem.getEstadoSIA() == null ? null : elem.getEstadoSIA();
//			this.fechaActualizacion = elem.getFechaActualizacion() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaActualizacion());
//			this.fechaCaducidad = elem.getFechaCaducidad() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaCaducidad());
//			this.fechaSIA = elem.getFechaSIA() == null ? null : Utiles.convertDateToJavaUtilCalendar(elem.getFechaSIA());
//			this.habilitadoApoderado = elem.isHabilitadoApoderado();
//			this.habilitadoFuncionario = elem.getHabilitadoFuncionario();
//			this.iniciacion = elem.getIniciacion() == null ? null : new Inicio(elem.getIniciacion(), urlBase, idioma, hateoasEnabled);
//			this.interno = elem.isInterno();
//			this.lopdDerechos = elem.getLopdDerechos() == null ? null : elem.getLopdDerechos().getTraduccion(idioma);
//			this.lopdDestinatario = elem.getLopdDestinatario() == null ? null : elem.getLopdDestinatario().getTraduccion(idioma);
//			this.lopdFinalidad = elem.getLopdFinalidad() == null ? null : elem.getLopdFinalidad().getTraduccion(idioma);
//			this.lopdInfoAdicional = elem.getLopdInfoAdicional() == null ? null : elem.getLopdInfoAdicional().getCodigo();
//			this.lopdResponsable = elem.getLopdResponsable();
////			this.mensajes = elem.getMensajes();
//			this.nombreProcedimientoWorkFlow = elem.getNombreProcedimientoWorkFlow() == null ? null : elem.getNombreProcedimientoWorkFlow().getTraduccion(idioma);
//			this.observaciones = elem.getObservaciones() == null ? null : elem.getObservaciones().getTraduccion(idioma);
////			this.pendienteIndexar = elem.isPendienteIndexar();
////			this.pendienteMensajesGestor = elem.isPendienteMensajesGestor();
////			this.pendienteMensajesSupervisor = elem.isPendienteMensajesSupervisor();
//			this.publicado = elem.isPublicado();
//			this.requisitos = elem.getRequisitos() == null ? null : elem.getRequisitos().getTraduccion(idioma);
//			this.responsable = elem.getResponsable();
//			this.responsableEmail = elem.getResponsableEmail();
//			this.responsableTelefono = elem.getResponsableTelefono();
//			this.silencio = elem.getSilencio() == null ? null : new Silencio(elem.getSilencio(), urlBase, idioma, hateoasEnabled);
//			this.terminoResolucion = elem.getTerminoResolucion() == null ? null : elem.getTerminoResolucion().getTraduccion(idioma);
//			this.tieneTasa = elem.isTieneTasa();
//			this.tipo = elem.getTipo();
//			this.tipoProcedimiento = elem.getTipoProcedimiento() == null ? null : elem.getTipoProcedimiento().getCodigo();
//			this.tipoVia = elem.getTipoVia() == null ? null : elem.getTipoVia().getCodigo();
//			this.uaInstructor = elem.getUaInstructor() == null ? null : elem.getUaInstructor().getCodigo();
//			this.uaResponsable = elem.getUaResponsable() == null ? null : elem.getUaResponsable().getCodigo();
////			this.usuarioAuditoria = elem.getUsuarioAuditoria();
//			this.workflow = elem.getWorkflow() == null ? null : elem.getWorkflow();
//		}


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
//	}

	public Procedimientos() {
		super();
	}

	/*public Procedimientos(final ProcedimientoGridDTO elem, final String urlBase, final String idioma,
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
	}*/

	public Procedimientos(ProcedimientoDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		/*if(nodo != null) {
			this.codigo = nodo.getCodigo();
			this.codigoSIA = nodo.getCodigoSIA() == null ? null : nodo.getCodigoSIA().toString();
			this.estado = nodo.getEstado().toString();
			this.estadoSIA = nodo.getEstadoSIA() == null ? null : nodo.getEstadoSIA().toString();
			this.tipo = nodo.getTipo();
			this.nombre = nodo.getNombreProcedimientoWorkFlow().getTraduccion("ca");
			this.codigoWF = nodo.getCodigoWF();
		}*/
		super(nodo, urlBase, idioma, hateoasEnabled);

		try {
			// copiamos los datos que no tienen la misma estructura:
			if (nodo.getSilencio() != null) {
				this.silencio = new Silencio(nodo.getSilencio(), urlBase, idioma, hateoasEnabled);
			}

			// copiamos los datos que no tienen la misma estructura:
			if (nodo.getIniciacion() != null) {
				this.iniciacion = new Inicio(nodo.getIniciacion(), urlBase, idioma, hateoasEnabled);
			}

			if(nodo.getTipoProcedimiento() !=null) {
				this.tipoProcedimiento = new TipoProcedimiento(nodo.getTipoProcedimiento(), urlBase, idioma, hateoasEnabled);
			}
			// copiamos los datos que no tienen la misma estructura:
			/*if (((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getLopdLegitimacion() != null) {
				this.lopdLegitimacion = new LopdLegitimacion();
				this.lopdLegitimacion.setIdentificador(
						((org.ibit.rol.sac.model.ProcedimientoLocal) elem).getLopdLegitimacion().getIdentificador());
				this.lopdLegitimacion.setNombre(
						((org.ibit.rol.sac.model.TraduccionLopdLegitimacion) ((org.ibit.rol.sac.model.ProcedimientoLocal) elem)
								.getLopdLegitimacion().getTraduccion(idioma)).getNombre());
			}

			if (nodo.getLopdResponsable().isComun()) {
				this.lopdResponsable = System.getProperty("es.caib.rolsac.lopd.responsable.comun." + idioma);
			} else {
				final String lopdResponsable = getUAByDir3(
						nodo.getLopdResponsable(), idioma);
				if (lopdResponsable != null) {
					this.lopdResponsable = lopdResponsable;
				}
			}*/


		} catch (final Exception e) {

		}
	}


	@Override
	public void generaLinks(final String urlBase) {
		linkUnidadAdministrativaResponsable = this.generaLink(this.uaResponsable, Constantes.ENTIDAD_UA, Constantes.URL_UA,
				urlBase, null);
		linkUnidadAdministrativaInstructora = this.generaLink(this.uaInstructor, Constantes.ENTIDAD_UA, Constantes.URL_UA,
				urlBase, null);
		linkUnidadAdministrativaCompetente = this.generaLink(this.uaCompetente, Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase,
				null);
		linkLopdInfoAdicional = this.generaLinkArchivo(this.lopdInfoAdicional, urlBase, null);

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
	public boolean getEstadoSIA() {
		return estadoSIA;
	}

	/**
	 * @param estadoSIA the estadoSIA to set
	 */
	public void setEstadoSIA(final boolean estadoSIA) {
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
//	 * @return the linkUnidadAdministrativaResponsable
//	 */
//	public Link getlinkUnidadAdministrativaResponsable() {
//		return linkUnidadAdministrativaResponsable;
//	}

//	/**
//	 * @param linkUnidadAdministrativaResponsable the linkUnidadAdministrativaResponsable to set
//	 */
//	public void setlinkUnidadAdministrativaResponsable(final Link linkUnidadAdministrativaResponsable) {
//		this.linkUnidadAdministrativaResponsable = linkUnidadAdministrativaResponsable;
//	}


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

//	/**
//	 * @return the linkUnidadAdministrativaInstructora
//	 */
//	public Link getlinkUnidadAdministrativaInstructora() {
//		return linkUnidadAdministrativaInstructora;
//	}

//	/**
//	 * @param linkUnidadAdministrativaInstructora the linkUnidadAdministrativaInstructora to set
//	 */
//	public void setlinkUnidadAdministrativaInstructora(final Link linkUnidadAdministrativaInstructora) {
//		this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
//	}


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
	public Long getLopdInfoAdicional() {
		return lopdInfoAdicional;
	}

	/**
	 * @param lopdInfoAdicional the lopdInfoAdicional to set
	 */
	public void setLopdInfoAdicional(final Long lopdInfoAdicional) {
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

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public void setLinkUnidadAdministrativaInstructora(Link linkUnidadAdministrativaInstructora) {
		this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
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

}
