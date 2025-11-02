package es.caib.rolsac2.commons.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.transaction.RollbackException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.InvocationTargetException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Permet mapejar a una respota qualsevol excepció unchecked de la capa EJB.
 * Envia un codi d'error 500 i evita que els detalls de l'excepció arribin al client,
 * ja que un error de sistema l'ha de mirar l'administrador.
 *
 * @author Indra
 */
@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    private static final Logger LOG = LoggerFactory.getLogger(EJBExceptionMapper.class);
    private static final int MAX_UNWRAP_DEPTH = 10;

    @Override
    public Response toResponse(EJBException e) {
        // Capa exterior: jamás propagar nada fuera del mapper
        try {
            final String correlationId = safeUUID();

            // Desencadenar causa raíz de forma segura y acotada
            Throwable root = safeUnwrap(preferCausedBy(e));
            if (root == null) root = e;

            // Log estructurado con stacktrace; si el logger fallara, no rompemos la respuesta
            try {
                LOG.error("EJBException atrapada. correlationId={}, top={}, root={}",
                        correlationId, safeToString(e), safeToString(root), e);
            } catch (Throwable ignore) {
                // No hacer nada: nunca romper por culpa del logger
            }

            // Intentar respuesta JSON amigable
            try {
                String body = "{\"error\":\"internal_server_error\","
                        + "\"correlationId\":\"" + correlationId + "\","
                        + "\"timestamp\":\"" + OffsetDateTime.now(ZoneOffset.UTC) + "\"}";
                return Response.serverError()
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(body)
                        .build();
            } catch (Throwable ignore) {
                // Si hasta aquí hubiera problemas (p. ej. provider JSON), caemos al fallback
            }

        } catch (Throwable t) {
            // Si algo raro ocurriese antes, lo registramos de forma best-effort
            try {
                LOG.error("Fallo interno dentro de EJBExceptionMapper", t);
            } catch (Throwable ignore) {
                // Ignorar para no propagar
            }
        }

        // Fallback definitivo: siempre devuelve algo sencillo y seguro
        return Response.serverError()
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity("internal_server_error")
                .build();
    }

    /* =================== Utilidades seguras =================== */

    private String safeUUID() {
        try {
            return UUID.randomUUID().toString();
        } catch (Throwable t) {
            // Último recurso: timestamp ISO (evita depender de UUID en entornos extraños)
            try {
                return "ts-" + OffsetDateTime.now(ZoneOffset.UTC).toString();
            } catch (Throwable ignored) {
                return "unknown"; // nunca lanzar
            }
        }
    }

    private Throwable preferCausedBy(EJBException e) {
        try {
            if (e == null) return null;
            Throwable t = e.getCausedByException();
            if (t != null) return t;
            t = e.getCause();
            return t != null ? t : e;
        } catch (Throwable ignored) {
            return e;
        }
    }

    private Throwable safeUnwrap(Throwable t) {
        if (t == null) return null;
        int depth = 0;
        while (depth++ < MAX_UNWRAP_DEPTH) {
            Throwable next = null;
            try {
                if (t instanceof InvocationTargetException) {
                    Throwable target = ((InvocationTargetException) t).getTargetException();
                    if (target != null) next = target;
                }
                if (next == null && t instanceof java.util.concurrent.CompletionException) {
                    if (t.getCause() != null) next = t.getCause();
                }
                if (next == null && t instanceof RollbackException) {
                    if (t.getCause() != null) next = t.getCause();
                }
                if (next == null) {
                    Throwable cause = t.getCause();
                    if (cause != null && cause != t) next = cause;
                }
            } catch (Throwable ignored) {
                // Si algo fallara al inspeccionar, paramos el unwrap
                next = null;
            }
            if (next == null) break;
            t = next;
        }
        return t;
    }

    private String safeToString(Object o) {
        try {
            return String.valueOf(o);
        } catch (Throwable t) {
            try {
                return (o != null) ? o.getClass().getName() : "null";
            } catch (Throwable ignored) {
                return "<?>"; // nunca lanzar
            }
        }
    }
}
