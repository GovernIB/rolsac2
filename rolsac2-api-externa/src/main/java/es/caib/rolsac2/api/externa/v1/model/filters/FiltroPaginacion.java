package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FiltroPaginacion.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroPaginacion", type = SchemaType.STRING, description = "Filtro que permite paginar los resultados")
public class FiltroPaginacion extends EntidadJson<FiltroPaginacion> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroPaginacion.class);

	public static final String SAMPLE_JSON = "{\"page\":\"0\",\"size\":\"10\"}";

	public static final String SAMPLE = Constantes.SALTO_LINEA + SAMPLE_JSON;

//	public static final String LANG_SAMPLE = "ca";

	/** Page. **/
	@Schema(required = false, type = SchemaType.INTEGER, name = "page", description = "Page", defaultValue = "0")
	private Integer page;

	/** Size. **/
	@Schema(required = false, type = SchemaType.INTEGER, name = "size", description = "Size", defaultValue = "10")
	private Integer size;

//	public static FiltroPaginacion valueOf(final String json) {
//		final ObjectMapper objectMapper = new ObjectMapper();
//		final TypeReference<FiltroPaginacion> typeRef = new TypeReference<FiltroPaginacion>() {
//		};
//		FiltroPaginacion obj;
//		try {
//			obj = (FiltroPaginacion) objectMapper.readValue(json, typeRef);
//		} catch (final IOException e) {
//			LOG.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
//		return obj;
//	}
//
//	public String toJson() {
//		try {
//			final ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//			return objectMapper.writeValueAsString(this);
//		} catch (final JsonProcessingException e) {
//			LOG.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
}
