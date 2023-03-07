package es.caib.rolsac2.api.externa.v1.model.respuestas;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.api.externa.v1.model.Servicios;

/**
 * Respuesta FitxesUA
 *
 * @author Indra
 *
 */

@XmlRootElement
@Schema(name = "RespuestaServicios", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_SERVICIOS)
public class RespuestaServicios extends RespuestaBase {

	/** Resultado. **/
	@Schema(description = "Listado con los objetos de resultado", required = false)
	private List<Servicios> resultado;

	/** Url. **/
	@Schema(description = "Enlace tramite telematico", required = false)
	private String url;

	public RespuestaServicios(final String status, final String mensaje, final Long numeroElementos,
			final List<Servicios> resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	};

	public RespuestaServicios() {
		super();
	}

	public List<Servicios> getResultado() {
		return resultado;
	}

	public void setResultado(final List<Servicios> resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
}