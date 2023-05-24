package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

/**
 * Unidad Administrativa.
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "UnidadAdministrativa", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_UA)
public class UnidadAdministrativa extends EntidadBase<UnidadAdministrativaDTO> {

	private static final Logger LOG = LoggerFactory.getLogger(UnidadAdministrativa.class);

	/** padre **/
	@Schema(description = "link_padre", required = false)
	private Link link_padre;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long padre;

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	/** codigoDIR3 **/
	@Schema(description = "codigoDIR3", type = SchemaType.STRING, required = false)
	private String codigoDIR3;

	@Schema(description = "link_entidad", required = false)
	private Link link_entidad;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long entidad;

	/** abreviatura. **/
	@Schema(description = "abreviatura", type = SchemaType.STRING, required = false)
	private String abreviatura;

	/** dominio **/
	@Schema(description = "dominio", type = SchemaType.STRING, required = false)
	private String dominio;

	/** email **/
	@Schema(description = "email", type = SchemaType.STRING, required = false)
	private String email;

	/** fax **/
	@Schema(description = "fax", type = SchemaType.STRING, required = false)
	private String fax;

	/** nombre **/
	@Schema(description = "nombre", type = SchemaType.STRING, required = false)
	private String nombre;

	/** orden **/
	@Schema(description = "orden", type = SchemaType.INTEGER, required = false)
	private Integer orden;

	/** presentacion **/
	@Schema(description = "presentacion", type = SchemaType.STRING, required = false)
	private String presentacion;

	/** responsable **/
	@Schema(description = "responsable", type = SchemaType.STRING, required = false)
	private String responsableNombre;

	/** responsableEmail **/
	@Schema(description = "responsableEmail", type = SchemaType.STRING, required = false)
	private String responsableEmail;

	/** sexoResponsable **/
	@Schema(description = "link_responsableSexo", required = false)
	private Link link_responsableSexo;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long responsableSexo;

	/**
	 * Tipo de UA
	 **/
	@Schema(description = "link_tipo", required = false)
	private Link link_tipo;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long tipo;

	/** telefono **/
	@Schema(description = "telefono", type = SchemaType.STRING, required = false)
	private String telefono;

	/** url **/
	@Schema(description = "url", type = SchemaType.STRING, required = false)
	private String url;

	/**
	 * Hijos.
	 **/
	/* Identificador funcional **/
	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	/**
	 * Usuario que realiza lso cambios
	 */
	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String usuarioAuditoria;

    /**
     * Versión de la UA
     */
	@Schema(description = "version", type = SchemaType.INTEGER, required = false)
    private Integer version;


    /**
     * Responsable
     */
	@Schema(description = "responsable", type = SchemaType.STRING, required = false)
    private String responsable;

	public UnidadAdministrativa() {
		super();
	}

	public UnidadAdministrativa(UnidadAdministrativaDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	/**
	 * Necesario solo si queremos añadir propiedades que no serán copiadas
	 * automaticamente y deben copiarse manualmente.
	 */
	@Override
	protected void addSetersInvalidos() {
	}

	@Override
	protected void generaLinks(String urlBase) {
		link_padre = this.generaLink(this.padre, Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, null);
		link_tipo = this.generaLink(this.tipo, Constantes.ENTIDAD_TIPO_UNIDAD, Constantes.URL_TIPO_UNIDAD, urlBase, null);
		link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
				null);
		link_responsableSexo = this.generaLink(this.responsableSexo == null ? null : this.responsableSexo.longValue(), Constantes.ENTIDAD_TIPO_SEXO, Constantes.URL_TIPO_SEXO, urlBase,
				null);
	}

	/**
	 * @return the abreviatura
	 */
	public String getAbreviatura() {
		return abreviatura;
	}

	/**
	 * @param abreviatura the abreviatura to set
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the id to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public void setId(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the padre
	 */
	@XmlTransient
	public Long getPadre() {
		return padre;
	}

	/**
	 * @param padre the padre to set
	 */
	public void setPadre(Long padre) {
		this.padre = padre;
	}

	/**
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * @return the responsableEmail
	 */
	public String getResponsableEmail() {
		return responsableEmail;
	}

	/**
	 * @param responsableEmail the responsableEmail to set
	 */
	public void setResponsableEmail(String responsableEmail) {
		this.responsableEmail = responsableEmail;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the codigoDIR3
	 */
	public String getCodigoDIR3() {
		return codigoDIR3;
	}

	/**
	 * @param codigoDIR3 the codigoDIR3 to set
	 */
	public void setCodigoDIR3(String codigoDIR3) {
		this.codigoDIR3 = codigoDIR3;
	}

	public Link getLink_padre() {
		return link_padre;
	}

	public void setLink_padre(Link link_padre) {
		this.link_padre = link_padre;
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

	public String getResponsableNombre() {
		return responsableNombre;
	}

	public void setResponsableNombre(String responsableNombre) {
		this.responsableNombre = responsableNombre;
	}

	public Long getResponsableSexo() {
		return responsableSexo;
	}

	public void setResponsableSexo(Long responsableSexo) {
		this.responsableSexo = responsableSexo;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public Link getLink_responsableSexo() {
		return link_responsableSexo;
	}

	public void setLink_responsableSexo(Link link_responsableSexo) {
		this.link_responsableSexo = link_responsableSexo;
	}

	public Link getLink_tipo() {
		return link_tipo;
	}

	public void setLink_tipo(Link link_tipo) {
		this.link_tipo = link_tipo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

}
