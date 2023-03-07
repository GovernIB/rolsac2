package es.caib.rolsac2.api.externa.v1.model;

import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.TipoBoletinDTO;

/**
 * Bulletins.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "TipoBoletin", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_BOLETINES)
public class TipoBoletin extends EntidadBase {

	private static final Logger LOG = LoggerFactory.getLogger(TipoBoletin.class);

	/**
	 * Identificador
	 */
	@Schema(description = "identificador", name = "identificador", type = SchemaType.STRING, required = true)
	private String identificador;

	/** enlace. **/
	@Schema(description = "url", name = "url", type = SchemaType.STRING, required = false)
	private String url;

	/** codigo **/
	@Schema(description = "codigo", name = "codigo", type = SchemaType.NUMBER, required = false)
	private long codigo;

	/** nombre **/
	@Schema(description = "nombre", name = "nombre", type = SchemaType.STRING, required = false)
	private String nombre;

	public TipoBoletin(TipoBoletinDTO nodo, String urlBase, String idioma, boolean hateoasEnabled) {
		super(nodo, urlBase, idioma, hateoasEnabled);
	}

	public TipoBoletin() {
		super();
	}

	@Override
	public void generaLinks(String urlBase) {

	}

	public static TipoBoletin valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<TipoBoletin> typeRef = new TypeReference<TipoBoletin>() {
		};
		TipoBoletin obj;
		try {
			obj = (TipoBoletin) objectMapper.readValue(json, typeRef);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param enlace the enlace to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the idioma
	 *
	 *         public java.lang.String getIdioma() { return idioma; }
	 */

	/**
	 * @param idioma the idioma to set
	 *
	 *               public void setIdioma(java.lang.String idioma) { this.idioma =
	 *               idioma; }
	 */

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
