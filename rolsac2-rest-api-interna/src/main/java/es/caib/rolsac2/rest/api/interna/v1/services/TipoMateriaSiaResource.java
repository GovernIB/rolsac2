package es.caib.rolsac2.rest.api.interna.v1.services;

import es.caib.rolsac2.api.interna.v1.model.TipoMateriaSia;
import es.caib.rolsac2.api.interna.v1.model.filters.FiltroTipoMateriaSia;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.interna.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MATERIA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MATERIA, name = Constantes.ENTIDAD_TIPO_MATERIA)
public class TipoMateriaSiaResource {

    @EJB
    MaestrasSupServiceFacade tipoMateriaService;

    @EJB
    SystemServiceFacade systemService;

    @Context
    private UriInfo uriInfo;

    /**
     * Listado de TiposMateria.
     *
     * @param lang   Código de idioma
     * @param filtro Filtro de tipos de materia
     * @return Listado de TiposMateria
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listarTiposMateria", summary = "Lista de tipos de materia", description = "Lista todos los tipos de materia disponibles")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listarTiposMateria(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro: " + FiltroTipoMateriaSia.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoMateriaSia.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoMateriaSia.class))) FiltroTipoMateriaSia filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroTipoMateriaSia();
        }

        TipoMateriaSIAFiltro fg = filtro.toTipoMateriaSIAFiltro();
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
     * Para obtener el tipo materia sia.
     *
     * @param codigo Código del tipo materia
     * @param lang   Código de idioma
     * @return Tipo materia sia
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getTipoMateriaSia", summary = "Obtiene un tipo de materia", description = "Obtiene el tipo de materia con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaBase.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getTipoMateriaSia(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @Parameter(description = "Código de tipo de materia", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo) {

        Instant start = Instant.now();
        TipoMateriaSIAFiltro fg = new TipoMateriaSIAFiltro();
        fg.setCodigo(Long.valueOf(codigo));
        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }

        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuesta(fg, start, url, null), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaBase getRespuesta(TipoMateriaSIAFiltro fg, Instant start, String url, Integer apiMaxLimit) {
        Pagina<TipoMateriaSIADTO> resultadoBusqueda = tipoMateriaService.findByFiltroRest(fg);

        List<TipoMateriaSia> lista = new ArrayList<>();
        TipoMateriaSia elemento;

        for (TipoMateriaSIADTO nodo : resultadoBusqueda.getItems()) {
            elemento = new TipoMateriaSia(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
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
