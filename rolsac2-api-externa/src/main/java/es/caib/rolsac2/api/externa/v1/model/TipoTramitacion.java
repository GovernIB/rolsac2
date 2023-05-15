package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;

/**
 * TipoTramitacion.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoTramitacion", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_TRAMITACION)
public class TipoTramitacion extends EntidadBase<TipoTramitacion> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoTramitacion.class);

	/**
	 * Identificador
	 */
	@Schema(description = "tramiteId", name = "tramiteId", type = SchemaType.STRING, required = true)
	private String tramiteId;

	/** enlace. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

    /**
     * Tramitación presencial
     */
	@Schema(description = "tramitPresencial", name = "tramitPresencial", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
	@Schema(description = "tramitElectronica", name = "tramitElectronica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitElectronica;

    /**
     * Tramitacion telefonica
     */
	@Schema(description = "tramitTelefonica", name = "tramitTelefonica", type = SchemaType.BOOLEAN, required = false)
    private boolean tramitTelefonica;

    /**
     * Url
     */
	@Schema(description = "url", name = "url", type = SchemaType.STRING, required = false)
    private String url;

    /**
     * Fase procedimiento
     */
	@Schema(description = "faseProc", name = "faseProc", type = SchemaType.INTEGER, required = false)
    private Integer faseProc;

    /**
     * URL tramitación
     */
	@Schema(description = "urlTramitacion", name = "urlTramitacion", type = SchemaType.STRING, required = false)
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */
	@Schema(description = "link_codPlatTramitacion", required = false)
	private Link link_codPlatTramitacion;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long codPlatTramitacion;

    /**
     * Trámite Versión
     */
	@Schema(description = "tramiteVersion", name = "tramiteVersion", type = SchemaType.INTEGER, required = false)
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
	@Schema(description = "tramiteParametros", name = "tramiteParametros", type = SchemaType.STRING, required = false)
    private String tramiteParametros;

    /**
     * Indica si es una plantilla
     **/
	@Schema(description = "plantilla", name = "plantilla", type = SchemaType.BOOLEAN, required = false)
    private boolean plantilla;

	@Schema(description = "link_entidad", required = false)
	private Link link_entidad;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long entidad;


	public TipoTramitacion(TipoTramitacionDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	public TipoTramitacion() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
				null);
		link_codPlatTramitacion = this.generaLink(this.codPlatTramitacion, Constantes.ENTIDAD_PLATAFORMA, Constantes.URL_PLATAFORMA, urlBase,
				null);
	}

	@Override
	protected void addSetersInvalidos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param enlace the enlace to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getFaseProc() {
		return faseProc;
	}

	public void setFaseProc(Integer faseProc) {
		this.faseProc = faseProc;
	}

	public String getUrlTramitacion() {
		return urlTramitacion;
	}

	public void setUrlTramitacion(String urlTramitacion) {
		this.urlTramitacion = urlTramitacion;
	}

	public Link getLink_codPlatTramitacion() {
		return link_codPlatTramitacion;
	}

	public void setLink_codPlatTramitacion(Link link_codPlatTramitacion) {
		this.link_codPlatTramitacion = link_codPlatTramitacion;
	}

	public Long getCodPlatTramitacion() {
		return codPlatTramitacion;
	}

	public void setCodPlatTramitacion(Long codPlatTramitacion) {
		this.codPlatTramitacion = codPlatTramitacion;
	}

	public Integer getTramiteVersion() {
		return tramiteVersion;
	}

	public void setTramiteVersion(Integer tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	public String getTramiteParametros() {
		return tramiteParametros;
	}

	public void setTramiteParametros(String tramiteParametros) {
		this.tramiteParametros = tramiteParametros;
	}

	public boolean isPlantilla() {
		return plantilla;
	}

	public void setPlantilla(boolean plantilla) {
		this.plantilla = plantilla;
	}

	public Link getLink_entidad() {
		return link_entidad;
	}

	public void setLink_entidad(Link link_entidad) {
		this.link_entidad = link_entidad;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}
}
