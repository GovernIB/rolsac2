package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.Tramite;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * RespuestaTramite
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTramite", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TRAMITE)
public class RespuestaTramite extends RespuestaBase {
	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<Tramite> resultado;

	public RespuestaTramite(String status, String mensaje, Long numeroElementos, List<Tramite> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	public RespuestaTramite() {
		super();
	}

	public List<Tramite> getResultado() {
		return resultado;
	}

	public void setResultado(List<Tramite> resultado) {
		this.resultado = resultado;
	}
}