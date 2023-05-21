package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewAlertas extends AbstractController implements Serializable {

    public void abrirVentana() {
        final Map<String, String> params = new HashMap<>();

        UtilJSF.openDialog("dialogAlertas", TypeModoAcceso.ALTA, params, true, 780, 500);
    }

}
