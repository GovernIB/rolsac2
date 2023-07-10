package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
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

    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
    }

    public void irEvolucionBasica() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.data != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), data.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionBasicaUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 540);
    }

    public void irEvolucionFusion() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.data != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), data.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionFusionUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 540);
    }

    public void irEvolucionDivision() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.data != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), data.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionDivisionUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 540);
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
        if (this.data != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), data.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionCompetenciasUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 540);
    }

    public void irEvolucionDependencia() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.data != null && (this.isModoEdicion() || this.isModoConsulta())) {
            params.put(TypeParametroVentana.ID.toString(), data.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogEvolucionDependenciaUnidadAdministrativa", TypeModoAcceso.EDICION, params, true, 775, 540);
    }

}