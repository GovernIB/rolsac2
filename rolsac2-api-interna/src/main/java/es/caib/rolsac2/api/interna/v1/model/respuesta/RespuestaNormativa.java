package es.caib.rolsac2.api.interna.v1.model.respuesta;

import es.caib.rolsac2.api.interna.v1.model.Normativa;
import es.caib.rolsac2.api.interna.v1.model.Procedimientos;
import es.caib.rolsac2.api.interna.v1.utils.Constantes;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Respuesta Fitxes
 *
 * @author Indra
 */

@XmlRootElement
@Schema(name = "RespuestaNormativa", description = Constantes.TXT_RESPUESTA + Constantes.ENTIDAD_NORMATIVAS)
public class RespuestaNormativa extends RespuestaBase<Normativa> {


    public RespuestaNormativa(String status, String mensaje, Long numeroElementos, List<Normativa> resultado, Long tiempo) {
        super(status, mensaje, numeroElementos, tiempo);
        setData(resultado);
    }

    public RespuestaNormativa() {
        super();
    }



    public static Builder builder() {
        return new Builder<Normativa>();
    }
}