package es.caib.rolsac2.ejb.facade;

import es.caib.rolsac2.service.exception.UnitatDuplicadaException;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.PersonalDTO;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Realitza tests de persistència i validació damunt Unitats Orgàniques.
 * <p>
 * Els tests s'executen sobre una instància de JBoss que o bé s'arranca automàticament (-Parq-jboss-managed), o bé
 * ja està en marxa (-Parq-jboss-remote).
 */
@RunWith(Arquillian.class)
public class PersonalServiceFacadeBeanIT extends AbstractFacadeIT {
    ;

    @EJB
    private PersonalServiceFacade personalServiceFacade;

    /**
     * Crea una unitat orgància amb codiDir3 "U87654321".
     */
    @Test
    @InSequence(1)
    public void testCreateUnitat() {
        PersonalDTO dto = new PersonalDTO();
        dto.setCodigo(1l);
        dto.setIdentificador("Identif");
        dto.setEmail("fewfji2@iof.es");

        adminManager.exec(() -> {
            personalServiceFacade.update(dto);
        });
    }

    /**
     * Crea una unitat orgància amb codiDir3 "U87654321".
     */
    @Test(expected = UnitatDuplicadaException.class)
    @InSequence(2)
    public void testCreateUnitatDuplicat() {
        PersonalDTO dto = new PersonalDTO();
        dto.setCodigo(1l);
        dto.setIdentificador("U87654321");
        dto.setEmail("fewfji2@iof.es");

        adminManager.exec(() -> {
            personalServiceFacade.create(dto);
            Assert.fail("No s'hauria d'haver pogut crear");
        });
    }

    /**
     * Selecciona la unitat orgànica
     */
    @Test
    @InSequence(3)
    public void testFindById() {

        userManager.exec(() -> {
            PersonalDTO dto = personalServiceFacade.findById(1l);

            Assert.assertTrue(dto != null);
            Assert.assertEquals("Identif", dto.getIdentificador());
            Assert.assertEquals("fewfji2@iof.es", dto.getEmail());
        });
    }

    /**
     * Selecciona la unitat orgànica inexistent
     */
    @Test
    @InSequence(4)
    public void testFindByIdError() {

        userManager.exec(() -> {
            PersonalDTO dto = personalServiceFacade.findById(999L);
            Assert.assertTrue(dto == null);
        });
    }

    /**
     * Actualitza la unitat orgànica
     *
     @Test
     @InSequence(5) public void testUpdateUnitat() {
     var dto = new UnitatOrganicaDTO();
     dto.setId(101L);
     dto.setCodiDir3("A87654321");
     dto.setNom("Unitat test arquillian 2");
     dto.setDataCreacio(LocalDate.of(2020, 10, 20));
     dto.setEstat(EstatPublicacio.INACTIU);

     adminManager.exec(() -> {
     unitatOrganicaServiceFacade.update(dto);

     var updated = unitatOrganicaServiceFacade.findById(101L).orElseThrow();
     Assert.assertEquals(dto.getId(), updated.getId());
     Assert.assertNotEquals(dto.getCodiDir3(), updated.getCodiDir3()); // el codiDir3 no s'actualitza
     Assert.assertEquals(dto.getNom(), updated.getNom());
     Assert.assertEquals(dto.getDataCreacio(), updated.getDataCreacio());
     Assert.assertEquals(dto.getEstat(), updated.getEstat());
     });
     }*/

    /**
     * Actualitza la unitat orgànica inexistent
     *
     @Test(expected = RecursoNoEncontradoException.class)
     @InSequence(6) public void testUpdateUnitatError() {
     UnitatOrganicaDTO dto = new UnitatOrganicaDTO();
     dto.setId(999L);
     dto.setCodiDir3("J87654321");
     dto.setNom("Unitat test arquillian 2 error");
     dto.setDataCreacio(LocalDate.of(2020, 10, 20));
     dto.setEstat(EstatPublicacio.INACTIU);

     adminManager.exec(() -> {
     unitatOrganicaServiceFacade.update(dto);
     Assert.fail("No s'hauria de poder haver actualitzat sense error");
     });
     }*/

    /**
     * Esborra la unitat
     *
     @Test
     @InSequence(7) public void testDelete() {
     adminManager.exec(() -> unitatOrganicaServiceFacade.delete(101L));
     }*/

    /**
     * Esborra una unitat que no existeix
     *
     @Test(expected = RecursoNoEncontradoException.class)
     @InSequence(8) public void testDeleteError() {
     adminManager.exec(() -> {
     unitatOrganicaServiceFacade.delete(999L);
     Assert.fail("No s'hauria de poder haver borrat sense error");
     });
     }*/

    /**
     * Esborra una unitat amb dependències
     *
     @Test(expected = UnitatTeProcedimentsException.class)
     @InSequence(9) public void testDeleteErrorDepencencies() {
     adminManager.exec(() -> {
     unitatOrganicaServiceFacade.delete(1L);
     Assert.fail("No s'hauria de poder haver borrat sense error");
     });
     }*/

    /**
     * Llistat
     *
     @Test
     @InSequence(10) public void testLlistat() {
     // No calen permisos
     //userManager.exec(() -> {
     Pagina<UnitatOrganicaDTO> llistat = unitatOrganicaServiceFacade.findFiltered(1, 10,
     Collections.singletonMap(AtributUnitat.codiDir3, "A"),
     List.of(Ordre.descendent(AtributUnitat.codiDir3)));
     Assert.assertEquals(12L, llistat.getTotal());
     Assert.assertEquals(10, llistat.getItems().size());
     Assert.assertEquals("A00000011", llistat.getItems().get(0).getCodiDir3());
     //});
     }

     @Test(expected = EJBAccessException.class)
     @InSequence(11) public void testCreateSensePermisos() {
     UnitatOrganicaDTO dto = new UnitatOrganicaDTO();
     userManager.exec(() -> {
     unitatOrganicaServiceFacade.create(dto);
     Assert.fail("No hauria de poder crear");
     });
     }

     @Test(expected = EJBAccessException.class)
     @InSequence(12) public void testUpdateSensePermisos() {
     UnitatOrganicaDTO dto = new UnitatOrganicaDTO();
     userManager.exec(() -> {
     unitatOrganicaServiceFacade.update(dto);
     Assert.fail("No hauria de poder actualitzar");
     });
     }

     @Test(expected = EJBAccessException.class)
     @InSequence(13) public void testDeleteSensePermisos() {
     userManager.exec(() -> {
     unitatOrganicaServiceFacade.delete(1L);
     Assert.fail("No hauria de poder esborrar");
     });
     } */
}
