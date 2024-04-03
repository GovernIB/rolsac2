package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.PlatTramitElectronica;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaPlatTramitElectronica
 *
 * @author indra
 */

@XmlRootElement
@Schema(name = "RespuestaPlatTramitElectronica", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PLATAFORMA)
public class RespuestaPlatTramitElectronica extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<PlatTramitElectronica> resultado;

    public RespuestaPlatTramitElectronica(String status, String mensaje, long l, List<PlatTramitElectronica> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaPlatTramitElectronica() {
        super();
    }

    public List<PlatTramitElectronica> getResultado() {
        return resultado;
    }

    public void setResultado(List<PlatTramitElectronica> resultado) {
        this.resultado = resultado;
    }
}