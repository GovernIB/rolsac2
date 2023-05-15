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
import es.caib.rolsac2.api.externa.v1.model.TipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PUBLICO_ENTIDAD)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PUBLICO_ENTIDAD, name = Constantes.ENTIDAD_PUBLICO_ENTIDAD)
public class TipoPublicoObjetivoEntidadResource {

	@EJB
	private MaestrasSupServiceFacade tipoPublicoObjetivoEntidadService;

	/**
	 * Listado de tipos de público objetivo entidad.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistar", summary = "Lista las tipos de público objetivo entidad", description = "Lista las tipos de público objetivo entidad disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoPublicoObjetivoEntidad.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistar(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de tipos de público objetivo entidad: "
					+ FiltroTipoPublicoObjetivoEntidad.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoPublicoObjetivoEntidad.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoPublicoObjetivoEntidad.class))) FiltroTipoPublicoObjetivoEntidad filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoPublicoObjetivoEntidad();
		}

		TipoPublicoObjetivoEntidadFiltro fg = filtro.toTipoPublicoObjetivoEntidadFiltro();

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
	 * Para obtener una tipoPublicoObjetivoEntidad.
	 *
	 * @param idioma
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/{codigo}")
	@Operation(operationId = "getPorId", summary = "Obtiene una tipo público objetivo entidad", description = "Obtiene el tipo público objetivo entidad con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoPublicoObjetivoEntidad.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPorId(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código tipo publico objetivo entidad", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo
			)
			throws Exception, ValidationException {

		TipoPublicoObjetivoEntidadFiltro fg = new TipoPublicoObjetivoEntidadFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}
		fg.setCodigo(new Long(codigo));

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoPublicoObjetivoEntidad getRespuesta(TipoPublicoObjetivoEntidadFiltro filtro) throws DelegateException {
		Pagina<TipoPublicoObjetivoEntidadDTO> resultadoBusqueda = tipoPublicoObjetivoEntidadService.findByFiltroRest(filtro);

		List<TipoPublicoObjetivoEntidad> lista = new ArrayList<>();
		TipoPublicoObjetivoEntidad elemento = null;

		for (TipoPublicoObjetivoEntidadDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoPublicoObjetivoEntidad(nodo, null, filtro.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoPublicoObjetivoEntidad(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

}
