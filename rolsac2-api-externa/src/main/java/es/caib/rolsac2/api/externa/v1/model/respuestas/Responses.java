package es.caib.rolsac2.api.externa.v1.model.respuestas;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * Common status codes and responses.
 *
 * @author Paul.Sandoz@Sun.Com
 */
public class Responses {
    public static final int NO_CONTENT = 204;

    public static final int NOT_MODIFIED = 304;

    public static final int CLIENT_ERROR = 400;

    public static final int NOT_FOUND = 404;

    public static final int METHOD_NOT_ALLOWED = 405;

    public static final int NOT_ACCEPTABLE = 406;

    public static final int CONFLICT = 409;

    public static final int PRECONDITION_FAILED = 412;

    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    private static StatusType METHOD_NOT_ALLOWED_TYPE = new StatusType() {
        @Override
        public int getStatusCode() {
            return METHOD_NOT_ALLOWED;
        }

        @Override
        public Family getFamily() {
            return Family.CLIENT_ERROR;
        }

        @Override
        public String getReasonPhrase() {
            return "Method Not Allowed";
        }
    };

    public static ResponseBuilder noContent() {
        return status(Status.NO_CONTENT);
    }

    public static ResponseBuilder notModified() {
        return status(Status.NOT_MODIFIED);
    }

    public static ResponseBuilder clientError() {
        return status(Status.BAD_REQUEST);
    }

    public static ResponseBuilder notFound() {
        return status(Status.NOT_FOUND);
    }

    public static ResponseBuilder methodNotAllowed() {
        return status(METHOD_NOT_ALLOWED_TYPE);
    }

    public static ResponseBuilder notAcceptable() {
        return status(Status.NOT_ACCEPTABLE);
    }

    public static ResponseBuilder conflict() {
        return status(Status.CONFLICT);
    }

    public static ResponseBuilder preconditionFailed() {
        return status(Status.PRECONDITION_FAILED);
    }

    public static ResponseBuilder unsupportedMediaType() {
        return status(Status.UNSUPPORTED_MEDIA_TYPE);
    }

    private static ResponseBuilder status(StatusType status) {
        return Response.status(status);
    }
}
