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
import es.caib.rolsac2.api.externa.v1.model.Procedimientos;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroProcedimientos;
import es.caib.rolsac2.api.externa.v1.model.order.CampoOrden;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaProcedimientos;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO, name = Constantes.ENTIDAD_PROCEDIMIENTO)
public class ProcedimientosResource {

	@EJB
	private ProcedimientoServiceFacade procedimientoService;

	/**
	 * Listado de Procedimientos.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/")
	@Operation(operationId = "llistarProcedimientos", summary = "Lista los procedimientos", description = "Lista los procedimientos disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientos.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarProcedimientos(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de procedimientos: "
					+ FiltroProcedimientos.SAMPLE, name = "filtro", content = @Content(example = FiltroProcedimientos.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroProcedimientos.class))) FiltroProcedimientos filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroProcedimientos();
		}

		/*
		 * if(filtro.getCodigoUADir3()!=null && filtro.getCodigoUA()!=null) { throw new
		 * ExcepcionAplicacion(
		 * 400,"No se puede indicar un codigoUA y un codigoUADir3 simultaneamente"); }
		 */

		final ProcedimientoFiltro fg = filtro.toProcedimientoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}

		// si no vienen los filtros se completan con los datos por defecto
		if(filtro.getFiltroPaginacion() != null) {
			fg.setPaginaTamanyo(filtro.getFiltroPaginacion().getSize());
			fg.setPaginaFirst(filtro.getFiltroPaginacion().getPage());
		}

		// si viene el orden intentamos rellenarlo
//		if (filtro.getListaOrden() != null && !filtro.getListaOrden().isEmpty()) {
//			final List<CampoOrden> ord = filtro.getListaOrden();
//			if (ord != null && ord.size() > 0) {
//				for (final CampoOrden campoOrden : ord) {
//					if (campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_CODIGO)
//							|| campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION)
//							|| campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION)) {
////						fg.addOrden(campoOrden.getCampo(), campoOrden.getTipoOrden());
//					}
//				}
//			}
//		}
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
	 * Para obtener un procedimiento.
	 *
	 * @Parameter idioma
	 * @Parameter id
	 * @return
	 * @throws Exception
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/{codigo}")
	@Operation(operationId = "getPorId", summary = "Obtiene un procedimiento", description = "Obtiene el procedimiento con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientos.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPorId(
			@Parameter(description = "Código procedimiento", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}
		fg.setCodigoProc(Long.parseLong(codigo));
		fg.setTipo("P");
//		final RespuestaProcedimientos respuesta = getRespuesta(fg);
//		if (respuesta.getResultado() != null && !respuesta.getResultado().isEmpty()) {
//			String cabecera;
//			if ("ca".equals(lang)) {
//				cabecera = System.getProperty("es.caib.rolsac.lopd.cabecera.ca");
//			} else {
//				cabecera = System.getProperty("es.caib.rolsac.lopd.cabecera.es");
//			}
//			respuesta.getResultado().get(0).setLopdCabecera(cabecera);
//		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}

	private RespuestaProcedimientos getRespuesta(final ProcedimientoFiltro filtro) throws DelegateException {
		Pagina<ProcedimientoBaseDTO> resultadoBusqueda = procedimientoService.findProcedimientosByFiltroRest(filtro);

		List<Procedimientos> lista = new ArrayList<>();
		Procedimientos elemento = null;

		for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new Procedimientos((ProcedimientoDTO)nodo, null, filtro.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaProcedimientos(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				Long.valueOf(resultadoBusqueda.getItems().size()), lista);
	}

	/**
	 * Para obtener el enlace.
	 *
	 * @param idioma
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/enlaceTelematico/{codigo}")
	@Operation(operationId = "getEnlaceTelematico", summary = "Obtiene enlace telematico", description = "Obtiene enlace telematico dado procedimiento")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientos.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getEnlaceTelematico(
			@Parameter(description = "Código procedimiento", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();
		fg.setCodigoProc(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		fg.setTipo("P");
		final String url = procedimientoService.getEnlaceTelematico(fg);
		RespuestaProcedimientos respuesta = new RespuestaProcedimientos();
		respuesta.setUrl(url);
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
	}

}
