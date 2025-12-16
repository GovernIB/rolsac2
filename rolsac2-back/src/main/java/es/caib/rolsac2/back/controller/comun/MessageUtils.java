package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class MessageUtils implements Serializable {

    @Inject
    private FacesContext context;

    public void showInvalidCharacterMessage() {
        ResourceBundle labelsBundle = context.getApplication().getResourceBundle(context, "labels");

        UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, labelsBundle.getString("msg.warning.paste.caracteres_invalidos"),
                        labelsBundle.getString("msg.warning.paste.caracteres_invalidos"));
    }
}
