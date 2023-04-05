package es.caib.rolsac2.commons.plugins.sia.sia;

import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.EnviaSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.ParamSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.WsSIAActualizarActuaciones;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.WsSIAActualizarActuaciones_Service;

import javax.ws.rs.client.Client;
import javax.xml.ws.BindingProvider;

public class SiaClient {

    private final WsSIAActualizarActuaciones cliente;
    private final String usr;
    private final String pwd;

    public SiaClient(String url, String user, String password) throws Exception {
        this.cliente = getCliente(url);
        this.usr = user;
        this.pwd = password;
    }

    /**
     * Obtiene el cliente soap.
     *
     * @param url
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    private WsSIAActualizarActuaciones getCliente(String url)
            throws NumberFormatException, Exception {
        final WsSIAActualizarActuaciones_Service servicio = new WsSIAActualizarActuaciones_Service();
        final WsSIAActualizarActuaciones cliente = servicio.getWsSIAActualizarActuacionesSOAP();
        final BindingProvider provider = (BindingProvider) cliente;
        provider.getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        return cliente;

    }

    public EnviaSIA actualizarSIAV31(ParamSIA paramSIA) {
        return cliente.actualizarSIAV31(paramSIA);
    }


}
