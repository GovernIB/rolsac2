package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.service.model.TemaDTO;

/**
 * Temas.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "Tema", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TEMAS)
public class Tema extends EntidadBase<Tema> {

	private static final Logger LOG = LoggerFactory.getLogger(Tema.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	/** identificador **/
	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	/** descripcion **/
	@Schema(description = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	/** mathPath **/
	@Schema(description = "mathPath", type = SchemaType.STRING, required = false)
	private String mathPath;//


	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** boletin **/
	@Schema(description = "link_temaPadre", required = false)
	private Link link_temaPadre;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long temaPadre;

	@Schema(description = "link_entidad", required = false)
	private Link link_entidad;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long entidad;

	public Tema(TemaDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
		super( elem, urlBase, idioma, hateoasEnabled);
	}

	public Tema() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
				null);
		link_temaPadre = this.generaLink(this.temaPadre, Constantes.ENTIDAD_TEMAS, Constantes.URL_TEMAS, urlBase,
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
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public Link getLink_entidad() {
		return link_entidad;
	}

	public void setLink_entidad(Link link_entidad) {
		this.link_entidad = link_entidad;
	}

	@XmlTransient
	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMathPath() {
		return mathPath;
	}

	public void setMathPath(String mathPath) {
		this.mathPath = mathPath;
	}

	public Link getLink_temaPadre() {
		return link_temaPadre;
	}

	public void setLink_temaPadre(Link link_temaPadre) {
		this.link_temaPadre = link_temaPadre;
	}

	public Long getTemaPadre() {
		return temaPadre;
	}

	public void setTemaPadre(Long temaPadre) {
		this.temaPadre = temaPadre;
	}

}
