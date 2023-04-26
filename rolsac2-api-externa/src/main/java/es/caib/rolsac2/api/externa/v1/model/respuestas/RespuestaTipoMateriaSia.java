package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoMateriaSia;
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
@Schema(name = "RespuestaTipoMateriaSia", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_MATERIA)
public class RespuestaTipoMateriaSia extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoMateriaSia> resultado;

	public RespuestaTipoMateriaSia(String status, String mensaje, long l, List<TipoMateriaSia> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoMateriaSia() {
		super();
	}

	public List<TipoMateriaSia> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoMateriaSia> resultado) {
		this.resultado = resultado;
	}
}