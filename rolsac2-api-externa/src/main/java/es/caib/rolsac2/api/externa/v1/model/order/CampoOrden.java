package es.caib.rolsac2.api.externa.v1.model.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

/**
 * FiltroUA.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "CampoOrden", description = "Campo a ordenar")
public class CampoOrden {

	/** campo. **/
	@Schema(description = "Campo", required = true)
	private String Campo;

	/** tipoOrden. **/
	@Schema(description = "Tipo de ordenacion", required = true, enumeration ="ASC,DESC")
	private String tipoOrden;

	public static CampoOrden valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<CampoOrden> typeRef = new TypeReference<CampoOrden>() {
		};
		CampoOrden obj;
		try {
			obj = (CampoOrden) objectMapper.readValue(json, typeRef);
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
	 * @return the campo
	 */
	public String getCampo() {
		return Campo;
	}


	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		Campo = campo;
	}


	/**
	 * @return the tipoOrden
	 */
	public String getTipoOrden() {
		return tipoOrden;
	}


	/**
	 * @param tipoOrden the tipoOrden to set
	 */
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}


}
