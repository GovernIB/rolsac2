package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.Tema;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Respuesta Fitxes
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTema", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TEMAS)
public class RespuestaTema extends RespuestaBase {
	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<Tema> resultado;

	public RespuestaTema(String status, String mensaje, Long numeroElementos, List<Tema> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	public RespuestaTema() {
		super();
	}

	public List<Tema> getResultado() {
		return resultado;
	}

	public void setResultado(List<Tema> resultado) {
		this.resultado = resultado;
	}
}