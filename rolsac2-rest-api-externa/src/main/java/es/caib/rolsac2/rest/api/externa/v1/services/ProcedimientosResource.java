package es.caib.rolsac2.rest.api.externa.v1.services;

import es.caib.rolsac2.api.externa.v1.model.Procediment;
import es.caib.rolsac2.api.externa.v1.model.respuestas.RespuestaBase;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.facade.EntidadServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoBaseDTO;
import es.caib.rolsac2.service.model.ProcedimientoDTO;
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

@Path(Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO)
@Tag(description = Constantes.API_VERSION_BARRA + Constantes.ENTIDAD_PROCEDIMIENTO, name = Constantes.ENTIDAD_PROCEDIMIENTO)
public class ProcedimientosResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientosResource.class);

    @EJB
    ProcedimientoServiceFacade procedimientoService;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    EntidadServiceFacade entidadService;

    @Context
    private UriInfo uriInfo;

    private boolean debugActivo = false;

    /**
     * Llistat de procediments.
     * <p>
     * Retorna els procediments disponibles en funció dels filtres indicats
     * com a paràmetres de consulta.
     *
     * @return Llistat de procediments.
     * @throws ValidationException Gestió d'excepcions.
     */
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Path("/")
    @Operation(
            operationId = "listarProcedimientos",
            summary = "Llista els procediments",
            description = "Llista els procediments disponibles en funció dels filtres indicats."
    )
    @APIResponse(
            responseCode = "200",
            description = Constantes.MSJ_200_GENERICO,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = RespuestaBase.class)
            )
    )/*
    @APIResponse(
            responseCode = "400",
            description = Constantes.MSJ_400_GENERICO,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = RespuestaError.class)
            )
    )*/
    public Response listarProcedimientos(

            @Parameter(
                    description = "Idioma de la informació retornada. Per defecte, català.",
                    name = "idioma",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "ca")
            )
            @QueryParam("idioma")
            @DefaultValue("ca") final String idioma,

            @Parameter(
                    description = "Codi de l'entitat. Per defecte, 1.",
                    name = "entitat",
                    in = ParameterIn.QUERY,
                    schema = @Schema(defaultValue = "1")
            )
            @QueryParam("entitat")
            @DefaultValue("1") final Long entitat,

            @Parameter(
                    description = "Codi del procediment.",
                    name = "codi",
                    in = ParameterIn.QUERY
            )
            @QueryParam("codi") final Long codi,

            @Parameter(
                    description = "Nom del procediment.",
                    name = "nom",
                    in = ParameterIn.QUERY
            )
            @QueryParam("nom") final String nom,

            @Parameter(
                    description = "Data inicial del rang de data d'actualització, en format ISO8601.",
                    name = "iniciDataActualitzacio",
                    in = ParameterIn.QUERY
            )
            @QueryParam("iniciDataActualitzacio") final String iniciDataActualitzacio,

            @Parameter(
                    description = "Data final del rang de data d'actualització, en format ISO8601.",
                    name = "fiDataActualitzacio",
                    in = ParameterIn.QUERY
            )
            @QueryParam("fiDataActualitzacio") final String fiDataActualitzacio,

            @Parameter(
                    description = "Data inicial del rang de data de caducitat, en format ISO8601.",
                    name = "iniciDataCaducitat",
                    in = ParameterIn.QUERY
            )
            @QueryParam("iniciDataCaducitat") final String iniciDataCaducitat,

            @Parameter(
                    description = "Data final del rang de data de caducitat, en format ISO8601.",
                    name = "fiDataCaducitat",
                    in = ParameterIn.QUERY
            )
            @QueryParam("fiDataCaducitat") final String fiDataCaducitat,

            @Parameter(
                    description = "Codi SIA del procediment.",
                    name = "codiSIACodi",
                    in = ParameterIn.QUERY
            )
            @QueryParam("codiSIACodi") final String codiSIACodi,

            @Parameter(
                    description = "Estat del procediment a SIA.",
                    name = "estatSIA",
                    in = ParameterIn.QUERY
            )
            @QueryParam("estatSIA") final String estatSIA,

            @Parameter(
                    description = "Codi de la unitat administrativa competent responsable.",
                    name = "uaCompetentCodi",
                    in = ParameterIn.QUERY
            )
            @QueryParam("uaCompetentCodi") final Long uaCompetentCodi,

            @Parameter(
                    description = "Nom de la unitat administrativa competent responsable.",
                    name = "uaCompetentNom",
                    in = ParameterIn.QUERY
            )
            @QueryParam("uaCompetentNom") final String uaCompetentNom,

            @Parameter(
                    description = "Codi de la unitat administrativa instructora.",
                    name = "uaInstructorCodi",
                    in = ParameterIn.QUERY
            )
            @QueryParam("uaInstructorCodi") final Long uaInstructorCodi,

            @Parameter(
                    description = "Nom de la unitat administrativa instructora.",
                    name = "uaInstructorNom",
                    in = ParameterIn.QUERY
            )
            @QueryParam("uaInstructorNom") final String uaInstructorNom,

            @Parameter(
                    description = "Indica si el procediment és comú a diverses unitats.",
                    name = "comu",
                    in = ParameterIn.QUERY
            )
            @QueryParam("comu") final Boolean comu,

            @Parameter(
                    description = "Codi del tipus de procediment.",
                    name = "tipusCodi",
                    in = ParameterIn.QUERY
            )
            @QueryParam("tipusCodi") final Long tipusCodi,

            @Parameter(
                    description = "Nom del tipus de procediment.",
                    name = "tipusNom",
                    in = ParameterIn.QUERY
            )
            @QueryParam("tipusNom") final String tipusNom,

            @Parameter(
                    description = "Estat general del procediment.",
                    name = "estat",
                    in = ParameterIn.QUERY
            )
            @QueryParam("estat") final String estat,

            @Parameter(
                    description = "Indica si es permet la tramitació mitjançant apoderat.",
                    name = "habilitatApoderat",
                    in = ParameterIn.QUERY
            )
            @QueryParam("habilitatApoderat") final Boolean habilitatApoderat,

            @Parameter(
                    description = "Indica si està habilitada la tramitació mitjançant funcionari.",
                    name = "habilitatFuncionari",
                    in = ParameterIn.QUERY
            )
            @QueryParam("habilitatFuncionari") final Boolean habilitatFuncionari,

            @Parameter(
                    description = "Termini de resolució del procediment.",
                    name = "terminiResolucio",
                    in = ParameterIn.QUERY
            )
            @QueryParam("terminiResolucio") final String terminiResolucio,

            @Parameter(
                    description = "Mida de la pàgina.",
                    name = "page-size",
                    in = ParameterIn.QUERY
            )
            @QueryParam("page-size") final Integer pageSize,

            @Parameter(
                    description = "Número de la pàgina.",
                    name = "page",
                    in = ParameterIn.QUERY
            )
            @QueryParam("page") final Integer page,

            @Parameter(
                    description = "Camp pel qual s'ha d'ordenar. "
                            + "Valors possibles: codi, dataActualitzacio, dataCaducitat, "
                            + "dataPublicacio, codiSIA o dataSIA.",
                    name = "ordenCampo",
                    in = ParameterIn.QUERY,
                    schema = @Schema(
                            enumeration = {
                                    "codi",
                                    "dataActualitzacio",
                                    "dataCaducitat",
                                    "dataPublicacio",
                                    "codiSIA",
                                    "dataSIA"
                            }
                    )
            )
            @QueryParam("ordenCampo") final String ordenCampo,

            @Parameter(
                    description = "Sentit de l'ordenació. Valors possibles: asc o desc.",
                    name = "ordenAscendente",
                    in = ParameterIn.QUERY,
                    schema = @Schema(
                            enumeration = {"asc", "desc"}
                    )
            )
            @QueryParam("ordenAscendente") final String ordenAscendente

    ) throws ValidationException {

        checkDebug();
        if (debugActivo) {
            LOG.error("---- DEBUG ACTIVO ----");

        }

        Instant start = Instant.now();

        final ProcedimientoFiltro fg = new ProcedimientoFiltro();
        fg.setTipo("P");
        fg.setIdEntidad(entitat);
        if (fg.getIdEntidad() == null) {
            fg.setIdEntidad(1L);
        }

        if (codi != null) {
            fg.setCodigo(codi);
        }

        if (nom != null && !nom.trim().isEmpty()) {
            fg.setNombre(nom.trim());
        }

        if (iniciDataActualitzacio != null && !iniciDataActualitzacio.trim().isEmpty()) {
            fg.setInicioFechaActualizacion(iniciDataActualitzacio.trim());
        }

        if (fiDataActualitzacio != null && !fiDataActualitzacio.trim().isEmpty()) {
            fg.setFinFechaActualizacion(fiDataActualitzacio.trim());
        }

        if (iniciDataCaducitat != null && !iniciDataCaducitat.trim().isEmpty()) {
            fg.setInicioFechaCaducidad(iniciDataCaducitat.trim());
        }

        if (fiDataCaducitat != null && !fiDataCaducitat.trim().isEmpty()) {
            fg.setFinFechaCaducidad(fiDataCaducitat.trim());
        }

        if (codiSIACodi != null && !codiSIACodi.trim().isEmpty()) {
            try {
                fg.setCodigoSIA(Integer.valueOf(codiSIACodi.trim()));
            } catch (NumberFormatException e) {
                LOG.warn("El parámetro codiSIACodi no es válido: {}", codiSIACodi);
            }
        }

        if (estatSIA != null && !estatSIA.trim().isEmpty()) {
            fg.setEstadoSIA(estatSIA.trim());
        }

        if (uaCompetentCodi != null) {
            fg.setCodigoUACompetente(uaCompetentCodi);
        }

        if (uaCompetentNom != null && !uaCompetentNom.trim().isEmpty()) {
            fg.setNombreUACompetente(uaCompetentNom.trim());
        }

        if (uaInstructorCodi != null) {
            fg.setCodigoUAInstructora(uaInstructorCodi);
        }

        if (uaInstructorNom != null && !uaInstructorNom.trim().isEmpty()) {
            fg.setNombreUAInstructora(uaInstructorNom.trim());
        }

        if (comu != null) {
            fg.setComun(comu ? "S" : "N");
        }

        if (tipusCodi != null) {
            fg.setCodigoTipoProcedimiento(tipusCodi);
        }

        if (tipusNom != null && !tipusNom.trim().isEmpty()) {
            fg.setNombreTipoProcedimiento(tipusNom.trim());
        }

        if (estat != null && !estat.trim().isEmpty()) {
            fg.setEstado(estat.trim());
        }

        if (habilitatApoderat != null) {
            fg.setHabilitadoApoderado(habilitatApoderat);
        }

        if (habilitatFuncionari != null) {
            fg.setHabilitadoFuncionario(habilitatFuncionari);
        }

        if (terminiResolucio != null && !terminiResolucio.trim().isEmpty()) {
            fg.setTerminoResolucion(terminiResolucio.trim());
        }

        if (pageSize != null && pageSize >= 0) {
            fg.setPaginaTamanyo(pageSize);
        }

        if (page != null && page >= 0) {
            fg.setPaginaFirst(page);
        }

        if (ordenCampo != null && !ordenCampo.trim().isEmpty()) {
            fg.setOrderBy(ordenCampo.trim());
        }

        if (ordenAscendente != null && !ordenAscendente.trim().isEmpty()) {
            if ("asc".equalsIgnoreCase(ordenAscendente.trim())) {
                fg.setOrder("ASCENDING");
                fg.setAscendente(true);
            } else if ("desc".equalsIgnoreCase(ordenAscendente.trim())) {
                fg.setOrder("DESCENDING");
                fg.setAscendente(false);
            } else {
                LOG.warn("El parámetro ordenAscendente no es válido: {}", ordenAscendente);
            }
        }

        if (debugActivo) LOG.error(" listarProcedimientos: filtro mapeado: {}", fg);


        String idiomaPorDefecto;
        if (fg.getIdEntidad() == null) {
            idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
        } else {
            idiomaPorDefecto = entidadService.getIdiomaPorDefecto(fg.getIdEntidad());
            if (idiomaPorDefecto == null) {
                idiomaPorDefecto = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMA_DEFECTO);
            }
        }
        if (idioma != null) {
            fg.setIdioma(idioma);
        } else {
            fg.setIdioma(idiomaPorDefecto);
        }


        // Limitar el número total de elementos según API_MAX_LIMIT
        Integer apiMaxLimit = null;
        try {
            String apiMaxLimitStr = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.API_MAX_LIMIT);
            apiMaxLimit = Integer.parseInt(apiMaxLimitStr);
            if (apiMaxLimit > 0) {
                int offset = fg.getPaginaFirst() != null ? fg.getPaginaFirst() : 0;
                int size = fg.getPaginaTamanyo() != null ? fg.getPaginaTamanyo() : 10;
                if (offset >= apiMaxLimit) {
                    fg.setPaginaFirst(0);
                    fg.setPaginaTamanyo(0);
                } else if (offset + size > apiMaxLimit) {
                    fg.setPaginaTamanyo(apiMaxLimit - offset);
                }
            } else {
                apiMaxLimit = null;
            }
        } catch (NumberFormatException e) {
            LOG.warn("Limite de la respuesta de la API no valido: ", e);
        }
        if (debugActivo) LOG.error(" listarProcedimientos: filtro final: {}", fg);


        URI uriCompleta = uriInfo.getRequestUri();
        String url = uriCompleta.toString();

        return Response.ok(getRespuesta(fg, idiomaPorDefecto, start, url, apiMaxLimit), MediaType.APPLICATION_JSON).build();
    }

    private void checkDebug() {
        try {
            String debug_activo = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.DEBUG_ACTIVO);
            if (debug_activo != null && debug_activo.equalsIgnoreCase("S")) {
                debugActivo = true;
            }
        } catch (Exception e) {
            debugActivo = false;
        }
    }


    private RespuestaBase getRespuesta(final ProcedimientoFiltro filtro, final String idiomaPorDefecto, final Instant start, String url, Integer apiMaxLimit) {
        if (debugActivo) LOG.error(" getRespuesta: filtro: {}", filtro);
        Pagina<ProcedimientoBaseDTO> resultadoBusqueda = procedimientoService.findProcedimientosByFiltroRest(filtro);
        if (debugActivo) LOG.error(" getRespuesta: resultadoBusqueda: {}", resultadoBusqueda);
        List<Procediment> lista = new ArrayList<>();
        Procediment elemento;

        for (ProcedimientoBaseDTO nodo : resultadoBusqueda.getItems()) {
            elemento = new Procediment((ProcedimientoDTO) nodo, null, filtro.getIdioma(), true, idiomaPorDefecto);
            lista.add(elemento);
            if (debugActivo) LOG.error(" getRespuesta: añadido procedimiento a la lista: {}", elemento);
        }

        // Limitar el total de elementos reportado según API_MAX_LIMIT
        int total = (int) resultadoBusqueda.getTotal();
        if (apiMaxLimit != null && total > apiMaxLimit) {
            total = apiMaxLimit;
        }

        Instant finish = Instant.now();
        long tiempoMiliSegundos = Duration.between(start, finish).toMillis();
        if (debugActivo) LOG.error(" getRespuesta: tiempoMiliSegundos: {}", tiempoMiliSegundos);

        return new RespuestaBase(
                total,
                lista.size(),
                filtro.getPaginaTamanyo(),
                filtro.getPaginaFirst(),
                url,
                lista,
                tiempoMiliSegundos);
    }

}