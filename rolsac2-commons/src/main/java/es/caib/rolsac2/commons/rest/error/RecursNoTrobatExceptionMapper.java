package es.caib.rolsac2.commons.rest.error;

import es.caib.rolsac2.service.exception.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Cas particular d'una subclasse de ServiceException que indica que no s'ha trobat un recurs.
 * En aquest cas, retornam un error 404.
 *
 * @author areus
 */
@Provider
public class RecursNoTrobatExceptionMapper implements ExceptionMapper<RecursoNoEncontradoException> {

    private static final Logger LOG = LoggerFactory.getLogger(RecursNoTrobatExceptionMapper.class);

    @Override
    public Response toResponse(RecursoNoEncontradoException recursNoTrobatException) {
        LOG.error("Rebut un error de RecursNoTrobatException");
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
