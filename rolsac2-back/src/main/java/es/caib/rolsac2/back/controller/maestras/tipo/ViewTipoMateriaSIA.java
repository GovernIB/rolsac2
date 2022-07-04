package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.TipoMateriaSIAServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.Traduccion;
import es.caib.rolsac2.service.model.filtro.TipoMateriaSIAFiltro;
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
    TipoMateriaSIAServiceFacade tipoMateriaSIAService;

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

        this.setearIdioma();

        //No hace falta sessionBean.setOpcion(this.getLiteral("viewTipoBoletin.titulo"));

        // Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoMateriaSIAFiltro();
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
            public TipoMateriaSIAGridDTO getRowData(String rowKey) {
                for (TipoMateriaSIAGridDTO TipoMateriaSIA : getWrappedData()) {
                    if (TipoMateriaSIA.getId().toString().equals(rowKey))
                        return TipoMateriaSIA;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoMateriaSIAGridDTO tipoMateriaSIA) {
                return tipoMateriaSIA.getId().toString();
            }

            @Override
            public List<TipoMateriaSIAGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                    Map<String, FilterMeta> filterBy) {
                try {
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoMateriaSIAGridDTO> pagina = tipoMateriaSIAService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoMateriaSIAGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTipoMateriaSIA() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTipoMateriaSIA() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
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
        UtilJSF.openDialog("dialogTipoMateriaSIA", modoAcceso, params, true, 780, 200);
    }


    public void borrarTipoMateriaSIA() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            tipoMateriaSIAService.delete(datoSeleccionado.getId());
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
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