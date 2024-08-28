package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
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
public class ViewTipoMateriaSIA extends AbstractController implements Serializable {

    private static final long serialVersionUID = 8458061227203462410L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewTipoMateriaSIA.class);

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<TipoMateriaSIAGridDTO> lazyModel;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    /**
     * Dato seleccionado
     */
    private TipoMateriaSIAGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private TipoMateriaSIAFiltro filtro;


    public LazyDataModel<TipoMateriaSIAGridDTO> getLazyModel() {
        return lazyModel;
    }


    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewTipoMateriaSIA.class);
        this.setearIdioma();

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoMateriaSIAFiltro();
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
            public String getRowKey(TipoMateriaSIAGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            @Override
            public TipoMateriaSIAGridDTO getRowData(String rowKey) {
                for (TipoMateriaSIAGridDTO TipoMateriaSIA : getWrappedData()) {
                    if (TipoMateriaSIA.getCodigo().toString().equals(rowKey)) return TipoMateriaSIA;
                }
                return null;
            }

            public int count(Map<String, FilterMeta> filterBy) {
                try {
                    return maestrasSupService.countTipoMateriaSIAByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return 0;
                }
            }

            @Override
            public List<TipoMateriaSIAGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    return maestrasSupService.listTipoMateriaSIAByFiltro(filtro);
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    return new ArrayList<>();
                }
            }
        };
    }

    public void nuevoTipoMateriaSIA() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoMateriaSIA() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTipoMateriaSIA() {
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
        UtilJSF.openDialog("dialogTipoMateriaSIA", modoAcceso, params, true, 780, 360);
    }


    public void borrarTipoMateriaSIA() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.noBorrado.seleccioneElemento"));
        } else {
            boolean existen = maestrasSupService.existeProcedimientoConTipoMateriaSIA(datoSeleccionado.getCodigo());
            if (existen) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.error.relacionProcedimientos"));
            } else {
                maestrasSupService.deleteTipoMateriaSIA(datoSeleccionado.getCodigo());
                addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
            }
        }
    }

    public TipoMateriaSIAGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TipoMateriaSIAGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public TipoMateriaSIAFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(TipoMateriaSIAFiltro filtro) {
        this.filtro = filtro;
    }

}
