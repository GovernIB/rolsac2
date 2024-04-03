package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.TipoPublicoObjetivoEntidad;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Tipo Publico Objetivo Entidad
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaTipoPublicoObjetivoEntidad", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_TEMAS)
public class RespuestaTipoPublicoObjetivoEntidad extends RespuestaBase {
    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<TipoPublicoObjetivoEntidad> resultado;

    public RespuestaTipoPublicoObjetivoEntidad(String status, String mensaje, Long numeroElementos, List<TipoPublicoObjetivoEntidad> resultado, Long tiempo) {
        super(status, mensaje, numeroElementos, tiempo);
        this.resultado = resultado;
    }

    public RespuestaTipoPublicoObjetivoEntidad() {
        super();
    }

    public List<TipoPublicoObjetivoEntidad> getResultado() {
        return resultado;
    }

    public void setResultado(List<TipoPublicoObjetivoEntidad> resultado) {
        this.resultado = resultado;
    }
}