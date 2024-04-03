package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.DocumentoNormativa;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroDocumentoNormativa;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaDocumentoNormativa;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.DocumentoNormativaDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.DocumentoNormativaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_DOCUMENTO_NORMATIVA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_DOCUMENTO_NORMATIVA, name = Constantes.ENTIDAD_DOCUMENTO_NORMATIVA)
public class DocumentoNormativaResource {

    @EJB
    NormativaServiceFacade normativaService;

    @EJB
    SystemServiceFacade systemService;

    /**
     * Listado de procedimientoDocumentos.
     *
     * @return Listado de procedimientoDocumentos
     * @throws DelegateException Manejo de excepciones
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/")
    @Operation(operationId = "listar", summary = "Lista los normativas documentos", description = "Lista los normativas documentos disponibles en funcion de los filtros")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaDocumentoNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response listar(@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang, @RequestBody(description = "Filtro de documentos normativa: " + FiltroDocumentoNormativa.SAMPLE, name = "filtro", content = @Content(example = FiltroDocumentoNormativa.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroDocumentoNormativa.class))) FiltroDocumentoNormativa filtro) throws DelegateException, ExcepcionAplicacion, ValidationException {

        if (filtro == null) {
            filtro = new FiltroDocumentoNormativa();
        }

        DocumentoNormativaFiltro fg = filtro.toDocumentoNormativaFiltro();
        Instant start = Instant.now();

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
     * Para obtener una procedimientoDocumento.
     *
     * @param codigo Código del procedimientoDocumento
     * @param lang   Código de idioma
     * @return
     * @throws Exception
     */
    @Produces({MediaType.APPLICATION_JSON})
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{codigo}")
    @Operation(operationId = "getPorId", summary = "Obtiene una normativa documento", description = "Obtiene la normativa documento con el código indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaDocumentoNormativa.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getPorId(@Parameter(description = "Código documento normativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo, @Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang) throws Exception, ValidationException {

        DocumentoNormativaFiltro fg = new DocumentoNormativaFiltro();
        Instant start = Instant.now();

        if (lang != null) {
            fg.setIdioma(lang);
        } else {
            fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
        }
        fg.setCodigo(Long.valueOf(codigo));

        return Response.ok(getRespuesta(fg, start), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaDocumentoNormativa getRespuesta(DocumentoNormativaFiltro filtro, Instant start) throws DelegateException {
        Pagina<DocumentoNormativaDTO> resultadoBusqueda = normativaService.findDocumentoNormativaByFiltroRest(filtro);

        List<DocumentoNormativa> lista = new ArrayList<>();
        DocumentoNormativa elemento = null;

        for (DocumentoNormativaDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new DocumentoNormativa(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), filtro.getIdioma(), true);
            lista.add(elemento);
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();

        return new RespuestaDocumentoNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), resultadoBusqueda.getTotal(), lista, tiempoMiliSegundos);
    }

}
