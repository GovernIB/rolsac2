package es.caib.rolsac2.rest.api.interna.v1.services;

import es.caib.rolsac2.api.interna.v1.model.Entidad;
import es.caib.rolsac2.api.interna.v1.model.EntidadIdioma;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroEntidad;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroPaginacion;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_ENTIDADES)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_ENTIDADES, name = Constantes.ENTIDAD_ENTIDADES)
public class EntidadesResource {

    @EJB
    EntidadServiceFacade entidadService;

    @EJB
    SystemServiceFacade systemService;

    @Context
    private UriInfo uriInfo;

    /**
     * Listado de TiposTramitacion.
     *
     * @return Listado de tipos tramitacion
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarEntidad", summary = "Lista de entidades", description = "Lista todos las entidades disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarEntidad(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroEntidad.SAMPLE, name = "filtro", content = @Content(example = FiltroEntidad.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroEntidad.class))) FiltroEntidad filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroEntidad();
        }

        EntidadFiltro fg = filtro.toEntidadFiltro();

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        // si no vienen los filtros se completan con los datos por defecto
        if (filtro.getFiltroPaginacion() != null) {
            fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
            fg.setPaginaFirst(filtro.getFiltroPaginacion().getOffset());
        }

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuesta(fg, start, url), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Listado de entidades idioma.
     *
     * @return Lista de entidades idioma
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/idioma/")
    @Operation(operationId = "listarEntidadesIdioma", summary = "Lista de entidades", description = "Lista todos las entidades disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarEntidadesIdioma(@RequestBody(description = "Filtro: " + FiltroPaginacion.SAMPLE, name = "filtro", content = @Content(example = FiltroPaginacion.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroPaginacion.class))) FiltroPaginacion filtro) throws ValidationException {

        Instant start = Instant.now();
        EntidadFiltro fg = new EntidadFiltro();

        fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));

        // si no vienen los filtros se completan con los datos por defecto
        if (filtro != null) {
            fg.setPaginaTamanyo(filtro.getSize());
            fg.setPaginaFirst(filtro.getPage() * filtro.getSize());
        }

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuestaIdioma(fg, start, url), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener el idioma.
     *
     * @param lang   Código de idioma
     * @param codigo Código de entidad
     * @return Entidad
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getEntidad", summary = "Obtiene un entidad", description = "Obtiene la entidad con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getEntidad(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de entidad", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        EntidadFiltro fg = new EntidadFiltro();
        fg.setCodigo(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }


        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuesta(fg, start, url), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaBase getRespuesta(EntidadFiltro fg, Instant start, String url) {
        Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(fg);

        List<Entidad> lista = new ArrayList<>();
        Entidad elemento;

        for (EntidadDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Entidad(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaBase(
                (int) resultadoBusqueda.getTotal(),
                lista.size(),
                fg.getPaginaTamanyo(),
                fg.getPaginaFirst(),
                url,
                lista,
                tiempoMiliSegundos);
    }

    private RespuestaBase getRespuestaIdioma(EntidadFiltro fg, Instant start, String url) {


        Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(fg);

        List<EntidadIdioma> lista = new ArrayList<>();
        EntidadIdioma elemento;

        for (EntidadDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new EntidadIdioma(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaBase(
                (int) resultadoBusqueda.getTotal(),
                lista.size(),
                fg.getPaginaTamanyo(),
                fg.getPaginaFirst(),
                url,
                lista,
                tiempoMiliSegundos);
    }
}
