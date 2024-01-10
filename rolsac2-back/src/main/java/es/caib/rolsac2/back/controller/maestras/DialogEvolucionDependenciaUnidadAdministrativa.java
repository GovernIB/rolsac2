package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@ViewScoped
public class DialogEvolucionDependenciaUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionDependenciaUnidadAdministrativa.class);

    private UnidadAdministrativaDTO padre;
    private Long idUA;
    private Integer version;
    private UnidadAdministrativaDTO padreAntiguo;

    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        if (id != null && id.split(",").length > 1) {
            ids = id.split(",");
        }

        if (ids != null && ids.length > 0) {
            idUA = Long.valueOf(ids[0]);
            data = unidadAdministrativaServiceFacade.findUASimpleByID(idUA, UtilJSF.getSessionBean().getLang(), null);
        } else if (id != null) {
            idUA = Long.valueOf(id);
            data = unidadAdministrativaServiceFacade.findUASimpleByID(idUA, UtilJSF.getSessionBean().getLang(), null);
        }

        version = data.getVersion();
        padreAntiguo = data.getPadre();
        padre = data.getPadre();
    }

    /**
     * Seleccionar UA
     */
    public void seleccionarUA() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.EDICION.toString());
        String direccion = "/comun/dialogSeleccionarUA";
        UtilJSF.anyadirMochila("ua", padre);
        UtilJSF.openDialog(direccion, TypeModoAcceso.EDICION, params, true, 850, 575);
    }

    /**
     * Gestión de retorno UA.
     *
     * @param event
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
            padre = (UnidadAdministrativaDTO) respuesta.getResult();
        }
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

    /**
     * Evolucionar
     */
    public void evolucionar() {
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        unidadAdministrativaServiceFacade.evolucionDependencia(idUA, padre.getCodigo(), UtilJSF.getSessionBean().getEntidad(), UtilJSF.getSessionBean().getPerfil(), usuario, version);

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
