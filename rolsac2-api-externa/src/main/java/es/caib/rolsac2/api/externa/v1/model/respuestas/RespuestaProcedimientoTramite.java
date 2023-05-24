package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.ProcedimientoTramite;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * RespuestaProcedimientoTramite
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTramite", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TRAMITE)
public class RespuestaProcedimientoTramite extends RespuestaBase {
	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<ProcedimientoTramite> resultado;

	public RespuestaProcedimientoTramite(String status, String mensaje, Long numeroElementos, List<ProcedimientoTramite> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	public RespuestaProcedimientoTramite() {
		super();
	}

	public List<ProcedimientoTramite> getResultado() {
		return resultado;
	}

	public void setResultado(List<ProcedimientoTramite> resultado) {
		this.resultado = resultado;
	}
}