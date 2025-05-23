package es.caib.rolsac2.common.test;

import es.caib.rolsac2.commons.plugins.pdu.api.IPluginPdu;
import es.caib.rolsac2.commons.plugins.pdu.api.model.*;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.junit.Test;

import java.util.*;

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

    private RPeticionImportarEnlace crearPeticionTest() {
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
        linkData.setDescription("El objeto de este bases es regular la constitución y la actualización de la bolsa única del cuerpo facultativo técnico, escala de ingeniería técnica, especialidad ingeniería técnica de obras públicas.");
        linkData.setLanguage(RTypeLanguage.ES.toString());
        linkData.setExcludedPaths(new ArrayList<>());
        linkData.setIgnoreParams(new ArrayList<>());
        linkData.setSitemaps(new ArrayList<>());
        linkData.setUrl("https://www.caib.es/seucaib/es/200/personas/tramites/tramite/6332536");
        linkData.setUrlType(RTypeUrl.Webpage);
        linkData.setType(RTypeLink.Procedure.toString());
        linkData.setNationalCode("ES53");
        linkData.setTitle("Convocatoria para la constitución de la bolsa única de personal funcionario interino del cuerpo facultativo técnico, escala de ingeniería técnica, especialidad ingeniería técnica de obras públicas");
        linkData.setParentUrl("https://pre-sede.gva.es/");
        linkData.setSdgDashboardInfoSearchResults("");
        //linkData.setProcedureName("");
        //linkData.setProcedureType("");
        //linkData.setProcedureAvailability("");
        linkData.setProcedureData(new ArrayList<>());
        List<RLinkData> rLinkDataList = new ArrayList<RLinkData>();
        rLinkDataList.add(linkData);

        peticion.setLinkData(rLinkDataList);

        linkData.setAssociatedProcedures(new ArrayList<>());

        return peticion;
    }


}
