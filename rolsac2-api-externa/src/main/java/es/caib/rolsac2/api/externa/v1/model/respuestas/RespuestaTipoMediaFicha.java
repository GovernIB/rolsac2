package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoMediaFicha;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RRespuestaTipoMediaFicha
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoMediaFicha", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_MEDIA_FICHA)
public class RespuestaTipoMediaFicha extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoMediaFicha> resultado;

	public RespuestaTipoMediaFicha(String status, String mensaje, long l, List<TipoMediaFicha> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoMediaFicha() {
		super();
	}

	public List<TipoMediaFicha> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoMediaFicha> resultado) {
		this.resultado = resultado;
	}
}