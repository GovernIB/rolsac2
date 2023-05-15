package es.caib.rolsac2.api.externa.v1.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;

/**
 * TipoPublicoObjetivoEntidads.
 *
 * @author Indra
 *
 */
@XmlRootElement
@Schema(name = "TipoPublicoObjetivoEntidad", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_PUBLICO_ENTIDAD)
public class TipoPublicoObjetivoEntidad extends EntidadBase<TipoPublicoObjetivoEntidad> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoPublicoObjetivoEntidad.class);

	/** codigo **/
	@Schema(description = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	/** identificador **/
	@Schema(description = "identificador", type = SchemaType.STRING, required = false)
	private String identificador;

	/** descripcion **/
	@Schema(description = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	// -- LINKS--//
	// -- se duplican las entidades para poder generar la clase link en funcion de
	// la propiedad principal (sin "link_")
	/** boletin **/
	@Schema(description = "link_tipo", required = false)
	private Link link_tipo;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long tipo;

	@Schema(description = "link_entidad", required = false)
	private Link link_entidad;
	@JsonIgnore
	@Schema(hidden = true)
	@XmlTransient
	private Long entidad;

	public TipoPublicoObjetivoEntidad(TipoPublicoObjetivoEntidadDTO elem, String urlBase, String idioma, boolean hateoasEnabled) {
		super( elem, urlBase, idioma, hateoasEnabled);
	}

	public TipoPublicoObjetivoEntidad() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		link_entidad = this.generaLink(this.entidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
				null);
		link_tipo = this.generaLink(this.tipo, Constantes.ENTIDAD_PUBLICO, Constantes.URL_PUBLICO, urlBase,
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

	public Link getLink_tipo() {
		return link_tipo;
	}

	public void setLink_tipo(Link link_tipo) {
		this.link_tipo = link_tipo;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

}
