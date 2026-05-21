package es.caib.rolsac2.rest.api.interna.v1.services;

import es.caib.rolsac2.api.interna.v1.model.ProcedimientoDocumento;
import es.caib.rolsac2.api.interna.v1.model.Tramite;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroTramite;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TRAMITE)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TRAMITE, name = Constantes.ENTIDAD_TRAMITE)
public class TramiteResource {
    private static final Logger LOG = LoggerFactory.getLogger(TramiteResource.class);

    @EJB
    ProcedimientoServiceFacade procedimientoService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    @Context
    private UriInfo uriInfo;

    /**
     * Listado de procedimientoTramites.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de trámites
     * @return Listado de procedimientoTramites
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarTramites", summary = "Lista los trámites", description = "Lista los trámites disponibles en función de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTramites(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de trámites: " + FiltroTramite.SAMPLE, name = "filtro", content = @Content(example = FiltroTramite.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTramite.class))) FiltroTramite filtro) throws ValidationException {


        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroTramite();
        }

        ProcedimientoTramiteFiltro fg = filtro.toProcedimientoTramiteFiltro();
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
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getOffset());
        }

        // Limitar el número total de elementos según API_MAX_LIMIT
        Integer apiMaxLimit = null;
    	try {
            String apiMaxLimitStr = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.API_MAX_LIMIT);
	        apiMaxLimit = Integer.parseInt(apiMaxLimitStr);
	        if (apiMaxLimit > 0) {
		        int offset = fg.getPaginaFirst() != null ? fg.getPaginaFirst() : 0;
		        int size = fg.getPaginaTamanyo() != null ? fg.getPaginaTamanyo() : 10;
		        if (offset >= apiMaxLimit) {
		            fg.setPaginaFirst(0);
		            fg.setPaginaTamanyo(0);
		        } else if (offset + size > apiMaxLimit) {
		            fg.setPaginaTamanyo(apiMaxLimit - offset);
		        }
	        } else {
	        	apiMaxLimit = null;
	        }
    	} catch (NumberFormatException e){
    		apiMaxLimit = null;
        }

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start, url, apiMaxLimit), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener una procedimientoTramite.
     *
     * @param codigo Código trámite
     * @param lang   Código de idioma
     * @return ProcedimientoTramite
     * @throws Exception Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene una trámite", description = "Obtiene el trámite con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código trámite", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
                             @Parameter(description = "Estados WF, siendo los valores \"D/M/T/A\", (D=Definitivo, M=Modificado, T=Todos (publicado o sino modificado), A=Ambos (publicado y modificado)) ", name = "estadoWF", in = ParameterIn.QUERY) @QueryParam("estadoWF") String estadoWF) throws Exception {

        Instant start = Instant.now();
        try {
            ProcedimientoTramiteFiltro fg = new ProcedimientoTramiteFiltro();
            String idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
            if (lang != null) {
                fg.setIdioma(lang);
            } else {
                fg.setIdioma(idiomaPorDefecto);
            }
            fg.setCodigoTramite(Long.valueOf(codigo));
            fg.setEstadoWF(Objects.requireNonNullElse(estadoWF, "T"));

            URI uriCompleta = uriInfo.getRequestUri();
            String url = uriCompleta.toString();

            return Response.ok(getRespuesta(fg, idiomaPorDefecto, start, url, null), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("No entity found for query")) {
                long tiempoMiliSegundos = Duration.between(start, Instant.now()).toMillis();
                RespuestaBase respuesta = new RespuestaBase(new ArrayList<>(), tiempoMiliSegundos);
                respuesta.setMensaje(e.getMessage());
                return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
            } else {
                throw e;
            }
        }
    }

    private RespuestaBase getRespuesta(ProcedimientoTramiteFiltro filtro, String idiomaPorDefecto, Instant start, String url, Integer apiMaxLimit) {
        Pagina<ProcedimientoTramiteDTO> resultadoBusqueda = procedimientoService.findProcedimientoTramiteByFiltroRest(filtro);

        List<Tramite> lista = new ArrayList<>();
        Tramite elemento;

        for (ProcedimientoTramiteDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Tramite(nodo, null, filtro.getIdioma(), true, idiomaPorDefecto);
            lista.add(elemento);
        }

        // Limitar el total de elementos reportado según API_MAX_LIMIT
        int total = (int) resultadoBusqueda.getTotal();
        if (apiMaxLimit != null && total > apiMaxLimit) {
            total = apiMaxLimit;
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaBase(
                total,
                lista.size(),
                filtro.getPaginaTamanyo(),
                filtro.getPaginaFirst(),
                url,
                lista,
                tiempoMiliSegundos);
    }

    /**
     * Para obtener el enlace.
     *
     * @param codigo Código trámite
     * @param lang   Código de idioma
     * @return Enlace telemático
     * @throws ValidationException Manejo de excepciones
     */
    @POST
    @Path("/enlaceTelematico/{codigo}")
    @Operation(operationId = "getEnlaceTelematico", summary = "Obtiene enlace telematico", description = "Obtiene el enlace telemático dado tramite")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getEnlaceTelematico(@Parameter(description = "Código trámite", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws ValidationException {

        Instant start = Instant.now();
        ProcedimientoTramiteFiltro fg = new ProcedimientoTramiteFiltro();
        fg.setCodigo(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        final String url = procedimientoService.getEnlaceTelematicoByTramite(fg);
        RespuestaBase respuesta = new RespuestaBase();
        respuesta.setResultadoURL(url);
        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();
        respuesta.setTiempo(tiempoMiliSegundos);
        return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de documentos de tramites.
     *
     * @param codigo Código trámite
     * @param lang   Código de idioma
     * @return Lista de documentos
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/documentos/{codigo}")
    @Operation(operationId = "listarDocumentos", summary = "Lista los documentos del trámite", description = "Lista los documentos del trámite dado por código")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarDocumentos(@Parameter(description = "Código trámite", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;
        String idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
        String idioma;

        if (lang != null) {
            idioma = lang;
        } else {
            idioma = idiomaPorDefecto;
        }

        if (codigo != null) {
            result = procedimientoService.getDocumentosByTram(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), idioma, true, idiomaPorDefecto);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(new RespuestaBase(
                (int) lista.size(),
                lista.size(),
                "0",
                0,
                0,
                url,
                lista,
                tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de modelos de tramites.
     *
     * @param codigo Código trámite
     * @param lang   Código de idioma
     * @return Lista de modelos
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/modelos/{codigo}")
    @Operation(operationId = "listarModelos", summary = "Lista los modelos del trámite", description = "Lista los modelos del trámite dado por código")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarModelos(@Parameter(description = "Código trámite", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
        List<ProcedimientoDocumento> lista = new ArrayList<>();
        ProcedimientoDocumento elemento;
        String idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
        String idioma;

        if (lang != null) {
            idioma = lang;
        } else {
            idioma = idiomaPorDefecto;
        }

        if (codigo != null) {
            result = procedimientoService.getModelosByTram(Long.valueOf(codigo));

            for (ProcedimientoDocumentoDTO nodo : result) {
                elemento = new ProcedimientoDocumento(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), idioma, true, idiomaPorDefecto);
                lista.add(elemento);
            }
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(new RespuestaBase(
                (int) lista.size(),
                lista.size(),
                "0",
                0,
                0,
                url,
                lista,
                tiempoMiliSegundos), MediaType.APPLICATION_JSON).build();
    }

}
