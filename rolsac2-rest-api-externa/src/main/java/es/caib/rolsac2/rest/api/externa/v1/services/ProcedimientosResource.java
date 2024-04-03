package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.*;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroProcedimientos;
import es.caib.rolsac2.api.externa.v1.model.respuestas.*;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO, name = Constantes.ENTIDAD_PROCEDIMIENTO)
public class ProcedimientosResource {

    @EJB
    ProcedimientoServiceFacade procedimientoService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    /**
     * Listado de Procedimientos.
     *
     * @return Listado de Procedimientos
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarProcedimientos", summary = "Lista los procedimientos", description = "Lista los procedimientos disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientos.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarProcedimientos(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de procedimientos: " + FiltroProcedimientos.SAMPLE, name = "filtro", content = @Content(example = FiltroProcedimientos.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroProcedimientos.class))) FiltroProcedimientos filtro) throws ValidationException {

        if (filtro == null) {
            filtro = new FiltroProcedimientos();
        }

        Instant start = Instant.now();

        final ProcedimientoFiltro fg = filtro.toProcedimientoFiltro();


        String idiomaPorDefecto;
        if (fg.getIdEntidad() == null) {
            idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
        } else {
            idiomaPorDefecto = entidadService.getIdiomaPorDefecto(fg.getIdEntidad());
            if (idiomaPorDefecto == null) {
                idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
            }
        }
        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(idiomaPorDefecto);
        }

        // si no vienen los filtros se completan con los datos por defecto
        if (filtro.getFiltroPaginacion() != null) {
            fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
        }


        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de Publico Objetivo Entidad de procedimientos.
     *
     * @return Devuelve los tipos de público objetivo entidad del procedimiento
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/publicoObjetivoEntidad/{codigo}")
    @Operation(operationId = "listarPublicoObjetivoEntidad", summary = "Lista los tipos de público objetivo entidad del procedimiento", description = "Lista los tipos de público objetivo entidad del procedimiento dado por código WF")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoPublicoObjetivoEntidad.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarPublicoObjetivoEntidad(@Parameter(description = "Código procedimiento workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        List<TipoPublicoObjetivoEntidadDTO> result = new ArrayList<>();
        List<TipoPublicoObjetivoEntidad> lista = new ArrayList<>();
        TipoPublicoObjetivoEntidad elemento;

        Instant start = Instant.now();

        if (codigo != null) {
            result = procedimientoService.getTipoPubObjEntByCodProcWF(Long.valueOf(codigo));

            for (TipoPublicoObjetivoEntidadDTO nodo : result) {
                elemento = new TipoPublicoObjetivoEntidad(nodo, null, lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaTipoPublicoObjetivoEntidad(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de normativas de procedimientos.
     *
     * @return Devuelve las normativas del procedimiento
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/normativas/{codigo}")
    @Operation(operationId = "listarNormativas", summary = "Lista los normativas del procedimiento", description = "Lista los normativas del procedimiento dado por código WF")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarNormativas(@Parameter(description = "Código procedimiento workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<Normativa> lista = new ArrayList<>();
        Normativa elemento;

        String idiomaPorDefecto = procedimientoService.obtenerIdiomaEntidad(Long.valueOf(codigo));

        List<NormativaDTO> result = procedimientoService.getNormativasByCodProcWF(Long.valueOf(codigo));

        for (NormativaDTO nodo : result) {
            elemento = new Normativa(nodo, null, lang, true, idiomaPorDefecto);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de temas de procedimientos.
     *
     * @return Devuelve los temas del procedimiento
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/temas/{codigo}")
    @Operation(operationId = "listarTemas", summary = "Lista los temas del procedimiento", description = "Lista los temas del procedimiento dado por código WF")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTema.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTemas(@Parameter(description = "Código procedimiento workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        List<TemaDTO> result = new ArrayList<>();
        List<Tema> lista = new ArrayList<>();
        Tema elemento;
        Instant start = Instant.now();

        if (codigo != null) {
            result = procedimientoService.getTemasByCodProcWF(Long.valueOf(codigo));

            for (TemaDTO nodo : result) {
                elemento = new Tema(nodo, null, lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();
        return Response.ok(new RespuestaTema(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de documentos de procedimientos.
     *
     * @return Devuelve los documentos del procedimiento
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/documentos/{codigo}")
    @Operation(operationId = "listarDocumentos", summary = "Lista los documentos del procedimiento", description = "Lista los documentos del procedimiento dado por código WF")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarDocumentos(@Parameter(description = "Código procedimiento workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;
        Instant start = Instant.now();

        if (codigo != null) {
            result = procedimientoService.getDocumentosByCodProcWF(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de documentos LOPD de procedimientos.
     *
     * @return Devuelve los documentos LOPD del procedimiento
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/documentosLopd/{codigo}")
    @Operation(operationId = "listarDocumentosLopd", summary = "Lista los documentos LOPD del procedimiento", description = "Lista los documentos LOPD del procedimiento dado por código WF")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarDocumentosLopd(@Parameter(description = "Código procedimiento workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;
        Instant start = Instant.now();
        if (codigo != null) {
            result = procedimientoService.getDocumentosLOPDByCodProcWF(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) result.size(), lista, tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener un procedimiento.
     *
     * @param lang   Código de idioma
     * @param codigo Código del procedimiento
     * @return Devuelve el procedimiento
     * @throws Exception Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene un procedimiento", description = "Obtiene el procedimiento con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientos.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código procedimiento", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws Exception {

        final ProcedimientoFiltro fg = new ProcedimientoFiltro();

        Instant start = Instant.now();

        String idiomaPorDefecto = procedimientoService.obtenerIdiomaEntidad(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(idiomaPorDefecto);
        }
        fg.setCodigoProc(Long.parseLong(codigo));
        fg.setTipo("P");

        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaProcedimientos getRespuesta(final ProcedimientoFiltro filtro, final String idiomaPorDefecto, final Instant start) {
        Pagina<ProcedimientoBaseDTO> resultadoBusqueda = procedimientoService.findProcedimientosByFiltroRest(filtro);

        List<Procedimientos> lista = new ArrayList<>();
        Procedimientos elemento;

        for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Procedimientos((ProcedimientoDTO) nodo, null, filtro.getIdioma(), true, idiomaPorDefecto);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaProcedimientos(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) resultadoBusqueda.getItems().size(), lista, tiempoMiliSegundos);
    }
}
