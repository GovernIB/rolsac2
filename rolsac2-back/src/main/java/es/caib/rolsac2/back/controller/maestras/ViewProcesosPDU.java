package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoPduFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypePluginEntidad;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypeTipoProceso;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ViewProcesosPDU extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosPDU.class);

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

    private LazyDataModel<IndexacionPDUDto> lazyModel;

    private LazyDataModel<ProcesoLogGridDTO> lazyModelLogs;


    private IndexacionPDUDto datoSeleccionado;


    private ProcesoPduFiltro filtro;
    private ProcesoLogFiltro filtroLog;

    public LazyDataModel<IndexacionPDUDto> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosPDU.class);
        LOG.debug("load");

        filtro = new ProcesoPduFiltro();
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

    public void verErrores(Long codigo) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), codigo.toString());
        UtilJSF.openDialog("dialogProcesoLog", TypeModoAcceso.CONSULTA, params, true, 1000, 733);
    }

    /**
     * Update
     */
    public void update() {
        buscar();
    }

    /**
     * Buscar
     */
    public void buscar() {
        lazyModel = new LazyDataModel<IndexacionPDUDto>() {

            @Override
            public IndexacionPDUDto getRowData(String rowKey) {
                for (IndexacionPDUDto peticion : (List<IndexacionPDUDto>) getWrappedData()) {
                    if (peticion.getCodigo().toString().equals(rowKey)) return peticion;
                }
                return null;
            }

            @Override
            public String getRowKey(IndexacionPDUDto objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<IndexacionPDUDto> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<IndexacionPDUDto> pagina = procesoServiceFacade.findPDUByFiltro(filtro);

                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<IndexacionPDUDto> pagina = new Pagina(new ArrayList(), 0);
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

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<ProcesoLogGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    filtroLog.setIdioma(sessionBean.getLang());
                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    filtroLog.setTipos(Arrays.asList("PDU_PUNT", "PDU"));
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

        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();

        // Comprobamos que el plugin PDU esté activo antes de lanzar el proceso
        if (!isPluginActivo(TypePluginEntidad.PDU, idEntidad, "activopdu")) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.procesoPDUNoActivo"));
            return;
        }

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SIA_COMPLETO);
        procesoTimerServiceFacade.procesadoManual(TypeTipoProceso.PDU_PUNT.valor, listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    public void indexarPendientes() {
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();

        // Comprobamos que el plugin PDU esté activo antes de lanzar el proceso
        if (!isPluginActivo(TypePluginEntidad.PDU, idEntidad, "activopdu")) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dict.procesoPDUNoActivo"));
            return;
        }

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SIA_PENDIENTES);
        procesoTimerServiceFacade.procesadoManual(TypeTipoProceso.PDU_PUNT.valor, listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    private boolean isPluginActivo(TypePluginEntidad tipo, Long idEntidad, String propName) {
        try {
            Object plg = systemServiceFacade.obtenerPluginEntidad(tipo, idEntidad);
            if (!(plg instanceof AbstractPluginProperties)) {
                return false;
            }
            String valor = ((AbstractPluginProperties) plg).getProperty(propName);
            return "true".equalsIgnoreCase(valor);
        } catch (Exception e) {
            LOG.debug("No se pudo leer la propiedad del plugin o no existe: {}", e.getMessage());
            return false;
        }
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

    public IndexacionPDUDto getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(IndexacionPDUDto datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoPduFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoPduFiltro filtro) {
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

    public void setLazyModel(LazyDataModel<IndexacionPDUDto> lazyModel) {
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
