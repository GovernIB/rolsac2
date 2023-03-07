package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.Normativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Respuesta Fitxes
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaNormativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_FICHA)
public class RespuestaNormativa extends RespuestaBase {
	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<Normativa> resultado;

	public RespuestaNormativa(String status, String mensaje, Long numeroElementos, List<Normativa> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	public RespuestaNormativa() {
		super();
	}

	public List<Normativa> getResultado() {
		return resultado;
	}

	public void setResultado(List<Normativa> resultado) {
		this.resultado = resultado;
	}
}