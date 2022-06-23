package es.caib.rolsac2.api.externa.services;

import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import es.caib.rolsac2.service.model.filtro.PersonalFiltro;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Recurs REST per accedir a personal.
 *
 * @author Indra
 */
@Path("personals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalResource {

    @EJB
    private PersonalServiceFacade personalService;

    /**
     * Retorna unta llista paginada de les personals.
     *
     * @return Un codi 200 amb les personals.
     */
    @GET
    @Operation(operationId = "getPersonals", summary = "Retorna una llista paginada de personals")
    @APIResponse(
            responseCode = "200",
            description = "Llista de personals",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Pagina.class)))
    public Response getPersonals(
            @Parameter(description = "Primer resultat, per defecte 0")
            @DefaultValue("0") @QueryParam("firstResult") int firstResult,
            @Parameter(description = "Nombre màxim de resultats, per defecte 10")
            @DefaultValue("10") @QueryParam("maxResult") int maxResult) {
        PersonalFiltro filtro = new PersonalFiltro();
        filtro.setPaginaFirst(firstResult);
        filtro.setPaginaTamanyo(maxResult);
        Pagina<PersonalGridDTO> pagina = personalService.findByFiltro(filtro);
        return Response.ok().entity(pagina).build();
    }

}
