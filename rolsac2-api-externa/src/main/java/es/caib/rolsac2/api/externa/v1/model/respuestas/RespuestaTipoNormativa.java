package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoNormativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoNormativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_NORMATIVA)
public class RespuestaTipoNormativa extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoNormativa> resultado;

	public RespuestaTipoNormativa(String status, String mensaje, long l, List<TipoNormativa> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoNormativa() {
		super();
	}

	public List<TipoNormativa> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoNormativa> resultado) {
		this.resultado = resultado;
	}
}