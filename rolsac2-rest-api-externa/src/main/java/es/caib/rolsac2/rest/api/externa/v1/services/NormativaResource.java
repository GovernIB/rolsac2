package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.Normativa;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroNormativas;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaNormativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_NORMATIVAS)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_NORMATIVAS, name = Constantes.ENTIDAD_NORMATIVAS)
public class NormativaResource {

    @EJB
    NormativaServiceFacade normativaService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;


    /**
     * Listado de normativas.
     *
     * @return Listado de normativas
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listar", summary = "Lista las normativas", description = "Lista las normativas disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listar(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de normativas: " + FiltroNormativas.SAMPLE, name = "filtro", content = @Content(example = FiltroNormativas.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroNormativas.class))) FiltroNormativas filtro) throws ValidationException {

        Instant start = Instant.now();
        if (filtro == null) {
            filtro = new FiltroNormativas();
        }

        NormativaFiltro fg = filtro.toNormativaFiltro();
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
     * Obtiene una normativa.
     *
     * @param codigo Código de la normativa
     * @param lang   Código de idioma
     * @return Normativa
     * @throws Exception           Manejo de excepciones
     * @throws ValidationException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene una normativa", description = "Obtiene La normativa con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código normativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws Exception, ValidationException {

        Instant start = Instant.now();
        NormativaFiltro fg = new NormativaFiltro();
        String idiomaPorDefecto = normativaService.obtenerIdiomaEntidad(Long.valueOf(codigo));

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(idiomaPorDefecto);
        }
        fg.setCodigo(Long.valueOf(codigo));

        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaNormativa getRespuesta(NormativaFiltro filtro, String idiomaPorDefecto, Instant start) {
        Pagina<NormativaDTO> resultadoBusqueda = normativaService.findByFiltroRest(filtro);

        List<Normativa> lista = new ArrayList<>();
        Normativa elemento;

        for (NormativaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Normativa(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), filtro.getIdioma(), true, idiomaPorDefecto);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }

}
