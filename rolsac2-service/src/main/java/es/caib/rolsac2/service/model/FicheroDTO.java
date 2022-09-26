package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Dades de un fichero.
 *
 * @author Indra
 */
@Schema(name = "Fichero")
public class FicheroDTO extends ModelApi {
    private Long codigo;

    private String filename;

    private byte[] contenido;

    private TypeFicheroExterno tipo;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public TypeFicheroExterno getTipo() {
        return tipo;
    }

    public void setTipo(TypeFicheroExterno tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "FicheroDTO{" +
                "id=" + codigo +
                ", filename='" + filename + '\'' +
                '}';
    }
}
