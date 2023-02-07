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
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewPublicoObjetivoEntidad extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ViewPublicoObjetivoEntidad.class);


    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    private TipoPublicoObjetivoEntidadGridDTO datoSeleccionado;

    private TipoPublicoObjetivoEntidadFiltro filtro;

    private LazyDataModel<TipoPublicoObjetivoEntidadGridDTO> lazyModel;


    public LazyDataModel<TipoPublicoObjetivoEntidadGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        permisoAccesoVentana(ViewPublicoObjetivoEntidad.class);
        //this.tienePermisos(this.getClass().getName());

        //Inicializamos combos/desplegables/inputs/filtro
        filtro = new TipoPublicoObjetivoEntidadFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());//UtilJSF.getSessionUnidadActiva());
        filtro.setIdioma(sessionBean.getLang());//UtilJSF.getSessionLang());

        LOG.error("Rol admin: " + this.isUserRoleRSCAdmin());
        LOG.error("Rol user: " + this.isUserRoleRSCUser());
        LOG.error("Rol user: " + this.isUserRoleRSCMentira());
        LOG.error("Username: " + this.getUserName());

        buscar();
    }

    public void nuevoTPOE() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarTPOE() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarTPOE() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarTPOE() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            boolean existen = maestrasSupService.existeProcedimientoConPublicoObjetivo(datoSeleccionado.getCodigo());
            if (existen) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.error.relacionProcedimientos"));
            } else {
                maestrasSupService.deleteTipoPublicoObjetivoEntidad(datoSeleccionado.getCodigo());
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.eliminaciocorrecta"));
            }
        }
    }


    public void abrirVentana(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso == TypeModoAcceso.CONSULTA)) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        UtilJSF.openDialog("dialogTipoPublicoObjetivoEntidad", modoAcceso, params, true, 800, 280);
    }

    public void buscar() {
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public TipoPublicoObjetivoEntidadGridDTO getRowData(String rowKey) {
                for (TipoPublicoObjetivoEntidadGridDTO proc : (List<TipoPublicoObjetivoEntidadGridDTO>) getWrappedData()) {
                    if (proc.getCodigo().toString().equals(rowKey))
                        return proc;
                }
                return null;
            }

            @Override
            public Object getRowKey(TipoPublicoObjetivoEntidadGridDTO proc) {
                return proc.getCodigo().toString();
            }

            @Override
            public List<TipoPublicoObjetivoEntidadGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                                Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<TipoPublicoObjetivoEntidadGridDTO> pagina = maestrasSupService.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<TipoPublicoObjetivoEntidadGridDTO> pagina = pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }
        };
    }


    public void setFiltro(TipoPublicoObjetivoEntidadFiltro filtro) {
        this.filtro = filtro;
    }

    public TipoPublicoObjetivoEntidadFiltro getFiltro() {
        return filtro;
    }

    public void setDatoSeleccionado(TipoPublicoObjetivoEntidadGridDTO dato) {
        this.datoSeleccionado = dato;
    }

    public TipoPublicoObjetivoEntidadGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void returnDialogo(final SelectEvent event) {

        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscar();

        }

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
