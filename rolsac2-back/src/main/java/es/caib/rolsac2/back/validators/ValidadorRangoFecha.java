package es.caib.rolsac2.back.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

@FacesValidator("validadorRangoFecha")
public class ValidadorRangoFecha implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        // Validar si la fecha que viene como value es válida
        Date fechaDesde = null;
        Date fechaHasta = (Date) component.getAttributes().get("fechaHasta");
        if(fechaHasta != null) {
            fechaDesde = (Date) value;
        }else{
            fechaHasta = (Date) value;
            fechaDesde = (Date) component.getAttributes().get("fechaDesde");
        }

        if(fechaDesde != null && fechaHasta!= null && fechaHasta.before(fechaDesde)){
            String mensajeError = "La fecha desde no puede ser posterior a la fecha hasta.";
            FacesMessage msg = new FacesMessage(mensajeError, mensajeError);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }



    private String getLiteral(FacesContext context, final String key, final Object[] parametros) {
        ResourceBundle labelsBundle = context.getApplication().getResourceBundle(context, "labels");
        return MessageFormat.format(labelsBundle.getString(key), parametros);
    }

}
