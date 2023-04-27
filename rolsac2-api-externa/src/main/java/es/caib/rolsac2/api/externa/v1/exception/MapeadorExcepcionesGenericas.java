package es.caib.rolsac2.api.externa.v1.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;

@Provider
public class MapeadorExcepcionesGenericas implements ExceptionMapper<Exception> {

	private static final Logger LOG = LoggerFactory.getLogger(MapeadorExcepcionesGenericas.class);

	public Response toResponse(Exception ex) {

		int status = getHttpStatus(ex);
		String mensaje = StringUtils.isEmpty(ex.getMessage()) ? Response.Status.fromStatusCode(status).getReasonPhrase()
				: ex.getMessage();
		RespuestaError respuesta = new RespuestaError(status + "", mensaje);
		LOG.error("", ex);
		return Response.status(status).entity(respuesta).type(MediaType.APPLICATION_JSON).build();
	}

	private int getHttpStatus(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatus();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		}
	}
}
