package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;

@Named
@ViewScoped
public class ViewGestionProcesosIndex extends AbstractController implements Serializable {


    @EJB
    ProcesoTimerServiceFacade procesoTimerServiceFacade;


    public void process() {
        procesoTimerServiceFacade.procesar();
    }


}
