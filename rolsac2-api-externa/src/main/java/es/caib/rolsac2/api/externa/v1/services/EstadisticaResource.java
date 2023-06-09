package es.caib.rolsac2.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.exception.DelegateException;
import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.model.TipoMediaFicha;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroEstadistica;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroTipoMediaFicha;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaEstadistica;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoMediaFicha;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.exception.EstadisticaException;
import es.caib.rolsac2.service.facade.EstadisticaServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMediaFichaDTO;
import es.caib.rolsac2.service.model.filtro.EstadisticaFiltro;
import es.caib.rolsac2.service.model.filtro.TipoMediaFichaFiltro;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ESTADISITICAS)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ESTADISITICAS, name = Constantes.ESTADISITICAS)
public class EstadisticaResource {

	@EJB
	private EstadisticaServiceFacade estadisticaServiceFacade;

	/**
	 * Listado de TiposMediaFicha.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_FORM_URLENCODED })
	@Path("grabar_acceso/{codigo}")
	@Operation(operationId = "grabarAcceso", summary = "Graba el acceso para el computo de estadísticas", description = "Método utilizado para grabar el acceso de una aplicación a un objeto determinado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaEstadistica.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response grabarAcceso(
	@Parameter(description = "Código del objeto al que se accede", name = "codigo", required = true, in =ParameterIn.QUERY ) @PathParam("codigo") final String codigo,
	@RequestBody(description = "Filtro: " + FiltroEstadistica.SAMPLE, name = "filtro", content = @Content(example = FiltroEstadistica.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroEstadistica.class))) FiltroEstadistica filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			//En este caso, lanzamos una excepción
		}

		EstadisticaFiltro fg = filtro.toEstadisticaFiltro(Long.valueOf(codigo));


		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaEstadistica getRespuesta(EstadisticaFiltro fg) throws DelegateException {

		try {
			estadisticaServiceFacade.grabarAcceso(fg);
			return new RespuestaEstadistica(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(1),
					1l);
		} catch (EstadisticaException e) {
			return new RespuestaEstadistica(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + "", e.getLocalizedMessage(),
					0);
		}
	}
}
