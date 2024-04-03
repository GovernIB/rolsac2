package es.caib.rolsac2.api.externa.v1.model.respuestas;

import es.caib.rolsac2.api.externa.v1.exception.ExcepcionAplicacion;
import es.caib.rolsac2.api.externa.v1.exception.NotFoundException;
import es.caib.rolsac2.api.externa.v1.exception.ParamException;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Respuesta Error
 *
 * @author indra
 */

@XmlRootElement
@Schema(name = "RespuestaError", description = Constantes.TXT_RESPUESTA + "Error")
public class RespuestaError extends RespuestaBase {

    public RespuestaError(String status, String mensaje, Long tiempo) {
        super(status, mensaje, 0l, tiempo);
    }

    ;

    public RespuestaError() {
        super();
    }

    public RespuestaError(ExcepcionAplicacion ex) {
        super(ex.getStatus() + "", ex.getMensajeError(), 0l, null);
    }

    public RespuestaError(NotFoundException ex) {
        super(ex.getResponse().getStatus() + "", Constantes.MSJ_404_GENERICO, 0l, null);
    }

    public RespuestaError(ParamException ex) {
        super(ex.getResponse().getStatus() + "", Constantes.MSJ_400_GENERICO + "(parametro: " + ex.getParameterName() + " // Tipo esperado: " + ex.getParameterName() + ")", 0l, null);
    }

    public RespuestaError(ValidationException ex, String errores) {
        super(Response.Status.BAD_REQUEST.getStatusCode() + "", Constantes.MSJ_400_GENERICO + "(" + errores + ")", 0l, null);
    }



}