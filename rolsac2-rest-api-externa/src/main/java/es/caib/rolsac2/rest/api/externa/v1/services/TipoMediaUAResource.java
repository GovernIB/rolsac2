package es.caib.rolsac2.rest.api.externa.v1.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoMediaUA;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoMediaUA;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoMediaUA;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMediaUADTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaUAFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MEDIA_UA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MEDIA_UA, name = Constantes.ENTIDAD_TIPO_MEDIA_UA)
public class TipoMediaUAResource {

	@EJB
	private MaestrasSupServiceFacade tipoMediaUAService;

	@EJB
	private SystemServiceFacade systemService;

	@EJB
	private EntidadServiceFacade entidadService;

	/**
	 * Listado de TiposMediaUA.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "listarTiposMediaUA", summary = "Lista de tipos de media ua", description = "Lista todos los tipos de media ua disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoMediaUA.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarTiposMediaUA(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroTipoMediaUA.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoMediaUA.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoMediaUA.class))) FiltroTipoMediaUA filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoMediaUA();
		}

		TipoMediaUAFiltro fg = filtro.toTipoMediaUAFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		} else if(filtro.getIdEntidad() != null) {
			EntidadFiltro filtroEntidad = new EntidadFiltro();
			filtroEntidad.setCodigo(filtro.getIdEntidad());
			Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(filtroEntidad);
			if(resultadoBusqueda.getTotal() > 0 && resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest() != null && !resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest().isEmpty()) {
				fg.setIdioma(resultadoBusqueda.getItems().get(0).getIdiomaDefectoRest());
			} else {
				fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
			}
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
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
	@Operation(operationId = "getTipoMediaUA", summary = "Obtiene un tipo de media ua", description = "Obtiene el tipo de media ua con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoMediaUA.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoMediaUA(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de media ua", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoMediaUAFiltro fg = new TipoMediaUAFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoMediaUA getRespuesta(TipoMediaUAFiltro fg) throws DelegateException {
		Pagina<TipoMediaUADTO> resultadoBusqueda = tipoMediaUAService.findByFiltroRest(fg);

		List<TipoMediaUA> lista = new ArrayList<TipoMediaUA>();
		TipoMediaUA elemento = null;

		for (TipoMediaUADTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoMediaUA(nodo, systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.URL_BASE), fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoMediaUA(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}