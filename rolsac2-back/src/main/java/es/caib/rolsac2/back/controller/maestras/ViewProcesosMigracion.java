package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.IndexacionSIADTO;
import es.caib.rolsac2.service.model.ListaPropiedades;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcesoLogGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSIAFiltro;
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
import java.util.*;

@Named
@ViewScoped
public class ViewProcesosMigracion extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosMigracion.class);

    @EJB
    private MigracionServiceFacade migracionServiceFacade;

    @EJB
    private ProcesoServiceFacade procesoServiceFacade;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    @EJB
    private ProcesoLogServiceFacade procesoLogServiceFacade;

    @EJB
    private ProcesoTimerServiceFacade procesoTimerServiceFacade;


    private LazyDataModel<ProcesoLogGridDTO> lazyModelLogs;


    private IndexacionSIADTO datoSeleccionado;


    private ProcesoSIAFiltro filtro;
    private ProcesoLogFiltro filtroLog;

    private boolean borrarDatos;
    private boolean cargarUas;
    private boolean cargarNormativas;
    private boolean cargarProcedimientos;


    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosMigracion.class);
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

        borrarDatos = false;
        cargarUas = true;
        cargarNormativas = false;
        cargarProcedimientos = false;
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

        lazyModelLogs = new LazyDataModel<ProcesoLogGridDTO>() {
            @Override
            public ProcesoLogGridDTO getRowData(String rowKey) {
                for (ProcesoLogGridDTO pers : (List<ProcesoLogGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

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
                    filtroLog.setTipo("MIGRA_PUNT");
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

    public void ejecutar() {

        ListaPropiedades listaPropiedades = new ListaPropiedades();
        Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
        listaPropiedades.addPropiedad("accion", "");
        listaPropiedades.addPropiedad("borrarDatos", borrarDatos ? "true" : "false");
        listaPropiedades.addPropiedad("cargarUas", cargarUas ? "true" : "false");
        listaPropiedades.addPropiedad("cargarNormativas", cargarNormativas ? "true" : "false");
        listaPropiedades.addPropiedad("cargarProcedimientos", cargarProcedimientos ? "true" : "false");
        procesoTimerServiceFacade.procesadoManual("MIGRA_PUNT", listaPropiedades, idEntidad);
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
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

    public boolean isBorrarDatos() {
        return borrarDatos;
    }

    public void setBorrarDatos(boolean borrarDatos) {
        this.borrarDatos = borrarDatos;
    }

    public boolean isCargarUas() {
        return cargarUas;
    }

    public void setCargarUas(boolean cargarUas) {
        this.cargarUas = cargarUas;
    }

    public boolean isCargarNormativas() {
        return cargarNormativas;
    }

    public void setCargarNormativas(boolean cargarNormativas) {
        this.cargarNormativas = cargarNormativas;
    }

    public boolean isCargarProcedimientos() {
        return cargarProcedimientos;
    }

    public void setCargarProcedimientos(boolean cargarProcedimientos) {
        this.cargarProcedimientos = cargarProcedimientos;
    }
}
