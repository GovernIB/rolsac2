package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcesoServiceFacade;
import es.caib.rolsac2.service.facade.ProcesoTimerServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcesoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcesoFiltro;
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
public class ViewProcesos extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewProcesos.class);

    @EJB
    private ProcesoServiceFacade procesoServiceFacade;

    @EJB
    ProcesoTimerServiceFacade procesoTimerServiceFacade;

    private LazyDataModel<ProcesoGridDTO> lazyModel;

    private ProcesoGridDTO datoSeleccionado;

    private ProcesoFiltro filtro;

    public LazyDataModel<ProcesoGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        permisoAccesoVentana(ViewProcesos.class);
        LOG.debug("load");
        filtro = new ProcesoFiltro();
        filtro.setIdioma(sessionBean.getLang());
        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }


    public void buscar() {
        lazyModel = new LazyDataModel<ProcesoGridDTO>() {
            @Override
            public ProcesoGridDTO getRowData(String rowKey) {
                for (ProcesoGridDTO pers : (List<ProcesoGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public String getRowKey(ProcesoGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<ProcesoGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<ProcesoGridDTO> pagina = procesoServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<ProcesoGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoProceso() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarProceso() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarProceso() {
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

    public void borrarProceso() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            procesoServiceFacade.borrar(datoSeleccionado.getCodigo());
        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogProceso", modoAcceso, params, true, 745, 580);
    }

    public void procesadoManual() {
        procesoTimerServiceFacade.procesadoManual(this.datoSeleccionado.getIdentificadorProceso(), sessionBean.getEntidad().getCodigo());
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcesos.procesoLanzado"));
    }

    public ProcesoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(ProcesoGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public ProcesoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(ProcesoFiltro filtro) {
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
