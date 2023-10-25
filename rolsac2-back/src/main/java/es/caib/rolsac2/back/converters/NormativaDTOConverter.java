package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

@Named
public class NormativaDTOConverter implements Converter {

    @EJB
    NormativaServiceFacade normativaService;

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        final Long id;
        try {
            id = Long.parseLong(value);
        } catch (final NumberFormatException nfe) {
            final String error = "normativa_no_valido";
            //			final FacesMessage facesMessage = MessageFactory.getMessage(error, FacesMessage.SEVERITY_ERROR);
            FacesMessage msg = new FacesMessage("Error", error);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ConverterException(msg);
        }

        return normativaService.findById(id);
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (value != null && value instanceof NormativaDTO && ((NormativaDTO) value).getCodigo() != null) {
            return ((NormativaDTO) value).getCodigo().toString();
        } else if (value != null && value instanceof NormativaGridDTO && ((NormativaGridDTO) value).getCodigo() != null) {
            return ((NormativaGridDTO) value).getCodigo().toString();
        } else {
            return "";
        }
    }

}
