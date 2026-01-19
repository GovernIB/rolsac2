package es.caib.rolsac2.commons.plugins.sia.sia;

import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.EnviaSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.ParamSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.WsSIAActualizarActuaciones;
import es.caib.rolsac2.commons.plugins.sia.sia.actualizar.WsSIAActualizarActuaciones_Service;

import javax.net.ssl.*;
import javax.xml.ws.BindingProvider;
import java.security.cert.X509Certificate;

public class SiaClient {

    private static void disableSSLVerification() {
        try {
            // Crear un TrustManager que no valida certificados
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Instalar el TrustManager que confía en todos
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Crear un HostnameVerifier que no valida el hostname
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final WsSIAActualizarActuaciones cliente;
    private final String usr;
    private final String pwd;

    public SiaClient(String url, String user, String password) throws Exception {
        disableSSLVerification(); // Agregar esta línea
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
