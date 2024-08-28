package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoBoletinFiltro;
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
public class ViewTipoBoletin extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoBoletin.class);
    @EJB
    MaestrasSupServiceFacade TipoBoletinService;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;
    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoBoletinGridDTO> lazyModel;

    /**
     * Dato seleccionado
     */
    private TipoBoletinGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoBoletinFiltro filtro;

    public LazyDataModel<TipoBoletinGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewTipoBoletin.class);
        this.setearIdioma();

        //this.tienePermisos(this.getClass().getName());

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoBoletinFiltro();
        //filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());//UtilJSF.getSessionUnidadActiva());
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
            public TipoBoletinGridDTO getRowData(String rowKey) {
                for (TipoBoletinGridDTO pers : (List<TipoBoletinGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public String getRowKey(TipoBoletinGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<TipoBoletinGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<TipoBoletinGridDTO> pagina = pagina = TipoBoletinService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoBoletinGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoBoletin() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoBoletin() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoBoletin() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
            if (respuesta.isAlta()) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.creaciocorrecta"));
            } else if (respuesta.isEdicion()) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.actualitzaciocorrecta"));
            }
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogTipoBoletin", modoAcceso, params, true, 800, 340);
    }

    public void borrarTipoBoletin() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.noBorrado.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            if (normativaServiceFacade.existeBoletin(datoSeleccionado.getCodigo())) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewTipoBoletin.existeRelacion"));
            } else {
                TipoBoletinService.deleteTipoBoletin(datoSeleccionado.getCodigo());
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
            }
        }
    }

    public TipoBoletinGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoBoletinGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoBoletinFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoBoletinFiltro filtro) {
        this.filtro = filtro;
    }

}
