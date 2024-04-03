package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoSilencioAdministrativo;
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
@Schema(name = "RespuestaTipoSilencioAdministrativo", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_SILENCIO)
public class RespuestaTipoSilencioAdministrativo extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoSilencioAdministrativo> resultado;

    public RespuestaTipoSilencioAdministrativo(String status, String mensaje, long l, List<TipoSilencioAdministrativo> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoSilencioAdministrativo() {
        super();
    }

    public List<TipoSilencioAdministrativo> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoSilencioAdministrativo> resultado) {
        this.resultado = resultado;
    }
}