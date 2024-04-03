package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoLegitimacion;
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
@Schema(name = "RespuestaTipoLegitimacion", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_LEGITIMACION)
public class RespuestaTipoLegitimacion extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoLegitimacion> resultado;

    public RespuestaTipoLegitimacion(String status, String mensaje, long l, List<TipoLegitimacion> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoLegitimacion() {
        super();
    }

    public List<TipoLegitimacion> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoLegitimacion> resultado) {
        this.resultado = resultado;
    }
}