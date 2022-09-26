package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.BoletinOficialServiceFacade;
import es.caib.rolsac2.service.model.BoletinOficialDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class BoletinOficialConverter implements Converter, Serializable {

    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private BoletinOficialServiceFacade boletinOficialServiceFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni un") || s.contains("Seleccione un"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            try{
                return boletinOficialServiceFacade.findBoletinOficialByCodigo(Long.parseLong(s));
            } catch (Exception e) {
                return null;
            }

        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((BoletinOficialDTO) o).getCodigo());
        } else {
            return null;
        }
    }
}