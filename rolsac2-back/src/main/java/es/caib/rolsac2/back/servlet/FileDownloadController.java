package es.caib.rolsac2.back.servlet;


import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/download/*")
public class FileDownloadController extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads"; // Carpeta donde se almacenan los archivos

    @Inject
    private FicheroServiceFacade ficheroServiceFacade;

    @Inject
    private SystemServiceFacade systemServiceFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la ruta relativa desde la URL
        String id = request.getPathInfo(); // "/nombreArchivo.ext"
        if (id == null || id.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Archivo no especificado");
            return;
        }
        id = id.replaceAll("/", "");

        // Solo sirve para inicialización del Servlet
        response.setContentType("text/css;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setDateHeader("Expires", System.currentTimeMillis() + 604800000L);
        String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        FicheroDTO fichero = ficheroServiceFacade.getContentAyudaByReferencia(id, path);


        if (fichero == null || fichero.getContenido() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        // Configurar el tipo de contenido
        response.setContentType(getServletContext().getMimeType(fichero.getFilename()));
        response.setContentLengthLong(fichero.getContenido().length);

        response.getOutputStream().write(fichero.getContenido());

    }
}
