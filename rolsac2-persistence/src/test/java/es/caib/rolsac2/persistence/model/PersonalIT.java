package es.caib.rolsac2.persistence.model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * Realitza tests de persistència i validació de personal.
 * <p>
 * Els tests s'executen sobre una instància de JBoss que o bé s'arranca automàticament (-Parq-jboss-managed), o bé
 * ja està en marxa (-Parq-jboss-remote).
 */
@RunWith(Arquillian.class)
public class PersonalIT {
    ;

    /**
     * Crea l'arxiu de deploy que es desplegarà sobre JBoss per fer els tests.
     *
     * @return arxiu desplegable.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        try {
            JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar")
                    .addPackages(true, "es.caib.rolsac2.persistence.model")
                    .addPackages(true, "es.caib.rolsac2.service.model")
                    .addAsResource("META-INF/beans.xml")
                    .addAsResource("META-INF/arquillian-persistence.xml", "META-INF/persistence.xml")
                    .addAsResource("META-INF/arquillian-ds.xml");
            System.out.println(jar.toString(true));
            return jar;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    /**
     * Abans de cada test s'inciarà una transacció.
     *
     * @throws Exception Error durant l'inici de la transacció
     */
    @Before
    public void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    /**
     * Finalitzsació d'una transacció. Es farà un commit, o un rollback si la transacció s'ha marcat com a rollbackonly
     *
     * @throws Exception Error durant el final de la transacció.
     */
    @After
    public void endTransaction() throws Exception {
        if (utx.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
            utx.rollback();
        } else {
            utx.commit();
        }
        em.clear();
    }

    /**
     * Crea un personal.
     */
    @Test
    @InSequence(1)
    public void testCreateJPersonal() {
        JPersonal jpersonal = new JPersonal();
        //jpersonal.setId(1l);
        jpersonal.setIdentificador("IDENTI");
        jpersonal.setEmail("email@jife.es");
        em.persist(jpersonal);
        em.flush();

        Assert.assertNotNull(jpersonal.getCodigo());
    }

    /**
     * Selecciona el personal con id 1l.
     */
    @Test
    @InSequence(2)
    public void testQueryUnitat() {
        TypedQuery<JPersonal> query = em.createNamedQuery(JPersonal.FIND_BY_ID, JPersonal.class);
        query.setParameter("id", 1l);
        JPersonal jpersonal = query.getSingleResult();

        // Comprovam el nom de la unitat seleccionada
        Assert.assertEquals("IDENTI", jpersonal.getIdentificador());
    }

    /**
     * Selecciona i esborra el personal.
     */
    @Test
    @InSequence(3)
    public void testRemoveUnitat() {
        TypedQuery<JPersonal> query = em.createNamedQuery(JPersonal.FIND_BY_ID, JPersonal.class);
        query.setParameter("id", 1l);
        JPersonal jpersonal = query.getSingleResult();
        em.remove(jpersonal);
        em.flush();

        // La unitat no hauria d'estar ja dins
        Assert.assertFalse(em.contains(jpersonal));
    }

    /**
     * Crea una unitat orgànica incomplint les validacons i intenta persistir-la per fer botar els errors de validació.
     *
     @Test
     @InSequence(4) public void testConstraintsUnitat() {

     JPersonal jpersonal = new JPersonal();
     jpersonal.setCodiDir3("Z87654321"); // inclumpleix el pattern
     jpersonal.setNom(""); // incumpleix que no potser buid
     jpersonal.setDataCreacio(LocalDate.now().plusDays(1)); // inclumpleix que no pot se futur
     jpersonal.setEstat(null); // inclumpleix que no pot ser null

     try {
     em.persist(jpersonal);
     em.flush();
     Assert.fail("Hauria d'haver donat un error de validació");
     } catch (ConstraintViolationException cve) {
     // Hi hauria d'haver 4 errors de validació.
     Assert.assertEquals(4, cve.getConstraintViolations().size());
     }
     } */
}
