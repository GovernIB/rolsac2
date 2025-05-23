package es.caib.rolsac2.api.interna.v1.model.respuesta;

import es.caib.rolsac2.api.interna.v1.model.UnidadAdministrativa;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Idioma
 *
 * @author indra
 */

@XmlRootElement
@Schema(name = "RespuestaUA", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_UA)
public class RespuestaUA extends RespuestaBase<UnidadAdministrativa> {
    /**
     * Resultado.
     **/
    @Schema(description = "Listado con los objetos de resultado", required = false)
    private List<UnidadAdministrativa> resultado;

    public RespuestaUA(String status, String mensaje, Long numeroElementos, List<UnidadAdministrativa> resultado, Long tiempo) {
        super(status, mensaje, numeroElementos, tiempo);
        setData(resultado);
    }

    public RespuestaUA() {
        super();
    }

    public List<UnidadAdministrativa> getResultado() {
        return resultado;
    }

    public void setResultado(List<UnidadAdministrativa> resultado) {
        this.resultado = resultado;
    }

    public static Builder builder() {
        return new Builder<UnidadAdministrativa>();
    }
}