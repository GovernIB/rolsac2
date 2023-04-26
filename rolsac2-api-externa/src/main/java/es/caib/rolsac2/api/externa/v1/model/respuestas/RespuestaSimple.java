package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaSimple", description = Constantes.TXT_RESPUESTA + "Objeto simple")
public class RespuestaSimple extends RespuestaBase{
	/** Resultado. **/
	@Schema(description = "Listado con los objetos de resultado",  required = false)
	private String resultado ;
	public RespuestaSimple(String status, String mensaje, Long numeroElementos,
			String resultado) {
		super(status,  mensaje, numeroElementos);
		this.resultado=resultado;
	};
	public RespuestaSimple() {
		super();
	}

	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}