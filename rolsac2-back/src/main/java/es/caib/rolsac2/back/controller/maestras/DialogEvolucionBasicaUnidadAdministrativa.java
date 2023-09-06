package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DialogEvolucionBasicaUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionBasicaUnidadAdministrativa.class);

    private UnidadAdministrativaDTO dataDestino;

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

        uaDestino = new Literal();

        Traduccion traduccionModificada;
        String textoTraduccion = "";

        for (Traduccion traduccion : data.getNombre().getTraducciones()) {
            textoTraduccion = traduccion.getLiteral() + " (DUPLICADO)";

            traduccionModificada = new Traduccion(traduccion.getIdioma(), textoTraduccion);

            uaDestino.add(traduccionModificada);
        }
    }

    public void evolucionar() {

        unidadAdministrativaServiceFacade.evolucionBasica(data, fechaBaja, uaDestino, normativa, UtilJSF.getSessionBean().getEntidad());

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    public void editarUnidadAdministrativa() {
        FacesMessage msg = new FacesMessage("Successful", "Edición pendiente de implementación");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void evolucionar2() {
        //		if (!verificarGuardar()) {
        //			return;
        //		}

        UnidadAdministrativaDTO dataAntigua = (UnidadAdministrativaDTO) data.clone();
        dataDestino = (UnidadAdministrativaDTO) data.clone();
        //		data.setFechaBaja(fechaBaja);
        dataDestino.setNombre(uaDestino);

        unidadAdministrativaServiceFacade.update(data, dataAntigua, sessionBean.getPerfil());

        unidadAdministrativaServiceFacade.create(dataDestino, sessionBean.getPerfil());

        //		if (this.data.getCodigo() == null) {
        //			unidadAdministrativaServiceFacade.create(this.data, sessionBean.getPerfil());
        //		} else {
        //			unidadAdministrativaServiceFacade.update(this.data, this.dataAntigua, sessionBean.getPerfil());
        //		}

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public UnidadAdministrativaDTO getDataDestino() {
        return dataDestino;
    }

    public void setDataDestino(UnidadAdministrativaDTO dataDestino) {
        this.dataDestino = dataDestino;
    }
}
