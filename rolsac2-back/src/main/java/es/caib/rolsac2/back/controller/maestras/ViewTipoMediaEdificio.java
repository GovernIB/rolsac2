package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMediaEdificioGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMediaEdificioFiltro;
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
public class ViewTipoMediaEdificio extends AbstractController implements Serializable {
    private static final long serialVersionUID = 8458061227203462410L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoMediaEdificio.class);
    @EJB
    MaestrasEntServiceFacade tipoMediaEdificioService;
    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoMediaEdificioGridDTO> lazyModel;
    /**
     * Dato seleccionado
     */
    private TipoMediaEdificioGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoMediaEdificioFiltro filtro;

    public LazyDataModel<TipoMediaEdificioGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoMediaEdificioFiltro();
        //filtro.setIdUA(sessionBean.getUnidadActiva().getId());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());

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
            public TipoMediaEdificioGridDTO getRowData(String rowKey) {
                for (TipoMediaEdificioGridDTO pers : (List<TipoMediaEdificioGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoMediaEdificioGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<TipoMediaEdificioGridDTO> load(
                    int first, int pageSize, String sortField, SortOrder sortOrder,
                    Map<String, FilterMeta> filterBy
            ) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoMediaEdificioGridDTO> pagina = pagina = tipoMediaEdificioService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoMediaEdificioGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoMediaEdificio() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoMediaEdificio() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"),
                    getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoMediaEdificio() {
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

        UtilJSF.openDialog("dialogTipoMediaEdificio", modoAcceso, params, true, 800, 240);


    }

    public void borrarTipoMediaEdificio() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            tipoMediaEdificioService.deleteTipoMediaEdificio(datoSeleccionado.getCodigo());
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public TipoMediaEdificioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoMediaEdificioGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoMediaEdificioFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoMediaEdificioFiltro filtro) {
        this.filtro = filtro;
    }

}
