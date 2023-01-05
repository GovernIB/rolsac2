package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dades de un fichero.
 *
 * @author Indra
 */
@Schema(name = "Fichero")
public class FicheroDTO extends ModelApi implements Cloneable {

    /**
     * LOGGERR
     **/
    private static final Logger LOG = LoggerFactory.getLogger(FicheroDTO.class);

    /**
     * Codigo
     */
    private Long codigo;

    /**
     * Nombre del archivo
     */
    private String filename;

    /**
     * Contenido
     */
    private byte[] contenido;

    /**
     * Tipo del fichero externo
     */
    private TypeFicheroExterno tipo;


    /**
     * Obtiene codigo.
     *
     * @return  codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo  codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene filename.
     *
     * @return  filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Establece filename.
     *
     * @param filename  filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Get contenido byte [ ].
     *
     * @return  byte [ ]
     */
    public byte[] getContenido() {
        return contenido;
    }

    /**
     * Establece contenido.
     *
     * @param contenido  contenido
     */
    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene tipo.
     *
     * @return  tipo
     */
    public TypeFicheroExterno getTipo() {
        return tipo;
    }

    /**
     * Establece tipo.
     *
     * @param tipo  tipo
     */
    public void setTipo(TypeFicheroExterno tipo) {
        this.tipo = tipo;
    }

    public Object clone() {
        FicheroDTO obj = null;
        try {
            obj = (FicheroDTO) super.clone();


        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }

    @Override
    public String toString() {
        return "FicheroDTO{" +
                "id=" + codigo +
                ", filename='" + filename + '\'' +
                '}';
    }
}
