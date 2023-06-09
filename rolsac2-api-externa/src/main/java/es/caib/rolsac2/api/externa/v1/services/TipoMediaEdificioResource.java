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
import es.caib.rolsac2.api.externa.v1.model.TipoMediaEdificio;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoMediaEdificio;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoMediaEdificio;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMediaEdificioDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MEDIA_EDIFICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_TIPO_MEDIA_EDIFICIO, name = Constantes.ENTIDAD_TIPO_MEDIA_EDIFICIO)
public class TipoMediaEdificioResource {

	@EJB
	private MaestrasSupServiceFacade tipoMediaEdificioService;

	@EJB
	private SystemServiceFacade systemService;

	@EJB
	private EntidadServiceFacade entidadService;

	/**
	 * Listado de TiposMediaEdificio.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "listarTiposMediaEdificio", summary = "Lista de tipos de media edificio", description = "Lista todos los tipos de media edificio disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoMediaEdificio.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarTiposMediaEdificio(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroTipoMediaEdificio.SAMPLE, name = "filtro", content = @Content(example = FiltroTipoMediaEdificio.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroTipoMediaEdificio.class))) FiltroTipoMediaEdificio filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroTipoMediaEdificio();
		}

		TipoMediaEdificioFiltro fg = filtro.toTipoMediaEdificioFiltro();

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
	@Operation(operationId = "getTipoMediaEdificio", summary = "Obtiene un tipo de media edificio", description = "Obtiene el tipo de media edificio con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoMediaEdificio.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getTipoMediaEdificio(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de tipo de media edificio", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		TipoMediaEdificioFiltro fg = new TipoMediaEdificioFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaTipoMediaEdificio getRespuesta(TipoMediaEdificioFiltro fg) throws DelegateException {
		Pagina<TipoMediaEdificioDTO> resultadoBusqueda = tipoMediaEdificioService.findByFiltroRest(fg);

		List<TipoMediaEdificio> lista = new ArrayList<TipoMediaEdificio>();
		TipoMediaEdificio elemento = null;

		for (TipoMediaEdificioDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new TipoMediaEdificio(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaTipoMediaEdificio(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
