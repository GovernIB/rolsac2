package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.ProcedimientoDocumento;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta ProcedimientoDocumento
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaProcedimientoDocumento", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_PROCEDIMIENTO_DOCUMENTO)
public class RespuestaProcedimientoDocumento extends RespuestaBase {
    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<ProcedimientoDocumento> resultado;

    public RespuestaProcedimientoDocumento(String status, String mensaje, Long numeroElementos, List<ProcedimientoDocumento> resultado, Long tiempo) {
        super(status, mensaje, numeroElementos, tiempo);
        this.resultado = resultado;
    }

    public RespuestaProcedimientoDocumento() {
        super();
    }

    public List<ProcedimientoDocumento> getResultado() {
        return resultado;
    }

    public void setResultado(List<ProcedimientoDocumento> resultado) {
        this.resultado = resultado;
    }
}