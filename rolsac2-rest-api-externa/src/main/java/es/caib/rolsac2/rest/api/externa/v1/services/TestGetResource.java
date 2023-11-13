package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.Procedimientos;
import es.caib.rolsac2.api.externa.v1.model.Servicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.TESTGET_SERVICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.TESTGET_SERVICIO, name = Constantes.TESTGET_SERVICIO)
public class TestGetResource {

    @EJB
    private ProcedimientoServiceFacade procedimientoService;

    /**
     * Metodo de tipo test para hacer una prueba que se llega a la url.
     *
     * @return
     * @throws DelegateException
     */
    @GET
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

        ProcedimientoFiltro filtro = new ProcedimientoFiltro();
        filtro.setIdioma("ca");
        //codigo = {Long@22870} 2882769
        //codigoWFPub = {Long@22871} 14868
        filtro.setCodigoProc(Long.parseLong("2882769"));
        filtro.setTipo("P");

        Pagina<ProcedimientoBaseDTO> resultadoBusqueda = procedimientoService.findProcedimientosByFiltroRest(filtro);


        for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
            Procedimientos x = new Procedimientos((ProcedimientoDTO) nodo, null, filtro.getIdioma(), true, "ca");
            //            lista.add(elemento);
        }

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
    @GET
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
