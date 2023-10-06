package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class DialogEvolucionFusionUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionFusionUnidadAdministrativa.class);

    private List<UnidadAdministrativaGridDTO> selectedUnidades;
    private UnidadAdministrativaGridDTO uaSeleccionada;
    private UnidadAdministrativaDTO uaFusion;
    private Long idUA;

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

        selectedUnidades = new ArrayList<UnidadAdministrativaGridDTO>();
        selectedUnidades.add(data.convertDTOtoGridDTO());

        uaDestino = new Literal();

    }

    public void evolucionar() {
        unidadAdministrativaServiceFacade.evolucionFusion(selectedUnidades, normativa, fechaBaja, uaFusion, UtilJSF.getSessionBean().getPerfil());

        final DialogResult result = new DialogResult();
        result.setCanceled(false);
        UtilJSF.closeDialog(result);
    }

    /**
     * Método para consultar el detalle de una UA
     */
    public void consultarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogUAs(TypeModoAcceso.CONSULTA);
        }
    }

    public void crearUA() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.MODO_EVOLUCION.toString(), "S");
        params.put(TypeParametroVentana.MODO_EVOLUCION_UAS.toString(), getUas());
        UtilJSF.anyadirMochila("ua", uaFusion);
        UtilJSF.openDialog("dialogUnidadAdministrativa", TypeModoAcceso.ALTA, params, true, 975, 733);
    }

    private String getUas() {

        String retorno = "";
        if (this.selectedUnidades != null) {
            for (UnidadAdministrativaGridDTO ua : selectedUnidades) {
                retorno += ua.getCodigo() + ",";
            }
        }
        return retorno;
    }

    public void returnDialogoCrearUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha rellenado los datos
        if (!respuesta.isCanceled()) {
            uaFusion = (UnidadAdministrativaDTO) respuesta.getResult();
            uaDestino = uaFusion.getNombre();
        }
    }

    /**
     * Abrir dialogo de Selección de Unidades Administrativas
     */
    public void abrirDialogUAs(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", uaSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("dialogUnidadAdministrativa", modoAcceso, params, true, 1530, 733);

        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("unidadesAdministrativas", selectedUnidades);
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.ALTA.toString());
            // params.put("esCabecera", "true");
            String direccion = "/comun/dialogSeleccionarUA";
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 850, 575);
        }
    }

    /**
     * Método para dar de alta UAs en un usuario
     */
    public void anyadirUAs() {
        abrirDialogUAs(TypeModoAcceso.ALTA);
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (selectedUnidades != null && !selectedUnidades.isEmpty()) {
                for (UnidadAdministrativaGridDTO ua : selectedUnidades) {
                    if (ua.getCodigo().compareTo(uaSeleccionada.getCodigo()) == 0) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
                        return;
                    }
                }
            }

            selectedUnidades.add(uaSeleccionada.convertDTOtoGridDTO());
        }
    }

    public String onFlowProcess(FlowEvent event) {

        if (event.getOldStep().contains("origen")) {

            if (this.selectedUnidades == null || this.selectedUnidades.size() < 2) {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogEvolucionFusionUnidadAdministrativa.error.minimoDosUnidades"), true);
                return event.getOldStep();
            }
        }

        return event.getNewStep();
    }

    /**
     * Método para borrar un usuario en una UA
     */
    public void borrarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            selectedUnidades.remove(uaSeleccionada);
            uaSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    public UnidadAdministrativaGridDTO getUaSeleccionada() {
        return uaSeleccionada;
    }

    public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) {
        this.uaSeleccionada = uaSeleccionada;
    }

    public void setSelectedUnidades(List<UnidadAdministrativaGridDTO> selectedUnidades) {
        this.selectedUnidades = selectedUnidades;
    }

    public List<UnidadAdministrativaGridDTO> getSelectedUnidades() {
        return selectedUnidades;
    }
}
