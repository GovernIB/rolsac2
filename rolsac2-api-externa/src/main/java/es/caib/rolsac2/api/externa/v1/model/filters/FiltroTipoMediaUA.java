package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Schema(name = "FiltroTipoMediaUA", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoMediaUA extends EntidadJson<FiltroTipoMediaUA> {

  private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoMediaUA.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA +
			"{"	+
			"\"texto\":\"string\"," 						+ Constantes.SALTO_LINEA +
			"\"idEntidad\":0," + Constantes.SALTO_LINEA +
			"\"filtroPaginacion\":{\"page\":0,\"size\":10}" +
			"}";

	public static final String SAMPLE_JSON =
			"{" +
			"\"texto\":null," 						+
			"\"idEntidad\":null," 						+
			"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
			"}";

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

    /**
     * Entidad
     */
	@Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
    private Long idEntidad;

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

	public TipoMediaUAFiltro toTipoMediaUAFiltro() {
		TipoMediaUAFiltro resultado = new TipoMediaUAFiltro();

		if (this.texto != null) {
			resultado.setTexto(texto);
		}

		if (this.idEntidad != null) {
			resultado.setIdEntidad(idEntidad);
		}

		return resultado;

	}

	public FiltroPaginacion getFiltroPaginacion() {
		return filtroPaginacion;
	}

	public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
		this.filtroPaginacion = filtroPaginacion;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}
}
