package es.caib.rolsac2.back.validators;

import es.caib.rolsac2.back.controller.component.LiteralComponent;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@FacesValidator("es.caib.rolsac2.back.validators.ValidadorLiteral")
public class ValidadorLiteral implements Validator {

    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (component instanceof LiteralComponent && component != null) {
            LiteralComponent literalComponent = (LiteralComponent) component;

            String estado = literalComponent.comprobarEstado();
            if (estado == null || estado.isEmpty() || LiteralComponent.ICONO_ROJO.equals(estado)) {

                literalComponent.setEstiloInput("bordeRojoRequired");


                String mensajeError = "";
                if (literalComponent.getAttributes().get("mensajeError") != null) {
                    mensajeError = literalComponent.getAttributes().get("mensajeError").toString();
                } else if (literalComponent.getAttributes().get("nombreLiteral") != null) {
                    String campo = literalComponent.getAttributes().get("nombreLiteral").toString();
                    mensajeError = getLiteral(context, "dict.obligatorio.generico", new Object[]{campo});

                } else {
                    mensajeError = "El literal és obligatori.";
                }
                FacesMessage msg =
                        new FacesMessage(mensajeError, mensajeError);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);

            } else {

                literalComponent.setEstiloInput("");
            }
        }
    }


    private String getLiteral(FacesContext context, final String key, final Object[] parametros) {
        ResourceBundle labelsBundle = context.getApplication().getResourceBundle(context, "labels");
        return MessageFormat.format(labelsBundle.getString(key), parametros);
    }

}
