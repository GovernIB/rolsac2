package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.procesos.EjecutorProcesos;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.facade.ProcesosExecServiceFacade;
import es.caib.rolsac2.service.utils.GeneradorId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ViewGestionProcesosIndex extends AbstractController implements Serializable {


    @EJB
    ProcesoTimerServiceFacade procesoTimerServiceFacade;


    public void process() {
        procesoTimerServiceFacade.procesar();
    }


}
