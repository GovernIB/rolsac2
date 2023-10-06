package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DialogEvolucionBasicaUnidadAdministrativa extends EvolucionController implements Serializable {

    /**
     * LOGGER
     **/
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionBasicaUnidadAdministrativa.class);

    /**
     * ID UA
     **/
    private Long idUA;

    /**
     * LOAD
     **/
    public void load() {
        LOG.debug("init Evolucion basica");

        this.setearIdioma();
        if (id != null && id.split(",").length > 1) {
            ids = id.split(",");
        }

        if (ids != null && ids.length > 0) {
            idUA = Long.valueOf(ids[0]);
            data = unidadAdministrativaServiceFacade.findUASimpleByID(idUA, UtilJSF.getSessionBean().getLang(), null);
        } else if (id != null) {
            idUA = Long.valueOf(id);
            data = unidadAdministrativaServiceFacade.findUASimpleByID(idUA, UtilJSF.getSessionBean().getLang(), null); //findById(Long.valueOf(id));
        }

        uaDestino = new Literal();
        Traduccion traduccionModificada;
        String textoTraduccion = "";

        for (Traduccion traduccion : data.getNombre().getTraducciones()) {
            textoTraduccion = traduccion.getLiteral() + " (DUPLICADO)";

            traduccionModificada = new Traduccion(traduccion.getIdioma(), textoTraduccion);

            uaDestino.add(traduccionModificada);
        }
    }

    /**
     * Evoluciona UA
     **/
    public void evolucionar() {

        unidadAdministrativaServiceFacade.evolucionBasica(idUA, fechaBaja, uaDestino, normativa, UtilJSF.getSessionBean().getEntidad());

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

}
