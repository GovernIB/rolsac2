package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import es.caib.rolsac2.api.externa.v1.model.TipoPublicoObjetivo;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoPublicoObjetivo", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PUBLICO)
public class RespuestaTipoPublicoObjetivo extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoPublicoObjetivo> resultado;

	public RespuestaTipoPublicoObjetivo(String status, String mensaje, long l, List<TipoPublicoObjetivo> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoPublicoObjetivo() {
		super();
	}

	public List<TipoPublicoObjetivo> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoPublicoObjetivo> resultado) {
		this.resultado = resultado;
	}
}