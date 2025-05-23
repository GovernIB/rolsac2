package es.caib.rolsac2.commons.plugins.pdu.api;

import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;

import java.util.List;

public interface IPluginPdu {

    /**
     * Dado un JSON como entrada devuelve el resultado de la importación (error o no), y en caso de éxito devuelve la url del link modificado. .
     *
     * @param @RPeticionImportarEnlace peticionImportarEnlace
     * @return RRespuestaImportarEnlace
     */
    RRespuestaImportarEnlace importarEnlace(RPeticionImportarEnlace peticionImportarEnlace);

    // DSS No probado
    RRespuestaImportarEnlace eliminarEnlaces(List<String> urls);
}
