package es.caib.rolsac2.api.interna.v1.model;

import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import es.caib.rolsac2.service.model.FicheroDTO;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;

/**
 * Fichero.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "Fichero", description = Constantes.TXT_DEFINICION_CLASE + Constantes.ENTIDAD_FICHERO)
public class Fichero extends EntidadBase<Fichero> {

    private static final Logger LOG = LoggerFactory.getLogger(Fichero.class);

    /**
     * referencia
     */
    @Schema(description = "referencia", name = "referencia", type = SchemaType.STRING, required = true)
    private String referencia;

    /**
     * filename.
     **/
    @Schema(description = "filename", name = "filename", type = SchemaType.STRING, required = false)
    private String filename;

    /**
     * codigo
     **/
    @Schema(description = "codigo", name = "codigo", type = SchemaType.INTEGER, required = false)
    private Long codigo;

    /**
     * tipo
     */
    @Schema(description = "tipo", name = "tipo", type = SchemaType.STRING, required = false)
    private String tipo;

    /**
     * Contenido
     */
    @Schema(description = "contenido", name = "contenido", type = SchemaType.STRING, required = false)
    private String contenido;

    public Fichero(FicheroDTO nodo, String urlBase, String idioma, String idiomaPorDefecto, boolean hateoasEnabled, boolean conDatos) {
        super(nodo, urlBase, idioma, idiomaPorDefecto, hateoasEnabled);

        if (nodo.getTipo() != null) {
            tipo = nodo.getTipo().getTipo();
        }

        if (conDatos && nodo.getContenido() != null) {
            contenido = Base64.getEncoder().encodeToString(nodo.getContenido());
        }

    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Fichero() {
        super();
    }

    @Override
    public void generaLinks(String urlBase) {
    }

    @Override
    protected void addSetersInvalidos() {
        SETTERS_INVALIDS.add("setContenido");
        SETTERS_INVALIDS.add("setTipo");
    }

    @Override
    public void setId(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve la referencia
     *
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia
     *
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Devuelve el codigo
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo
     *
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el filenmae
     *
     * @return Filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Establece el filename
     *
     * @param filename filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Devuelve el tipo
     *
     * @return Tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     *
     * @param tipo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
