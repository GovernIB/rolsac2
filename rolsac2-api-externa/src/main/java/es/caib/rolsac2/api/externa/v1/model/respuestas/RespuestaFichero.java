package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Fichero;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaFichero
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaFichero", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_FICHERO)
public class RespuestaFichero extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<Fichero> resultado;

	public RespuestaFichero(String status, String mensaje, long l, List<Fichero> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaFichero() {
		super();
	}

	public List<Fichero> getResultado() {
		return resultado;
	}

	public void setResultado(List<Fichero> resultado) {
		this.resultado = resultado;
	}
}