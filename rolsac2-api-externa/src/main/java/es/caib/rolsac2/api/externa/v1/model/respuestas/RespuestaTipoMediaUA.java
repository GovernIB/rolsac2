package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.TipoMediaUA;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * RespuestaTipoMediaUA
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoMediaUA", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_MEDIA_UA)
public class RespuestaTipoMediaUA extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoMediaUA> resultado;

	public RespuestaTipoMediaUA(String status, String mensaje, long l, List<TipoMediaUA> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	}

	public RespuestaTipoMediaUA() {
		super();
	}

	public List<TipoMediaUA> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoMediaUA> resultado) {
		this.resultado = resultado;
	}
}