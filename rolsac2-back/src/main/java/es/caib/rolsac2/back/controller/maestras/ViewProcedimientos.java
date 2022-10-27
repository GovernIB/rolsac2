package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ViewProcedimientos extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ViewProcedimientos.class);

    @EJB
    ProcedimientoServiceFacade procedimientoService;
    private ProcedimientoGridDTO datoSeleccionado;

    private ProcedimientoFiltro filtro;

    private LazyDataModel<ProcedimientoGridDTO> lazyModel;


    public LazyDataModel<ProcedimientoGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");

        //this.tienePermisos(this.getClass().getName());

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new ProcedimientoFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());

        LOG.debug("Rol admin: " + this.isUserRoleRSCAdmin());
        LOG.debug("Rol user: " + this.isUserRoleRSCUser());
        LOG.debug("Rol user: " + this.isUserRoleRSCMentira());
        LOG.debug("Username: " + this.getUserName());

        buscar();
    }

    public void nuevoProcedimiento() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarProcedimiento() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            procedimientoService.delete(datoSeleccionado.getCodigo());

        }
    }

    private void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        Integer ancho = sessionBean.getScreenWidthInt();
        if (ancho == null) {
            ancho = 1433;
        }
        UtilJSF.openDialog("dialogProcedimiento", modoAcceso, params, true, ancho, 733);
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ProcedimientoGridDTO getRowData(String rowKey) {
                for (ProcedimientoGridDTO proc : (List<ProcedimientoGridDTO>) getWrappedData()) {
                    if (proc.getCodigo().toString().equals(rowKey))
                        return proc;
                }
                return null;
            }

            @Override
            public Object getRowKey(ProcedimientoGridDTO proc) {
                return proc.getCodigo().toString();
            }

            @Override
            public List<ProcedimientoGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                   Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<ProcedimientoGridDTO> pagina = procedimientoService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<ProcedimientoGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }

    public void abrirVentanaProc() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        Integer ancho = sessionBean.getScreenWidthInt();
        if (ancho == null) {
            ancho = 1433;
        }
        UtilJSF.openDialog("dialogProcedimiento", TypeModoAcceso.EDICION, params, true, ancho, 690);
    }

    public void abrirVentanaServ() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        Integer ancho = sessionBean.getScreenWidthInt();
        if (ancho == null) {
            ancho = 1433;
        }
        UtilJSF.openDialog("dialogServicio", TypeModoAcceso.EDICION, params, true, ancho, 690);
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();
        }
    }

    public void setFiltro(ProcedimientoFiltro filtro) {
        this.filtro = filtro;
    }

    public ProcedimientoFiltro getFiltro() {
        return filtro;
    }

    public void setDatoSeleccionado(ProcedimientoGridDTO dato) {
        this.datoSeleccionado = dato;
    }

    public ProcedimientoGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

}