package es.caib.rolsac2.rest.api.interna.v1.service;

import es.caib.rolsac2.api.interna.v1.model.UnidadAdministrativa;
import es.caib.rolsac2.api.interna.v1.model.respuesta.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuesta.RespuestaError;
import es.caib.rolsac2.api.interna.v1.model.respuesta.RespuestaProcedimientos;
import es.caib.rolsac2.api.interna.v1.model.respuesta.RespuestaSimple;
import es.caib.rolsac2.api.interna.v1.model.respuesta.RespuestaUA;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.commons.utils.FechaUtil;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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

    /**
     * Listado de unidades administrativas.
     *
     * @param lang   Código de idioma
     * @return Listado de unidades administrativas
     */
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Path("/")
    @Operation(operationId = "listarUA", summary = "Lista las Unidades Administrativas", description = "Lista las Unidades administrativas disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaUA.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarUA(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
                             @Parameter(description = "Tamanyo de la página", name = "page_size", in = ParameterIn.QUERY) @QueryParam("page_size") final String pageSize,
                             @Parameter(description = "Página", name = "page", in = ParameterIn.QUERY) @QueryParam("page") final String page,
                             @Parameter(description = "Id entidad", name = "entidad", in = ParameterIn.QUERY) @QueryParam("entidad") final String idEntidad,
                             @Parameter(description = "Orden", name = "orden", in = ParameterIn.QUERY) @QueryParam("entidad") final String orden
    ) throws ValidationException {


        Instant start = Instant.now();

        UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else if (NumberUtils.isCreatable(idEntidad)) {
            EntidadFiltro filtroEntidad = new EntidadFiltro();
            filtroEntidad.setCodigo(Long.valueOf(idEntidad)   );
            Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(filtroEntidad);
            if (resultadoBusqueda.getTotal() > 0 && resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest() != null && !resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest().isEmpty()) {
                fg.setIdioma(resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest());
            } else {
                fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
            }
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        if(StringUtils.isNotBlank(pageSize)){
            fg.setPaginaTamanyo(Integer.parseInt(pageSize));
        }

        if(StringUtils.isNotBlank(page)){
            fg.setPaginaFirst(Integer.parseInt(page));
        }

        if(StringUtils.isNotBlank(idEntidad)){
            fg.setIdEntidad(Long.parseLong(idEntidad));
        }


        // si viene el orden intentamos rellenarlo
        // TODO
//        if (orden != null) {
//            fg.setOrderBy(filtro.getOrden().getCampo());
//            fg.setAscendente(filtro.getOrden().getTipoOrden().compareTo("ASC") == 0);
//        }

        return Response.ok(getRespuesta(fg, start, "listarUA"), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener una unidad administrativa.
     *
     * @param codigo Código de la unidad administrativa
     * @return UnidadAdministrativa
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getUA", summary = "Obtiene una Unidad Administrativa", description = "Obtiene La Unidad Administrativa con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaUA.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
    public Response getUA(@Parameter(description = "Código Unidad Administrativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) {

        Instant start = Instant.now();
        UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        fg.setCodigo(Long.valueOf(codigo));

        return Response.ok(getRespuesta(fg, start, "getUA"), MediaType.APPLICATION_JSON).build();

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
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaSimple.class)))
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
     * @return RespuestaSimple
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/codigosDir3/{codigo}")
    @Operation(operationId = "getCodDir3UA", summary = "Obtiene el codigo dir3 de la Unidad Administrativa", description = "Obtiene el codigo dir3 de la Unidad Administrativa ")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaSimple.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
    public Response getCodsDir3UA(@Parameter(description = "Codigo de la UA de la que se desea obtener el DIR3", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();
        fg.setCodigos(Stream.of(codigo.split(",")).map(Long::valueOf).collect(Collectors.toList()));
        return Response.ok(getRespuestasDir3(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaBase<UnidadAdministrativa> getRespuesta(UnidadAdministrativaFiltro fg, Instant start, String nombreMetodo) {
        Pagina<UnidadAdministrativaDTO> resultadoBusqueda = unidadAdministrativaService.findByFiltroRest(fg);
        List<UnidadAdministrativa> lista = new ArrayList<>();
        UnidadAdministrativa elemento;

        for (UnidadAdministrativaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new UnidadAdministrativa(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        String formattedDate = FechaUtil.formatISO8601(LocalDate.now());

        return RespuestaUA.builder().pageSize(fg.getPaginaTamanyo()).
                totalCount(resultadoBusqueda.getTotal()).itemsReturned("UAs").page(fg.getPaginaFirst()).
                dateDownload(formattedDate).name("UAs." + nombreMetodo).data(lista).
                status(Response.Status.OK.getStatusCode() + "").mensaje(Constantes.mensaje200(lista.size())).tiempo(tiempoMiliSegundos).build();

    }

    private RespuestaSimple getRespuestaDir3(UnidadAdministrativaFiltro fg, Instant start) {
        String dir3 = unidadAdministrativaService.obtenerCodigoDIR3(fg.getCodigo());

        Instant finish = Instant.now();
        long tiempoSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaSimple(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(1), 1L, dir3, tiempoSegundos);
    }

    private RespuestaSimple getRespuestasDir3(UnidadAdministrativaFiltro fg, Instant start) {
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



        return new RespuestaSimple(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(1), 1L, respuesta.toString(), tiempoSegundos);
    }
}
