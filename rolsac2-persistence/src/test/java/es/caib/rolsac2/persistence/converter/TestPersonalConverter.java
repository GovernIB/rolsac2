package es.caib.rolsac2.persistence.converter;

import es.caib.rolsac2.persistence.model.JPersonal;
import es.caib.rolsac2.service.model.PersonalDTO;
import org.junit.Assert;

public class TestPersonalConverter {

    private final PersonalConverter converter = null;//= new PersonalConverterImpl();

    //@Test
    public void testToDTO() {
        JPersonal entity = new JPersonal();
        entity.setId(1L);
        entity.setCargo("cargo");
        entity.setIdentificador("Identi");

        PersonalDTO dto = converter.createDTO(entity);

        Assert.assertEquals(1L, (long) dto.getId());
        Assert.assertEquals("cargo", dto.getCargo());
        Assert.assertEquals("Identi", dto.getIdentificador());
        //Assert.assertEquals(2L, (long) dto.getIdUnitat());
    }

    //@Test
    public void testToEntity() {
        PersonalDTO dto = new PersonalDTO();
        dto.setId(1L);
        dto.setIdentificador("identif");
        dto.setCargo("cargo");
        dto.setIdUnidadAdministrativa(2L);

        JPersonal entity = converter.createEntity(dto);

        Assert.assertEquals(1L, (long) entity.getId());
        Assert.assertEquals("cargo", entity.getCargo());
        Assert.assertEquals("identif", entity.getIdentificador());
        //Assert.assertNull(entity.getUnitatOrganica()); // no s'actualitza
    }

/*
    @Test
    public void testUpdateFromDTO() {
        Procediment entity = new Procediment();
        entity.setId(1L);
        entity.setCodiSia("000001");
        entity.setNom("Nom Test");

        UnitatOrganica unitat = new UnitatOrganica();
        unitat.setId(2L);
        entity.setUnitatOrganica(unitat);

        ProcedimentDTO dto = new ProcedimentDTO();
        dto.setId(3L);
        dto.setCodiSia("100001");
        dto.setNom("Nom Test bis");
        dto.setIdUnitat(4L);

        converter.updateFromDTO(entity, dto);

        Assert.assertEquals(3L, (long) entity.getId());
        Assert.assertEquals("000001", entity.getCodiSia()); // no s'actualitza
        Assert.assertEquals("Nom Test bis", entity.getNom());
        Assert.assertEquals(2L, (long)entity.getUnitatOrganica().getId()); // no s'actualitza
    }*/
}
