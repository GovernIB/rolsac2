package es.caib.rolsac2.api.externa.v1.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoViaDTO;

/**
 * Bulletins.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoVia", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_TIPO_VIA)
public class TipoVia extends EntidadBase<TipoVia> {

	private static final Logger LOG = LoggerFactory.getLogger(TipoVia.class);

	/**
	 * Identificador
	 */
	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = true)
	private String identificador;

	/** descripcion. **/
	@Schema(description = "descripcion", name = "descripcion", type = SchemaType.STRING, required = false)
	private String descripcion;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
	private Long codigo;

	public TipoVia(TipoViaDTO nodo, String urlBase, String idioma,
			boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	public TipoVia() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {
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
}
