package es.caib.rolsac2.back.servlet;

import es.caib.rolsac2.service.facade.FicheroServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/api/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 20   // 20 MB
)
public class FileUploadController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    private static final String UPLOAD_DIR = "uploads/";

    @Inject
    private FicheroServiceFacade ficheroServiceFacade;

    @Inject
    private SystemServiceFacade systemServiceBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Extraer el archivo del formulario
            Part filePart = request.getPart("file"); // 'file' es el nombre del campo del formulario
            if (filePart != null && filePart.getSize() > 0) {
                // Crear un nombre de archivo único
                String fileName = System.currentTimeMillis() + "_" + simplicarFilename(getFileName(filePart));

                // Obtener el id de la pagina
                String idAyuda = getIdAyuda(filePart);

                // Obtener el byte[] del archivo subido
                byte[] fileBytes = getBytesFromPart(filePart);

                // Obtener la ruta de los ficheros externos
                String path = systemServiceBean.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);

                // Creamos el fichero
                String idFichero = ficheroServiceFacade.createFicheroAyuda(fileBytes, fileName, TypeFicheroExterno.AYUDAS_IMAGEN, path);

                // Devolvemos la ruta
                String contextPath = request.getContextPath();
                String fileUrl = contextPath + "/api/download/" + idFichero;
                response.setContentType("application/json");
                response.getWriter().write("{\"location\": \"" + fileUrl + "\"}");

            } else {
                // Manejar el error si no se recibió un archivo
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"No se pudo procesar la imagen\"}");
            }
        } catch (Exception e) {
            // Manejar el error si no se pudo procesar la imagen
            LOG.error("Error al subir la imagen del tinymce", e);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No se pudo procesar la imagen\"}");
        }
    }

    /**
     * Método para obtener el id de la ayuda
     *
     * @param filePart Part
     * @return Id ayuda
     */
    private String getIdAyuda(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("idAyuda")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "desconocido";
    }

    /**
     * Método para obtener el nombre del archivo
     *
     * @param filePart Part
     * @return Filename
     */
    private String getFileName(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "archivo";
    }

    private String simplicarFilename(String filename) {
        if (filename == null) return "";
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    /**
     * Método para leer un Part y obtener su contenido en un byte[]
     *
     * @param filePart Part
     * @return byte[]
     * @throws IOException IOException
     */
    private byte[] getBytesFromPart(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }
}
