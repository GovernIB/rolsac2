package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;

@XmlRootElement
@Schema(name = "FiltroTipoAfectacion", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoAfectacion extends EntidadJson<FiltroTipoAfectacion> {

  private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoAfectacion.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA +
			"{"	+
			"\"texto\":\"string\"," 						+ Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":0,\"size\":10}" +
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

	public TipoAfectacionFiltro toTipoAfectacionFiltro() {
		TipoAfectacionFiltro resultado = new TipoAfectacionFiltro();

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
