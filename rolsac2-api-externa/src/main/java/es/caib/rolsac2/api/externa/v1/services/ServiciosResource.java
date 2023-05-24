package es.caib.rolsac2.api.externa.v1.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import es.caib.rolsac2.api.externa.v1.model.Servicios;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroServicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO, name = Constantes.ENTIDAD_SERVICIO)
public class ServiciosResource {

	@EJB
	private ProcedimientoServiceFacade servicioService;

	/**
	 * Listado de Servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@POST
	@Path("/")
	@Operation(operationId = "llistarServicios", summary = "Lista los servicios", description = "Lista los servicios disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response llistarServicios(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de servicios: "
					+ FiltroServicios.SAMPLE, name = "filtro", content = @Content(example = FiltroServicios.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroServicios.class))) FiltroServicios filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroServicios();
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
//		if (orden != null) {
//			final List<CampoOrden> ord = orden.getListaOrden();
//			if (ord != null && ord.size() > 0) {
//				for (final CampoOrden campoOrden : ord) {
//					if (campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_CODIGO)
//							|| campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_FECHA_ACTUALIZACION)
//							|| campoOrden.getCampo().equals(Orden.CAMPO_ORD_PROCEDIMIENTO_FECHA_PUBLICACION)) {
//						fg.addOrden(campoOrden.getCampo(), campoOrden.getTipoOrden());
//					}
//				}
//			}
//		}

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
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
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getEnlaceTelematico(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();
		fg.setCodigoProc(new Long(codigo));

		if (lang != null) {
			fg.setIdioma(lang);
		}

		fg.setTipo("S");
		final String url = servicioService.getEnlaceTelematico(fg);
		RespuestaServicios respuesta = new RespuestaServicios();
		respuesta.setUrl(url);
		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Para obtener un servicio.
	 *
	 * @Parameter idioma
	 * @Parameter id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/{codigo}")
	@Operation(operationId = "getPorId", summary = "Obtiene un servicio", description = "Obtiene el servicio con el código indicado")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getPorId(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		}
		fg.setIdEntidad(new Long(codigo));
		fg.setTipo("P");
//		final RespuestaServicios respuesta = getRespuesta(fg);
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

//	private RespuestaServicios getRespuesta(final ProcedimientoFiltro filtro) throws DelegateException {
//		Pagina<ServicioGridDTO> resultadoBusqueda = servicioService.findServiciosByFiltro(filtro);
//
//		List<Servicios> lista = new ArrayList<>();
//		Servicios elemento = null;
//
//		for (ServicioGridDTO nodo : resultadoBusqueda.getItems()) {
//			elemento = new Servicios(nodo, null, filtro.getIdioma(), true);
//			lista.add(elemento);
//		}
//
//		return new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
//				resultadoBusqueda.getTotal(), lista);
//	}
//
//	private RespuestaServicios getRespuestaSimple(ProcedimientoFiltro filtro) throws DelegateException {
//		ServicioDTO resultadoBusqueda = servicioService.findServicioByCodigo(filtro.getIdEntidad());
//
//		List<Servicios> lista = new ArrayList<>();
//
//		lista.add(new Servicios(resultadoBusqueda, null, filtro.getIdioma(), true));
//
//		return new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()), 1L,
//				lista);
//	}


	private RespuestaServicios getRespuesta(final ProcedimientoFiltro filtro) throws DelegateException {


		Pagina<ProcedimientoBaseDTO> resultadoBusqueda = servicioService.findProcedimientosByFiltroRest(filtro);

		List<Servicios> lista = new ArrayList<>();
		Servicios elemento = null;

		for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
			elemento = new Servicios((ServicioDTO)nodo, null, filtro.getIdioma(), true);
			lista.add(elemento);
		}

		return new RespuestaServicios(Response.Status.OK.getStatusCode() + "", Constantes.mensaje200(lista.size()),
				Long.valueOf(resultadoBusqueda.getItems().size()), lista);
	}

//	/**
//	 * Para obtener el enlace.
//	 *
//	 * @param idioma
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 */
//	@POST
//	@Path("/enlaceTelematico/{codigo}")
//	@Operation(operationId = "getEnlaceTelematico", summary = "Obtiene un enlace telematico", description = "Obtiene el enlace con el id (código) indicado y su idioma")
//	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
//	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
//	public Response getEnlaceTelematico(
//			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
//			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @DefaultValue(Constantes.IDIOMA_DEFECTO) @QueryParam("lang") final String lang)
//			throws Exception, ValidationException {
//
////		ServicioDTO resultadoBusqueda = servicioService.findServicioById(codigo);
//
////		if (resultadoBusqueda != null) {
////
////		}
//
////		final String url = DelegateUtil.getServicioDelegate().getEnlaceTelematico(Long.valueOf(codigo), lang);
//		final String url = "";
//
//		final RespuestaServicios respuesta = new RespuestaServicios();
//		respuesta.setUrl(url);
//
//		return Response.ok(respuesta, MediaType.APPLICATION_JSON).build();
//	}

}
