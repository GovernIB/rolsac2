package es.caib.rolsac2.back.controller.maestras.tipo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewTipoProcedimiento extends AbstractController implements Serializable {

    private static final long serialVersionUID = 8458061227203462410L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoProcedimiento.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoProcedimientoGridDTO> lazyModel;

    @EJB
    MaestrasSupServiceFacade tipoProcedimientoService;

    /**
     * Dato seleccionado
     */
    private TipoProcedimientoGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoProcedimientoFiltro filtro;


    public LazyDataModel<TipoProcedimientoGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
    	permisoAccesoVentana(ViewTipoProcedimiento.class);
        this.setearIdioma();

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoProcedimientoFiltro();
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());//UtilJSF.getSessionUnidadActiva());
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
            public TipoProcedimientoGridDTO getRowData(String rowKey) {
                for (TipoProcedimientoGridDTO pers : (List<TipoProcedimientoGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoProcedimientoGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<TipoProcedimientoGridDTO> load(
                    int first, int pageSize, String sortField, SortOrder sortOrder,
                    Map<String, FilterMeta> filterBy
            ) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoProcedimientoGridDTO> pagina = pagina = tipoProcedimientoService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoProcedimientoGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoProcedimiento() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"),
                    getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoProcedimiento() {
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

        UtilJSF.openDialog("dialogTipoProcedimiento", modoAcceso, params, true, 800, 285);
    }

    public void borrarTipoProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            tipoProcedimientoService.deleteTipoProcedimiento(datoSeleccionado.getCodigo());
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
        }
    }


    public TipoProcedimientoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoProcedimientoGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoProcedimientoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoProcedimientoFiltro filtro) {
        this.filtro = filtro;
    }

}
