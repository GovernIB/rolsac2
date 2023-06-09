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
import es.caib.rolsac2.api.externa.v1.model.ProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoDocumentoFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO, name = Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
public class ProcedimientoDocumentoResource {

	@EJB
	private ProcedimientoServiceFacade procedimientoService;

	@EJB
	private SystemServiceFacade systemService;


	/**
	 * Listado de procedimientoDocumentos.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "listar", summary = "Lista los procedimientos documentos", description = "Lista los procedimientos documentos disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listar(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de procedimiento documentos: "
					+ FiltroProcedimientoDocumento.SAMPLE, name = "filtro", content = @Content(example = FiltroProcedimientoDocumento.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroProcedimientoDocumento.class))) FiltroProcedimientoDocumento filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroProcedimientoDocumento();
		}

		ProcedimientoDocumentoFiltro fg = filtro.toProcedimientoDocumentoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}

		// si no vienen los filtros se completan con los datos por defecto
		if(filtro.getFiltroPaginacion() != null) {
			fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
			fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
		}

		// si viene el orden intentamos rellenarlo
		if (filtro.getCampoOrden() != null) {
			fg.setOrderBy(filtro.getCampoOrden().getCampo());
			fg.setAscendente(filtro.getCampoOrden().getTipoOrden().compareTo("ASC") == 0);
		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Para obtener una procedimientoDocumento.
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
	@Operation(operationId = "getPorId", summary = "Obtiene una procedimiento documento", description = "Obtiene el procedimiento documento con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPorId(
			@Parameter(description = "Código procedimiento documento", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		ProcedimientoDocumentoFiltro fg = new ProcedimientoDocumentoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}
		fg.setCodigo(new Long(codigo));

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaProcedimientoDocumento getRespuesta(ProcedimientoDocumentoFiltro filtro) throws DelegateException {
		Pagina<ProcedimientoDocumentoDTO> resultadoBusqueda = procedimientoService.findProcedimientoDocumentoByFiltroRest(filtro);

		List<ProcedimientoDocumento> lista = new ArrayList<>();
		ProcedimientoDocumento elemento = null;

		for (ProcedimientoDocumentoDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new ProcedimientoDocumento(nodo, null, filtro.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				resultadoBusqueda.getTotal(), lista);
	}

}
