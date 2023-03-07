package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
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
	@Schema(description = "codigo", required = false)
	private long codigo;

	/**  **/
	@Schema(description = "destinatarios", required = false)
	private java.lang.String destinatarios;

	/**  **/
	@Schema(description = "fechaActualizacion", required = false)
	private java.util.Calendar fechaActualizacion;

	/**  **/
	@Schema(description = "fechaCaducidad", required = false)
	private java.util.Calendar fechaCaducidad;

	/**  **/
	@Schema(description = "fechaPublicacion", required = false)
	private java.util.Calendar fechaPublicacion;

	/**  **/
	@Schema(description = "indicador", required = false)
	private boolean indicador;

	/**  **/
	@Schema(description = "dirElectronica", required = false)
	private java.lang.String dirElectronica;

	/**  **/
	@Schema(description = "lugar", required = false)
	private java.lang.String lugar;

	/**  **/
	@Schema(description = "nombre", required = false)
	private java.lang.String nombre;

	/**  **/
	@Schema(description = "notificacion", required = false)
	private java.lang.String notificacion;

	/**  **/
	@Schema(description = "observaciones", required = false)
	private java.lang.String observaciones;

	/**  **/
	@Schema(description = "plazos", required = false)
	private java.lang.String plazos;

	/**  **/
	@Schema(description = "recursos", required = false)
	private java.lang.String recursos;

	/**  **/
	@Schema(description = "requisitos", required = false)
	private java.lang.String requisitos;

	/**  **/
	@Schema(description = "resolucion", required = false)
	private java.lang.String resolucion;

	/**  **/
	@Schema(description = "responsable", required = false)
	private java.lang.String responsable;

	/**  **/
	@Schema(description = "resumen", required = false)
	private java.lang.String resumen;

	/**  **/
	@Schema(description = "signatura", required = false)
	private java.lang.String signatura;

	/**  **/
	@Schema(description = "signatura", required = false)
	private boolean taxa;

	/**  **/
	@Schema(description = "url", required = false)
	private java.lang.String url;

	/**  **/
	@Schema(description = "validacion", required = false)
	private java.lang.Integer validacion;

	/**  **/
	@Schema(description = "codigoSIA", required = false)
	private java.lang.String codigoSIA;

	/**  **/
	@Schema(description = "estadoSIA", required = false)
	private java.lang.String estadoSIA;

	/**  **/
	@Schema(description = "fechaSIA", required = false)
	private java.util.Calendar fechaSIA;

	@Schema(description = "tramite", required = false)
	private java.lang.String tramite;

	@Schema(description = "version", required = false)
	private java.lang.Long version;

	/*
	 * private java.lang.String resultat; private boolean ventanillaUnica;
	 */

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** servicioResponsable **/
	@Schema(description = "linkServicioResponsable", required = false)
	private Link linkServicioResponsable;
	@Schema(hidden = true)
	@XmlTransient
	private Long servicioResponsable;

	/** unidadAdministrativa **/
	@Schema(description = "linkUnidadAdministrativa", required = false)
	private Link linkUnidadAdministrativa;
	@Schema(hidden = true)
	@XmlTransient
	private Long unidadAdministrativa;

	/** organResolutori **/
	@Schema(description = "linkOrganResolutori", required = false)
	private Link linkOrganResolutori;
	@Schema(hidden = true)
	@XmlTransient
	private Long organResolutori;

	/** familia **/
	@Schema(description = "linkFamilia", required = false)
	private Link linkFamilia;
	@Schema(hidden = true)
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
	@Schema(description = "comun", required = false)
	private boolean comun;

//	@Schema
//	private LopdLegitimacion lopdLegitimacion;

	/** Info Adicional **/
	@Schema(description = "linkLopdInfoAdicional", required = false)
	private Link linkLopdInfoAdicional;
	@Schema(hidden = true)
	@XmlTransient
	private java.lang.Long lopdInfoAdicional;

	@Schema(description = "lopdResponsable", required = false)
	private String lopdResponsable;

	@Schema(description = "lopdFinalidad", required = false)
	private String lopdFinalidad;

	@Schema(description = "lopdDestinatario", required = false)
	private String lopdDestinatario;

	@Schema(description = "lopdDerechos", required = false)
	private String lopdDerechos;

	@Schema(description = "lopdCabecera", required = false)
	private String lopdCabecera;

	@Schema(description = "disponibleApoderadoHabilitado", required = false)
	private boolean disponibleApoderadoHabilitado;

	@Schema(description = "disponibleFuncionarioHabilitado", required = false)
	private boolean disponibleFuncionarioHabilitado;

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
		super(elem, urlBase, idioma, hateoasEnabled);

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
		super(elem, urlBase, idioma, hateoasEnabled);
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

	public static Procedimientos valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<Procedimientos> typeRef = new TypeReference<Procedimientos>() {
		};
		Procedimientos obj;
		try {
			obj = (Procedimientos) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return obj;
	}

	public String toJson() {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
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

	/**
	 * @return the linkServicioResponsable
	 */
	public Link getLinkServicioResponsable() {
		return linkServicioResponsable;
	}

	/**
	 * @param linkServicioResponsable the linkServicioResponsable to set
	 */
	public void setLinkServicioResponsable(final Link linkServicioResponsable) {
		this.linkServicioResponsable = linkServicioResponsable;
	}

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

	/**
	 * @return the linkUnidadAdministrativa
	 */
	public Link getLinkUnidadAdministrativa() {
		return linkUnidadAdministrativa;
	}

	/**
	 * @param linkUnidadAdministrativa the linkUnidadAdministrativa to set
	 */
	public void setLinkUnidadAdministrativa(final Link linkUnidadAdministrativa) {
		this.linkUnidadAdministrativa = linkUnidadAdministrativa;
	}

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

	/**
	 * @return the linkOrganResolutori
	 */
	public Link getLinkOrganResolutori() {
		return linkOrganResolutori;
	}

	/**
	 * @param linkOrganResolutori the linkOrganResolutori to set
	 */
	public void setLinkOrganResolutori(final Link linkOrganResolutori) {
		this.linkOrganResolutori = linkOrganResolutori;
	}

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

	/**
	 * @return the linkFamilia
	 */
	public Link getLinkFamilia() {
		return linkFamilia;
	}

	/**
	 * @param linkFamilia the linkFamilia to set
	 */
	public void setLinkFamilia(final Link linkFamilia) {
		this.linkFamilia = linkFamilia;
	}

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
	public boolean isComun() {
		return comun;
	}

	/**
	 * @param comun the comun to set
	 */
	public void setComun(final boolean comun) {
		this.comun = comun;
	}

	/**
	 * @return the linkLopdInfoAdicional
	 */
	public Link getLinkLopdInfoAdicional() {
		return linkLopdInfoAdicional;
	}

	/**
	 * @param linkLopdInfoAdicional the linkLopdInfoAdicional to set
	 */
	public void setLinkLopdInfoAdicional(final Link linkLopdInfoAdicional) {
		this.linkLopdInfoAdicional = linkLopdInfoAdicional;
	}

	/**
	 * @return the lopdInfoAdicional
	 */
	public java.lang.Long getLopdInfoAdicional() {
		return lopdInfoAdicional;
	}

	/**
	 * @param lopdInfoAdicional the lopdInfoAdicional to set
	 */
	public void setLopdInfoAdicional(final java.lang.Long lopdInfoAdicional) {
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

}
