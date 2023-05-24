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
import es.caib.rolsac2.api.externa.v1.model.PlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroPlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaPlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PLATAFORMA)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PLATAFORMA, name = Constantes.ENTIDAD_PLATAFORMA)
public class PlatTramitElectronicaResource {

	@EJB
	private PlatTramitElectronicaServiceFacade platTramitElectronicaService;

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
	@Operation(operationId = "llistarPlatTramitElectronica", summary = "Lista de plataformas de tramitación electrónica", description = "Lista todos las plataformas de tramitación electrónica disponibles")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaPlatTramitElectronica.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarPlatTramitElectronica(
	@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
	@RequestBody(description = "Filtro: " + FiltroPlatTramitElectronica.SAMPLE, name = "filtro", content = @Content(example = FiltroPlatTramitElectronica.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroPlatTramitElectronica.class))) FiltroPlatTramitElectronica filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroPlatTramitElectronica();
		}

		PlatTramitElectronicaFiltro fg = filtro.toPlatTramitElectronicaFiltro();

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
	@Operation(operationId = "getPlatTramitElectronica", summary = "Obtiene un plataforma de tramitación electrónica", description = "Obtiene la plataforma de tramitación electrónica con el id(código) indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaPlatTramitElectronica.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPlatTramitElectronica(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@Parameter(description = "Código de plataforma de tramitación electrónica", required = true, name = "codigo", in = ParameterIn.PATH) @PathParam("codigo") final String codigo)
			throws Exception, ValidationException {

		PlatTramitElectronicaFiltro fg = new PlatTramitElectronicaFiltro();
		fg.setCodigo(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaPlatTramitElectronica getRespuesta(PlatTramitElectronicaFiltro fg) throws DelegateException {
		Pagina<PlatTramitElectronicaDTO> resultadoBusqueda = platTramitElectronicaService.findByFiltroRest(fg);

		List<PlatTramitElectronica> lista = new ArrayList<PlatTramitElectronica>();
		PlatTramitElectronica elemento = null;

		for (PlatTramitElectronicaDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new PlatTramitElectronica(nodo, null, fg.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaPlatTramitElectronica(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}
}
