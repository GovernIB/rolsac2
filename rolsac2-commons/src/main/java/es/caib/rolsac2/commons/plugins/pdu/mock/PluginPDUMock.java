package es.caib.rolsac2.commons.plugins.pdu.mock;

import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;
import org.apache.http.HttpStatus;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.Arrays;
import java.util.List;

public class PluginPDUMock extends AbstractPluginProperties implements IPluginPdu {


    @Override
    public RRespuestaImportarEnlace importarEnlace(RPeticionImportarEnlace peticionImportarEnlace) {

        if(peticionImportarEnlace != null && peticionImportarEnlace.getLinkData() != null && !peticionImportarEnlace.getLinkData().isEmpty() && "error".equals(peticionImportarEnlace.getLinkData().get(0).getTitle())){
            RRespuestaImportarEnlace respuesta = new RRespuestaImportarEnlace();
            respuesta.setCodigoEstado(HttpStatus.SC_BAD_REQUEST);

            return respuesta;
        }


        RRespuestaImportarEnlace respuesta = new RRespuestaImportarEnlace();
        respuesta.setCodigoEstado(HttpStatus.SC_OK);
        respuesta.setEnlaces(Arrays.asList("https://www.caib.es/seucaib/es/200/persones%20/tramites/tramite/2882755", "https://www.caib.es/seucaib/en/200/persones%20/tramites/tramite/2882755"));
        return respuesta;
    }

    @Override
    public RRespuestaImportarEnlace eliminarEnlaces(List<String> urls) {
        RRespuestaImportarEnlace respuesta = new RRespuestaImportarEnlace();
        respuesta.setCodigoEstado(HttpStatus.SC_OK);
        respuesta.setEnlaces(Arrays.asList("https://www.caib.es/seucaib/es/200/persones%20/tramites/tramite/2882755", "https://www.caib.es/seucaib/en/200/persones%20/tramites/tramite/2882755"));
        return respuesta;
    }
}
