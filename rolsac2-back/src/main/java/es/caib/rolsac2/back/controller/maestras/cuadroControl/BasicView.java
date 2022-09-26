package es.caib.rolsac2.back.controller.maestras.cuadroControl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("dtBasicView")
@ViewScoped
public class BasicView implements Serializable {

  private List<Contingut> continguts;

  @Inject
  private ContingutService service;

  @PostConstruct
  public void init() {
    continguts = service.createContingut(3);
  }

  public List<Contingut> getContinguts() {
    return continguts;
  }

  public void setService(ContingutService service) {
    this.service = service;
  }
}
