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
import es.caib.rolsac2.api.externa.v1.model.Normativa;
import es.caib.rolsac2.api.externa.v1.model.ProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.Servicios;
import es.caib.rolsac2.api.externa.v1.model.Tema;
import es.caib.rolsac2.api.externa.v1.model.TipoMateriaSia;
import es.caib.rolsac2.api.externa.v1.model.TipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.model.filters.FiltroServicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaError;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaNormativa;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaServicios;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTema;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoMateriaSia;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaTipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDocumentoDTO;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.TemaDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadDTO;
import es.caib.rolsac2.service.model.filtro.EntidadFiltro;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_SERVICIO, name = Constantes.ENTIDAD_SERVICIO)
public class ServiciosResource {

	@EJB
	private ProcedimientoServiceFacade servicioService;

	@EJB
	private SystemServiceFacade systemService;

	@EJB
	private EntidadServiceFacade entidadService;

	/**
	 * Listado de Servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@POST
	@Path("/")
	@Operation(operationId = "listarServicios", summary = "Lista los servicios", description = "Lista los servicios disponibles en funcion de los filtros")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarServicios(
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@RequestBody(description = "Filtro de servicios: "
					+ FiltroServicios.SAMPLE, name = "filtro", content = @Content(example = FiltroServicios.SAMPLE_JSON, mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = FiltroServicios.class))) FiltroServicios filtro)
			throws DelegateException, ExcepcionAplicacion, ValidationException {

		if (filtro == null) {
			filtro = new FiltroServicios();
		}

		final ProcedimientoFiltro fg = filtro.toProcedimientoFiltro();

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
	 * Para obtener el enlace.
	 *
	 * @param idioma
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/enlaceTelematico/{codigo}")
	@Operation(operationId = "getEnlaceTelematico", summary = "Obtiene enlace telematico", description = "Obtiene enlace telematico dado servicio")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaServicios.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response getEnlaceTelematico(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();
		fg.setCodigoProc(new Long(codigo));
		fg.setEstadoWF(estadoWF);

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}

		fg.setTipo("S");
		final String url = servicioService.getEnlaceTelematicoByServicio(fg);
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
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang)
			throws Exception, ValidationException {

		final ProcedimientoFiltro fg = new ProcedimientoFiltro();

		if (lang != null) {
			fg.setIdioma(lang);
		} else {
			fg.setIdioma(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO));
		}
		fg.setCodigoProc(Long.parseLong(codigo));
		fg.setTipo("S");

		return Response.ok(getRespuesta(fg), MediaType.APPLICATION_JSON).build();
	}


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

	/**
	 * Listado de Publico Objetivo Entidad de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/publicoObjetivoEntidad/{codigo}")
	@Operation(operationId = "listarPublicoObjetivoEntidad", summary = "Lista los tipos de público objetivo entidad del servicio", description = "Lista los tipos de público objetivo entidad del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoPublicoObjetivoEntidad.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarPublicoObjetivoEntidad(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<TipoPublicoObjetivoEntidadDTO> result = new ArrayList<>();
		List<TipoPublicoObjetivoEntidad> lista = new ArrayList<>();
		TipoPublicoObjetivoEntidad elemento = null;

		if (codigo != null) {
			result = servicioService.getTipoPubObjEntByProc(new Long(codigo), estadoWF);

			for (TipoPublicoObjetivoEntidadDTO nodo : result) {
				elemento = new TipoPublicoObjetivoEntidad(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaTipoPublicoObjetivoEntidad(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de materias de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/materiasSia/{codigo}")
	@Operation(operationId = "listarMateriasSia", summary = "Lista los tipos de materias SIA del servicio", description = "Lista los tipos de de materias SIA del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTipoMateriaSia.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarMateriasSia(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<TipoMateriaSIADTO> result = new ArrayList<>();
		List<TipoMateriaSia> lista = new ArrayList<>();
		TipoMateriaSia elemento = null;

		if (codigo != null) {
			result = servicioService.getTipoMateriaByProc(new Long(codigo), estadoWF);

			for (TipoMateriaSIADTO nodo : result) {
				elemento = new TipoMateriaSia(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaTipoMateriaSia(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de normativas de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/normativas/{codigo}")
	@Operation(operationId = "listarNormativas", summary = "Lista los normativas del servicio", description = "Lista los normativas del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaNormativa.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarNormativas(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<NormativaDTO> result = new ArrayList<>();
		List<Normativa> lista = new ArrayList<>();
		Normativa elemento = null;

		if (codigo != null) {
			result = servicioService.getNormativasByProc(new Long(codigo), estadoWF);

			for (NormativaDTO nodo : result) {
				elemento = new Normativa(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaNormativa(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de temas de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/temas/{codigo}")
	@Operation(operationId = "listarTemas", summary = "Lista los temas del servicio", description = "Lista los temas del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaTema.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarTemas(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<TemaDTO> result = new ArrayList<>();
		List<Tema> lista = new ArrayList<>();
		Tema elemento = null;

		if (codigo != null) {
			result = servicioService.getTemasByProc(new Long(codigo), estadoWF);

			for (TemaDTO nodo : result) {
				elemento = new Tema(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaTema(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de documentos de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/documentos/{codigo}")
	@Operation(operationId = "listarDocumentos", summary = "Lista los documentos del servicio", description = "Lista los documentos del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarDocumentos(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
		List<ProcedimientoDocumento> lista = new ArrayList<>();
		ProcedimientoDocumento elemento = null;

		if (codigo != null) {
			result = servicioService.getDocumentosByProc(new Long(codigo), estadoWF);

			for (ProcedimientoDocumentoDTO nodo : result) {
				elemento = new ProcedimientoDocumento(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Listado de documentos LOPD de servicios.
	 *
	 * @return
	 * @throws DelegateException
	 */
	@Produces({ MediaType.APPLICATION_JSON })
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/documentosLopd/{codigo}")
	@Operation(operationId = "listarDocumentosLopd", summary = "Lista los documentos LOPD del servicio", description = "Lista los documentos LOPD del servicio dado por código")
	@APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaProcedimientoDocumento.class)))
	@APIResponse(responseCode = "400", description = Constantes.MSJ_400_GENERICO, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RespuestaError.class)))
	public Response listarDocumentosLopd(
			@Parameter(description = "Código servicio", name = "codigo", required = true, in = ParameterIn.PATH) @PathParam("codigo") final String codigo,
			@Parameter(description = "Código de idioma", name = "lang", in = ParameterIn.QUERY) @QueryParam("lang") final String lang,
			@Parameter(description = "Estado workflow (D=Definitivo, M=Modificado, T=Todos (publicado o modificado), A=Ambos (publicado y modificado))", name = "estadoWF", in = ParameterIn.QUERY) @DefaultValue(Constantes.ESTADOWF_DEFECTO) @QueryParam("estadoWF") final String estadoWF)
			throws Exception, ValidationException {

		List<ProcedimientoDocumentoDTO> result = new ArrayList<>();
		List<ProcedimientoDocumento> lista = new ArrayList<>();
		ProcedimientoDocumento elemento = null;

		if (codigo != null) {
			result = servicioService.getDocumentosLOPDByProc(new Long(codigo), estadoWF);

			for (ProcedimientoDocumentoDTO nodo : result) {
				elemento = new ProcedimientoDocumento(nodo, null, lang, true);
				lista.add(elemento);
			}
		}

		return Response.ok(new RespuestaProcedimientoDocumento(Response.Status.OK.getStatusCode() + "",
				Constantes.mensaje200(lista.size()), new Long(result.size()), lista), MediaType.APPLICATION_JSON).build();
	}

}
