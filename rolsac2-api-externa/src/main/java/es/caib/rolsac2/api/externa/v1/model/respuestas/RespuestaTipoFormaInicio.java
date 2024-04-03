package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoFormaInicio;
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
@Schema(name = "RespuestaTipoFormaInicio", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_FORMA)
public class RespuestaTipoFormaInicio extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoFormaInicio> resultado;

    public RespuestaTipoFormaInicio(String status, String mensaje, long l, List<TipoFormaInicio> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoFormaInicio() {
        super();
    }

    public List<TipoFormaInicio> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoFormaInicio> resultado) {
        this.resultado = resultado;
    }
}