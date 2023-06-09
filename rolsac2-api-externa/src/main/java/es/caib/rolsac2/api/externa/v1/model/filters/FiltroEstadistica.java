package es.caib.rolsac2.api.externa.v1.model.filters;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoSexoFiltro;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Schema(name = "FiltroEstadistica", type = SchemaType.STRING, description = "Filtro para grabar una estadística")
public class FiltroEstadistica extends EntidadJson<FiltroEstadistica> {

  private static final Logger LOG = LoggerFactory.getLogger(FiltroEstadistica.class);

	public static final String SAMPLE = Constantes.SALTO_LINEA +
			"{"	+ Constantes.SALTO_LINEA +
			"\"idApp\":\"string\"," + Constantes.SALTO_LINEA +
			"\"tipo\":\"string\"" + Constantes.SALTO_LINEA +
			"}";

	public static final String SAMPLE_JSON =
					"{\n"	+
					"	\"idApp\":\"string\",\n" +
					"	\"tipo\":\"string\"\n" +
					"}";

	/** Identificador aplicación. **/
	@Schema(name = "idApp", description = "Id aplicación", type = SchemaType.STRING, required = true)
	private String idApp;

	/** Tipo del objeto al que se accede. **/
	@Schema(name = "tipo", description = "Tipo del objeto al que se accede: Procedimiento (P) / Servicio (S) / Unidad administrativa (UA)", type = SchemaType.STRING, required = true)
	private String tipo;

	public EstadisticaFiltro toEstadisticaFiltro(Long codigo) {
		EstadisticaFiltro filtro = new EstadisticaFiltro();
		filtro.setCodigo(codigo);
		filtro.setTipo(this.tipo);
		filtro.setIdApp(this.idApp);
		return filtro;
	}

	public String getIdApp() {
		return idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
