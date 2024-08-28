package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.UsuarioGridDTO;
import es.caib.rolsac2.service.model.filtro.UsuarioFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogSeleccionUsuario extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogSeleccionUsuario.class);

    private LazyDataModel<UsuarioGridDTO> lazyModel;

    @EJB
    private AdministracionEntServiceFacade AdministracionEntServiceFacade;

    private UsuarioGridDTO datoSeleccionado;

    private UsuarioFiltro filtro;

    public LazyDataModel<UsuarioGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        this.setearIdioma();
        LOG.debug("load");
        filtro = new UsuarioFiltro();
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
        lazyModel = new LazyDataModel<UsuarioGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public UsuarioGridDTO getRowData(String rowKey) {
                for (UsuarioGridDTO pers : (List<UsuarioGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey)) return pers;
                }
                return null;
            }

            @Override
            public String getRowKey(UsuarioGridDTO objeto) {
                return objeto.getCodigo().toString();
            }

            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<UsuarioGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
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
                    Pagina<UsuarioGridDTO> pagina = AdministracionEntServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<UsuarioGridDTO> pagina = new Pagina(new ArrayList(), 0);
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

    public void setLazyModel(LazyDataModel<UsuarioGridDTO> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public UsuarioGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(UsuarioGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public UsuarioFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(UsuarioFiltro filtro) {
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
