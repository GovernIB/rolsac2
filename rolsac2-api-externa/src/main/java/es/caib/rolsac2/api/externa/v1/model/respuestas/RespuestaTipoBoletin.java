package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoBoletin;
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
@Schema(name = "RespuestaTipoBoletin", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_BOLETINES)
public class RespuestaTipoBoletin extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoBoletin> resultado;

    public RespuestaTipoBoletin(String status, String mensaje, long l, List<TipoBoletin> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoBoletin() {
        super();
    }

    public List<TipoBoletin> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoBoletin> resultado) {
        this.resultado = resultado;
    }
}