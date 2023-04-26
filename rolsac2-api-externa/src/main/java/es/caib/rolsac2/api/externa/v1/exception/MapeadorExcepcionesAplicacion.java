package es.caib.rolsac2.api.externa.v1.exception;

import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Clase encargada de mapear los errores controlados de la aplicación
 *
 * @author Indra
 *
 */

@Provider
public class MapeadorExcepcionesAplicacion implements ExceptionMapper<ExcepcionAplicacion> {

	public Response toResponse(ExcepcionAplicacion ex) {
		return Response.status(ex.getStatus())
				.entity(new RespuestaError(ex))
				.type(MediaType.APPLICATION_JSON).
				build();
	}

}