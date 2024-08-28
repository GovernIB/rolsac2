package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.EdificioGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EdificioFiltro;
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
public class ViewEdificios extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(ViewEdificios.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<EdificioGridDTO> lazyModel;

    @EJB
    private AdministracionEntServiceFacade administracionEntService;

    /**
     * Dato seleccionado
     */
    private EdificioGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private EdificioFiltro filtro;


    public LazyDataModel<EdificioGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewEdificios.class);
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new EdificioFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public EdificioGridDTO getRowData(String rowKey) {
                for (EdificioGridDTO Edificio : getWrappedData()) {
                    if (Edificio.getCodigo().toString().equals(rowKey)) return Edificio;
                }
                return null;
            }

            @Override
            public String getRowKey(EdificioGridDTO procedimiento) {
                return procedimiento.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<EdificioGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<EdificioGridDTO> pagina = administracionEntService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<EdificioGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoEdificio() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarEdificio() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarEdificio() {
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
        UtilJSF.openDialog("dialogEdificio", modoAcceso, params, true, 780, 555);
    }


    public void borrarEdificio() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            administracionEntService.delete(datoSeleccionado.getCodigo());
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public EdificioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(EdificioGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public EdificioFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(EdificioFiltro filtro) {
        this.filtro = filtro;
    }

}
