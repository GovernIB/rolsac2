package es.caib.rolsac2.back.procesos;

import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.utils.GeneradorId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Named
@RequestScoped
public final class EjecutorProcesos extends HttpServlet {

    private static final long serialVersionUID = 7040095709523857004L;

    private static final Logger LOG = LoggerFactory.getLogger(EjecutorProcesos.class);

    @EJB
    ProcesoTimerServiceFacade procesoTimerServiceFacade;


    public void init() {
        LOG.info("Proceso para iniciar timer");
        /**
         * Como el id de la instancia se genera al inicializarse la aplicación, no hace falta registrarlo en el Servlet Context
         * ya que el identificador de la instancia se guardará en el EJB utilizado en la aplicación
         *
         */
        String instancia = GeneradorId.generarId();
        procesoTimerServiceFacade.initTimer(instancia);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException
    {
        // Solo sirve para inicialización del Servlet
    }
}
