package es.caib.rolsac2.api.externa.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoMediaUADTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Bulletins.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoMediaUA", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_MEDIA_UA)
public class TipoMediaUA extends EntidadBase<TipoMediaUA> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoMediaUA.class);

	/**
	 * Identificador
	 */
	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = true)
	private String identificador;

	/** enlace. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	@Schema(description = "linkEntidad", required = false)
	private Link linkEntidad;
	@Schema(hidden = true)
	@JsonIgnore
	@XmlTransient
	private Long entidad;

	public TipoMediaUA(TipoMediaUADTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);

		if(nodo != null) {
			entidad = nodo.getEntidad() == null ? null : nodo.getEntidad().getCodigo();
		}
	}

	public TipoMediaUA() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		linkEntidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
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

	/**
	 * Obtiene identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece identificador.
	 *
	 * @param identificador identificador
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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

}
