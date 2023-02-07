package es.caib.rolsac2.back.controller.maestras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.exception.ServiceException;
import es.caib.rolsac2.service.facade.SeccionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewSeccion extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewSeccion.class);

    private LazyDataModel<SeccionGridDTO> lazyModel;

    @EJB
    private SeccionServiceFacade seccionServiceFacade;

    private SeccionGridDTO datoSeleccionado;

    private SeccionFiltro filtro;

    public LazyDataModel<SeccionGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load(){
        this.setearIdioma();
        permisoAccesoVentana(ViewSeccion.class);
        LOG.debug("load");
        filtro = new SeccionFiltro();
        filtro.setIdioma(sessionBean.getLang());
        buscar();
    }

    public void update() {
        buscar();
    }

    public void buscarAvanzada() {
        System.out.println();
    }

    public void buscar() {
        lazyModel = new LazyDataModel<SeccionGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public SeccionGridDTO getRowData(String rowKey) {
                for (SeccionGridDTO pers : (List<SeccionGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(SeccionGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<SeccionGridDTO> load(int first, int pageSize, String sortField,
                                                          SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<SeccionGridDTO> pagina = seccionServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<SeccionGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };
    }

    public void nuevaSeccion() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarSeccion() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarSeccion() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarSeccion() {
        try {
            if (datoSeleccionado == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
            } else {
                seccionServiceFacade.delete(datoSeleccionado.getCodigo());
            }
        } catch(ServiceException e){
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.hijos.relacionados"));
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
        if (this.datoSeleccionado != null
                && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogSeccion", modoAcceso, params, true, 850, 435);
    }


    public SeccionGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(SeccionGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public SeccionFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(SeccionFiltro filtro) {
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
