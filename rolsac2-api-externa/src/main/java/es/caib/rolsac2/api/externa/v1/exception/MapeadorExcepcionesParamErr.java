package es.caib.rolsac2.api.externa.v1.exception;


import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class MapeadorExcepcionesParamErr implements ExceptionMapper<ParamException> {

	public Response toResponse(ParamException ex) {
		return Response.status(ex.getResponse().getStatus())
				.entity(new RespuestaError(ex))
				.type(MediaType.APPLICATION_JSON) //this has to be set to get the generated JSON
				.build();
	}

}
