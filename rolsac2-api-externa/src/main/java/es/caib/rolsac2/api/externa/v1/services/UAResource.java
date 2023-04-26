package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.UnidadAdministrativa;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroUA;
import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaSimple;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaUA;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_UA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_UA, name = Constantes.ENTIDAD_UA)
public class UAResource {

	@EJB
	UnidadAdministrativaServiceFacade unidadAdministrativaService;

	/**
	 * Listado de unidades administrativas.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarUA", summary = "Lista las Unidades Administrativas", description = "Lista las Unidades administrativas disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaUA.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))

	public Response llistarUA(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de Unidades Administrativas: "
					+ FiltroUA.SAMPLE, name = "filtro", content = @Content(example = FiltroUA.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroUA.class))) FiltroUA filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroUA();
		}

		UnidadAdministrativaFiltro fg = filtro.toUnidadAdministrativaFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}

		// si no vienen los filtros se completan con los datos por defecto
		if(filtro.getFiltroPaginacion() != null) {
			fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
			fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
		}

		// si viene el orden intentamos rellenarlo
		if (filtro.getListaOrden() != null && !filtro.getListaOrden().isEmpty()) {
			List<CampoOrden> ord = filtro.getListaOrden();
			if (ord != null && !ord.isEmpty()) {
				CampoOrden campoOrden = ord.get(0);
				fg.setOrderBy(campoOrden.getCampo());
				fg.setAscendente(campoOrden.getTipoOrden().compareTo("ASC") == 0);
			}
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Para obtener una unidad administrativa.
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
	@Operation(operationId = "getUA", summary = "Obtiene una Unidad Administrativa", description = "Obtiene La Unidad Administrativa con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaUA.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
	public Response getUA(
			@Parameter(description = "Código Unidad Administrativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}

		fg.setCodigo(new Long(codigo));

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();

	}

	/**
	 * Para obtener el código DIR3 de la UA (el suyo o el antecesor) .
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/codigoDir3/{codigo}")
	@Operation(operationId = "getCodDir3UA", summary = "Obtiene el codigo dir3 de la Unidad Administrativa", description = "Obtiene el codigo dir3 de la Unidad Administrativa ")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaSimple.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RespuestaError.class)))
	public Response getCodDir3UA(
			@Parameter(description = "Codigo DIR3 Unidad Administrativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo) throws Exception, ValidationException {

		UnidadAdministrativaFiltro fg = new UnidadAdministrativaFiltro();

		fg.setCodigoDIR3(codigo);
		fg.setIdioma(Constantes.IDIOMA_DEFECTO);

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();

	}

	private RespuestaUA getRespuesta(UnidadAdministrativaFiltro fg) throws DelegateException {
		Pagina<UnidadAdministrativaDTO> resultadoBusqueda = unidadAdministrativaService.findByFiltroRest(fg);
		List<UnidadAdministrativa> lista = new ArrayList<>();
		UnidadAdministrativa elemento;

		for (UnidadAdministrativaDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new UnidadAdministrativa(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaUA(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
