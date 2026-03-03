package es.caib.rolsac2.rest.api.interna.v1.filters;

import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaFichero;
import es.caib.rolsac2.api.interna.v1.utils.ApiLimitUtil;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Filtro para limitar el número de elementos devueltos por la API.
 */
@Provider
public class ApiLimitFilter implements ContainerResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ApiLimitFilter.class);

    @EJB
    private SystemServiceFacade systemServiceFacade;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Object entity = responseContext.getEntity();

        if (entity == null) {
            return;
        }

        int limit = 0;
        try {
            String limitStr = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.API_MAX_LIMIT);
            if (limitStr != null && !limitStr.trim().isEmpty()) {
                limit = Integer.parseInt(limitStr.trim());
            }
        } catch (Exception e) {
            LOG.warn("Error al obtener la propiedad global API_MAX_LIMIT: {}", e.getMessage());
        }

        // Si api.max.limit = 0 no se aplicara el limite
        if (limit > 0) {
            if (entity instanceof RespuestaFichero) {
                RespuestaFichero resp = (RespuestaFichero) entity;
                if (resp.getResultado() != null && resp.getResultado().size() > limit) {
                    resp.setResultado(ApiLimitUtil.limitList(resp.getResultado(), limit));
                    resp.setItemsReturned(limit);
                    resp.setTotalCount(limit);
                }
            } else if (entity instanceof RespuestaBase) {
                RespuestaBase resp = (RespuestaBase) entity;

                // Calculo de las paginas que van a ser necesarias con el nuevo limite
                int totalCount = resp.getTotalCount() != null ? resp.getTotalCount() : 0;

                if (totalCount > limit) {
                    resp.setTotalCount(limit);

                    int pageSize = 0;
                    try {
                        if (resp.getPageSize() != null) {
                            pageSize = Integer.parseInt(resp.getPageSize());
                        }
                    } catch (NumberFormatException e) {
                        // ignorar
                    }

                    if (pageSize > 0) {
                        resp.setTotalPages((int) Math.ceil((double) limit / pageSize));
                    } else {
                        resp.setTotalPages(1);
                    }
                }

                int page = resp.getPage() != null ? resp.getPage() : 0;
                int pageSize = 0;
                try {
                    if (resp.getPageSize() != null) {
                        pageSize = Integer.parseInt(resp.getPageSize());
                    }
                } catch (NumberFormatException e) {
                    // ignora
                }

                int startIndex = page * pageSize;
                int maxItemsAllowed = limit - startIndex;

                if (maxItemsAllowed <= 0) {
                    resp.setItems(new java.util.ArrayList<>());
                    resp.setItemsReturned(0);
                } else if (resp.getItems() != null && resp.getItems().size() > maxItemsAllowed) {
                    resp.setItems(ApiLimitUtil.limitList(resp.getItems(), maxItemsAllowed));
                    resp.setItemsReturned(resp.getItems().size());
                } else if (resp.getItems() != null) {
                    resp.setItemsReturned(resp.getItems().size());
                }
            }
        }
    }
}