package es.caib.rolsac2.common.test;

import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.dir3.api.IPluginDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganica;
import es.caib.rolsac2.commons.plugins.dir3.caib.Dir3CaibRestPlugin;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.*;

public class TestDir3CaibPlugin {



    @Test
    public void obtenerUnidades() {

        Map<String, String> opciones = new HashMap<>();
        Properties prop = new Properties();
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.url", "https://dev.caib.es/dir3caib/rest");
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.usr", "$sistra_dir3caib");
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.pwd", "sistra_dir3caib");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.dir3.caib.Dir3CaibRestPlugin",
                "es.caib.rolsac2.pluginsib.dir3.caib.", prop);
        IPluginDir3 plgDir3 = (IPluginDir3) plg;
        ParametrosDir3 parametros = new ParametrosDir3();
        parametros.setCodigo("A04026906");
        parametros.setDenominacionCooficial(Boolean.TRUE);
        List<UnidadOrganica> unidades = null;
        try {
            unidades = plgDir3.obtenerArbolUnidades(parametros);
            System.out.println("Árbol de la unidad: " + parametros.getCodigo());
            for(UnidadOrganica unidad : unidades) {
                System.out.println(unidad.toString());
            }
        } catch (Dir3ErrorException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void obtenerHistoricosFInales() {
        Map<String, String> opciones = new HashMap<>();
        Properties prop = new Properties();
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.url", "https://dev.caib.es/dir3caib/rest");
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.usr", "$sistra_dir3caib");
        prop.put("es.caib.rolsac2.pluginsib.dir3.caib.pwd", "sistra_dir3caib");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.dir3.caib.Dir3CaibRestPlugin",
                "es.caib.rolsac2.pluginsib.dir3.caib.", prop);
        IPluginDir3 plgDir3 = (IPluginDir3) plg;
        ParametrosDir3 parametros = new ParametrosDir3();
        parametros.setCodigo("A04026908");
        parametros.setDenominacionCooficial(Boolean.TRUE);
        List<UnidadOrganica> unidades = null;
        try {
            unidades = plgDir3.obtenerHistoricosFinales(parametros);
            System.out.println("Histórico de la unidad: " + parametros.getCodigo());
            for(UnidadOrganica unidad : unidades) {
                System.out.println(unidad.toString());
            }
        } catch (Dir3ErrorException e) {
            e.printStackTrace();
        }
    }
}
