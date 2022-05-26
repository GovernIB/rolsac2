package es.caib.rolsac2.back.controller;

import es.caib.rolsac2.back.model.UnitatModel;
import es.caib.rolsac2.service.facade.ProcedimentServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimentDTO;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè
 * a nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació.
 * Amb view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class Test extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ListProcediment.class);

    @EJB
    ProcedimentServiceFacade procedimentService;

    @Inject
    private UnitatModel unitat;

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<ProcedimentDTO> lazyModel;

    public LazyDataModel<ProcedimentDTO> getLazyModel() {
        return lazyModel;
    }

    public List<ProcedimentDTO> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(List<ProcedimentDTO> procedimientos) {
        this.procedimientos = procedimientos;
    }

    private  List<ProcedimentDTO> procedimientos;

    /**
     * Inicializacion.
     */
    @PostConstruct
    public void init() {
        procedimientos = new ArrayList<>();
        ProcedimentDTO p1 = new ProcedimentDTO();
        p1.setNom("Nombre p1");
        p1.setId(1l);
        p1.setCodiSia("Codsia p1");
        procedimientos.add(p1);
        ProcedimentDTO p2 = new ProcedimentDTO();
        p2.setNom("Nombre p2");
        p2.setId(2l);
        p2.setCodiSia("Codsia p2");
        procedimientos.add(p2);
        ProcedimentDTO p3 = new ProcedimentDTO();
        p3.setNom("Nombre p3");
        p3.setId(3l);
        p3.setCodiSia("Codsia p3");
        procedimientos.add(p3);
    }


    public List<ProcedimentDTO> getDatos() {
        List<ProcedimentDTO> datos = new ArrayList<>();
        ProcedimentDTO p1 = new ProcedimentDTO();
        p1.setNom("Nombre p1");
        p1.setId(1l);
        p1.setCodiSia("Codsia p1");
        datos.add(p1);
        ProcedimentDTO p2 = new ProcedimentDTO();
        p2.setNom("Nombre p2");
        p2.setId(2l);
        p2.setCodiSia("Codsia p2");
        datos.add(p2);
        return datos;
    }

    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load test.xhtml");
        lazyModel = new LazyDataModel<>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ProcedimentDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                             Map<String, FilterMeta> filterBy) {
                Pagina<ProcedimentDTO> pagina =
                        procedimentService.findByUnitat(first, pageSize, unitat.getValue().getId());
                setRowCount((int) pagina.getTotal());
                return pagina.getItems();
            }
        };
    }

}
