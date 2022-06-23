package es.caib.rolsac2.back.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;

@Named
@ViewScoped
public class PlatTramitElectronicaConverter implements Converter, Serializable {

  private static final long serialVersionUID = 7636429006079444223L;

  @Inject
  private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
    // Workaround para que salte la validación de que hay que seleccionar un valor
    if ("Seleccioni una".equalsIgnoreCase(s) || "Seleccione una".equalsIgnoreCase(s)) {
      s = null;
    }

    if (s != null && s.trim().length() > 0) {
      return platTramitElectronicaServiceFacade.findById(Long.parseLong(s));

    } else {
      return null;
    }
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
    if (o != null) {
      return String.valueOf(((PlatTramitElectronicaDTO) o).getId());
    } else {
      return null;
    }
  }
}
