package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.CategoriaPDUGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class DialogSeleccionCategoriaPDU extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionCategoriaPDU.class);

    private LazyDataModel<CategoriaPDUGridDTO> lazyModel;

    @EJB
    private MaestrasEntServiceFacade categoriaPDUServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    private CategoriaPDUGridDTO datoSeleccionado;

    private CategoriaPDUFiltro filtro;

    private List<CategoriaPDUGridDTO> categoriaPDUsSeleccionadas;

    private CategoriaPDUGridDTO categoriaPDUGridSeleccionada;

    public LazyDataModel<CategoriaPDUGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new CategoriaPDUFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());

        // Generamos una búsqueda
        buscar();
        categoriaPDUsSeleccionadas = (List<CategoriaPDUGridDTO>) UtilJSF.getValorMochilaByKey("categoriasPDUSeleccionadas");

    }

    /**
     * Limpia el filtro.
     */
    public void limpiarFiltro() {
        filtro = new CategoriaPDUFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setOrder("DESCENDING");
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public CategoriaPDUGridDTO getRowData(String rowKey) {
                for (CategoriaPDUGridDTO pers : (List<CategoriaPDUGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public String getRowKey(CategoriaPDUGridDTO procedimiento) {
                return procedimiento.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<CategoriaPDUGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<CategoriaPDUGridDTO> pagina = categoriaPDUServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<CategoriaPDUGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void guardar() {
        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(categoriaPDUsSeleccionadas);

        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public CategoriaPDUGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(CategoriaPDUGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public CategoriaPDUFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(CategoriaPDUFiltro filtro) {
        this.filtro = filtro;
    }

    public List<CategoriaPDUGridDTO> getCategoriaPDUsSeleccionadas() {
        return categoriaPDUsSeleccionadas;
    }

    public void setCategoriaPDUsSeleccionadas(List<CategoriaPDUGridDTO> categoriaPDUsSeleccionadas) {
        this.categoriaPDUsSeleccionadas = categoriaPDUsSeleccionadas;
    }

    public CategoriaPDUGridDTO getCategoriaPDUGridSeleccionada() {
        return categoriaPDUGridSeleccionada;
    }

    public void setCategoriaPDUGridSeleccionada(CategoriaPDUGridDTO n) {
        this.categoriaPDUGridSeleccionada = n;
    }


    public void borrarCategoriaPDU() {
        if (categoriaPDUGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            categoriaPDUsSeleccionadas.remove(categoriaPDUGridSeleccionada);
            categoriaPDUGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void anadirCategoriaPDULista() {
        if (this.datoSeleccionado == null) {
            return;
        }
        if (categoriaPDUsSeleccionadas != null && contains(this.datoSeleccionado)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.yaEstaEnLaLista"));
        } else {
            if (categoriaPDUsSeleccionadas == null) {
                categoriaPDUsSeleccionadas = new ArrayList<>();
            }
            categoriaPDUsSeleccionadas.add(datoSeleccionado);
        }
    }

    private boolean contains(CategoriaPDUGridDTO dat) {
        boolean contiene = false;
        if (dat != null) {
            if (categoriaPDUsSeleccionadas != null && !categoriaPDUsSeleccionadas.isEmpty()) {
                for (CategoriaPDUGridDTO tipo : categoriaPDUsSeleccionadas) {
                    if (tipo.getCodigo().compareTo(dat.getCodigo()) == 0) {
                        contiene = true;
                        break;
                    }
                }
            }
        }
        return contiene;
    }

}
