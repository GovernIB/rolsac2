package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Schema(name = "FiltroTipoMediaFicha", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoMediaFicha extends EntidadJson<FiltroTipoMediaFicha> {

  private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoMediaFicha.class);

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

	public TipoMediaFichaFiltro toTipoMediaFichaFiltro() {
		TipoMediaFichaFiltro resultado = new TipoMediaFichaFiltro();

		if (this.texto != null) {
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
