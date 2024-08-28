package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
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
public class ViewProcesosSolr extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosSolr.class);

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
    private LazyDataModel<IndexacionDTO> lazyModel;

    private LazyDataModel<ProcesoLogGridDTO> lazyModelLogs;


    private IndexacionDTO datoSeleccionado;


    private ProcesoSolrFiltro filtro;
    private ProcesoLogFiltro filtroLog;
    private Integer activeTab = 1;

    public LazyDataModel<IndexacionDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosSolr.class);
        LOG.debug("load");
        filtro = new ProcesoSolrFiltro();
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

    public void verErrores(Long codigo) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), codigo.toString());
        UtilJSF.openDialog("dialogProcesoLog", TypeModoAcceso.CONSULTA, params, true, 1000, 733);
    }

    public void buscar() {
        lazyModel = new LazyDataModel<IndexacionDTO>() {
            @Override
            public IndexacionDTO getRowData(String rowKey) {
                for (IndexacionDTO pers : (List<IndexacionDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public String getRowKey(IndexacionDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<IndexacionDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
                    Pagina<IndexacionDTO> pagina = procesoServiceFacade.findSolrByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<IndexacionDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };

        lazyModelLogs = new LazyDataModel<ProcesoLogGridDTO>() {
            @Override
            public ProcesoLogGridDTO getRowData(String rowKey) {
                for (ProcesoLogGridDTO pers : (List<ProcesoLogGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            /*
            @Override
            public Object getRowKey(ProcesoLogGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<ProcesoLogGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtroLog.setIdioma(sessionBean.getLang());
                    if (sortField != null && !sortField.equals("filtroLog.orderBy") && !sortField.equals("filtro.orderBy")) {
                        filtroLog.setOrderBy(sortField);
                    }
                    filtroLog.setTipo("SOLR_PUNT");
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
            }*/

            public int count(Map<String, FilterMeta> filterBy) {
                return 200;
            }

            @Override
            public List<ProcesoLogGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtroLog.setIdioma(sessionBean.getLang());
                    /*if (sortField != null && !sortField.equals("filtroLog.orderBy") && !sortField.equals("filtro.orderBy")) {
                        filtroLog.setOrderBy(sortField);
                    }*/
                    filtroLog.setTipo("SOLR_PUNT");
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

    public void abrirMensaje() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogMensajeSolr", TypeModoAcceso.CONSULTA, params, true, 645, 520);
    }

    private void generarIndexacion(String accion) {
        ListaPropiedades listaPropiedades = new ListaPropiedades();
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
        listaPropiedades.addPropiedad("accion", accion);
        procesoTimerServiceFacade.procesadoManual("SOLR_PUNT", listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    public void indexarProcedimientos() {
        generarIndexacion(Constantes.INDEXAR_SOLR_PROCEDIMIENTOS);
    }

    public void indexarNormativas() {
        generarIndexacion(Constantes.INDEXAR_SOLR_NORMATIVAS);
    }

    public void indexarServicios() {
        generarIndexacion(Constantes.INDEXAR_SOLR_SERVICIOS);
    }


    public void indexarUAs() {
        generarIndexacion(Constantes.INDEXAR_SOLR_UAS);
    }


    public void desindexarTodo() {
        generarIndexacion(Constantes.INDEXAR_SOLR_BORRAR_TODO);
    }

    public void desindexarCaducadas() {
        generarIndexacion(Constantes.INDEXAR_SOLR_BORRAR_CADUCADAS);
    }

    public void indexarPendientes() {

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
        listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SOLR_PENDIENTES);
        procesoTimerServiceFacade.procesadoManual("SOLR_PUNT", listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));

        //final List<ProcedimientoBaseSolr> idProcedimientos = procedimientoServiceFacade.obtenerPendientesIndexar(true, null);
        //indexarDatos(idProcedimientos);
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

    public IndexacionDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(IndexacionDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoSolrFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoSolrFiltro filtro) {
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

    public void setLazyModel(LazyDataModel<IndexacionDTO> lazyModel) {
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

    public Integer getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(Integer activeTab) {
        this.activeTab = activeTab;
    }
}
