package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoUnidadAdministrativa;
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
@Schema(name = "RespuestaTipoUnidadAdministrativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TIPO_UNIDAD)
public class RespuestaTipoUnidadAdministrativa extends RespuestaBase {

    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoUnidadAdministrativa> resultado;

    public RespuestaTipoUnidadAdministrativa(String status, String mensaje, long l, List<TipoUnidadAdministrativa> resultado, Long tiempo) {
        super(status, mensaje, l, tiempo);
        this.resultado = resultado;
    }

    ;

    public RespuestaTipoUnidadAdministrativa() {
        super();
    }

    public List<TipoUnidadAdministrativa> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoUnidadAdministrativa> resultado) {
        this.resultado = resultado;
    }
}