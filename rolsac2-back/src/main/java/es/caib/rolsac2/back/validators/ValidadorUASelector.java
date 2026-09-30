package es.caib.rolsac2.back.validators;

import es.caib.rolsac2.back.controller.component.UnidadAdministrativaComponent;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@FacesValidator("es.caib.rolsac2.back.validators.ValidadorUASelector")
public class ValidadorUASelector implements Validator {

    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (component instanceof UnidadAdministrativaComponent && component != null) {
            UnidadAdministrativaComponent uaComponent = (UnidadAdministrativaComponent) component;

            if (uaComponent.getValue() == null || (uaComponent.getValue() instanceof UnidadAdministrativaDTO && ((UnidadAdministrativaDTO) uaComponent.getValue()).getCodigo() == null)) {

                uaComponent.setEstiloInput("bordeRojoRequired botonRojoRequired");


                String mensajeError = "";
                if (uaComponent.getAttributes().get("mensajeError") != null) {
                    mensajeError = uaComponent.getAttributes().get("mensajeError").toString();
                } else if (uaComponent.getAttributes().get("nombreLiteral") != null) {
                    String campo = uaComponent.getAttributes().get("nombreLiteral").toString();
                    mensajeError = getLiteral(context, "dict.obligatorio.generico", new Object[]{campo});

                } else {
                    mensajeError = "El literal és obligatori.";
                }
                FacesMessage msg =
                        new FacesMessage(mensajeError, mensajeError);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } else {
                uaComponent.setEstiloInput("");
            }
        }
    }


    private String getLiteral(FacesContext context, final String key, final Object[] parametros) {
        ResourceBundle labelsBundle = context.getApplication().getResourceBundle(context, "labels");
        return MessageFormat.format(labelsBundle.getString(key), parametros);
    }

}
