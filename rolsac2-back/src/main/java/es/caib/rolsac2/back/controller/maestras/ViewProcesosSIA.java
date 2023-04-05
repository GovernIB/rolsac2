package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class ViewProcesosSIA extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosSIA.class);

    @EJB
    private ProcesoServiceFacade procesoServiceFacade;

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    @EJB
    private ProcesoLogServiceFacade procesoLogServiceFacade;

    @EJB
    private ProcesoTimerServiceFacade procesoTimerServiceFacade;
    private LazyDataModel<IndexacionSIADTO> lazyModel;

    private LazyDataModel<ProcesoLogGridDTO> lazyModelLogs;


    private IndexacionSIADTO datoSeleccionado;


    private ProcesoSIAFiltro filtro;
    private ProcesoLogFiltro filtroLog;

    public LazyDataModel<IndexacionSIADTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosSIA.class);
        LOG.debug("load");
        filtro = new ProcesoSIAFiltro();
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtroLog = new ProcesoLogFiltro();
        filtroLog.setIdioma(sessionBean.getLang());
        filtroLog.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtroLog.setOrderBy("fechaInicio");
        filtroLog.setAscendente(false);

        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }


    public void buscar() {
        lazyModel = new LazyDataModel<IndexacionSIADTO>() {
            @Override
            public IndexacionSIADTO getRowData(String rowKey) {
                for (IndexacionSIADTO pers : (List<IndexacionSIADTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(IndexacionSIADTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<IndexacionSIADTO> load(int first, int pageSize, String sortField,
                                               SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
                    Pagina<IndexacionSIADTO> pagina = procesoServiceFacade.findSIAByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<IndexacionSIADTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };

        lazyModelLogs = new LazyDataModel<ProcesoLogGridDTO>() {
            @Override
            public ProcesoLogGridDTO getRowData(String rowKey) {
                for (ProcesoLogGridDTO pers : (List<ProcesoLogGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(ProcesoLogGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<ProcesoLogGridDTO> load(int first, int pageSize, String sortField,
                                                SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtroLog.setIdioma(sessionBean.getLang());
                    if (sortField != null && !sortField.equals("filtroLog.orderBy") && !sortField.equals("filtro.orderBy")) {
                        filtroLog.setOrderBy(sortField);
                    }
                    filtroLog.setTipo("SIA_PUNT");
                    filtroLog.setAscendente(false);
                    filtroLog.setIdEntidad(sessionBean.getEntidad().getCodigo());
                    Pagina<ProcesoLogGridDTO> pagina = procesoLogServiceFacade.findByFiltro(filtroLog);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<ProcesoLogGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };
    }

    public void indexarTodo() {

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
        listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SIA_COMPLETO);
        procesoTimerServiceFacade.procesadoManual("SIA_PUNT", listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    public void indexarPendientes() {

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
        listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SIA_PENDIENTES);
        procesoTimerServiceFacade.procesadoManual("SIA_PUNT", listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }


    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    public void borrarProceso() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            procesoServiceFacade.borrar(datoSeleccionado.getCodigo());
        }
    }

    public IndexacionSIADTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(IndexacionSIADTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoSIAFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoSIAFiltro filtro) {
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

    public void setLazyModel(LazyDataModel<IndexacionSIADTO> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public LazyDataModel<ProcesoLogGridDTO> getLazyModelLogs() {
        return lazyModelLogs;
    }

    public void setLazyModelLogs(LazyDataModel<ProcesoLogGridDTO> lazyModelLogs) {
        this.lazyModelLogs = lazyModelLogs;
    }

    public ProcesoLogFiltro getFiltroLog() {
        return filtroLog;
    }

    public void setFiltroLog(ProcesoLogFiltro filtroLog) {
        this.filtroLog = filtroLog;
    }
}
