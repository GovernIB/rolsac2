package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.model.TipoUnidadAdministrativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;

/**
 * Respuesta Idioma
 *
 * @author indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaTipoUnidadAdministrativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_UNIDAD)
public class RespuestaTipoUnidadAdministrativa extends RespuestaBase {

	/** Resultado. **/
	@Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
	private List<TipoUnidadAdministrativa> resultado;

	public RespuestaTipoUnidadAdministrativa(String status, String mensaje, long l, List<TipoUnidadAdministrativa> resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RespuestaTipoUnidadAdministrativa() {
		super();
	}

	public List<TipoUnidadAdministrativa> getResultado() {
		return resultado;
	}

	public void setResultado(List<TipoUnidadAdministrativa> resultado) {
		this.resultado = resultado;
	}
}