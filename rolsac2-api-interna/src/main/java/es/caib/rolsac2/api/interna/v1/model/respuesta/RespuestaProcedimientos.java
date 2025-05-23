package es.caib.rolsac2.api.interna.v1.model.respuesta;

import es.caib.rolsac2.api.interna.v1.model.Procedimientos;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
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
public class RespuestaProcedimientos extends RespuestaBase<Procedimientos> {


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
		setData(resultado);
		setItemsReturned("Procedimientos");
	}

	/**
	 * Constructor vacio.
	 */
	public RespuestaProcedimientos() {
		super();
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


	public static Builder builder() {
		return new Builder<Procedimientos>();
	}
}