package es.caib.rolsac2.api.interna.v1.model.filters;

import es.caib.rolsac2.api.interna.v1.model.EntidadJson;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * FiltroPaginacion.
 *
 * @author indra
 */
@XmlRootElement
@Schema(name = "FiltroPaginacion", type = SchemaType.STRING, description = "Filtro que permite paginar los resultados")
public class FiltroPaginacion extends EntidadJson<FiltroPaginacion> {

    private static final Logger LOG = LoggerFactory.getLogger(FiltroPaginacion.class);

    public static final String SAMPLE_JSON = "{\"page\":\"0\",\"size\":\"10\"}";

    public static final String SAMPLE = Constantes.SALTO_LINEA + SAMPLE_JSON;

//	public static final String LANG_SAMPLE = "ca";

    /**
     * Page.
     **/
    @Schema(required = false, type = SchemaType.INTEGER, name = "page", description = "Pagina", defaultValue = "0")
    private Integer page;

    /**
     * Size.
     **/
    @Schema(required = false, type = SchemaType.INTEGER, name = "size", description = "Resultados por pagina", defaultValue = "10")
    private Integer size;

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Calcula el primer valor de la paginación a partir de la página y el tamaño.
     *
     * @return the offset
     */
    public Integer getOffset() {
        if (page == null || size == null) {
            return 0;
        }
        return page * size;
    }

    @Override
    public String toString() {
        return "FiltroPaginacion{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
