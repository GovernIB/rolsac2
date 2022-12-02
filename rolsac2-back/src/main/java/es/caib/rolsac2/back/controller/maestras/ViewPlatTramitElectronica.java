package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.PlatTramitElectronicaGridDTO;
import es.caib.rolsac2.service.model.filtro.PlatTramitElectronicaFiltro;
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
public class ViewPlatTramitElectronica extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewPlatTramitElectronica.class);

    private LazyDataModel<PlatTramitElectronicaGridDTO> lazyModel;

    @EJB
    PlatTramitElectronicaServiceFacade serviceFacade;

    private PlatTramitElectronicaFiltro filtro;

    private PlatTramitElectronicaGridDTO datoSeleccionado;

    public LazyDataModel<PlatTramitElectronicaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        LOG.debug("load");

        filtro = new PlatTramitElectronicaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<PlatTramitElectronicaGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public PlatTramitElectronicaGridDTO getRowData(String rowKey) {
                for (PlatTramitElectronicaGridDTO pers : (List<PlatTramitElectronicaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) {
                        return pers;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(PlatTramitElectronicaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<PlatTramitElectronicaGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                           Map<String, FilterMeta> filterBy) {
                try {
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<PlatTramitElectronicaGridDTO> pagina = serviceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<PlatTramitElectronicaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void nuevoTramiteElectronico() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTramiteElectronico() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTramiteElectronico() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarTramiteElectronico() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Seleccione un elemento");
        } else {
            serviceFacade.delete(datoSeleccionado.getCodigo());
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
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogPlatTramitElectronica", modoAcceso, params, true, 850, 350);
    }

    public PlatTramitElectronicaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(PlatTramitElectronicaFiltro filtro) {
        this.filtro = filtro;
    }

    public PlatTramitElectronicaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(PlatTramitElectronicaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
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
