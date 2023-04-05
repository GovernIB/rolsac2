package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.TipoAfectacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoAfectacion", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_AFECTACION)
public class RespuestaTipoAfectacion extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoAfectacion> resultado;

	public RespuestaTipoAfectacion(String status, String mensaje, long l, List<TipoAfectacion> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoAfectacion() {
		super();
	}

	public List<TipoAfectacion> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoAfectacion> resultado) {
		this.resultado = resultado;
	}
}