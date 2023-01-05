package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.TipoAfectacionDTO;
import es.caib.rolsac2.service.model.TipoViaDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TipoAfectacionConverter implements Converter, Serializable {

    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni") || s.contains("Seleccione") || s.equals("Selecciona una opción") || s.equals("Tria una opció"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            TipoAfectacionDTO tipo = maestrasSupServiceFacade.findTipoAfectacionById(Long.parseLong(s));
            return tipo;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((TipoAfectacionDTO) o).getCodigo());
        } else {
            return null;
        }
    }
}
