package es.caib.rolsac2.api.externa.v1.exception;

import es.caib.rolsac2.api.externa.v1.model.respuestas.Responses;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;

public abstract class ParamException extends WebApplicationException {

    /**
     * An abstract parameter exception for the class of URI-parameter-based
     * exceptions.
     * <p>
     * All such exceptions of this type will contain a response with a 404
     * (Not Found) status code.
     */
    public static abstract class URIParamException extends ParamException {
        protected URIParamException(Throwable cause,
                Class<? extends Annotation> parameterType, String name, String defaultStringValue) {
            super(cause, Responses.NOT_FOUND, parameterType, name, defaultStringValue);
        }
    }

    /**
     * A URI-parameter-based exception for errors with {@link PathParam}.
     */
    public static class PathParamException extends URIParamException {
        public PathParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, PathParam.class, name, defaultStringValue);
        }
    }

    /**
     * A URI-parameter-based exception for errors with {@link MatrixParam}.
     */
    public static class MatrixParamException extends URIParamException {
        public MatrixParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, MatrixParam.class, name, defaultStringValue);
        }
    }

    /**
     * A URI-parameter-based exception for errors with {@link QueryParam}.
     */
    public static class QueryParamException extends URIParamException {
        public QueryParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, QueryParam.class, name, defaultStringValue);
        }
    }

    /**
     * A parameter exception for errors with {@link HeaderParam}.
     */
    public static class HeaderParamException extends ParamException {
        public HeaderParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, Responses.CLIENT_ERROR, HeaderParam.class, name, defaultStringValue);
        }
    }

    /**
     * A parameter exception for errors with {@link CookieParam}.
     */
    public static class CookieParamException extends ParamException {
        public CookieParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, Responses.CLIENT_ERROR, CookieParam.class, name, defaultStringValue);
        }
    }

    /**
     * A parameter exception for errors with {@link FormParam}.
     */
    public static class FormParamException extends ParamException {
        public FormParamException(Throwable cause, String name, String defaultStringValue) {
            super(cause, Responses.CLIENT_ERROR, FormParam.class, name, defaultStringValue);
        }
    }

    private final Class<? extends Annotation> parameterType;

    private final String name;

    private final String defaultStringValue;

    protected ParamException(Throwable cause, int status,
            Class<? extends Annotation> parameterType, String name, String defaultStringValue) {
        super(cause, status);
        this.parameterType = parameterType;
        this.name = name;
        this.defaultStringValue = defaultStringValue;
    }

    /**
     * Get the type of the parameter annotation.
     *
     * @return the type of the parameter annotation.
     */
    public Class<? extends Annotation> getParameterType() {
        return parameterType;
    }

    /**
     * Get the parameter name.
     *
     * @return the parameter name.
     */
    public String getParameterName() {
        return name;
    }

    /**
     * Get the default String value.
     *
     * @return the default String value.
     */
    public String getDefaultStringValue() {
        return defaultStringValue;
    }
}
