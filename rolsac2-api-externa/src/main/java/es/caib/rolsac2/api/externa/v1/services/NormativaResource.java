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
import es.caib.rolsac2.api.externa.v1.model.Normativa;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroNormativas;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaNormativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.TipoNormativaServiceFacade;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;

@Path("/v1/" + Constantes.ENTIDAD_NORMATIVAS)
@Tag(description = "/v1/" + Constantes.ENTIDAD_NORMATIVAS, name = Constantes.ENTIDAD_NORMATIVAS)
public class NormativaResource {

	@EJB
	private NormativaServiceFacade normativaService;

	@EJB
	private TipoNormativaServiceFacade tipoNormativaService;

	@EJB
	private MaestrasSupServiceFacade tipoBoletinService;

	/**
	 * Listado de normativas.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistar", summary = "Lista las normativas", description = "Lista las normativas disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistar(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de normativas: "
					+ FiltroNormativas.SAMPLE, name = "filtro", content = @Content(example = FiltroNormativas.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroNormativas.class))) FiltroNormativas filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroNormativas();
		}

		TipoNormativaDTO tipoNormativa = null;
		TipoBoletinDTO tipoBoletin = null;

		if (filtro.getTipoPublicacion() != null) {
			tipoNormativa = tipoNormativaService.findTipoNormativaByCodigo(filtro.getTipoPublicacion().longValue());
		}

		if (filtro.getTipoPublicacion() != null) {
			tipoBoletin = tipoBoletinService.findTipoBoletinById(filtro.getTipoPublicacion().longValue());
		}

		NormativaFiltro fg = filtro.toNormativaFiltro(tipoNormativa, tipoBoletin);

		if (lang != null) {
			fg.setIdioma(lang);
		}

		// si no vienen los filtros se completan con los datos por defecto
		fg.setPaginaTamanyo(filtro.getSize());
		fg.setPaginaFirst(filtro.getPage());

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Para obtener una normativa.
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
	@Operation(operationId = "getPorId", summary = "Obtiene una normativa", description = "Obtiene La normativa con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPorId(
			@Parameter(description = "Código normativa", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		NormativaFiltro fg = new NormativaFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}
		fg.setIdEntidad(new Long(codigo));

		return Response.ok(getRespuestaSimple(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaNormativa getRespuesta(NormativaFiltro filtro) throws DelegateException {
		Pagina<NormativaGridDTO> resultadoBusqueda = normativaService.findByFiltro(filtro);

		List<Normativa> lista = new ArrayList<>();
		Normativa elemento = null;

		for (NormativaGridDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new Normativa(nodo, null, filtro.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

	private RespuestaNormativa getRespuestaSimple(NormativaFiltro filtro) throws DelegateException {
		NormativaDTO resultadoBusqueda = normativaService.findById(filtro.getIdEntidad());

		List<Normativa> lista = new ArrayList<>();

		lista.add(new Normativa(resultadoBusqueda, null, filtro.getIdioma(), true));

		return new RespuestaNormativa(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1L,
				lista);
	}

}
