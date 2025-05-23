package es.caib.rolsac2.common.test;

import es.caib.rolsac2.commons.plugins.dir3.api.Dir3ErrorException;
import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RCategory;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RLinkData;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RPeticionImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RRespuestaImportarEnlace;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeDelete;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeLanguage;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeLink;
import es.caib.rolsac2.commons.plugins.pdu.api.model.RTypeUrl;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestPduCaibPlugin {



    @Test
    public void importarEnlace() {

        Map<String, String> opciones = new HashMap<>();
        Properties prop = new Properties();
        prop.put("pluginsib.pdu.url", "https://webgate.acceptance.ec.europa.eu/youreurope/sdg/public/dtx");
        prop.put("pluginsib.pdu.usr", "usercaib");
        prop.put("pluginsib.pdu.pwd", "pwdCaib");
        IPlugin plg = (IPlugin) PluginsManager.instancePluginByClassName("es.caib.rolsac2.commons.plugins.pdu.PDUPlugin",
                "pluginsib.pdu.", prop);
        IPluginPdu plgPdu = (IPluginPdu) plg;


        RPeticionImportarEnlace peticion = crearPeticionTest();


        try {
            RRespuestaImportarEnlace respueesta = plgPdu.importarEnlace(peticion);
            System.out.println("Árbol de la unidad: " + respueesta);
//            for(UnidadOrganica unidad : unidades) {
//                System.out.println(unidad.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private RPeticionImportarEnlace crearPeticionTest(){
        final RPeticionImportarEnlace peticion = new RPeticionImportarEnlace();
        RLinkData linkData = new RLinkData();
        List<RCategory> categories = new ArrayList<RCategory>();
        RCategory category = new RCategory();
        category.setCategory("J1");
        categories.add(category);
        RCategory category2 = new RCategory();
        category2.setCategory("J2");
        categories.add(category2);
        RCategory category3 = new RCategory();
        category3.setCategory("K1");
        categories.add(category3);
        linkData.setCategories(categories);
        linkData.setCrawlUrl("");
        linkData.setDelete(RTypeDelete.NO);
        linkData.setDescription("Resolver las incidencias posteriores a la concesión de Incentivos Regionales, en especial cuando se refieran a los siguientes aspectos: a. Modificaciones al proyecto inicial que supongan cambio de actividad, variación de los incentivos, del importe de la inversión aprobada o de los puestos de trabajo a crear. b. Cambio de denominación o de las circunstancias societarias con o sin cambio de titularidad que afecten al proyecto. c. Cambio de ubicación del proyecto cuando se produzca dentro de la misma zona de promoción económica. d. Modificaciones de los plazos, y/o calendarios de cumplimiento de condiciones para la ejecución del proyecto y para el cumplimiento de las condiciones particulares de la concesión. e. Modificaciones de los puestos de trabajo a mantener por la titular como consecuencia de operaciones societarias.");
        linkData.setLanguage(RTypeLanguage.ES.toString());
        linkData.setExcludedPaths(new ArrayList<>());
        linkData.setIgnoreParams(new ArrayList<>());
        linkData.setSitemaps(new ArrayList<>());
        linkData.setUrl("https://pre-sede.gva.es/es/inicio/procedimientos?id_proc=20827");
        linkData.setUrlType(RTypeUrl.Webpage);
        linkData.setType(RTypeLink.Procedure.toString());
        linkData.setNationalCode("ES52");
        linkData.setTitle("Incentivos Regionales. Incidencias posteriores a la concesión: modificaciones y prórrogas");
        linkData.setParentUrl("https://pre-sede.gva.es/");
        linkData.setSdgDashboardInfoSearchResults("");
        //linkData.setProcedureName("");
        //linkData.setProcedureType("");
        //linkData.setProcedureAvailability("");
        linkData.setProcedureData(new ArrayList<>());
        List<RLinkData> rLinkDataList =  new ArrayList<RLinkData>();
        rLinkDataList.add(linkData);

        peticion.setLinkData(rLinkDataList);

        linkData.setAssociatedProcedures(new ArrayList<>());

        return peticion;
    }


}
