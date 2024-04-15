package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.EntidadRaizGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.EntidadRaizFiltro;
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
public class ViewEntidadRaiz extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewEntidadRaiz.class);

    @EJB
    private AdministracionEntServiceFacade administracionEntServiceFacade;

    /**
     * Model de dades emprat pel compoment dataTable de primefaces.
     */
    private LazyDataModel<EntidadRaizGridDTO> lazyModel;

    /**
     * Dato seleccionado
     */
    private EntidadRaizGridDTO datoSeleccionado;

    /**
     * Filtro
     **/
    private EntidadRaizFiltro filtro;

    public LazyDataModel<EntidadRaizGridDTO> getLazyModel() {
        return lazyModel;
    }

    // ACCIONS

    /**
     * Carrega la unitat orgànica i els procediments.
     */
    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewEntidadRaiz.class);
        this.setearIdioma();

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new EntidadRaizFiltro();
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<EntidadRaizGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public EntidadRaizGridDTO getRowData(String rowKey) {
                for (EntidadRaizGridDTO pers : (List<EntidadRaizGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(EntidadRaizGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<EntidadRaizGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<EntidadRaizGridDTO> pagina = pagina = administracionEntServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<EntidadRaizGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }

            }
        };
    }

    public void nuevaEntidadRaiz() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarEntidadRaiz() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarEntidadRaiz() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarEntidadRaiz() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            administracionEntServiceFacade.deleteEntidadRaiz(datoSeleccionado.getCodigo());
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
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

        UtilJSF.openDialog("dialogEntidadRaiz", modoAcceso, params, true, 800, 340);
    }

    public EntidadRaizGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(EntidadRaizGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public EntidadRaizFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(EntidadRaizFiltro filtro) {
        this.filtro = filtro;
    }
}
