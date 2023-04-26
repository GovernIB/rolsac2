package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoAfectacion;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoAfectacion;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoAfectacion;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.filtro.TipoAfectacionFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_AFECTACION)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_AFECTACION, name = Constantes.ENTIDAD_TIPO_AFECTACION)
public class TipoAfectacionResource {

	@EJB
	private MaestrasSupServiceFacade tipoAfectacionService;

	/**
	 * Listado de TiposAfectacion.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarTiposAfectacion", summary = "Lista de tipos de afectación", description = "Lista todos los tipos de afectación disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoAfectacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposAfectacion(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroTipoAfectacion.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoAfectacion.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoAfectacion.class))) FiltroTipoAfectacion filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoAfectacion();
		}

		TipoAfectacionFiltro fg = filtro.toTipoAfectacionFiltro();

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
	@Operation(operationId = "getTipoAfectacion", summary = "Obtiene un tipo de afectación", description = "Obtiene el tipo de afectación con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoAfectacion.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoAfectacion(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de afectación", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoAfectacionFiltro fg = new TipoAfectacionFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoAfectacion getRespuesta(TipoAfectacionFiltro fg) throws DelegateException {
		Pagina<TipoAfectacionDTO> resultadoBusqueda = tipoAfectacionService.findByFiltroRest(fg);

		List<TipoAfectacion> lista = new ArrayList<TipoAfectacion>();
		TipoAfectacion elemento = null;

		for (TipoAfectacionDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoAfectacion(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoAfectacion(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

//	private RespuestaTipoAfectacion getRespuestaSimple(TipoAfectacionFiltro fg) throws DelegateException {
//		TipoAfectacionDTO resultadoBusqueda = tipoAfectacionService.findTipoAfectacionById(fg.getIdEntidad());
//
//		List<TipoAfectacion> lista = new ArrayList<TipoAfectacion>();
//
//		if (resultadoBusqueda != null) {
//			lista.add(new TipoAfectacion(resultadoBusqueda, null, fg.getIdioma(), true));
//		}
//
//		return new RespuestaTipoAfectacion(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1,
//				lista);
//	}

}
