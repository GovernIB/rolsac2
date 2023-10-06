package es.caib.rolsac2.back.validators;

import es.caib.rolsac2.back.controller.component.LiteralComponent;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Traduccion;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@FacesValidator("es.caib.rolsac2.back.validators.ValidadorListaNoVacia")
public class ValidadorListaNoVacia implements Validator {

    public void customValidation(ComponentSystemEvent event) {
        if (event != null) {

        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (component != null) {
            LiteralComponent literalComponent = (LiteralComponent) component;

            String estado = literalComponent.comprobarEstado();

            if (literalComponent.isObligatorio() && (estado == null || estado.isEmpty() || LiteralComponent.ICONO_ROJO.equals(estado))) {

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
                FacesMessage msg = new FacesMessage(mensajeError, mensajeError);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);

            } else if (literalComponent.getAttributes().get("maxlength") != null) {
                Integer length = (Integer) literalComponent.getAttributes().get("maxlength");
                //Si no es el largo por defecto, revisamos si se pasan
                if (length != 9999) {
                    Literal literal = (Literal) literalComponent.getAttributes().get("literal");
                    for (Traduccion trad : literal.getTraducciones()) {
                        if (trad.getLiteral() != null && trad.getLiteral().length() > length) {
                            literalComponent.setEstiloInput("bordeRojoRequired");
                            Object[] param = new Object[3];
                            param[0] = literalComponent.getAttributes().get("nombreLiteral").toString();
                            param[1] = trad.getIdioma();
                            param[2] = length.toString();
                            String mensajeError = getLiteral(context, "dict.maxlength", param);
                            FacesMessage msg = new FacesMessage(mensajeError, mensajeError);
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(msg);
                        }
                    }
                }
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
