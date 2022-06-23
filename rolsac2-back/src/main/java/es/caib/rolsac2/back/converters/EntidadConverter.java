package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EntidadConverter implements Converter, Serializable {

    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            return administracionSupServiceFacade.findEntidadById(Long.parseLong(s));
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((EntidadDTO) o).getId());
        } else {
            return null;
        }
    }
}