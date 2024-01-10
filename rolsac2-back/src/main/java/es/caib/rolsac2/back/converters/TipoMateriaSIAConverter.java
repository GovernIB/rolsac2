package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.TipoMateriaSIADTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TipoMateriaSIAConverter implements Converter, Serializable {

    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni un") || s.contains("Seleccione un") || s.equals("Seleccione un tema") || s.equals("Seleccioni un tema"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            return maestrasSupServiceFacade.findTipoMateriaSIAById(Long.parseLong(s));
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((TipoMateriaSIADTO) o).getCodigo());
        } else {
            return null;
        }
    }
}