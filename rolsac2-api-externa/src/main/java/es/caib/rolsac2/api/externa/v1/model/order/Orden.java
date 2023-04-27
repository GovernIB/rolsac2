package es.caib.rolsac2.api.externa.v1.model.order;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Orden Clase encargada de almacenar los campos por los que ordenar
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "Orden", description = "Campos que se pueden usar para ordenar")
public class Orden {

	public static final String CAMPO_ORD_UA_ORDEN = "orden";
	public static final String CAMPO_ORD_DOC_ORDEN = "orden";
	public static final String CAMPO_ORD_ENLACE_ORDEN = "orden";
	public static final String CAMPO_ORD_FICHA_FECHA_ACTUALIZACION = "fechaActualizacion";

	public static final String CAMPO_ORD_FICHAUA_ORDEN = "orden";
	public static final String CAMPO_ORD_FICHAUA_ORDEN_SECCION = "ordenseccion";

	public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION = "fechaPublicacion";
	public static final String CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION = "fechaActualizacion";
	public static final String CAMPO_ORD_PROCEDIMIENTO_CODIGO = "id";

	public static final String SAMPLE_ORDEN_UA = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_UA_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]}";
	public static final String SAMPLE_ORDEN_DOC = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_DOC_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]}";
	public static final String SAMPLE_ORDEN_ENLACE = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_ENLACE_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}]}";
	public static final String SAMPLE_ORDEN_FICHA = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_FICHA_FECHA_ACTUALIZACION + "\",\"tipoOrden\":\"ASC/DESC\"}]}";

	public static final String SAMPLE_ORDEN_FICHAUA = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_FICHAUA_ORDEN + "\",\"tipoOrden\":\"ASC/DESC\"}," + "{\"campo\":\""
			+ CAMPO_ORD_FICHAUA_ORDEN_SECCION + "\",\"tipoOrden\":\"ASC/DESC\"}]}";

	public static final String SAMPLE_ORDEN_PROCEDIMIENTO = Constantes.SALTO_LINEA + "{\"listaOrden\":[{\"campo\":\""
			+ CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION + "\",\"tipoOrden\":\"ASC/DESC\"}," + "{\"campo\":\""
			+ CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION + "\",\"tipoOrden\":\"ASC/DESC\"}," + "{\"campo\":\""
			+ CAMPO_ORD_PROCEDIMIENTO_CODIGO + "\",\"tipoOrden\":\"ASC/DESC\"}" + "]}";

	/** Lista de campos a ordenar. **/
	@Schema(description = "Lista de campos por los que ordenar", required = false)
	private List<CampoOrden> listaOrden;

	public static Orden valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<Orden> typeRef = new TypeReference<Orden>() {
		};
		Orden obj;
		try {
			obj = (Orden) objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			// TODO PENDIENTE
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
			// TODO PENDIENTE
			throw new RuntimeException(e);
		}
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
