package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.EntidadDTO;

/**
 * Dades d'un Entidad.
 *
 * @author jsegovia
 */
@XmlRootElement
@Schema(name = "EntidadExterna", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_ENTIDADES)
public class Entidad extends EntidadBase<Entidad> {

	private static final Logger LOG = LoggerFactory.getLogger(Entidad.class);

	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	@Schema(description = "activa", name = "activa", type = SchemaType.BOOLEAN, required = false)
	private Boolean activa;

	@Schema(description = "rolAdmin", name = "rolAdmin", type = SchemaType.STRING, required = false)
	private String rolAdmin;

	@Schema(description = "rolAdminContenido", name = "rolAdminContenido", type = SchemaType.STRING, required = false)
	private String rolAdminContenido;

	@Schema(description = "rolGestor", name = "rolGestor", type = SchemaType.STRING, required = false)
	private String rolGestor;

	@Schema(description = "rolInformador", name = "rolInformador", type = SchemaType.STRING, required = false)
	private String rolInformador;

//    private FicheroDTO logo;
	@Schema(description = "linkLogo", name = "linkLogo", required = false)
	private Link linkLogo;
	@Schema(hidden = true)
	@XmlTransient
	private Long logo;

//    private FicheroDTO cssPersonalizado;
	@Schema(description = "linkCssPersonalizado", name = "linkCssPersonalizado", required = false)
	private Link linkCssPersonalizado;
	@Schema(hidden = true)
	@XmlTransient
	private Long cssPersonalizado;

	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	@Schema(description = "idiomaDefectoRest", name = "idiomaDefectoRest", type = SchemaType.STRING, required = false)
	private String idiomaDefectoRest;

	@Schema(description = "idiomasPermitidos", name = "idiomasPermitidos", type = SchemaType.STRING, required = false)
	private String idiomasPermitidos;

	@Schema(description = "idiomasObligatorios", name = "idiomasObligatorios", type = SchemaType.STRING, required = false)
	private String idiomasObligatorios;

	/**
	 * LOPD Datos
	 **/
	@Schema(description = "lopdDestinatario", name = "lopdDestinatario", type = SchemaType.STRING, required = false)
	private String lopdDestinatario;

	@Schema(description = "lopdDerechos", name = "lopdDerechos", type = SchemaType.STRING, required = false)
	private String lopdDerechos;

	@Schema(description = "lopdFinalidad", name = "lopdFinalidad", type = SchemaType.STRING, required = false)
	private String lopdFinalidad;

	@Schema(description = "uaComun", name = "uaComun", type = SchemaType.STRING, required = false)
	private String uaComun;

	public Entidad(EntidadDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);

		if(nodo != null) {
			logo = nodo.getLogo() == null ? null : nodo.getLogo().getCodigo();
		}

		if(nodo != null) {
			cssPersonalizado = nodo.getCssPersonalizado() == null ? null : nodo.getCssPersonalizado().getCodigo();
		}
	}

	public Entidad() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		linkLogo = this.generaLink(this.logo, Constantes.ENTIDAD_FICHERO, Constantes.URL_FICHERO, urlBase,
				null);
		linkCssPersonalizado = this.generaLink(this.cssPersonalizado, Constantes.ENTIDAD_FICHERO, Constantes.URL_FICHERO, urlBase,
				null);
	}

	@Override
	protected void addSetersInvalidos() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setId(Long codigo) {
		// TODO Auto-generated method stub

	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public String getRolAdmin() {
		return rolAdmin;
	}

	public void setRolAdmin(String rolAdmin) {
		this.rolAdmin = rolAdmin;
	}

	public String getRolAdminContenido() {
		return rolAdminContenido;
	}

	public void setRolAdminContenido(String rolAdminContenido) {
		this.rolAdminContenido = rolAdminContenido;
	}

	public String getRolGestor() {
		return rolGestor;
	}

	public void setRolGestor(String rolGestor) {
		this.rolGestor = rolGestor;
	}

	public String getRolInformador() {
		return rolInformador;
	}

	public void setRolInformador(String rolInformador) {
		this.rolInformador = rolInformador;
	}

	public Link getLinkLogo() {
		return linkLogo;
	}

	public void setLinkLogo(Link linkLogo) {
		this.linkLogo = linkLogo;
	}

	public Long getLogo() {
		return logo;
	}

	public void setLogo(Long logo) {
		this.logo = logo;
	}

	public Link getLinkCssPersonalizado() {
		return linkCssPersonalizado;
	}

	public void setLinkCssPersonalizado(Link linkCssPersonalizado) {
		this.linkCssPersonalizado = linkCssPersonalizado;
	}

	public Long getCssPersonalizado() {
		return cssPersonalizado;
	}

	public void setCssPersonalizado(Long cssPersonalizado) {
		this.cssPersonalizado = cssPersonalizado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdiomaDefectoRest() {
		return idiomaDefectoRest;
	}

	public void setIdiomaDefectoRest(String idiomaDefectoRest) {
		this.idiomaDefectoRest = idiomaDefectoRest;
	}

	public String getIdiomasPermitidos() {
		return idiomasPermitidos;
	}

	public void setIdiomasPermitidos(String idiomasPermitidos) {
		this.idiomasPermitidos = idiomasPermitidos;
	}

	public String getIdiomasObligatorios() {
		return idiomasObligatorios;
	}

	public void setIdiomasObligatorios(String idiomasObligatorios) {
		this.idiomasObligatorios = idiomasObligatorios;
	}

	public String getLopdDestinatario() {
		return lopdDestinatario;
	}

	public void setLopdDestinatario(String lopdDestinatario) {
		this.lopdDestinatario = lopdDestinatario;
	}

	public String getLopdDerechos() {
		return lopdDerechos;
	}

	public void setLopdDerechos(String lopdDerechos) {
		this.lopdDerechos = lopdDerechos;
	}

	public String getLopdFinalidad() {
		return lopdFinalidad;
	}

	public void setLopdFinalidad(String lopdFinalidad) {
		this.lopdFinalidad = lopdFinalidad;
	}

	public String getUaComun() {
		return uaComun;
	}

	public void setUaComun(String uaComun) {
		this.uaComun = uaComun;
	}
}
