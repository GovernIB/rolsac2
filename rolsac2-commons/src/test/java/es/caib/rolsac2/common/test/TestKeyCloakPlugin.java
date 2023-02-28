package es.caib.rolsac2.common.test;


import es.caib.rolsac2.commons.plugins.autenticacion.api.AutenticacionErrorException;
import es.caib.rolsac2.commons.plugins.autenticacion.api.IPluginAutenticacion;
import es.caib.rolsac2.commons.plugins.autenticacion.api.model.UsuarioAutenticado;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.Properties;

public class TestKeyCloakPlugin {

    @Test
    public void testAutenticacion(){
        Properties prop = new Properties();
        /*prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.resource", "goib-ws");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.authUrl", "http://localhost:8180/auth");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.realm", "GOIB");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.secret", "6175c27c-deaa-426e-a744-75f6047c93d4");*/
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.resource", "goib-ws");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.authUrl", "http://logindes.caib.es/auth");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.realm", "webdes");
        prop.put("es.caib.rolsac2.pluginsib.autenticacion.keycloak.secret", "cf346def-19fd-42b9-885d-9a65519e4cd9");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.autenticacion.keycloak.KeyCloakRestPlugin",
                "es.caib.rolsac2.pluginsib.autenticacion.", prop);
        if (plg == null) {
            System.out.println("No se ha instanciado el plugin correctamente");
        } else {
            System.out.println("Se ha instanciado correctamente el plugin");

            try {
                IPluginAutenticacion plgAutenticacion = (IPluginAutenticacion) plg;
                UsuarioAutenticado usuario = plgAutenticacion.login("$rolsac_translatorib", "rolsac_translatorib");
                System.out.println(usuario.toString());
            } catch (AutenticacionErrorException e) {
                e.printStackTrace();
            }


        }
    }

}