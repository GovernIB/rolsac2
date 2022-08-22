package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JPersonal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TestPersonalRepository {

    private PersonalRepository repository;

    @Mock
    private EntityManager entityManager;

    @Before
    public void setup() {
        PersonalRepositoryBean repositoryBean = new PersonalRepositoryBean();
        repositoryBean.entityManager = entityManager;
        repository = repositoryBean;
    }

    @Test
    public void testFindByCodisiaPresent() {

        JPersonal personal = new JPersonal();
        personal.setCodigo(1l);
        personal.setIdentificador("Personal_identif");


        @SuppressWarnings("unchecked")
        TypedQuery<JPersonal> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getResultList()).thenReturn(List.of(personal));
        Mockito.when(entityManager.createNamedQuery(JPersonal.FIND_BY_ID, JPersonal.class))
                .thenReturn(mockedQuery);

        Optional<JPersonal> result = repository.findById("1l");

        Mockito.verify(mockedQuery).setParameter("id", "1l");
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(personal, result.get());
    }
/*
    @Test
    public void testFindByCodisiaNotPresent() {

        @SuppressWarnings("unchecked")
        TypedQuery<Procediment> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());
        Mockito.when(entityManager.createNamedQuery(Procediment.FIND_BY_CODISIA, Procediment.class))
                .thenReturn(mockedQuery);

        Optional<Procediment> result = repository.findByCodiSia("0123456");

        Mockito.verify(mockedQuery).setParameter("codiSia", "0123456");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testCountByUnitat() {

        @SuppressWarnings("unchecked")
        TypedQuery<Long> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getSingleResult()).thenReturn(123L);
        Mockito.when(entityManager.createNamedQuery(Procediment.COUNT_BY_IDUNITAT, Long.class)).thenReturn(mockedQuery);

        long result = repository.countByUnitat(1L);

        Mockito.verify(mockedQuery).setParameter("idUnitat", 1L);
        Assert.assertEquals(123L, result);
    }


    @Test
    public void testFindPagedByUnitatEmpty() {

        @SuppressWarnings("unchecked")
        TypedQuery<ProcedimentDTO> mockedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());
        Mockito.when(entityManager.createNamedQuery(Procediment.FIND_DTO_BY_IDUNITAT, ProcedimentDTO.class))
                .thenReturn(mockedQuery);

        List<ProcedimentDTO> result = repository.findPagedByUnitat(3, 13, 1L);

        Mockito.verify(mockedQuery).setFirstResult(3);
        Mockito.verify(mockedQuery).setMaxResults(13);
        Mockito.verify(mockedQuery).setParameter("idUnitat", 1L);

        Assert.assertTrue(result.isEmpty());
    }*/
}
