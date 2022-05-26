package es.caib.rolsac2.back.model;

import es.caib.rolsac2.service.facade.UnitatOrganicaServiceFacade;
import es.caib.rolsac2.service.model.UnitatOrganicaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UnitatModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UnitatOrganicaServiceFacade unitatOrganicaService;

    private UnitatOrganicaDTO value = new UnitatOrganicaDTO();

    public UnitatOrganicaDTO getValue() {
        return value;
    }

    public void setValue(UnitatOrganicaDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = unitatOrganicaService.findById(value.getId()).orElseThrow();
    }
}
