package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FlowEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class EvolucionController extends AbstractController {

    protected String id;
    protected String[] ids;
    protected UnidadAdministrativaDTO data;
    protected Date fechaBaja;
    protected NormativaDTO normativa;

    protected Literal uaDestino;

    @Inject
    protected UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @Inject
    protected NormativaServiceFacade normativaServiceFacade;

    public List<NormativaDTO> completeNormativa(final String query) {
        List<NormativaDTO> suggestions = new ArrayList<NormativaDTO>();
        NormativaFiltro filtro = new NormativaFiltro();
        filtro.setIdioma(getIdioma());

        if (query == null || query.isBlank()) {
            filtro.setPaginaFirst(0);
            filtro.setPaginaTamanyo(10);
        }

        filtro.setNombre(StringUtils.stripAccents(query.toLowerCase()));

        Pagina<NormativaDTO> resultado = normativaServiceFacade.findByFiltroRest(filtro);

        if (resultado != null) {
            suggestions = resultado.getItems();
        }

        return suggestions;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(UnidadAdministrativaDTO data) {
        this.data = data;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public NormativaDTO getNormativa() {
        return normativa;
    }

    public void setNormativa(NormativaDTO normativa) {
        this.normativa = normativa;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public Literal getUaDestino() {
        return uaDestino;
    }

    public void setUaDestino(Literal uaDestino) {
        this.uaDestino = uaDestino;
    }

}
