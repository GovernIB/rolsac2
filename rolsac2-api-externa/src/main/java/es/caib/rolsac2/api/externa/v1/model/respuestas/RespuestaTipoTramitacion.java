package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoTramitacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaTipoTramitacion
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoTramitacion", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_TRAMITACION)
public class RespuestaTipoTramitacion extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoTramitacion> resultado;

	public RespuestaTipoTramitacion(String status, String mensaje, long l, List<TipoTramitacion> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoTramitacion() {
		super();
	}

	public List<TipoTramitacion> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoTramitacion> resultado) {
		this.resultado = resultado;
	}
}