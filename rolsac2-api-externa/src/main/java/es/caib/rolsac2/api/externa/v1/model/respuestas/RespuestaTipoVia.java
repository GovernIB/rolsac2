package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoVia;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Idioma
 *
 * @author indra
 */

@XmlRootElement
@Schema(name = "RespuestaTipoVia", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_VIA)
public class RespuestaTipoVia extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoVia> resultado;

    public RespuestaTipoVia(String status, String mensaje, long l, List<TipoVia> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoVia() {
        super();
    }

    public List<TipoVia> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoVia> resultado) {
        this.resultado = resultado;
    }
}