package es.caib.rolsac2.common.test;

import es.caib.rolsac2.commons.plugins.traduccion.api.*;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestTranslatorIbPlugin {

    public static void main(String arg[]) throws IPluginTraduccionException {

        Map<String, String> opciones = new HashMap<>();
        Properties prop = new Properties();
        prop.put("es.caib.rolsac2.pluginsib.traduccion.translatorib.url", "https://dev.caib.es/translatorib/api/services/traduccion/v1");
        prop.put("es.caib.rolsac2.pluginsib.traduccion.translatorib.usr", "api-tib");
        prop.put("es.caib.rolsac2.pluginsib.traduccion.translatorib.pwd", "XXXXXXX");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.traduccion.translatorib.TranslatorIBPlugin",
                "es.caib.rolsac2.pluginsib.traduccion.translatorib.", prop);
        IPluginTraduccion pdgTraduccion = (IPluginTraduccion) plg;
        String resultadoTraduccion =  pdgTraduccion.traducir(TipoEntrada.TEXTO_PLANO.toString(), "prueba", Idioma.CASTELLANO, Idioma.CATALAN, opciones);
        System.out.println("Resultado de la traducción es: " + resultadoTraduccion);
    }

}