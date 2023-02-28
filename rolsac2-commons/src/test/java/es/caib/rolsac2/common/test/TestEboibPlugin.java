package es.caib.rolsac2.common.test;


import es.caib.rolsac2.commons.plugins.boletin.api.BoletinErrorException;
import es.caib.rolsac2.commons.plugins.boletin.api.IPluginBoletin;
import es.caib.rolsac2.commons.plugins.boletin.api.model.Edicto;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class TestEboibPlugin {

    @Test
    public void testConsultaBOIB() {
        Properties prop = new Properties();
        prop.put("es.caib.rolsac2.pluginsib.boletin.eboib.eboibUrl", "https://www.caib.es/eboibfront/");
        prop.put("es.caib.rolsac2.pluginsib.boletin.eboib.eboibUrlHack", "true");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.boletin.eboib.EboibPlugin",
                "es.caib.rolsac2.pluginsib.boletin.eboib.", prop);
        if (plg == null) {
            System.out.println("No se ha instanciado el plugin correctamente");
        } else {
            System.out.println("Se ha instanciado correctamente el plugin");

            try {
                IPluginBoletin plgBoletin = (IPluginBoletin) plg;
                List<Edicto> edictosObtenidos = plgBoletin.listar("042/2022", "", "");
                System.out.println("Se obtiene listado de edictos correctamente");
                for (Edicto edicto : edictosObtenidos) {
                    System.out.println(edicto.toString());
                }
            } catch (BoletinErrorException e) {
                e.printStackTrace();
            }
        }
    }

}