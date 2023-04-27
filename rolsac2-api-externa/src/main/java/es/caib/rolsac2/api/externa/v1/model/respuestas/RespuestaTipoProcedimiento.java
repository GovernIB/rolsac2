package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.TipoProcedimiento;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * RespuestaTipoProcedimiento
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoProcedimiento", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_PROCEDIMIENTO)
public class RespuestaTipoProcedimiento extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoProcedimiento> resultado;

	public RespuestaTipoProcedimiento(String status, String mensaje, long l, List<TipoProcedimiento> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoProcedimiento() {
		super();
	}

	public List<TipoProcedimiento> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoProcedimiento> resultado) {
		this.resultado = resultado;
	}
}