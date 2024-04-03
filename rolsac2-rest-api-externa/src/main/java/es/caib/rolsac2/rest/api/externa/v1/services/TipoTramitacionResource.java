package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.TipoTramitacion;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoTramitacion;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoTramitacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_TRAMITACION)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_TRAMITACION, name = Constantes.ENTIDAD_TIPO_TRAMITACION)
public class TipoTramitacionResource {

    @EJB
    MaestrasSupServiceFacade tipoTramitacionService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    /**
     * Listado de TiposTramitacion.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de tipos de tramitación
     * @return Listado de TiposTramitacion
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarTiposTramitacion", summary = "Lista de tipos de tramitación", description = "Lista todos los tipos de tramitación disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoTramitacion.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTiposTramitacion(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroTipoTramitacion.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoTramitacion.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoTramitacion.class))) FiltroTipoTramitacion filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroTipoTramitacion();
        }

        TipoTramitacionFiltro fg = filtro.toTipoTramitacionFiltro();

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

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    /**
     * Para obtener el tipo tramitacion.
     *
     * @param lang   Código de idioma
     * @param codigo Código de tipo de tramitación
     * @return TipoTramitacion
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getTipoTramitacion", summary = "Obtiene un tipo de tramitación", description = "Obtiene el tipo de tramitación con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoTramitacion.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getTipoTramitacion(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de tipo de tramitación", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        TipoTramitacionFiltro fg = new TipoTramitacionFiltro();
        fg.setCodigo(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaTipoTramitacion getRespuesta(TipoTramitacionFiltro fg, Instant start) {
        Pagina<TipoTramitacionDTO> resultadoBusqueda = tipoTramitacionService.findByFiltroRest(fg);

        List<TipoTramitacion> lista = new ArrayList<>();
        TipoTramitacion elemento;

        for (TipoTramitacionDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new TipoTramitacion(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaTipoTramitacion(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }
}
