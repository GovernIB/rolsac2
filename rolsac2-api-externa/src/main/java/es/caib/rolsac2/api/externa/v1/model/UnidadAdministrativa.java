package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;

/**
 * Unidad Administrativa.
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "UnidadAdministrativa", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_UA)
public class UnidadAdministrativa extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(UnidadAdministrativa.class);

	/** padre **/
	@Schema(description = "link_padre", required = false)
	private Link linkPadre;
	@Schema(hidden = true)
	@XmlTransient
	private Long padre;

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	/** codigoDIR3 **/
	@Schema(description = "codigoDIR3", type = SchemaType.STRING, required = false)
	private String codigoDIR3;

	@Schema(description = "linkEntidad", required = false)
	private Link linkEntidad;
	@Schema(hidden = true)
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
	private Long orden;

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
	@Schema(description = "sexoResponsable", type = SchemaType.INTEGER, required = false)
	private Integer responsableSexo;

	/** telefono **/
	@Schema(description = "telefono", type = SchemaType.STRING, required = false)
	private String telefono;

	/** url **/
	@Schema(description = "url", type = SchemaType.STRING, required = false)
	private String url;

//	/**  **/
//	@Schema(description = "idioma", type = SchemaType.STRING, required = false)
//	private String idioma;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")

	/**
	 * Tipo de UA
	 **/
	@Schema(description = "tipo", type = SchemaType.INTEGER, required = false)
	private Long tipo;
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

	public UnidadAdministrativa(UnidadAdministrativaGridDTO nodo, String urlBase, String idioma,
			boolean hateoasEnabled) {
		if(nodo != null) {
			this.codigo = nodo.getCodigo();
			this.codigoDIR3 = nodo.getCodigoDIR3();
			this.identificador = nodo.getIdentificador();
			this.hateoasEnabled = true;
			generaLinks(urlBase);
			this.nombre = nodo.getNombre() == null ? null : nodo.getNombre().getTraduccion(idioma);
			this.orden = nodo.getOrden() == null ? null : nodo.getOrden().longValue();
		}
//		super(nodo, urlBase, idioma, hateoasEnabled);

	}

	public UnidadAdministrativa() {
		super();
	}

	public UnidadAdministrativa(UnidadAdministrativaDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
//		super(nodo, urlBase, idioma, hateoasEnabled);
		if(nodo != null) {
			this.abreviatura = nodo.getAbreviatura();
			this.codigo = nodo.getCodigo();
			this.codigoDIR3 = nodo.getCodigoDIR3();
			this.dominio = nodo.getDominio();
			this.email = nodo.getEmail();
			this.entidad = nodo.getEntidad() == null ? null : nodo.getEntidad().getCodigo();
			this.fax = nodo.getFax();
			this.identificador = nodo.getIdentificador();
			this.hateoasEnabled = true;
			this.nombre = nodo.getNombre() == null ? null : nodo.getNombre().getTraduccion(idioma);
			this.orden = nodo.getOrden() == null ? null : nodo.getOrden().longValue();
			this.padre = nodo.getPadre() == null ? null : nodo.getPadre().getCodigo();
			this.presentacion = nodo.getPresentacion() == null ? null : nodo.getPresentacion().getTraduccion(idioma);
			this.responsableEmail = nodo.getResponsableEmail();
			this.responsableNombre = nodo.getResponsableNombre();
			this.responsableSexo = nodo.getResponsableSexo() == null ? null : nodo.getResponsableSexo().getCodigo().intValue();
			this.telefono = nodo.getTelefono();
			this.tipo = nodo.getTipo() == null ? null : nodo.getTipo().getCodigo();
			this.url = nodo.getUrl() == null ? null : nodo.getUrl().getTraduccion(idioma);
			this.usuarioAuditoria = nodo.getUsuarioAuditoria();
			generaLinks(urlBase);
		}

	}

	/**
	 * Necesario solo si queremos añadir propiedades que no serán copiadas
	 * automaticamente y deben copiarse manualmente.
	 */
	@Override
	protected void addSetersInvalidos() {
//		if (!SETTERS_INVALIDS.contains("setTratamiento")) {
//			SETTERS_INVALIDS.add("setTratamiento");
//		}
	}

//	@Override
//	public <T> void fill(T ua, String urlBase, String idioma, boolean hateoasEnabled) {
//		super.fill(ua, urlBase, idioma, hateoasEnabled);
//
//		// copiamos los datos que no tienen la misma estructura:
////		if(((org.ibit.rol.sac.model.UnidadAdministrativa)ua).getTratamiento()!=null ) {
////			this.tratamiento = new Tratamiento(((org.ibit.rol.sac.model.UnidadAdministrativa)ua).getTratamiento(),urlBase,idioma,hateoasEnabled);
////		}
//
//		// si el padre es null lo convertimos a -1
//		if (this.padre == null) {
//			this.padre = new Long(-1);
//		}
//
//	}

//	public <T> void fill(T tr, String urlBase, String idioma, boolean hateoasEnabled) {
//		setHateoasEnabled(hateoasEnabled);
//		copiaPropiedadesDeEntity(tr, idioma);
//		generaLinks(urlBase);
//	}

	@Override
	protected void generaLinks(String urlBase) {
		linkPadre = this.generaLink(this.padre, Constantes.ENTIDAD_UA, Constantes.URL_UA, urlBase, null);
		linkEntidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
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
	public Long getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Long orden) {
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

//	/**
//	 * @return the idioma
//	 */
//	public String getIdioma() {
//		return idioma;
//	}

//	/**
//	 * @param idioma the idioma to set
//	 */
//	public void setIdioma(String idioma) {
//		this.idioma = idioma;
//	}

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

	public Link getLinkPadre() {
		return linkPadre;
	}

	public void setLinkPadre(Link linkPadre) {
		this.linkPadre = linkPadre;
	}

	public Link getLinkEntidad() {
		return linkEntidad;
	}

	public void setLinkEntidad(Link linkEntidad) {
		this.linkEntidad = linkEntidad;
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

	public Integer getResponsableSexo() {
		return responsableSexo;
	}

	public void setResponsableSexo(Integer responsableSexo) {
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

}
