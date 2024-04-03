package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.*;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroServicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.*;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO, name = Constantes.ENTIDAD_SERVICIO)
public class ServiciosResource {

    @EJB
    ProcedimientoServiceFacade servicioService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    /**
     * Metodo de tipo test para hacer una prueba que se llega a la url.
     *
     * @return RespuestaServicios
     * @throws ValidationException Manejo de excepciones
     */
    @GET
    @Path("/test")
    @Operation(operationId = "test", summary = "Test", description = "Test")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response test() throws ValidationException {

        Instant start = Instant.now();
        List<Servicios> lista = new ArrayList<>();
        Servicios elemento;
        elemento = new Servicios();
        elemento.setCodigo(1L);
        elemento.setNombre("nombre");
        lista.add(elemento);
        lista.add(elemento);
        lista.add(elemento);

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        RespuestaServicios resp = new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(3), (long) (3), lista, tiempoMiliSegundos);
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de Servicios.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de servicios
     * @return Listado de Servicios
     * @throws ValidationException Manejo de excepciones
     */
    @POST
    @Path("/")
    @Operation(operationId = "listarServicios", summary = "Lista los servicios", description = "Lista los servicios disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarServicios(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de servicios: " + FiltroServicios.SAMPLE, name = "filtro", content = @Content(example = FiltroServicios.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroServicios.class))) FiltroServicios filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroServicios();
        }

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
     * Listado de Servicios.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de servicios
     * @return Listado de Servicios
     * @throws ValidationException Manejo de excepciones
     */
    @POST
    @Path("/numTotal")
    @Operation(operationId = "numeroServicios", summary = "Devuelve el número total de servicios", description = "Devuelve el número total de servicios que se corresponden al filtro indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response numeroServicios(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de servicios: " + FiltroServicios.SAMPLE, name = "filtro", content = @Content(example = FiltroServicios.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroServicios.class))) FiltroServicios filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroServicios();
        }

        final ProcedimientoFiltro fg = filtro.toProcedimientoFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else if (filtro.getIdEntidad() != null) {
            EntidadFiltro filtroEntidad = new EntidadFiltro();
            filtroEntidad.setCodigo(filtro.getIdEntidad());
            Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(filtroEntidad);
            if (resultadoBusqueda.getTotal() > 0 && resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest() != null && !resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest().isEmpty()) {
                fg.setIdioma(resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest());
            } else {
                fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
            }
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        // si no vienen los filtros se completan con los datos por defecto
        if (filtro.getFiltroPaginacion() != null) {
            fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
        }

        Long total = servicioService.countByFiltro(fg);

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();


        RespuestaBase respuesta = new RespuestaBase(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(total.intValue()), total, tiempoMiliSegundos);


        return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener el enlace.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Enlace
     * @throws ValidationException Manejo de excepciones
     */
    @POST
    @Path("/enlaceTelematico/{codigo}")
    @Operation(operationId = "getEnlaceTelematico", summary = "Obtiene enlace telematico", description = "Obtiene enlace telematico dado servicio")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getEnlaceTelematico(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws ValidationException {
        Instant start = Instant.now();
        final ProcedimientoFiltro fg = new ProcedimientoFiltro();
        fg.setCodigoWF(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        fg.setTipo("S");
        final String url = servicioService.getEnlaceTelematicoByServicio(fg);
        RespuestaServicios respuesta = new RespuestaServicios();
        respuesta.setUrl(url);
        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();
        respuesta.setTiempo(tiempoMiliSegundos);
        return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener un servicio.
     *
     * @param lang   Código de idioma
     * @param codigo Código del servicio
     * @return Servicio
     * @throws Exception Manejo de excepciones
     */
    @POST
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene un servicio", description = "Obtiene el servicio con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws Exception {

        Instant start = Instant.now();
        final ProcedimientoFiltro fg = new ProcedimientoFiltro();
        String idiomaPorDefecto = servicioService.obtenerIdiomaEntidad(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(idiomaPorDefecto);
        }
        fg.setCodigoProc(Long.parseLong(codigo));
        fg.setTipo("S");

        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start), MediaType.APPLICATION_JSON).build();
    }


    private RespuestaServicios getRespuesta(final ProcedimientoFiltro filtro, String idiomaPorDefecto, Instant start) {
        Pagina<ProcedimientoBaseDTO> resultadoBusqueda = servicioService.findProcedimientosByFiltroRest(filtro);

        List<Servicios> lista = new ArrayList<>();
        Servicios elemento;

        for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Servicios((ServicioDTO) nodo, null, filtro.getIdioma(), true, idiomaPorDefecto);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (resultadoBusqueda.getItems().size()), lista, tiempoMiliSegundos);
    }

    /**
     * Listado de Publico Objetivo Entidad de servicios.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Listado de Publico Objetivo Entidad de servicios
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/publicoObjetivoEntidad/{codigo}")
    @Operation(operationId = "listarPublicoObjetivoEntidad", summary = "Lista los tipos de público objetivo entidad del servicio", description = "Lista los tipos de público objetivo entidad del servicio dado por código workflow")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoPublicoObjetivoEntidad.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarPublicoObjetivoEntidad(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<TipoPublicoObjetivoEntidadDTO> result = new ArrayList<>();
        List<TipoPublicoObjetivoEntidad> lista = new ArrayList<>();
        TipoPublicoObjetivoEntidad elemento;

        if (codigo != null) {
            result = servicioService.getTipoPubObjEntByCodProcWF(Long.valueOf(codigo));

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
     * Listado de normativas de servicios.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Listado de normativas de servicios
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/normativas/{codigo}")
    @Operation(operationId = "listarNormativas", summary = "Lista los normativas del servicio", description = "Lista los normativas del servicio dado por código workflow")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarNormativas(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<NormativaDTO> result = new ArrayList<>();
        List<Normativa> lista = new ArrayList<>();
        Normativa elemento;

        if (codigo != null) {
            result = servicioService.getNormativasByCodProcWF(Long.valueOf(codigo));

            for (NormativaDTO nodo : result) {
                elemento = new Normativa(nodo, null, lang, true, null);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de temas de servicios.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Listado de temas de servicios
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/temas/{codigo}")
    @Operation(operationId = "listarTemas", summary = "Lista los temas del servicio", description = "Lista los temas del servicio dado por código workflow")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTema.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTemas(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {
        Instant start = Instant.now();
        List<TemaDTO> result = new ArrayList<>();
        List<Tema> lista = new ArrayList<>();
        Tema elemento;

        if (codigo != null) {
            result = servicioService.getTemasByCodProcWF(Long.valueOf(codigo));

            for (TemaDTO nodo : result) {
                elemento = new Tema(nodo, null, lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaTema(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de documentos de servicios.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Listado de documentos de servicios
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/documentos/{codigo}")
    @Operation(operationId = "listarDocumentos", summary = "Lista los documentos del servicio", description = "Lista los documentos del servicio dado por código workflow")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarDocumentos(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;

        if (codigo != null) {
            result = servicioService.getDocumentosByCodProcWF(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), lang, true);
                lista.add(elemento);
            }
        }
        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();


        return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de documentos LOPD de servicios.
     *
     * @param codigo Código del servicio workflow
     * @param lang   Código de idioma
     * @return Listado de documentos LOPD de servicios
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/documentosLopd/{codigo}")
    @Operation(operationId = "listarDocumentosLopd", summary = "Lista los documentos LOPD del servicio", description = "Lista los documentos LOPD del servicio dado por código workflow")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarDocumentosLopd(@Parameter(description = "Código servicio workflow", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;

        if (codigo != null) {
            result = servicioService.getDocumentosLOPDByCodProcWF(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), lang, true);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), (long) (result.size()), lista, tiempoSegundos), MediaType.APPLICATION_JSON).build();
    }

}
