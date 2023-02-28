package es.caib.rolsac2.back.config;

import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.utils.GeneradorId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Named
@RequestScoped
public final class ResourcesServlet extends HttpServlet {

    private static final long serialVersionUID = 7040095709523857004L;

    private static final Logger LOG = LoggerFactory.getLogger(ResourcesServlet.class);

    @EJB
    FicheroServiceFacade ficheroServiceFacade;


    public void init() {
        /**
         * Como el id de la instancia se genera al inicializarse la aplicación, no hace falta registrarlo en el Servlet Context
         * ya que el identificador de la instancia se guardará en el EJB utilizado en la aplicación
         *
         */
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Solo sirve para inicialización del Servlet
        response.setContentType("text/css;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setDateHeader("Expires", System.currentTimeMillis() + 604800000L);
        FicheroDTO fichero = ficheroServiceFacade.getContentById(Long.valueOf(request.getParameter("id")));
        response.getOutputStream().write(fichero.getContenido());
    }
}
