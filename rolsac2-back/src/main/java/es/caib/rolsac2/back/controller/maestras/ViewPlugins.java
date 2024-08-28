package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PluginGridDTO;
import es.caib.rolsac2.service.model.filtro.PluginFiltro;
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
import java.util.*;

@Named
@ViewScoped
public class ViewPlugins extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewPlugins.class);

    private LazyDataModel<PluginGridDTO> lazyModel;

    @EJB
    AdministracionEntServiceFacade administracionEntService;

    private PluginGridDTO datoSeleccionado;

    private PluginFiltro filtro;

    public LazyDataModel<PluginGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        LOG.debug("load");
        permisoAccesoVentana(ViewPlugins.class);

        filtro = new PluginFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<PluginGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getRowKey(PluginGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            @Override
            public PluginGridDTO getRowData(String rowKey) {
                for (PluginGridDTO pers : (List<PluginGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }


            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<PluginGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<PluginGridDTO> pagina = administracionEntService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<PluginGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoPlugin() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarPlugin() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarPlugin() {
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
        UtilJSF.openDialog("dialogPlugins", modoAcceso, params, true, 850, 670);
    }

    public void borrarPlugin() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            administracionEntService.deletePlugin(datoSeleccionado.getCodigo());
        }
    }

    public void setDatoSeleccionado(PluginGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public PluginGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public PluginFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(PluginFiltro filtro) {
        this.filtro = filtro;
    }

    public void setFiltroTexto(String texto) {
        if (Objects.nonNull(this.filtro)) {
            this.filtro.setTexto(texto);
        }
    }

    public String getFiltroTexto() {
        if (Objects.nonNull(this.filtro)) {
            return this.filtro.getTexto();
        }
        return "";
    }

}
