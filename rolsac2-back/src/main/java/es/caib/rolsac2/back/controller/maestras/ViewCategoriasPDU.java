package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.model.CategoriaPDUGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.CategoriaPDUFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ViewCategoriasPDU extends AbstractController implements Serializable {
    private static final long serialVersionUID = 8458061227203462410L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewCategoriasPDU.class);
    @EJB
    MaestrasEntServiceFacade categoriasPDUService;
    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<CategoriaPDUGridDTO> lazyModel;
    /**
     * Dato seleccionado
     */
    private CategoriaPDUGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private CategoriaPDUFiltro filtro;

    public LazyDataModel<CategoriaPDUGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewCategoriasPDU.class);
        this.setearIdioma();

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new CategoriaPDUFiltro();
        //filtro.setIdUA(sessionBean.getUnidadActiva().getId());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());
        filtro.setAscendente(true);
        filtro.setOrderBy("identificador");
        //Generamos una búsqueda
        buscar();
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
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public String getRowKey(CategoriaPDUGridDTO objeto) {
                return objeto.getCodigo().toString();
            }


            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<CategoriaPDUGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<CategoriaPDUGridDTO> pagina = categoriasPDUService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<CategoriaPDUGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoCategoriaPDU() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarCategoriaPDU() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarCategoriaPDU() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {

        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }

        UtilJSF.openDialog("dialogCategoriaPDU", modoAcceso, params, true, 800, 330);


    }

    public void borrarCategoriaPDU() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            categoriasPDUService.deleteCategoriaPduDTO(datoSeleccionado.getCodigo());
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
        }
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
}
