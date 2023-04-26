package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoSexo;
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
@Schema(name = "RespuestaTipoSexo", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_SEXO)
public class RespuestaTipoSexo extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoSexo> resultado;

	public RespuestaTipoSexo(String status, String mensaje, long l, List<TipoSexo> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoSexo() {
		super();
	}

	public List<TipoSexo> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoSexo> resultado) {
		this.resultado = resultado;
	}
}