package es.caib.rolsac2.rest.api.interna.v1.services;

import es.caib.rolsac2.api.interna.v1.model.TipoVia;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroTipoVia;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoViaDTO;
import es.caib.rolsac2.service.model.filtro.TipoViaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_VIA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_VIA, name = Constantes.ENTIDAD_TIPO_VIA)
public class TipoViaResource {

    @EJB
    MaestrasSupServiceFacade tipoViaService;

    @EJB
    SystemServiceFacade systemService;

    @Context
    private UriInfo uriInfo;

    /**
     * Listado de TiposVia.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de tipos de via
     * @return Listado de TiposVia
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarTiposVia", summary = "Lista de tipos de via", description = "Lista todos los tipos de via disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTiposVia(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroTipoVia.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoVia.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoVia.class))) FiltroTipoVia filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroTipoVia();
        }

        TipoViaFiltro fg = filtro.toTipoViaFiltro();
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
     * Para obtener el tipo via
     *
     * @param lang   Código de idioma
     * @param codigo Código del tipo de via
     * @return TipoVia
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getTipoVia", summary = "Obtiene un tipo de via", description = "Obtiene el tipo de via con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getTipoVia(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de tipo de via", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        try {
            TipoViaFiltro fg = new TipoViaFiltro();
            fg.setCodigo(Long.valueOf(codigo));
            if (lang != null) {
                fg.setIdioma(lang);
            } else {
                fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
            }

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

    private RespuestaBase getRespuesta(TipoViaFiltro fg, Instant start, String url, Integer apiMaxLimit) {
        Pagina<TipoViaDTO> resultadoBusqueda = tipoViaService.findByFiltroRest(fg);

        List<TipoVia> lista = new ArrayList<>();
        TipoVia elemento;

        for (TipoViaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new TipoVia(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
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
}
