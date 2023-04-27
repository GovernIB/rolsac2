package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.TipoBoletin;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoBoletin", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_BOLETINES)
public class RespuestaTipoBoletin extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoBoletin> resultado;

	public RespuestaTipoBoletin(String status, String mensaje, long l, List<TipoBoletin> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoBoletin() {
		super();
	}

	public List<TipoBoletin> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoBoletin> resultado) {
		this.resultado = resultado;
	}
}