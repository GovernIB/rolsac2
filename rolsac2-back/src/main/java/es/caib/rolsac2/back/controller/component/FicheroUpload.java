package es.caib.rolsac2.back.controller.component;/**
 * @author Indra
 */

import org.primefaces.model.file.UploadedFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Indra
 **/
public class FicheroUpload implements UploadedFile {

    private byte[] data;

    private String filename;

    private String contentType;

    public FicheroUpload(byte[] data, String filename, String contentType) {
        this.data = data;
        this.filename = filename;
        this.contentType = contentType;
    }

    @Override
    public String getFileName() {
        return filename;
    }

    public void filename(String filename) {
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public byte[] getContent() {
        return data;
    }

    public void setContent(byte[] contenido) {
        data = contenido;
    }


    @Override
    public long getSize() {
        if (data == null) {
            return 0l;
        }
        return data.length;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        contentType = contentType;
    }

    @Override
    public void write(String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        }
    }
}
