package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoMediaEdificio;
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
@Schema(name = "RespuestaEstadistica", description = Constantes.TXT_RESPUESTA + Constantes.ESTADISITICAS)
public class RespuestaEstadistica extends RespuestaBase {

	public RespuestaEstadistica(String status, String mensaje, long l) {
		super(status, mensaje, l);
	};

	public RespuestaEstadistica() {
		super();
	}

}