package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

@XmlRootElement
@Schema(name = "FiltroTipoBoletin", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoBoletin extends EntidadJson<FiltroTipoBoletin> {

  private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoBoletin.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA +
			"{"	+
			"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	public static final String SAMPLE_JSON =
			"{" +
			"\"texto\":null," 						+
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

	/** FiltroPaginacion. **/
	@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
	private FiltroPaginacion filtroPaginacion;

//	public static FiltroNormativas valueOf(final String json) {
//		final ObjectMapper objectMapper = new ObjectMapper();
//		final TypeReference<FiltroNormativas> typeRef = new TypeReference<FiltroNormativas>() {
//		};
//		FiltroNormativas obj;
//		try {
//			obj = (FiltroNormativas) objectMapper.readValue(json, typeRef);
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
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public TipoBoletinFiltro toTipoBoletinFiltro() {
		TipoBoletinFiltro resultado = new TipoBoletinFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}

		return resultado;

	}

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}
}
