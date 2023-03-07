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
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.ServicioGridDTO;

/**
 * Serveis.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Servicios", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_SERVICIOS)
public class Servicios extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(Servicios.class);

	/** codigo **/
	@Schema(description = "codigo", required = false)
	private long codigo;

	@Schema(description = "codigoServicio", required = false)
	private java.lang.String codigoServicio; // en el modelo se llama codigo

	@Schema(description = "codigoSIA", required = false)
	private java.lang.String codigoSIA;

	@Schema(description = "correo", required = false)
	private java.lang.String correo;

	@Schema(description = "destinatarios", required = false)
	private java.lang.String destinatarios;

	@Schema(description = "estadoSIA", required = false)
	private java.lang.String estadoSIA;

	@Schema(description = "fechaActualizacion", required = false)
	private java.util.Calendar fechaActualizacion;

	@Schema(description = "fechaDespublicacion", required = false)
	private java.util.Calendar fechaDespublicacion;

	@Schema(description = "fechaPublicacion", required = false)
	private java.util.Calendar fechaPublicacion;

	@Schema(description = "fechaSIA", required = false)
	private java.util.Calendar fechaSIA;

	@Schema(description = "id", required = false)
	private java.lang.Long id;

	@Schema(description = "nombre", required = false)
	private java.lang.String nombre;

	@Schema(description = "nombreResponsable", required = false)
	private java.lang.String nombreResponsable;

	@Schema(description = "objeto", required = false)
	private java.lang.String objeto;

	@Schema(description = "observaciones", required = false)
	private java.lang.String observaciones;

	@Schema(description = "requisitos", required = false)
	private java.lang.String requisitos;

	@Schema(description = "telefono", required = false)
	private java.lang.String telefono;

	@Schema(description = "urlTramiteExterno", required = false)
	private java.lang.String urlTramiteExterno;

	@Schema(description = "tramiteId", required = false)
	private java.lang.String tramiteId;

	@Schema(description = "tasaUrl", required = false)
	private java.lang.String tasaUrl;

	@Schema(description = "tramiteVersion", required = false)
	private java.lang.String tramiteVersion;

//	@Schema(hidden = true)
//	private Plataformas plataforma;

	@Schema(description = "parametros", required = false)
	private java.lang.String parametros;

	@Schema(description = "telematico", required = false)
	private boolean telematico;

	@Schema(description = "validacion", required = false)
	private java.lang.Integer validacion;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/**  **/
	@Schema(description = "linkServicioResponsable", required = false)
	private Link linkServicioResponsable;

	@Schema(hidden = true)
	@XmlTransient
	private Long servicioResponsable;

	@Schema(description = "linkOrganoInstructor", required = false)
	private Link linkOrganoInstructor;

	@Schema(hidden = true)
	@XmlTransient
	private Long organoInstructor;

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

	/**
	 * Constructor
	 *
	 * @param elem
	 * @param urlBase
	 * @param idioma
	 * @param hateoasEnabled
	 */
	public Servicios(final ServicioDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
		super(elem, urlBase, idioma, hateoasEnabled);
		if (elem.getCodigo() != null) {
			this.codigoServicio = elem.getCodigo().toString();
		}

//		try {
//			// copiamos los datos que no tienen la misma estructura:
//			if (elem.getPlataforma() != null) {
//				this.plataforma = new Plataformas(elem.getPlataforma(), urlBase, idioma, hateoasEnabled);
//			}
//
//			// copiamos los datos que no tienen la misma estructura:
//			if (elem.getLopdLegitimacion() != null) {
//				this.lopdLegitimacion = new LopdLegitimacion();
//				this.lopdLegitimacion.setIdentificador(elem.getLopdLegitimacion().getIdentificador());
//				this.lopdLegitimacion.setNombre(((org.ibit.rol.sac.model.TraduccionLopdLegitimacion) elem
//						.getLopdLegitimacion().getTraduccion(idioma)).getNombre());
//			}
//
//			if (elem.isComun()) {
//				this.lopdResponsable = System.getProperty("es.caib.rolsac.lopd.responsable.comun." + idioma);
//			} else {
//				final String lopdResponsable = getUAByDir3(elem.getServicioResponsable(), idioma);
//				if (lopdResponsable != null) {
//					this.lopdResponsable = lopdResponsable;
//				}
//			}
//		} catch (final Exception e) {
//
//		}
	}

	public Servicios() {
		super();
	}

	public Servicios(ServicioGridDTO elem, final String urlBase, final String idioma, final boolean hateoasEnabled) {
		super(elem, urlBase, idioma, hateoasEnabled);
		if (elem.getCodigo() != null) {
			this.codigoServicio = elem.getCodigo().toString();
		}
	}

	@Override
	public void generaLinks(final String urlBase) {
//		linkServicioResponsable = this.generaLink(this.servicioResponsable, Constantes.ENTIDAD_UA, Constantes.URL_UA,
//				urlBase, null);
//		linkOrganoInstructor = this.generaLink(this.organoInstructor, Constantes.ENTIDAD_UA, Constantes.URL_UA,
//				urlBase, null);
//		linkLopdInfoAdicional = this.generaLinkArchivo(this.lopdInfoAdicional, urlBase, null);
	}

	public static Servicios valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<Servicios> typeRef = new TypeReference<Servicios>() {
		};
		Servicios obj;
		try {
			obj = (Servicios) objectMapper.readValue(json, typeRef);
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
	 * @return the codigoServicio
	 */
	public java.lang.String getCodigoServicio() {
		return codigoServicio;
	}

	/**
	 * @param codigoServicio the codigoServicio to set
	 */
	public void setCodigoServicio(final java.lang.String codigoServicio) {
		this.codigoServicio = codigoServicio;
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
	 * @return the correo
	 */
	public java.lang.String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(final java.lang.String correo) {
		this.correo = correo;
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

	/**
	 * @return the fechaDespublicacion
	 */
	public java.util.Calendar getFechaDespublicacion() {
		return fechaDespublicacion;
	}

	/**
	 * @param fechaDespublicacion the fechaDespublicacion to set
	 */
	public void setFechaDespublicacion(final java.util.Calendar fechaDespublicacion) {
		this.fechaDespublicacion = fechaDespublicacion;
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

	/**
	 * @return the nombreResponsable
	 */
	public java.lang.String getNombreResponsable() {
		return nombreResponsable;
	}

	/**
	 * @param nombreResponsable the nombreResponsable to set
	 */
	public void setNombreResponsable(final java.lang.String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

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

	/**
	 * @return the telefono
	 */
	public java.lang.String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(final java.lang.String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the tramiteId
	 */
	public java.lang.String getTramiteId() {
		return tramiteId;
	}

	/**
	 * @param tramiteId the tramiteId to set
	 */
	public void setTramiteId(final java.lang.String tramiteId) {
		this.tramiteId = tramiteId;
	}

	/**
	 * @return the tasaUrl
	 */
	public java.lang.String getTasaUrl() {
		return tasaUrl;
	}

	/**
	 * @param tasaUrl the tasaUrl to set
	 */
	public void setTasaUrl(final java.lang.String tasaUrl) {
		this.tasaUrl = tasaUrl;
	}

	/**
	 * @return the tramiteVersion
	 */
	public java.lang.String getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public void setTramiteVersion(final java.lang.String tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
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
	 * @return the linkOrganoInstructor
	 */
	public Link getLinkOrganoInstructor() {
		return linkOrganoInstructor;
	}

	/**
	 * @param linkOrganoInstructor the linkOrganoInstructor to set
	 */
	public void setLinkOrganoInstructor(final Link linkOrganoInstructor) {
		this.linkOrganoInstructor = linkOrganoInstructor;
	}

	/**
	 * @return the organoInstructor
	 */
	public Long getOrganoInstructor() {
		return organoInstructor;
	}

	/**
	 * @param organoInstructor the organoInstructor to set
	 */
	public void setOrganoInstructor(final Long organoInstructor) {
		this.organoInstructor = organoInstructor;
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
	 * @return the id
	 */
	public java.lang.Long getId() {
		return id;
	}

	/**
	 * @return the telematico
	 */
	public boolean getTelematico() {
		return telematico;
	}

	/**
	 * @param telematico the telematico to set
	 */
	public void setTelematico(final boolean telematico) {
		this.telematico = telematico;
	}

	/**
	 * @return the parametros
	 */
	public java.lang.String getParametros() {
		return parametros;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(final java.lang.String parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the urlTramiteExterno
	 */
	public java.lang.String getUrlTramiteExterno() {
		return urlTramiteExterno;
	}

	/**
	 * @param urlTramiteExterno the urlTramiteExterno to set
	 */
	public void setUrlTramiteExterno(final java.lang.String urlTramiteExterno) {
		this.urlTramiteExterno = urlTramiteExterno;
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

}
