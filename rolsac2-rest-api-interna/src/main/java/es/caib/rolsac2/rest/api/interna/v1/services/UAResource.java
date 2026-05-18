package es.caib.rolsac2.rest.api.interna.v1.services;

import es.caib.rolsac2.api.interna.v1.model.UnidadAdministrativa;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroUA;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_UA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_UA, name = Constantes.ENTIDAD_UA)
public class UAResource {

    @EJB
    UnidadAdministrativaServiceFacade unidadAdministrativaService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    @Context
    private UriInfo uriInfo;

    /**
     * Listado de unidades administrativas.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de unidades administrativas
     * @return Listado de unidades administrativas
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarUA", summary = "Lista las Unidades Administrativas", description = "Lista las Unidades administrativas disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarUA(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de Unidades Administrativas: " + FiltroUA.SAMPLE, name = "filtro", content = @Content(example = FiltroUA.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroUA.class))) FiltroUA filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroUA();
        }

        UnidadAdministrativaFiltro fg = filtro.toUnidadAdministrativaFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else if (filtro.getCodEnti() != null) {
            EntidadFiltro filtroEntidad = new EntidadFiltro();
            filtroEntidad.setCodigo(filtro.getCodEnti());
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
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getOffset());
        }

        // si viene el orden intentamos rellenarlo
        if (filtro.getOrden() != null) {
            fg.setOrderBy(filtro.getOrden().getCampo());
            fg.setAscendente(filtro.getOrden().getTipoOrden().compareTo("ASC") == 0);
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

        return Response.ok(getRespuesta(fg, start, url, apiMaxLimit), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener una unidad administrativa.
     *
     * @param codigo Código de la unidad administrativa
     * @return UnidadAdministrativa
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getUA", summary = "Obtiene una Unidad Administrativa", description = "Obtiene La Unidad Administrativa con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
    public Response getUA(@Parameter(description = "Código Unidad Administrativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        try {
            UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();

            if (lang != null) {
                fg.setIdioma(lang);
            } else {
                fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
            }

            fg.setCodigo(Long.valueOf(codigo));

            URI uriCompleta = uriInfo.getRequestUri();
            String url = uriCompleta.toString();

            return Response.ok(getRespuesta(fg, start, url, null), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("No entity found for query")) {
                long tiempoMiliSegundos = Duration.between(start, Instant.now()).toMillis();
                RespuestaBase respuesta = new RespuestaBase(new ArrayList<>(), tiempoMiliSegundos);
                respuesta.setMensaje(e.getMessage());
                return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Para obtener el código DIR3 de la UA (el suyo o el antecesor) .
     *
     * @param codigo Código de la UA de la que se desea obtener el DIR3
     * @return Codigo DIR3
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/codigoDir3/{codigo}")
    @Operation(operationId = "getCodDir3UA", summary = "Obtiene el codigo dir3 de la Unidad Administrativa", description = "Obtiene el codigo dir3 de la Unidad Administrativa ")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
    public Response getCodDir3UA(@Parameter(description = "Codigo de la UA de la que se desea obtener el DIR3", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();
        fg.setCodigo(Long.valueOf(codigo));
        return Response.ok(getRespuestaDir3(fg, start), MediaType.APPLICATION_JSON).build();

    }

    /**
     * Para obtener el código DIR3 de la UA (el suyo o el antecesor) .
     *
     * @param codigo Código de la UA de la que se desea obtener el DIR3
     * @return RespuestaBase
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/codigosDir3/{codigos}")
    @Operation(operationId = "getCodDir3UA", summary = "Obtiene los codigo dir3 de cada una de las Unidades Administrativas", description = "Obtiene el codigo dir3 de la Unidad Administrativa ")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
    public Response getCodsDir3UA(@Parameter(description = "Codigos de las UAs de la sque se desea obtener su DIR3, separados por comas", name = "codigos", required = true, in = ParameterIn.PATH) @PathParam("codigos") final String codigo) {

        Instant start = Instant.now();
        UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();
        fg.setCodigos(Stream.of(codigo.split(",")).map(Long::valueOf).collect(Collectors.toList()));
        return Response.ok(getRespuestasDir3(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaBase getRespuesta(UnidadAdministrativaFiltro fg, Instant start, String url, Integer apiMaxLimit) {
        Pagina<UnidadAdministrativaDTO> resultadoBusqueda = unidadAdministrativaService.findByFiltroRest(fg);
        List<UnidadAdministrativa> lista = new ArrayList<>();
        UnidadAdministrativa elemento;

        for (UnidadAdministrativaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new UnidadAdministrativa(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
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
                fg.getPaginaTamanyo(),
                fg.getPaginaFirst(),
                url,
                lista,
                tiempoMiliSegundos);
    }

    private RespuestaBase getRespuestaDir3(UnidadAdministrativaFiltro fg, Instant start) {
        String dir3 = unidadAdministrativaService.obtenerCodigoDIR3(fg.getCodigo());

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        RespuestaBase respuesta = new RespuestaBase();
        respuesta.setStatus(Response.Status.OK.getStatusCode() + "");
        respuesta.setMensaje(Constantes.mensaje200(1));
        respuesta.setResultadoURL(dir3);
        respuesta.setTiempo(tiempoSegundos);
        return respuesta;
    }

    private RespuestaBase getRespuestasDir3(UnidadAdministrativaFiltro fg, Instant start) {
        Map<Long, String> dir3 = unidadAdministrativaService.obtenerCodigosDIR3(fg.getCodigos());
        StringBuilder respuesta = new StringBuilder();
        if (dir3 != null && !dir3.isEmpty()) {
            for (Map.Entry<Long, String> entry : dir3.entrySet()) {
                respuesta.append(entry.getKey());
                respuesta.append(":");
                respuesta.append(entry.getValue());
                respuesta.append(",");
            }
        }

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        //  return new RespuestaBase(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(1), respuesta.toString(), tiempoSegundos);

        RespuestaBase retorno = new RespuestaBase();

        retorno.setStatus(Response.Status.OK.getStatusCode() + "");
        retorno.setMensaje(Constantes.mensaje200(1));
        retorno.setResultadoURL(respuesta.toString());
        retorno.setTiempo(tiempoSegundos);
        return retorno;
    }
}
