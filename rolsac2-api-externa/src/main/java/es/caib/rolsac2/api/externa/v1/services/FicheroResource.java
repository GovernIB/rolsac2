package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.model.Fichero;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaFichero;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_FICHERO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_FICHERO, name = Constantes.ENTIDAD_FICHERO)
public class FicheroResource {

    @EJB
    private FicheroServiceFacade ficheroService;

//	/**
//	 * Listado de TiposTramitacion.
//	 *
//	 * @return
//	 * @throws DelegateException
//	 */
//	@Produces({ MediaType.APPLICATION_JSON })
//	@POST
//	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
//	@Path("/")
//	@Operation(operationId = "llistarTiposTramitacion", summary = "Lista de plataformas de tramitación electrónica", description = "Lista todos las plataformas de tramitación electrónica disponibles")
//	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaFichero.class)))
//	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
//	public Response llistarTiposTramitacion(
//	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
//	@RequestBody(description = "Filtro: " + FiltroFichero.SAMPLE, name = "filtro", content = @Content(example = FiltroFichero.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroFichero.class))) FiltroFichero filtro)
//			throws DelegateException, ExcepcionAplicacion, ValidationException {
//
//		if (filtro == null) {
//			filtro = new FiltroFichero();
//		}
//
//		FicheroFiltro fg = filtro.toFicheroFiltro();
//
//		if (lang != null) {
//			fg.setIdioma(lang);
//		}
//
//		// si no vienen los filtros se completan con los datos por defecto
//		if(filtro.getFiltroPaginacion() != null) {
//			fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
//			fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
//		}
//
//		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
//	}

    /**
     * Para obtener el idioma.
     *
     * @param idioma
     * @param id
     * @return
     * @throws Exception
     */
    @Produces({ MediaType.APPLICATION_JSON })
    @POST
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    @Path("/{codigo}")
    @Operation(operationId = "getFichero", summary = "Obtiene un fichero", description = "Obtiene la fichero con el id(código) indicado")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaFichero.class)))
    @APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
    public Response getFichero(
            @Parameter(description = "Código de fichero", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
            throws Exception, ValidationException {

//		FicheroFiltro fg = new FicheroFiltro();
//		fg.setCodigo(new Long(codigo));
//
//		if (lang != null) {
//			fg.setIdioma(lang);
//		}

        return Response.ok(getRespuesta(new Long(codigo)), MediaType.APPLICATION_JSON).build();
    }

    private RespuestaFichero getRespuesta(Long codigo) throws DelegateException {
        FicheroDTO resultadoBusqueda = ficheroService.getFicheroDTOById(codigo);

        List<Fichero> lista = new ArrayList<Fichero>();

        if(resultadoBusqueda != null) {
        	Fichero elemento = new Fichero(resultadoBusqueda, null, null, true);
            lista.add(elemento);
        }

        return new RespuestaFichero(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
        		resultadoBusqueda != null ? 1 : 0, lista);
    }
}
