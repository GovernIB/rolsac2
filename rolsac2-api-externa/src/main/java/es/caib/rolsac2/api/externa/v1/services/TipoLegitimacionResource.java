package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoLegitimacion;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoLegitimacion;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoLegitimacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoLegitimacionDTO;
import es.caib.rolsac2.service.model.filtro.TipoLegitimacionFiltro;
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
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_LEGITIMACION)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_LEGITIMACION, name = Constantes.ENTIDAD_TIPO_LEGITIMACION)
public class TipoLegitimacionResource {

	@EJB
	private MaestrasSupServiceFacade tipoLegitimacionService;

	/**
	 * Listado de TiposLegitimacion.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarTiposLegitimacion", summary = "Lista de tipos de legitimacion", description = "Lista todos los tipos de legitimacion disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoLegitimacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposLegitimacion(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro: " + FiltroTipoLegitimacion.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoLegitimacion.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoLegitimacion.class))) FiltroTipoLegitimacion filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoLegitimacion();
		}

		TipoLegitimacionFiltro fg = filtro.toTipoLegitimacionFiltro();
		if (lang != null) {
			fg.setIdioma(lang);
		}

		// si no vienen los filtros se completan con los datos por defecto
		if(filtro.getFiltroPaginacion() != null) {
			fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
			fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

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
	@Operation(operationId = "getTipoLegitimacion", summary = "Obtiene un tipo de legitimacion", description = "Obtiene el tipo de legitimacion con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoLegitimacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoLegitimacion(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de legitimacion", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoLegitimacionFiltro fg = new TipoLegitimacionFiltro();
		fg.setCodigo(new Long(codigo));
		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoLegitimacion getRespuesta(TipoLegitimacionFiltro fg) throws DelegateException {
		Pagina<TipoLegitimacionDTO> resultadoBusqueda = tipoLegitimacionService.findByFiltroRest(fg);

		List<TipoLegitimacion> lista = new ArrayList<TipoLegitimacion>();
		TipoLegitimacion elemento = null;

		for (TipoLegitimacionDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoLegitimacion(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoLegitimacion(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
