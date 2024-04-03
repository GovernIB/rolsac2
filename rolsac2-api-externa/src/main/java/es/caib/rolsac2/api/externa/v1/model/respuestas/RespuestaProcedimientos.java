package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Procedimientos;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Procedimientos
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaProcedimientos", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PROCEDIMIENTO)
public class RespuestaProcedimientos extends RespuestaBase {

	/**
	 * Resultado.
	 **/
	@Schema(description = "Listado con los objetos de resultado")
	private List<Procedimientos> resultado;

	/**
	 * Url.
	 **/
	@Schema(description = "Enlace tramite telematico")
	private String url;

	/**
	 * Constructor
	 *
	 * @param status          Status de la consulta
	 * @param mensaje         Mensaje de error
	 * @param numeroElementos Numero de elementos
	 * @param resultado       Lista de datos
	 * @param tiempo          Tiempo
	 */
	public RespuestaProcedimientos(final String status, final String mensaje, final Long numeroElementos, final List<Procedimientos> resultado, final Long tiempo) {
		super(status, mensaje, numeroElementos, tiempo);
		this.resultado = resultado;
	}

	/**
	 * Constructor vacio.
	 */
	public RespuestaProcedimientos() {
		super();
	}

	/**
	 * Devuelve el resultado.
	 *
	 * @return
	 */
	public List<Procedimientos> getResultado() {
		return resultado;
	}

	/**
	 * Establece el resultado.
	 *
	 * @param resultado
	 */
	public void setResultado(final List<Procedimientos> resultado) {
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