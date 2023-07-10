package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.TipoFormaInicio;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoFormaInicio", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_FORMA)
public class RespuestaTipoFormaInicio extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoFormaInicio> resultado;

	public RespuestaTipoFormaInicio(String status, String mensaje, long l, List<TipoFormaInicio> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoFormaInicio() {
		super();
	}

	public List<TipoFormaInicio> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoFormaInicio> resultado) {
		this.resultado = resultado;
	}
}