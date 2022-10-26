package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.SeccionServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.SeccionGridDTO;
import es.caib.rolsac2.service.model.filtro.SeccionFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
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
public class DialogSelectSeccion extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSelectSeccion.class);

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

    public void guardar() {

        //Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(datoSeleccionado);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    public void setLazyModel(LazyDataModel<SeccionGridDTO> lazyModel) {
        this.lazyModel = lazyModel;
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
