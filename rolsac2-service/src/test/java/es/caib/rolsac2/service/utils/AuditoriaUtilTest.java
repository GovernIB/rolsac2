package es.caib.rolsac2.service.utils;

import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCambio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuditoriaUtilTest {

    @Test
    public void testAuditarCambioCanalPlantilla() {
        // Initialize test data
        TipoTramitacionDTO valorPublicado = new TipoTramitacionDTO();
        valorPublicado.setCodigo(1L);
        Literal descripcionPublicado = new Literal();
        valorPublicado.setDescripcion(descripcionPublicado);

        valorPublicado.setTramiteId("id1");


        TipoTramitacionDTO valorModificado = new TipoTramitacionDTO();

        valorModificado.setCodigo(1L);
        Literal descripcionModificado = new Literal();
        Traduccion descEsp = new Traduccion();
        descEsp.setIdioma("es");
        descEsp.setLiteral("Plantilla A");
        descripcionModificado.add(descEsp);

        Traduccion descCat = new Traduccion();
        descCat.setIdioma("ca");
        descCat.setLiteral("Plantilla A");

        descripcionModificado.add(descCat);

        valorModificado.setDescripcion(descripcionModificado);

        valorModificado.setPlantilla(true);


        List<AuditoriaCambio> cambios = new ArrayList<>();
        String idCampo = "testCampo";

        // Call the method
        AuditoriaUtil.auditar(valorPublicado, valorModificado, cambios, idCampo);

        // Assert the results
        assertFalse(cambios.isEmpty());
        assertEquals(2, cambios.size());
        AuditoriaCambio cambio = cambios.get(0);
        assertEquals(idCampo + ".telematico.canal", cambio.getIdCampo());
        assertNotNull(cambio.getValoresModificados());
        assertEquals(1, cambio.getValoresModificados().size());
        assertEquals("plataforma tramitació", cambio.getValoresModificados().get(0).getValorAnterior());
        assertEquals("plantilla", cambio.getValoresModificados().get(0).getValorNuevo());

        assertEquals(idCampo + ".plantillaSel.add", cambios.get(1).getIdCampo());

    }

    @Test
    public void testAuditarCambioCanalPlataforma() {
        // Initialize test data
        TipoTramitacionDTO valorPublicado = createTipoPlantilla();


        TipoTramitacionDTO valorModificado = createTipoPlataforma();



        List<AuditoriaCambio> cambios = new ArrayList<>();
        String idCampo = "testCampo";

        // Call the method
        AuditoriaUtil.auditar(valorPublicado, valorModificado, cambios, idCampo);

        // Assert the results
        assertFalse(cambios.isEmpty());
        assertEquals(5, cambios.size());
        AuditoriaCambio cambio = cambios.get(0);
        assertEquals(idCampo + ".telematico.canal", cambio.getIdCampo());
        assertNotNull(cambio.getValoresModificados());
        assertEquals(1, cambio.getValoresModificados().size());
        assertEquals("plataforma tramitació", cambio.getValoresModificados().get(0).getValorNuevo());
        assertEquals("plantilla", cambio.getValoresModificados().get(0).getValorAnterior());

        assertEquals(idCampo + ".tramiteId.add", cambios.get(1).getIdCampo());

    }

    @Test
    public void testAuditarCambioPlantilla() {
        // Initialize test data
        TipoTramitacionDTO valorPublicado = createTipoPlantilla();


        TipoTramitacionDTO valorModificado = createTipoPlantilla();
        valorModificado.getDescripcion().getTraducciones().get(1).setLiteral("Plantilla B");


        List<AuditoriaCambio> cambios = new ArrayList<>();
        String idCampo = "testCampo";

        // Call the method
        AuditoriaUtil.auditar(valorPublicado, valorModificado, cambios, idCampo);

        // Assert the results
        assertFalse(cambios.isEmpty());
        assertEquals(1, cambios.size());
        AuditoriaCambio cambio = cambios.get(0);
        assertEquals(idCampo + ".plantillaSel", cambio.getIdCampo());
        assertNotNull(cambio.getValoresModificados());
        assertEquals(1, cambio.getValoresModificados().size());
        assertEquals("Plantilla B", cambio.getValoresModificados().get(0).getValorNuevo());
        assertEquals("Plantilla A", cambio.getValoresModificados().get(0).getValorAnterior());


    }

    private TipoTramitacionDTO createTipoPlantilla(){
        TipoTramitacionDTO tipoPlantilla = new TipoTramitacionDTO();
        tipoPlantilla.setCodigo(1L);

        tipoPlantilla.setPlantilla(true);

        Literal descripcion = new Literal();
        Traduccion descEsp = new Traduccion();
        descEsp.setIdioma("es");
        descEsp.setLiteral("Plantilla A");
        descripcion.add(descEsp);

        Traduccion descCat = new Traduccion();
        descCat.setIdioma("ca");
        descCat.setLiteral("Plantilla A");
        descripcion.add(descCat);

        tipoPlantilla.setDescripcion(descripcion);

        return tipoPlantilla;
    }

    private TipoTramitacionDTO createTipoPlataforma(){
        TipoTramitacionDTO tipoPlataforma = new TipoTramitacionDTO();
        tipoPlataforma.setCodigo(1L);

        tipoPlataforma.setPlantilla(false);

        Literal descripcion = new Literal();
        Traduccion descEsp = new Traduccion();
        descEsp.setIdioma("es");
        descEsp.setLiteral("Plataforma A");
        descripcion.add(descEsp);

        Traduccion descCat = new Traduccion();
        descCat.setIdioma("ca");
        descCat.setLiteral("Plataforma A");
        descripcion.add(descCat);

        tipoPlataforma.setDescripcion(descripcion);

        tipoPlataforma.setTramiteId("AB2");
        PlatTramitElectronicaDTO platTramit = new PlatTramitElectronicaDTO();
        platTramit.setIdentificador("idplat");
        tipoPlataforma.setCodPlatTramitacion(platTramit);
        tipoPlataforma.setTramiteParametros("params test");
        tipoPlataforma.setTramiteVersion(1);

        return tipoPlataforma;
    }
}