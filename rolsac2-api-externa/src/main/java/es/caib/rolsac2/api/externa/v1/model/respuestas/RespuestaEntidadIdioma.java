package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.EntidadIdioma;
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
@Schema(name = "RespuestaEntidadIdioma", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_ENTIDADES)
public class RespuestaEntidadIdioma extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<EntidadIdioma> resultado;

    public RespuestaEntidadIdioma(String status, String mensaje, long l, List<EntidadIdioma> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaEntidadIdioma() {
        super();
    }

    public List<EntidadIdioma> getResultado() {
        return resultado;
    }

    public void setResultado(List<EntidadIdioma> resultado) {
        this.resultado = resultado;
    }
}