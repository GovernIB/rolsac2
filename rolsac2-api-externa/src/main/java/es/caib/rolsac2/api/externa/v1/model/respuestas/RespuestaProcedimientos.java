package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Procediment;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Procedimientos
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaProcedimientos", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PROCEDIMIENTO)
public class RespuestaProcedimientos extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(description = "Listado con los objetos de resultado")
    private List<Procediment> resultado;

    /**
     * Url.
     **/
    @Schema(description = "Enlace tramite telematico")
    private String url;

    /**
     * Constructor
     *
     * @param total              Total de elementos
     * @param size               Tamaño de la página
     * @param paginaTamanyo      Tamaño de la página
     * @param paginaFirst        Primer elemento de la página
     * @param url                URL del recurso
     * @param lista              Lista de procedimientos
     * @param tiempoMiliSegundos Tiempo en milisegundos
     */
    public RespuestaProcedimientos(int total, int size, Integer paginaTamanyo, Integer paginaFirst, String url, List<Procediment> lista, long tiempoMiliSegundos) {
        super(total, size, paginaTamanyo, paginaFirst, url, lista, tiempoMiliSegundos);
        this.url = url;
        this.resultado = lista;
    }

    /**
     * Constructor vacio.
     */
    public RespuestaProcedimientos() {
        super();
    }

    /**
     * Devuelve el resultado.
     *
     * @return
     */
    public List<Procediment> getResultado() {
        return resultado;
    }

    /**
     * Establece el resultado.
     *
     * @param resultado
     */
    public void setResultado(final List<Procediment> resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

}