package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.Servicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.ValidationException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.TESTPOST_SERVICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.TESTPOST_SERVICIO, name = Constantes.TESTPOST_SERVICIO)
public class TestPostResource {


    /**
     * Metodo de tipo test para hacer una prueba que se llega a la url.
     *
     * @return
     * @throws DelegateException
     */
    @POST
    @Path("/")
    @Operation(operationId = "test", summary = "Test", description = "Test")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response test() throws DelegateException, ExcepcionAplicacion, ValidationException {
        List<Servicios> lista = new ArrayList<>();
        Servicios elemento = null;
        elemento = new Servicios();
        elemento.setCodigo(1l);
        elemento.setNombre("nombre");
        lista.add(elemento);
        lista.add(elemento);
        lista.add(elemento);
        RespuestaServicios resp = new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(3), Long.valueOf(3), lista);
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }


    /**
     * Para obtener un servicio.
     *
     * @return
     * @throws Exception
     * @Parameter idioma
     * @Parameter id
     */
    @POST
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene un servicio", description = "Obtiene el servicio con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId() throws Exception, ValidationException {

        List<Servicios> lista = new ArrayList<>();
        Servicios elemento = null;
        elemento = new Servicios();
        elemento.setCodigo(1l);
        elemento.setNombre("nombre");
        lista.add(elemento);
        lista.add(elemento);
        lista.add(elemento);
        RespuestaServicios resp = new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(3), Long.valueOf(3), lista);
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

}
