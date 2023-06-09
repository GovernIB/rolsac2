package es.caib.rolsac2.back.converters;

import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.Traduccion;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class TipoTramitacionConverter implements Converter, Serializable {

    private static final long serialVersionUID = -8972013394803135445L;

    @Inject
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // Workaround para que salte la validación de que hay que seleccionar un valor
        if (s != null && (s.contains("Seleccioni un") || s.contains("Seleccione un") || s.equals("Selecciona una opción") || s.equals("Tria una opció") || s.equals("Seleccioni un tipus") || s.equals("Seleccione un tipo"))) {
            s = null;
        }

        if (s != null && s.trim().length() > 0) {
            if(s.equals("-1")) {
                TipoTramitacionDTO plantillaFake = new TipoTramitacionDTO();
                Literal literal = Literal.createInstance();
                List<Traduccion> traduccions = new ArrayList<>();
                traduccions.add(new Traduccion("es", "Ninguna"));
                traduccions.add(new Traduccion("es", "Cap"));
                literal.setTraducciones(traduccions);
                plantillaFake.setCodigo(-1l);
                plantillaFake.setDescripcion(literal);
                return plantillaFake;
            } else {
                return maestrasSupServiceFacade.findTipoTramitacionById(Long.parseLong(s));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((TipoTramitacionDTO) o).getCodigo());
        } else {
            return null;
        }
    }
}
