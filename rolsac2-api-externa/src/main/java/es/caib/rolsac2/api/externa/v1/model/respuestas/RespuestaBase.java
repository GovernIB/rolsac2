package es.caib.rolsac2.api.externa.v1.model.respuestas;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a todas las respuestas.
 *
 * @author indra
 *
 */
@XmlRootElement
@Schema(name = "RespuestaBase", description = "Respuesta Base")
public class RespuestaBase {

	/** Status a retornar. **/
	@Schema(required = true, description = "Status")
	private String status;

	/** Mensaje de  error. **/
	@Schema(required = false, description = "Mensaje")
	private String mensaje;

	/** Numero de Elementos. **/
	@Schema(required = true, description = "Numero de Elementos")
	private Long numeroElementos;


	//private List<T> resultado;

	public RespuestaBase(String status, String mensaje, Long l) {
		super();
		this.status = status;
		this.mensaje = mensaje;
		this.numeroElementos = l;
	}

	public RespuestaBase() {
		this.status = null;
		this.mensaje = null;
		this.numeroElementos = null;
	};

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the mensajeError
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensajeError the mensajeError to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the numeroElementos
	 */
	public Long getNumeroElementos() {
		return numeroElementos;
	}

	/**
	 * @param numeroElementos the numeroElementos to set
	 */
	public void setNumeroElementos(Long numeroElementos) {
		this.numeroElementos = numeroElementos;
	}

}
