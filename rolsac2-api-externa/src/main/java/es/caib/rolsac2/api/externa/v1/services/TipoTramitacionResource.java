package es.caib.rolsac2.api.externa.v1.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoTramitacion;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoTramitacion;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoTramitacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_TRAMITACION)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_TRAMITACION, name = Constantes.ENTIDAD_TIPO_TRAMITACION)
public class TipoTramitacionResource {

	@EJB
	private MaestrasSupServiceFacade tipoTramitacionService;

	/**
	 * Para obtener el enlace.
	 *
	 * @param idioma
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/enlaceTelematico/{codigo}")
	@Operation(operationId = "getEnlaceTelematico", summary = "Obtiene enlace telematico", description = "Obtiene enlace telematico dado procedimiento")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getEnlaceTelematico(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		TipoTramitacionFiltro fg = new TipoTramitacionFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		final String url = tipoTramitacionService.getEnlaceTelematico(fg);
		RespuestaTipoTramitacion respuesta = new RespuestaTipoTramitacion();
		respuesta.setMensaje(url);
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de TiposTramitacion.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarTiposTramitacion", summary = "Lista de tipos de tramitación", description = "Lista todos los tipos de tramitación disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoTramitacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposTramitacion(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroTipoTramitacion.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoTramitacion.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoTramitacion.class))) FiltroTipoTramitacion filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoTramitacion();
		}

		TipoTramitacionFiltro fg = filtro.toTipoTramitacionFiltro();

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
	@Operation(operationId = "getTipoTramitacion", summary = "Obtiene un tipo de tramitación", description = "Obtiene el tipo de tramitación con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoTramitacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoTramitacion(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de tramitación", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoTramitacionFiltro fg = new TipoTramitacionFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoTramitacion getRespuesta(TipoTramitacionFiltro fg) throws DelegateException {
		Pagina<TipoTramitacionDTO> resultadoBusqueda = tipoTramitacionService.findByFiltroRest(fg);

		List<TipoTramitacion> lista = new ArrayList<TipoTramitacion>();
		TipoTramitacion elemento = null;

		for (TipoTramitacionDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoTramitacion(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoTramitacion(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
