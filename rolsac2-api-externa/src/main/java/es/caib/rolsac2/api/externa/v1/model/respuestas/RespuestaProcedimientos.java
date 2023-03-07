package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.Procedimientos;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Procedimientos
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaProcedimientos", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PROCEDIMIENTO)
public class RespuestaProcedimientos extends RespuestaBase {

	/** Resultado. **/
	@Schema(description = "Listado con los objetos de resultado", required = false)
	private List<Procedimientos> resultado;

	public RespuestaProcedimientos(final String status, final String mensaje, final Long numeroElementos,
			final List<Procedimientos> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	};

	public RespuestaProcedimientos() {
		super();
	}

	public List<Procedimientos> getResultado() {
		return resultado;
	}

	public void setResultado(final List<Procedimientos> resultado) {
		this.resultado = resultado;
	}

}