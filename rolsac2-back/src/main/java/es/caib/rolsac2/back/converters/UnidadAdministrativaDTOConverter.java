package es.caib.rolsac2.back.converters;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;

@Named
public class UnidadAdministrativaDTOConverter implements Converter {

	@EJB
	UnidadAdministrativaServiceFacade unidadAdministrativaService;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, String value) {
		if (value == null || value.length() == 0) {
			return null;
		}

		final Long id;
		try {
			id = Long.parseLong(value);
		} catch (final NumberFormatException nfe) {
			final String error = "unidadAdministrativa_no_valido";
//			final FacesMessage facesMessage = MessageFactory.getMessage(error, FacesMessage.SEVERITY_ERROR);
			FacesMessage msg = new FacesMessage("Error", error);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			throw new ConverterException(msg);
		}

		return unidadAdministrativaService.findById(id);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof UnidadAdministrativaDTO && ((UnidadAdministrativaDTO) value).getCodigo() != null) {
			return ((UnidadAdministrativaDTO) value).getCodigo().toString();
		} else {
			return "";
		}
	}

}
