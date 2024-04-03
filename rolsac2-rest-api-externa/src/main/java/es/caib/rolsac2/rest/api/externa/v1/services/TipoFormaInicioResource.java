package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.TipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_FORMA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_FORMA, name = Constantes.ENTIDAD_TIPO_FORMA)
public class TipoFormaInicioResource {

    @EJB
    MaestrasSupServiceFacade tipoFormaInicioService;

    @EJB
    SystemServiceFacade systemService;

    /**
     * Listado de TiposFormaInicio.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de tipos de forma de inicio
     * @return Listado de TiposFormaInicio
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarTiposFormaInicio", summary = "Lista de tipos de forma de inicio", description = "Lista todos los tipos de forma de inicio disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoFormaInicio.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTiposFormaInicio(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroTipoFormaInicio.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoFormaInicio.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoFormaInicio.class))) FiltroTipoFormaInicio filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroTipoFormaInicio();
        }

        TipoFormaInicioFiltro fg = filtro.toTipoFormaInicioFiltro();

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

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener el tipo forma inicio.
     *
     * @param codigo Código del tipo de forma de inicio
     * @return TipoFormaInicio
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getTipoFormaInicio", summary = "Obtiene un tipo de forma de inicio", description = "Obtiene el tipo de forma de inicio con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoFormaInicio.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getTipoFormaInicio(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de tipo de forma de inicio", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        TipoFormaInicioFiltro fg = new TipoFormaInicioFiltro();
        fg.setCodigo(Long.valueOf(codigo));
        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaTipoFormaInicio getRespuesta(TipoFormaInicioFiltro fg, Instant start) {
        Pagina<TipoFormaInicioDTO> resultadoBusqueda = tipoFormaInicioService.findByFiltroRest(fg);

        List<TipoFormaInicio> lista = new ArrayList<>();
        TipoFormaInicio elemento;

        for (TipoFormaInicioDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new TipoFormaInicio(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaTipoFormaInicio(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }
}
