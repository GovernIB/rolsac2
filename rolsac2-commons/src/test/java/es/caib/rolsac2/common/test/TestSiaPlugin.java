package es.caib.rolsac2.common.test;


import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIA;
import es.caib.rolsac2.commons.plugins.sia.api.IPluginSIAException;
import es.caib.rolsac2.commons.plugins.sia.api.model.EnvioSIA;
import es.caib.rolsac2.commons.plugins.sia.sia.SiaClient;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestSiaPlugin {

    /**
     * Se genera un envío vacío para ver si SIA responde correctamente.
     */
    @Test
    public void actualizarActiacionSia() {
        Map<String, String> opciones = new HashMap<>();
        Properties prop = new Properties();
        prop.put("es.caib.rolsac2.pluginsib.sia.sia.url", "http://pre-sia2.redsara.es/axis2/services/wsSIAActualizarActuaciones");
        prop.put("es.caib.rolsac2.pluginsib.sia.sia.usr", "WSBALEARES");
        prop.put("es.caib.rolsac2.pluginsib.sia.sia.pwd", "primera");
        IPluginSIA plg = (IPluginSIA) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.sia.sia.SiaWSPlugin",
                "es.caib.rolsac2.pluginsib.sia.sia.", prop);
        EnvioSIA envioSIA = new EnvioSIA();
        try {
            plg.enviarSIA(envioSIA, false, false);
        } catch (IPluginSIAException e) {
            throw new RuntimeException(e);
        }
    }
}
