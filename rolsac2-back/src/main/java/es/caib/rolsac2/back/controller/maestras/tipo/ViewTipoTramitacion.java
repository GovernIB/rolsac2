package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;
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
public class ViewTipoTramitacion extends AbstractController implements Serializable {

    private static final long serialVersionUID = 8175276208449464612L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoTramitacion.class);


    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoTramitacionGridDTO> lazyModel;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    /**
     * Dato seleccionado
     */
    private TipoTramitacionGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoTramitacionFiltro filtro;

    public LazyDataModel<TipoTramitacionGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewTipoTramitacion.class);
        this.setearIdioma();
        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoTramitacionFiltro();
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setTipoPlantilla(true);
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
            public TipoTramitacionGridDTO getRowData(String rowKey) {
                for (TipoTramitacionGridDTO tipoTramitacion : getWrappedData()) {
                    if (tipoTramitacion.getCodigo().toString().equals(rowKey)) return tipoTramitacion;
                }
                return null;
            }

            @Override
            public String getRowKey(TipoTramitacionGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<TipoTramitacionGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<TipoTramitacionGridDTO> pagina = maestrasSupService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoTramitacionGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoTramitacion() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoTramitacion() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoTramitacion() {
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
        params.put(TypeParametroVentana.PLANTILLA.toString(), "S");
        UtilJSF.openDialog("dialogTipoTramitacion", modoAcceso, params, true, 780, 550);
    }

    public void borrarTipoTramitacion() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            maestrasSupService.deleteTipoTramitacion(datoSeleccionado.getCodigo());
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public TipoTramitacionGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoTramitacionGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoTramitacionFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoTramitacionFiltro filtro) {
        this.filtro = filtro;
    }

}
