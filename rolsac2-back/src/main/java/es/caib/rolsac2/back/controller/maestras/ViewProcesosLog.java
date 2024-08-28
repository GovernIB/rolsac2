package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcesoLogServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcesoLogGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoLogFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
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
public class ViewProcesosLog extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesosLog.class);

    @EJB
    private ProcesoLogServiceFacade procesoLogServiceFacade;

    private LazyDataModel<ProcesoLogGridDTO> lazyModel;

    private ProcesoLogGridDTO datoSeleccionado;

    private ProcesoLogFiltro filtro;

    public LazyDataModel<ProcesoLogGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesosLog.class);
        LOG.debug("load");
        filtro = new ProcesoLogFiltro();
        filtro.setIdioma(sessionBean.getLang());
        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }


    public void buscar() {
        lazyModel = new LazyDataModel<ProcesoLogGridDTO>() {
            @Override
            public ProcesoLogGridDTO getRowData(String rowKey) {
                for (ProcesoLogGridDTO pers : (List<ProcesoLogGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public String getRowKey(ProcesoLogGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<ProcesoLogGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<ProcesoLogGridDTO> pagina = procesoLogServiceFacade.findByFiltro(filtro);
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


    public void detalleLogProceso() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
            params.put("ESTADO", this.datoSeleccionado.getEstadoTexto());
        }
        UtilJSF.openDialog("dialogDetalleLogProceso", modoAcceso, params, true, 800, 600);
    }

    public ProcesoLogGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(ProcesoLogGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoLogFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoLogFiltro filtro) {
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
