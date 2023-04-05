package es.caib.rolsac2.api.externa.v1.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import es.caib.rolsac2.api.externa.v1.model.TipoBoletin;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroNormativas;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroPaginacion;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoBoletin;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoBoletin;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;

@Path("/v1/" + Constantes.ENTIDAD_BOLETINES)
@Tag(description = "/v1/" + Constantes.ENTIDAD_BOLETINES, name = Constantes.ENTIDAD_BOLETINES)
public class TipoBoletinResource {

	@EJB
	private MaestrasSupServiceFacade tipoBoletinService;

	/**
	 * Listado de Boletines.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarBoletines", summary = "Lista de boletines", description = "Lista todos los boletines disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoBoletin.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarBoletines(
	@RequestBody(description = "Filtro: " + FiltroTipoBoletin.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoBoletin.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoBoletin.class))) FiltroTipoBoletin filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoBoletin();
		}

		TipoBoletinFiltro fg = filtro.toTipoBoletinFiltro();

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
	@Operation(operationId = "getBoletin", summary = "Obtiene un boletín", description = "Obtiene el boletín con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoBoletin.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getBoletin(
			@Parameter(description = "Código de boletín", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoBoletinFiltro fg = new TipoBoletinFiltro();
		fg.setCodigo(new Long(codigo));

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoBoletin getRespuesta(TipoBoletinFiltro fg) throws DelegateException {
		Pagina<TipoBoletinDTO> resultadoBusqueda = tipoBoletinService.findByFiltroRest(fg);

		List<TipoBoletin> lista = new ArrayList<TipoBoletin>();
		TipoBoletin elemento = null;

		for (TipoBoletinDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoBoletin(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoBoletin(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

//	private RespuestaTipoBoletin getRespuestaSimple(TipoBoletinFiltro fg) throws DelegateException {
//		TipoBoletinDTO resultadoBusqueda = tipoBoletinService.findTipoBoletinById(fg.getIdEntidad());
//
//		List<TipoBoletin> lista = new ArrayList<TipoBoletin>();
//
//		if (resultadoBusqueda != null) {
//			lista.add(new TipoBoletin(resultadoBusqueda, null, fg.getIdioma(), true));
//		}
//
//		return new RespuestaTipoBoletin(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1,
//				lista);
//	}

}
