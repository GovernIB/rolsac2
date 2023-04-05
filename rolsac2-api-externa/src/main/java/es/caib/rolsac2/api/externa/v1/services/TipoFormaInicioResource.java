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
import es.caib.rolsac2.api.externa.v1.model.TipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoFormaInicio;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoFormaInicioDTO;
import es.caib.rolsac2.service.model.filtro.TipoFormaInicioFiltro;

@Path("/v1/" + Constantes.ENTIDAD_TIPO_FORMA)
@Tag(description = "/v1/" + Constantes.ENTIDAD_TIPO_FORMA, name = Constantes.ENTIDAD_TIPO_FORMA)
public class TipoFormaInicioResource {

	@EJB
	private MaestrasSupServiceFacade tipoFormaInicioService;

	/**
	 * Listado de TiposFormaInicio.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarTiposFormaInicio", summary = "Lista de tipos de forma de inicio", description = "Lista todos los tipos de forma de inicio disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoFormaInicio.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposFormaInicio(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro: " + FiltroTipoFormaInicio.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoFormaInicio.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoFormaInicio.class))) FiltroTipoFormaInicio filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoFormaInicio();
		}

		TipoFormaInicioFiltro fg = filtro.toTipoFormaInicioFiltro();

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
	@Operation(operationId = "getTipoFormaInicio", summary = "Obtiene un tipo de forma de inicio", description = "Obtiene el tipo de forma de inicio con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoFormaInicio.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoFormaInicio(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de forma de inicio", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoFormaInicioFiltro fg = new TipoFormaInicioFiltro();
		fg.setCodigo(new Long(codigo));
		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoFormaInicio getRespuesta(TipoFormaInicioFiltro fg) throws DelegateException {
		Pagina<TipoFormaInicioDTO> resultadoBusqueda = tipoFormaInicioService.findByFiltroRest(fg);

		List<TipoFormaInicio> lista = new ArrayList<TipoFormaInicio>();
		TipoFormaInicio elemento = null;

		for (TipoFormaInicioDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoFormaInicio(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoFormaInicio(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

//	private RespuestaTipoFormaInicio getRespuestaSimple(TipoFormaInicioFiltro fg) throws DelegateException {
//		TipoFormaInicioDTO resultadoBusqueda = tipoFormaInicioService.findTipoFormaInicioById(fg.getIdEntidad());
//
//		List<TipoFormaInicio> lista = new ArrayList<TipoFormaInicio>();
//
//		if (resultadoBusqueda != null) {
//			lista.add(new TipoFormaInicio(resultadoBusqueda, null, fg.getIdioma(), true));
//		}
//
//		return new RespuestaTipoFormaInicio(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1,
//				lista);
//	}

}
