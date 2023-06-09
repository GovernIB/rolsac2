package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;

@XmlRootElement
@Schema(name = "FiltroTipoTramitacion", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoTramitacion extends EntidadJson<FiltroTipoTramitacion> {

	private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoTramitacion.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA + "{" + "\"texto\":\"string\"," + Constantes.SALTO_LINEA
			+ "\"faseProc\":0," + Constantes.SALTO_LINEA + "\"codPlatTramitacion\":0," + Constantes.SALTO_LINEA
			+ "\"plantilla\":\"boolean\"," + Constantes.SALTO_LINEA + "\"idEntidad\":0," + Constantes.SALTO_LINEA
			+ "\"filtroPaginacion\":{\"page\":0,\"size\":10}" + "}";

	public static final String SAMPLE_JSON = "{" + "\"texto\":null," + "\"plantilla\":null," + "\"faseProc\":null,"
			+ "\"codPlatTramitacion\":null," + "\"idEntidad\":null,"
			+ "\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" + "}";

	/** texto. **/
	@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
	private String texto;

	@Schema(name = "faseProc", description = "faseProc", type = SchemaType.INTEGER, required = false)
	private Integer faseProc;

	/**
	 * Indica la entidad
	 **/
	@Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
	private Long idEntidad;

	@Schema(name = "codPlatTramitacion", description = "codPlatTramitacion", type = SchemaType.INTEGER, required = false)
	private Long codPlatTramitacion;

	/**
	 * Indica si es de tipo plantilla
	 **/
	@Schema(name = "plantilla", description = "plantilla", type = SchemaType.BOOLEAN, required = false)
	private Boolean plantilla;

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

	public TipoTramitacionFiltro toTipoTramitacionFiltro() {
		TipoTramitacionFiltro resultado = new TipoTramitacionFiltro();

		if (this.texto != null && !this.texto.isEmpty()) {
			resultado.setTexto(texto);
		}

		if (this.plantilla != null) {
			resultado.setTipoPlantilla(plantilla);
		}

		if (this.faseProc != null) {
			resultado.setFaseProc(faseProc);
		}

		if (this.codPlatTramitacion != null) {
			resultado.setCodPlatTramitacion(codPlatTramitacion);
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

	public Boolean getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(Boolean plantilla) {
		this.plantilla = plantilla;
	}

	public Integer getFaseProc() {
		return faseProc;
	}

	public void setFaseProc(Integer faseProc) {
		this.faseProc = faseProc;
	}

	public Long getCodPlatTramitacion() {
		return codPlatTramitacion;
	}

	public void setCodPlatTramitacion(Long codPlatTramitacion) {
		this.codPlatTramitacion = codPlatTramitacion;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}
}
