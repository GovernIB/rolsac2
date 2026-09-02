package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.Servei;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ServicioDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Path(Constantes.API_VERSION_BARRA + "serveis")
@Tag(description = Constantes.API_VERSION_BARRA + "serveis", name = "serveis")
public class ServicioResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServicioResource.class);

    @EJB
    ProcedimientoServiceFacade procedimientoService;
    @EJB
    SystemServiceFacade systemService;
    @EJB
    EntidadServiceFacade entidadService;
    @Context
    private UriInfo uriInfo;

    private boolean debugActivo = false;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/")
    @Operation(operationId = "listarServicios", summary = "Llista els serveis",
            description = "Llista els serveis disponibles en funció dels filtres indicats.")
    @APIResponse(responseCode = "200", description = Constantes.MSJ_200_GENERICO,
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = RespuestaBase.class)))
    public Response listarServicios(
            @Parameter(description = "Idioma de la informació retornada. Per defecte, català.", name = "idioma", in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "ca"))
            @QueryParam("idioma") @DefaultValue("ca") final String idioma,

            @Parameter(description = "Codi de l'entitat. Per defecte, 1.", name = "entitat", in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "1"))
            @QueryParam("entitat") @DefaultValue("1") final Long entitat,

            @Parameter(description = "Codi del servei.", name = "codi", in = ParameterIn.QUERY)
            @QueryParam("codi") final Long codi,
            @Parameter(description = "Nom del servei.", name = "nom", in = ParameterIn.QUERY)
            @QueryParam("nom") final String nom,
            @Parameter(description = "Data inicial del rang d'actualització en format ISO8601.", name = "iniciDataActualitzacio", in = ParameterIn.QUERY)
            @QueryParam("iniciDataActualitzacio") final String iniciDataActualitzacio,
            @Parameter(description = "Data final del rang d'actualització en format ISO8601.", name = "fiDataActualitzacio", in = ParameterIn.QUERY)
            @QueryParam("fiDataActualitzacio") final String fiDataActualitzacio,
            @Parameter(description = "Data inicial del rang de caducitat en format ISO8601.", name = "iniciDataCaducitat", in = ParameterIn.QUERY)
            @QueryParam("iniciDataCaducitat") final String iniciDataCaducitat,
            @Parameter(description = "Data final del rang de caducitat en format ISO8601.", name = "fiDataCaducitat", in = ParameterIn.QUERY)
            @QueryParam("fiDataCaducitat") final String fiDataCaducitat,
            @Parameter(description = "Codi SIA del servei.", name = "codiSIACodi", in = ParameterIn.QUERY)
            @QueryParam("codiSIACodi") final String codiSIACodi,
            @Parameter(description = "Estat del servei a SIA.", name = "estatSIA", in = ParameterIn.QUERY)
            @QueryParam("estatSIA") final String estatSIA,
            @Parameter(description = "Data inicial del rang de data SIA en format ISO8601.", name = "iniciDataSia", in = ParameterIn.QUERY)
            @QueryParam("iniciDataSia") final String iniciDataSia,
            @Parameter(description = "Data final del rang de data SIA en format ISO8601.", name = "fiDataSia", in = ParameterIn.QUERY)
            @QueryParam("fiDataSia") final String fiDataSia,
            @Parameter(description = "Codi de la unitat administrativa instructora.", name = "uaInstructorCodi", in = ParameterIn.QUERY)
            @QueryParam("uaInstructorCodi") final Long uaInstructorCodi,
            @Parameter(description = "Nom de la unitat administrativa instructora.", name = "uaInstructorNom", in = ParameterIn.QUERY)
            @QueryParam("uaInstructorNom") final String uaInstructorNom,
            @Parameter(description = "Indica si el servei és comú.", name = "comu", in = ParameterIn.QUERY)
            @QueryParam("comu") final Boolean comu,
            @Parameter(description = "Estat general del servei.", name = "estat", in = ParameterIn.QUERY)
            @QueryParam("estat") final String estat,
            @Parameter(description = "Indica si permet tramitació mitjançant apoderat.", name = "habilitatApoderat", in = ParameterIn.QUERY)
            @QueryParam("habilitatApoderat") final Boolean habilitatApoderat,
            @Parameter(description = "Indica si permet tramitació mitjançant funcionari habilitat.", name = "habilitatFuncionari", in = ParameterIn.QUERY)
            @QueryParam("habilitatFuncionari") final Boolean habilitatFuncionari,
            @Parameter(description = "Termini de resolució.", name = "terminiResolucio", in = ParameterIn.QUERY)
            @QueryParam("terminiResolucio") final String terminiResolucio,
            @Parameter(description = "Codi del tipus de tramitació.", name = "tipusTramitacioCodi", in = ParameterIn.QUERY)
            @QueryParam("tipusTramitacioCodi") final Long tipusTramitacioCodi,
            @Parameter(description = "Nom del tipus de tramitació.", name = "tipusTramitacioNom", in = ParameterIn.QUERY)
            @QueryParam("tipusTramitacioNom") final String tipusTramitacioNom,
            @Parameter(description = "Indica si la tramitació és presencial.", name = "tramitPresencial", in = ParameterIn.QUERY)
            @QueryParam("tramitPresencial") final Boolean tramitPresencial,
            @Parameter(description = "Indica si la tramitació és telefònica.", name = "tramitTelefonica", in = ParameterIn.QUERY)
            @QueryParam("tramitTelefonica") final Boolean tramitTelefonica,
            @Parameter(description = "Indica si la tramitació és electrònica.", name = "tramitElectronica", in = ParameterIn.QUERY)
            @QueryParam("tramitElectronica") final Boolean tramitElectronica,
            @Parameter(description = "Codi de la plataforma de tramitació.", name = "plataformaTramitCodi", in = ParameterIn.QUERY)
            @QueryParam("plataformaTramitCodi") final Long plataformaTramitCodi,
            @Parameter(description = "Nom de la plataforma de tramitació.", name = "plataformaTramitNom", in = ParameterIn.QUERY)
            @QueryParam("plataformaTramitNom") final String plataformaTramitNom,
            @Parameter(description = "Codi de la plantilla de tramitació.", name = "plantillaTramitCodi", in = ParameterIn.QUERY)
            @QueryParam("plantillaTramitCodi") final Long plantillaTramitCodi,
            @Parameter(description = "Nom de la plantilla de tramitació.", name = "plantillaTramitNom", in = ParameterIn.QUERY)
            @QueryParam("plantillaTramitNom") final String plantillaTramitNom,
            @Parameter(description = "Mida de la pàgina.", name = "page-size", in = ParameterIn.QUERY)
            @QueryParam("page-size") final Integer pageSize,
            @Parameter(description = "Número de pàgina.", name = "page", in = ParameterIn.QUERY)
            @QueryParam("page") final Integer page,
            @Parameter(description = "Camp d'ordenació: codi, dataActualitzacio, dataCaducitat, dataPublicacio, codiSIA o dataSIA.",
                    name = "ordenCampo", in = ParameterIn.QUERY,
                    schema = @Schema(enumeration = {"codi", "dataActualitzacio", "dataCaducitat", "dataPublicacio", "codiSIA", "dataSIA"}))
            @QueryParam("ordenCampo") final String ordenCampo,
            @Parameter(description = "Sentit de l'ordenació: asc o desc.", name = "ordenAscendente", in = ParameterIn.QUERY,
                    schema = @Schema(enumeration = {"asc", "desc"}))
            @QueryParam("ordenAscendente") final String ordenAscendente
    ) throws ValidationException {

        checkDebug();
        Instant start = Instant.now();
        final ProcedimientoFiltro fg = new ProcedimientoFiltro();
        fg.setTipo("S");
        fg.setIdEntidad(entitat == null ? 1L : entitat);

        if (codi != null) fg.setCodigo(codi);
        if (hasText(nom)) fg.setNombre(nom.trim());
        if (hasText(iniciDataActualitzacio)) {
            // Se sincronizan ambas grafías porque ProcedimientoFiltro conserva campos
            // históricos para procedimientos y servicios.
            fg.setInicioFechaActualizacion(iniciDataActualitzacio.trim());
            fg.setInicioFechaActualitzacion(iniciDataActualitzacio.trim());
        }
        if (hasText(fiDataActualitzacio)) {
            fg.setFinFechaActualizacion(fiDataActualitzacio.trim());
            fg.setFinFechaActualitzacion(fiDataActualitzacio.trim());
        }
        if (hasText(iniciDataCaducitat)) fg.setInicioFechaCaducidad(iniciDataCaducitat.trim());
        if (hasText(fiDataCaducitat)) fg.setFinFechaCaducidad(fiDataCaducitat.trim());
        if (hasText(codiSIACodi)) {
            try {
                fg.setCodigoSIA(Integer.valueOf(codiSIACodi.trim()));
            } catch (NumberFormatException e) {
                throw new ValidationException("codiSIACodi ha de tenir un valor numèric compatible amb el model intern.");
            }
        }
        if (hasText(estatSIA)) fg.setEstadoSIA(estatSIA.trim());
        if (hasText(iniciDataSia)) fg.setInicioFechaSIA(iniciDataSia.trim());
        if (hasText(fiDataSia)) fg.setFinFechaSIA(fiDataSia.trim());
        if (uaInstructorCodi != null) {
            fg.setCodigoUAInstructora(uaInstructorCodi);
            fg.setUaInstructorCodigo(uaInstructorCodi);
        }
        if (hasText(uaInstructorNom)) {
            fg.setNombreUAInstructora(uaInstructorNom.trim());
            fg.setUaInstructorNombre(uaInstructorNom.trim());
        }
        if (comu != null) fg.setComun(comu ? "S" : "N");
        if (hasText(estat)) fg.setEstado(estat.trim());
        if (habilitatApoderat != null) fg.setHabilitadoApoderado(habilitatApoderat);
        if (habilitatFuncionari != null) fg.setHabilitadoFuncionario(habilitatFuncionari);
        if (hasText(terminiResolucio)) fg.setTerminoResolucion(terminiResolucio.trim());
        if (tipusTramitacioCodi != null) fg.setTipoTramitacionCodigo(tipusTramitacioCodi);
        if (hasText(tipusTramitacioNom)) fg.setTipoTramitacionNombre(tipusTramitacioNom.trim());
        if (tramitPresencial != null) fg.setTramitacionPresencial(tramitPresencial);
        if (tramitTelefonica != null) fg.setTramitacionTelefonica(tramitTelefonica);
        if (tramitElectronica != null) fg.setTramitacionElectronica(tramitElectronica);
        if (plataformaTramitCodi != null) fg.setPlataformaTramitacionCodigo(plataformaTramitCodi);
        if (hasText(plataformaTramitNom)) fg.setPlataformaTramitacionNombre(plataformaTramitNom.trim());
        if (plantillaTramitCodi != null) fg.setPlantillaTramitacionCodigo(plantillaTramitCodi);
        if (hasText(plantillaTramitNom)) fg.setPlantillaTramitacionNombre(plantillaTramitNom.trim());

        if (pageSize != null && pageSize >= 0) fg.setPaginaTamanyo(pageSize);
        if (page != null && page >= 0) fg.setPaginaFirst(page);

        if (hasText(ordenCampo)) {
            String mapped = mapOrdenCampo(ordenCampo.trim());
            if (mapped == null) throw new ValidationException("ordenCampo no és vàlid.");
            fg.setOrderBy(mapped);
        }
        if (hasText(ordenAscendente)) {
            if ("asc".equalsIgnoreCase(ordenAscendente.trim())) {
                fg.setOrder("ASCENDING");
                fg.setAscendente(true);
            } else if ("desc".equalsIgnoreCase(ordenAscendente.trim())) {
                fg.setOrder("DESCENDING");
                fg.setAscendente(false);
            } else {
                throw new ValidationException("ordenAscendente ha de ser asc o desc.");
            }
        }

        String idiomaPorDefecto = idiomaPorDefecto(fg.getIdEntidad());
        fg.setIdioma(idioma != null ? idioma : idiomaPorDefecto);
        Integer apiMaxLimit = aplicarApiMaxLimit(fg);
        URI uriCompleta = uriInfo.getRequestUri();
        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start, uriCompleta.toString(), apiMaxLimit),
                MediaType.APPLICATION_JSON).build();
    }

    private RespuestaBase getRespuesta(final ProcedimientoFiltro filtro, final String idiomaPorDefecto,
                                       final Instant start, final String url, final Integer apiMaxLimit) {
        // El contrato exige sólo versiones definitivas. La consulta REST debe aplicar
        // esa condición antes de paginar para que totalCount y totalPages sean correctos.
        Pagina<ProcedimientoBaseDTO> resultadoBusqueda = procedimientoService.findProcedimientosByFiltroRest(filtro);
        List<Servei> lista = new ArrayList<>();
        for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
            lista.add(new Servei((ServicioDTO) nodo, null, filtro.getIdioma(), true, idiomaPorDefecto));
        }
        int total = (int) resultadoBusqueda.getTotal();
        if (apiMaxLimit != null && total > apiMaxLimit) total = apiMaxLimit;
        long tiempo = Duration.between(start, Instant.now()).toMillis();
        return new RespuestaBase(total, lista.size(), filtro.getPaginaTamanyo(), filtro.getPaginaFirst(), url, lista, tiempo);
    }

    private String idiomaPorDefecto(final Long idEntidad) {
        if (idEntidad != null) {
            String idiomaEntidad = entidadService.getIdiomaPorDefecto(idEntidad);
            if (idiomaEntidad != null) return idiomaEntidad;
        }
        return systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
    }

    private Integer aplicarApiMaxLimit(final ProcedimientoFiltro filtro) {
        try {
            String raw = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.API_MAX_LIMIT);
            int max = Integer.parseInt(raw);
            if (max <= 0) return null;
            int offset = filtro.getPaginaFirst() != null ? filtro.getPaginaFirst() : 0;
            int size = filtro.getPaginaTamanyo() != null ? filtro.getPaginaTamanyo() : 10;
            if (offset >= max) {
                filtro.setPaginaFirst(0);
                filtro.setPaginaTamanyo(0);
            } else if (offset + size > max) {
                filtro.setPaginaTamanyo(max - offset);
            }
            return max;
        } catch (RuntimeException e) {
            LOG.warn("Límit API_MAX_LIMIT no vàlid", e);
            return null;
        }
    }

    private String mapOrdenCampo(final String value) {
        switch (value) {
            case "codi":
                return "codigo";
            case "dataActualitzacio":
            case "dataActualitazio":
                return "fechaActualizacion";
            case "dataCaducitat":
                return "fechaCaducidad";
            case "dataPublicacio":
                return "fechaPublicacion";
            case "codiSIA":
                return "codigoSIA";
            case "dataSIA":
                return "fechaSIA";
            default:
                return null;
        }
    }

    private boolean hasText(final String value) {
        return value != null && !value.trim().isEmpty();
    }

    private void checkDebug() {
        try {
            debugActivo = "S".equalsIgnoreCase(
                    systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.DEBUG_ACTIVO));
        } catch (Exception e) {
            debugActivo = false;
        }
        if (debugActivo) LOG.error("---- DEBUG ACTIVO ----");
    }
}
