package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.model.DocumentoNormativa;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * RespuestaDocumentoDocumentoNormativa
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaDocumentoNormativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_DOCUMENTO_NORMATIVA)
public class RespuestaDocumentoNormativa extends RespuestaBase {
    /**
     * Resultado.
     **/
    @Schema(name = "resultado", description = "Listado con los objetos de resultado", required = false)
    private List<DocumentoNormativa> resultado;

    public RespuestaDocumentoNormativa(String status, String mensaje, Long numeroElementos, List<DocumentoNormativa> resultado, Long tiempo) {
        super(status, mensaje, numeroElementos, tiempo);
        this.resultado = resultado;
    }

    public RespuestaDocumentoNormativa() {
        super();
    }

    public List<DocumentoNormativa> getResultado() {
        return resultado;
    }

    public void setResultado(List<DocumentoNormativa> resultado) {
        this.resultado = resultado;
    }
}