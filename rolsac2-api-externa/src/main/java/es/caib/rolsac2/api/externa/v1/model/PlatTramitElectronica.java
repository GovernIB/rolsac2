package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;

/**
 * PlatTramitElectronica.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "PlatTramitElectronica", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_TRAMITACION)
public class PlatTramitElectronica extends EntidadBase<PlatTramitElectronica> {

	private static final Logger LOG = LoggerFactory.getLogger(PlatTramitElectronica.class);

	/**
	 * Identificador
	 */
	@Schema(description = "tramiteId", name = "tramiteId", type = SchemaType.STRING, required = true)
	private String identificador;

	/** enlace. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
    private String descripcion;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

    /**
     * Url
     */
	@Schema(description = "urlAcceso", name = "urlAcceso", type = SchemaType.STRING, required = false)
    private String urlAcceso;

	@Schema(description = "linkEntidad", required = false)
	private Link linkEntidad;
	@Schema(hidden = true)
	@XmlTransient
	private Long codEntidad;


	public PlatTramitElectronica(PlatTramitElectronicaDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);

		if(nodo != null) {
			codEntidad = nodo.getCodEntidad() == null ? null : nodo.getCodEntidad().getCodigo();
		}
	}

	public PlatTramitElectronica() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
		linkEntidad = this.generaLink(this.codEntidad, Constantes.ENTIDAD_ENTIDADES, Constantes.URL_ENTIDADES, urlBase,
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

	public Link getLinkEntidad() {
		return linkEntidad;
	}

	public void setLinkEntidad(Link linkEntidad) {
		this.linkEntidad = linkEntidad;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getUrlAcceso() {
		return urlAcceso;
	}

	public void setUrlAcceso(String urlAcceso) {
		this.urlAcceso = urlAcceso;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}
}