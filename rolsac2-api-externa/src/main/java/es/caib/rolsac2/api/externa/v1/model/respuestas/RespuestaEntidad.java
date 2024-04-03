package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.Entidad;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaEntidad
 *
 * @author indra
 */

@XmlRootElement
@Schema(name = "RespuestaEntidad", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_ENTIDADES)
public class RespuestaEntidad extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<Entidad> resultado;

    public RespuestaEntidad(String status, String mensaje, long l, List<Entidad> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaEntidad() {
        super();
    }

    public List<Entidad> getResultado() {
        return resultado;
    }

    public void setResultado(List<Entidad> resultado) {
        this.resultado = resultado;
    }
}