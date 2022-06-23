package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoTramitacionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoTramitacionGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoTramitacionFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
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
    TipoTramitacionServiceFacade tipoTramitacionService;

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
        this.setearIdioma();
        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoTramitacionFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getId());// UtilJSF.getSessionUnidadActiva());
        filtro.setIdioma(sessionBean.getLang());// UtilJSF.getSessionLang());

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
                    if (tipoTramitacion.getId().toString().equals(rowKey))
                        return tipoTramitacion;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoTramitacionGridDTO tipoTramitacion) {
                return tipoTramitacion.getId().toString();
            }

            @Override
            public List<TipoTramitacionGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                     Map<String, FilterMeta> filterBy) {
                try {
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoTramitacionGridDTO> pagina = tipoTramitacionService.findByFiltro(filtro);
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
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
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
        if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
            this.buscar();
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
        }
        UtilJSF.openDialog("dialogTipoTramitacion", modoAcceso, params, true, 780, 360);
    }

    public void borrarTipoTramitacion() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            tipoTramitacionService.delete(datoSeleccionado.getId());
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
