package es.caib.rolsac2.commons.plugins.pdu.api.model;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;

public class ResultadoPdu extends ResultadoAccion {
    private RRespuestaImportarEnlace respuestaPdu;

    public ResultadoPdu(boolean correcto, String mensaje) {
        super(correcto, mensaje);
    }

    public ResultadoPdu(boolean correcto, String mensaje, RRespuestaImportarEnlace resp) {
        super(correcto, mensaje);

        this.respuestaPdu = resp;
    }

    public RRespuestaImportarEnlace getRespuestaPdu() {
        return respuestaPdu;
    }

    public void setRespuestaPdu(RRespuestaImportarEnlace respuestaPdu) {
        this.respuestaPdu = respuestaPdu;
    }
}
