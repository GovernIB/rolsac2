package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.PersonalGridDTO;
import org.primefaces.component.datatable.DataTable;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Collection;

@FacesConverter(value = "personalConverter")
public class PersonalConverter implements Converter {

    @EJB
    PersonalServiceFacade personalService;


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String valor) {
        Long id = Long.valueOf(valor);
        try {
            Object ret = null;
            if (uiComponent instanceof DataTable) {
                Object dualList = ((DataTable) uiComponent).getValue();
                /*
                DualListModel<?> dl = (DualListModel<?>) dualList;
                for (Object o : dl.getSource()) {
                    String id = String.valueOf(((Vaga) o).getId());
                    if (value.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                */
            }
        } catch (Exception e) {
        }

        try {
            Collection items = (Collection) uiComponent.getAttributes().get("items");
            System.out.println(items);
        } catch (Exception e) {

        }
        if (id != null) {
            return personalService.findById(id);
        }


        return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o instanceof PersonalGridDTO ? ((PersonalGridDTO) o).getCodigo().toString() : " X ";
    }

}
