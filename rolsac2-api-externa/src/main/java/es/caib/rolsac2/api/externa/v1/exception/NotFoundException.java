package es.caib.rolsac2.api.externa.v1.exception;

import java.net.URI;
import javax.ws.rs.WebApplicationException;

import es.caib.rolsac2.api.externa.v1.model.respuestas.Responses;

/**
 * A HTTP 404 (Not Found) exception.
 *
 * @author Paul.Sandoz@Sun.Com
 */
public class NotFoundException extends WebApplicationException {

    private final URI notFoundUri;

    /**
     * Create a HTTP 404 (Not Found) exception.
     */
    public NotFoundException() {
        this((URI)null);
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     *
     * @param notFoundUri the URI that cannot be found.
     */
    public NotFoundException(URI notFoundUri) {
        super(Responses.notFound().build());
        this.notFoundUri = notFoundUri;
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     *
     * @param message the String that is the entity of the 404 response.
     */
    public NotFoundException(String message) {
        this(message, null);
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     *
     * @param message the String that is the entity of the 404 response.
     * @param notFoundUri the URI that cannot be found.
     */
    public NotFoundException(String message, URI notFoundUri) {
        super(Responses.notFound().
                entity(message).type("text/plain").build());
        this.notFoundUri = notFoundUri;
    }

    /**
     * Get the URI that is not found.
     *
     * @return the URI that is not found.
     */
    public URI getNotFoundUri() {
        return notFoundUri;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " for uri: " + notFoundUri;
    }
}
