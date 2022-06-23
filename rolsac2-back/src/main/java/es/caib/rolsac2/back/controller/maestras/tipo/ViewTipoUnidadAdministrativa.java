package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TestServiceFacade;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoUnidadAdministrativaGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoUnidadAdministrativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Controlador per obtenir la vista dels procediments d'una unitat orgànica. El definim a l'scope de view perquè
 * a nivell de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació.
 * Amb view es manté mentre no es canvii de vista.
 *
 * @author Indra
 */
@Named
@ViewScoped
public class ViewTipoUnidadAdministrativa extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoUnidadAdministrativa.class);

    List<String> idiomas;


    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoUnidadAdministrativaGridDTO> lazyModel;

    @EJB
    TipoUnidadAdministrativaServiceFacade tipoNormativaService;

    @EJB
    TestServiceFacade testService;

    /**
     * Dato seleccionado
     */
    private TipoUnidadAdministrativaGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoUnidadAdministrativaFiltro filtro;


    public LazyDataModel<TipoUnidadAdministrativaGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     */
    public void load() {
        this.setearIdioma();
        LOG.debug("load");

    }

}
