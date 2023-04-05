package es.caib.rolsac2.api.externa.v1.model.filters;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * FiltroPaginacion.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "FiltroPaginacion", type = SchemaType.STRING, description = "Filtro que permite paginar los resultados")
public class FiltroPaginacion {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroPaginacion.class);

	public static final String SAMPLE_JSON = "{\"page\":\"1\",\"size\":\"30\"}";

	public static final String SAMPLE = Constantes.SALTO_LINEA + SAMPLE_JSON;

	public static final String LANG_SAMPLE = "ca";

	/** Lista de campos a ordenar. **/
	@Schema(description = "Lista de campos por los que ordenar", required = false)
	private List<CampoOrden> listaOrden;

	/** Page. **/
	@Schema(required = false, type = SchemaType.INTEGER, name = "page", description = "Page")
	private Integer page;

	/** Size. **/
	@Schema(required = false, type = SchemaType.INTEGER, name = "size", description = "Size")
	private Integer size;

	public static FiltroPaginacion valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<FiltroPaginacion> typeRef = new TypeReference<FiltroPaginacion>() {
		};
		FiltroPaginacion obj;
		try {
			obj = (FiltroPaginacion) objectMapper.readValue(json, typeRef);
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

	/**
	 * @return the listaOrden
	 */
	public List<CampoOrden> getListaOrden() {
		return listaOrden;
	}

	/**
	 * @param listaOrden the listaOrden to set
	 */
	public void setListaOrden(List<CampoOrden> listaOrden) {
		this.listaOrden = listaOrden;
	}

}