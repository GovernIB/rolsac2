package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AlertaServiceFacade;
import es.caib.rolsac2.service.model.AlertaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.filtro.AlertaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePerfiles;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ViewMisAlertas extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewMisAlertas.class);

    private LazyDataModel<AlertaGridDTO> lazyModel;


    @EJB
    AlertaServiceFacade alertaService;

    private AlertaGridDTO datoSeleccionado;

    private AlertaFiltro filtro;

    private boolean renderMarcarLeido;

    private boolean renderBotones;

    public LazyDataModel<AlertaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<AlertaGridDTO> lazyModel) {
        this.lazyModel = lazyModel;
    }


    public void load() {
        this.setearIdioma();
        sessionBean.cargarAlertas();
        LOG.debug("load");
        permisoAccesoVentana(ViewMisAlertas.class);

        filtro = new AlertaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setAscendente(false);
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        filtro.setUsuario(usuario);

        if (sessionBean.isPerfil(TypePerfiles.SUPER_ADMINISTRADOR) || sessionBean.isPerfil(TypePerfiles.ADMINISTRADOR_ENTIDAD)) {
            renderMarcarLeido = false;
            renderBotones = true;
        } else {
            renderMarcarLeido = true;
            renderBotones = false;
        }

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<AlertaGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public AlertaGridDTO getRowData(String rowKey) {
                for (AlertaGridDTO pers : (List<AlertaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public String getRowKey(AlertaGridDTO pers) {
                return pers.getCodigo().toString();
            }
             
            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<AlertaGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    Pagina<AlertaGridDTO> pagina = alertaService.findAlertaUsuarioDTOByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<AlertaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevaAlerta() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarAlerta() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarAlerta() {
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
        UtilJSF.openDialog("dialogConfiguracionAlerta", modoAcceso, params, true, 750, 520);
    }

    public void borrarAlerta() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            alertaService.delete(datoSeleccionado.getCodigo());
        }
    }

    public void setDatoSeleccionado(AlertaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public AlertaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public AlertaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(AlertaFiltro filtro) {
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

    public boolean isRenderMarcarLeido() {
        return renderMarcarLeido;
    }

    public void setRenderMarcarLeido(boolean renderMarcarLeido) {
        this.renderMarcarLeido = renderMarcarLeido;
    }

    public boolean isRenderBotones() {
        return renderBotones;
    }

    public void setRenderBotones(boolean renderBotones) {
        this.renderBotones = renderBotones;
    }
}
