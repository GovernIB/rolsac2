package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogEvolucionUnidadAdministrativa extends EvolucionController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogEvolucionUnidadAdministrativa.class);

    private String idUA;

    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        idUA = id;
        //data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
    }

    public void irEvolucionBasica() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (idUA != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), idUA);
        }
        UtilJSF.openDialog("dialogEvolucionBasicaUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 570);
    }

    public void irEvolucionFusion() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (idUA != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), idUA);
        }
        UtilJSF.openDialog("dialogEvolucionFusionUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 875, 570);
    }

    /**
     * Si viene de una evolución, comprobar que ha ido ok.
     *
     * @param event
     */
    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogEvolucionUnidadAdministrativa.evolucionCorrecta"));
        }
    }

    public void irEvolucionDivision() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (idUA != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), idUA);
        }
        UtilJSF.openDialog("dialogEvolucionDivisionUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 740);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void irEvolucionCompetencias() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (idUA != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), idUA);
        }
        UtilJSF.openDialog("dialogEvolucionCompetenciasUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 570);
    }

    public void irEvolucionDependencia() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (idUA != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), idUA);
        }
        UtilJSF.openDialog("dialogEvolucionDependenciaUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 570);
    }

}
