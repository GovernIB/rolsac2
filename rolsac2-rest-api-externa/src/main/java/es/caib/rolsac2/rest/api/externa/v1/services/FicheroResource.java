package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.Fichero;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaFichero;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_FICHERO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_FICHERO, name = Constantes.ENTIDAD_FICHERO)
public class FicheroResource {

    @EJB
    FicheroServiceFacade ficheroService;

    @Inject
    SystemServiceFacade systemServiceFacade;

    /**
     * Para obtener el fichero.
     *
     * @param codigo Codigo Fichero
     * @return Fichero
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getFichero", summary = "Obtiene un fichero", description = "Obtiene la fichero con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaFichero.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getFichero(@Parameter(description = "Código de fichero", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        return Response.ok(getRespuesta(Long.valueOf(codigo), start), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener los detalles de un fichero sin el contenido
     *
     * @param codigo Codigo Fichero
     * @return Respuesta metadatos
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/metadatos/{codigo}")
    @Operation(operationId = "getFichero", summary = "Obtiene un fichero", description = "Obtiene la fichero con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaFichero.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getFicheroSinContenido(@Parameter(description = "Código de fichero", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        return Response.ok(getRespuestaMetadatos(Long.valueOf(codigo), start), MediaType.APPLICATION_JSON).build();
    }


    private RespuestaFichero getRespuesta(Long codigo, Instant start) {
        String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO resultadoBusqueda = ficheroService.getContentById(codigo, path);

        List<Fichero> lista = new ArrayList<>();

        if (resultadoBusqueda != null) {
            Fichero elemento = new Fichero(resultadoBusqueda, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), null, true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaFichero(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda != null ? 1 : 0, lista, tiempoMiliSegundos);
    }

    private RespuestaFichero getRespuestaMetadatos(Long codigo, Instant start) {
        String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO resultadoBusqueda = ficheroService.getContentMetadata(codigo, path);

        List<Fichero> lista = new ArrayList<>();

        if (resultadoBusqueda != null) {
            Fichero elemento = new Fichero(resultadoBusqueda, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), null, true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaFichero(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda != null ? 1 : 0, lista, tiempoMiliSegundos);
    }
}
