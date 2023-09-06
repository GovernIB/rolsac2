package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.UnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FlowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DialogEvolucionDependenciaUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionDependenciaUnidadAdministrativa.class);

    private UnidadAdministrativaDTO padre;
    private UnidadAdministrativaDTO padreAntiguo;

    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        if (id != null && id.split(",").length > 1) {
            ids = id.split(",");
        }

        if (ids != null && ids.length > 0) {
            data = unidadAdministrativaServiceFacade.findById(Long.valueOf(ids[0]));
        } else if (id != null) {
            data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
        }

        padreAntiguo = data.getPadre();
        padre = data.getPadre();
    }

    public String onFlowProcess(FlowEvent event) {

        if (event.getOldStep().contains("destino")) {
            String comprobar = "";

            if (data.getCodigo().compareTo(padre.getCodigo()) == 0) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionDependenciaUnidadAdministrativa.error.elMismo"), true);
                return event.getOldStep();
            }

            if (padreAntiguo.getCodigo().compareTo(padre.getCodigo()) == 0) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionDependenciaUnidadAdministrativa.error.sinCambios"), true);
                return event.getOldStep();
            }

            //Comprobamos si cuelga del mismo arbol
            if (unidadAdministrativaServiceFacade.checkCuelga(data, padre)) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionDependenciaUnidadAdministrativa.error.cuelgaUA"), true);
                return event.getOldStep();
            }
        }

        return event.getNewStep();
    }

    public void evolucionar() {
        unidadAdministrativaServiceFacade.evolucionDependencia(data.getCodigo(), padre.getCodigo(), UtilJSF.getSessionBean().getEntidad());

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    public List<UnidadAdministrativaDTO> completeUnidadAdministrativa(final String query) {
        List<UnidadAdministrativaDTO> suggestions = new ArrayList<UnidadAdministrativaDTO>();
        UnidadAdministrativaFiltro filtro = new UnidadAdministrativaFiltro();
        filtro.setIdioma(getIdioma());

        if (query == null || query.isBlank()) {
            filtro.setPaginaFirst(0);
            filtro.setPaginaTamanyo(10);
        }

        filtro.setNombre(StringUtils.stripAccents(query.toLowerCase()));

        Pagina<UnidadAdministrativaDTO> resultado = unidadAdministrativaServiceFacade.findByFiltroRest(filtro);

        if (resultado != null) {
            suggestions = resultado.getItems();
        }

        return suggestions;
    }

    public Literal getUaDestino() {
        return uaDestino;
    }

    public void setUaDestino(Literal uaDestino) {
        this.uaDestino = uaDestino;
    }

    public UnidadAdministrativaDTO getPadre() {
        return padre;
    }

    public void setPadre(UnidadAdministrativaDTO padre) {
        this.padre = padre;
    }

    public UnidadAdministrativaDTO getPadreAntiguo() {
        return padreAntiguo;
    }

    public void setPadreAntiguo(UnidadAdministrativaDTO padreAntiguo) {
        this.padreAntiguo = padreAntiguo;
    }
}
