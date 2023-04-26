package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoUnidadAdministrativa;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoUnidadAdministrativa;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoUnidadAdministrativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_UNIDAD)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_UNIDAD, name = Constantes.ENTIDAD_TIPO_UNIDAD)
public class TipoUnidadAdministrativaResource {

	@EJB
	private MaestrasSupServiceFacade tipoUnidadAdministrativaService;

	/**
	 * Listado de TiposUnidadAdministrativa.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarTiposUnidadAdministrativa", summary = "Lista de tipos de unidad administrativa", description = "Lista todos los tipos de unidad administrativa disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoUnidadAdministrativa.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposUnidadAdministrativa(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro: " + FiltroTipoUnidadAdministrativa.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoUnidadAdministrativa.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoUnidadAdministrativa.class))) FiltroTipoUnidadAdministrativa filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoUnidadAdministrativa();
		}

		TipoUnidadAdministrativaFiltro fg = filtro.toTipoUnidadAdministrativaFiltro();
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
	@Operation(operationId = "getTipoUnidadAdministrativa", summary = "Obtiene un tipo de unidad administrativa", description = "Obtiene el tipo de unidad administrativa con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoUnidadAdministrativa.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoUnidadAdministrativa(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de unidad administrativa", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoUnidadAdministrativaFiltro fg = new TipoUnidadAdministrativaFiltro();
		fg.setCodigo(new Long(codigo));
		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoUnidadAdministrativa getRespuesta(TipoUnidadAdministrativaFiltro fg) throws DelegateException {
		Pagina<TipoUnidadAdministrativaDTO> resultadoBusqueda = tipoUnidadAdministrativaService.findByFiltroRest(fg);

		List<TipoUnidadAdministrativa> lista = new ArrayList<TipoUnidadAdministrativa>();
		TipoUnidadAdministrativa elemento = null;

		for (TipoUnidadAdministrativaDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoUnidadAdministrativa(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoUnidadAdministrativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

//	private RespuestaTipoUnidadAdministrativa getRespuestaSimple(TipoUnidadAdministrativaFiltro fg) throws DelegateException {
//		TipoUnidadAdministrativaDTO resultadoBusqueda = tipoUnidadAdministrativaService.findTipoUnidadAdministrativaById(fg.getIdEntidad());
//
//		List<TipoUnidadAdministrativa> lista = new ArrayList<TipoUnidadAdministrativa>();
//
//		if (resultadoBusqueda != null) {
//			lista.add(new TipoUnidadAdministrativa(resultadoBusqueda, null, fg.getIdioma(), true));
//		}
//
//		return new RespuestaTipoUnidadAdministrativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1,
//				lista);
//	}

}
