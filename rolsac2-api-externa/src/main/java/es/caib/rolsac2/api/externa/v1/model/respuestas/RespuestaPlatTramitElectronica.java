package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.PlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * RespuestaPlatTramitElectronica
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaPlatTramitElectronica", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PLATAFORMA)
public class RespuestaPlatTramitElectronica extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<PlatTramitElectronica> resultado;

	public RespuestaPlatTramitElectronica(String status, String mensaje, long l, List<PlatTramitElectronica> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaPlatTramitElectronica() {
		super();
	}

	public List<PlatTramitElectronica> getResultado() {
		return resultado;
	}

	public void setResultado(List<PlatTramitElectronica> resultado) {
		this.resultado = resultado;
	}
}