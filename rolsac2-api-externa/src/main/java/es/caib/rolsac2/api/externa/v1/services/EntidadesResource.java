package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.Entidad;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroEntidad;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaEntidad;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_ENTIDADES)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_ENTIDADES, name = Constantes.ENTIDAD_ENTIDADES)
public class EntidadesResource {

	@EJB
	private EntidadServiceFacade entidadService;

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
	@Operation(operationId = "llistarTiposTramitacion", summary = "Lista de entidades", description = "Lista todos las entidades disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaEntidad.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarTiposTramitacion(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroEntidad.SAMPLE, name = "filtro", content = @Content(example = FiltroEntidad.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroEntidad.class))) FiltroEntidad filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroEntidad();
		}

		EntidadFiltro fg = filtro.toEntidadFiltro();

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
	@Operation(operationId = "getEntidad", summary = "Obtiene un entidad", description = "Obtiene la entidad con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaEntidad.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getEntidad(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de entidad", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		EntidadFiltro fg = new EntidadFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaEntidad getRespuesta(EntidadFiltro fg) throws DelegateException {
		Pagina<EntidadDTO> resultadoBusqueda = entidadService.findByFiltroRest(fg);

		List<Entidad> lista = new ArrayList<Entidad>();
		Entidad elemento = null;

		for (EntidadDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new Entidad(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaEntidad(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
