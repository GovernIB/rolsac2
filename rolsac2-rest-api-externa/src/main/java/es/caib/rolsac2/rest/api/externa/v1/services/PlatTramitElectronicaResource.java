package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.PlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroPlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaPlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PLATAFORMA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PLATAFORMA, name = Constantes.ENTIDAD_PLATAFORMA)
public class PlatTramitElectronicaResource {

    @EJB
    PlatTramitElectronicaServiceFacade platTramitElectronicaService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    /**
     * Listado de TiposTramitacion.
     *
     * @return Listado de TiposTramitacion
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarPlatTramitElectronica", summary = "Lista de plataformas de tramitación electrónica", description = "Lista todos las plataformas de tramitación electrónica disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaPlatTramitElectronica.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarPlatTramitElectronica(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroPlatTramitElectronica.SAMPLE, name = "filtro", content = @Content(example = FiltroPlatTramitElectronica.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroPlatTramitElectronica.class))) FiltroPlatTramitElectronica filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroPlatTramitElectronica();
        }

        PlatTramitElectronicaFiltro fg = filtro.toPlatTramitElectronicaFiltro();

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
     * Para obtener la plataforma del tramite electronico.
     *
     * @param codigo Codigo de la plataforma del tramite electronico
     * @param lang   Código de idioma
     * @return Devuelve la plataforma del tramite electronico
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPlatTramitElectronica", summary = "Obtiene un plataforma de tramitación electrónica", description = "Obtiene la plataforma de tramitación electrónica con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaPlatTramitElectronica.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPlatTramitElectronica(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de plataforma de tramitación electrónica", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        PlatTramitElectronicaFiltro fg = new PlatTramitElectronicaFiltro();
        fg.setCodigo(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaPlatTramitElectronica getRespuesta(PlatTramitElectronicaFiltro fg, Instant start) {
        Pagina<PlatTramitElectronicaDTO> resultadoBusqueda = platTramitElectronicaService.findByFiltroRest(fg);

        List<PlatTramitElectronica> lista = new ArrayList<>();
        PlatTramitElectronica elemento;

        for (PlatTramitElectronicaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new PlatTramitElectronica(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaPlatTramitElectronica(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }
}
