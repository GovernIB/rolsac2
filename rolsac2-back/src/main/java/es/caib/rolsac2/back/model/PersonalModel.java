package es.caib.rolsac2.back.model;

import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.PersonalDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PersonalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PersonalServiceFacade personalService;

    private PersonalDTO value = new PersonalDTO();

    public PersonalDTO getValue() {
        return value;
    }

    public void setValue(PersonalDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = personalService.findById(value.getId());
    }
}
