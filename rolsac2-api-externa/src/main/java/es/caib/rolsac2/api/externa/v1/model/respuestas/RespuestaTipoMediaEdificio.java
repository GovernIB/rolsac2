package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.TipoMediaEdificio;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoMediaEdificio", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_MEDIA_EDIFICIO)
public class RespuestaTipoMediaEdificio extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoMediaEdificio> resultado;

	public RespuestaTipoMediaEdificio(String status, String mensaje, long l, List<TipoMediaEdificio> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoMediaEdificio() {
		super();
	}

	public List<TipoMediaEdificio> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoMediaEdificio> resultado) {
		this.resultado = resultado;
	}
}