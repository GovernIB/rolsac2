package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.ProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO, name = Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
public class ProcedimientoDocumentoResource {

    @EJB
    ProcedimientoServiceFacade procedimientoService;

    @EJB
    SystemServiceFacade systemService;


    /**
     * Listado de procedimientoDocumentos.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de procedimientoDocumentos
     * @return Listado de procedimientoDocumentos
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listar", summary = "Lista los procedimientos documentos", description = "Lista los procedimientos documentos disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listar(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de procedimiento documentos: " + FiltroProcedimientoDocumento.SAMPLE, name = "filtro", content = @Content(example = FiltroProcedimientoDocumento.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroProcedimientoDocumento.class))) FiltroProcedimientoDocumento filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroProcedimientoDocumento();
        }

        ProcedimientoDocumentoFiltro fg = filtro.toProcedimientoDocumentoFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        // si no vienen los filtros se completan con los datos por defecto
        if (filtro.getFiltroPaginacion() != null) {
            fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
        }

        // si viene el orden intentamos rellenarlo
        if (filtro.getCampoOrden() != null) {
            fg.setOrderBy(filtro.getCampoOrden().getCampo());
            fg.setAscendente(filtro.getCampoOrden().getTipoOrden().compareTo("ASC") == 0);
        }

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener una procedimientoDocumento.
     *
     * @param codigo Código del procedimientoDocumento
     * @param lang   Código de  idioma
     * @return Devuelve la procedimientoDocumento
     * @throws Exception           Manejo de excepciones
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene una procedimiento documento", description = "Obtiene el procedimiento documento con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código procedimiento documento", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws Exception, ValidationException {

        Instant start = Instant.now();
        ProcedimientoDocumentoFiltro fg = new ProcedimientoDocumentoFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }
        fg.setCodigo(Long.valueOf(codigo));

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaProcedimientoDocumento getRespuesta(ProcedimientoDocumentoFiltro filtro, Instant start) {
        Pagina<ProcedimientoDocumentoDTO> resultadoBusqueda = procedimientoService.findProcedimientoDocumentoByFiltroRest(filtro);

        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;

        for (ProcedimientoDocumentoDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), filtro.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }

}
