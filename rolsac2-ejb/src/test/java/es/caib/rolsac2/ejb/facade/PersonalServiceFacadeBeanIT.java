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

}
