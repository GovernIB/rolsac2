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

import org.primefaces.PrimeFaces;
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
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.NormativaDTO;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.Pagina;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.TipoNormativaDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.filtro.NormativaFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;

@Named
@ViewScoped
public class ViewNormativa extends AbstractController implements Serializable {
    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewNormativa.class);

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    private LazyDataModel<NormativaGridDTO> lazyModel;

    private NormativaGridDTO datoSeleccionado;

    private NormativaFiltro filtro;

    private List<TipoNormativaDTO> listTipoNormativa;

    private List<TipoBoletinDTO> listTipoBoletin;

    private Boolean isTraspaso = false;

    public LazyDataModel<NormativaGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load");
        this.setearIdioma();

        permisoAccesoVentana(ViewNormativa.class);
        filtro = new NormativaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        cargarFiltros();
        // Generamos una búsqueda
        buscar();
    }

    public void update() {
        buscar();
    }

    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    public void filtroHijasActivasChange() {
        if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsHijas(unidadAdministrativaServiceFacade.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        } else if (filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> ids = new ArrayList<>();

            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                List<Long> idsUa = unidadAdministrativaServiceFacade.getListaHijosRecursivo(ua.getCodigo());
                ids.addAll(idsUa);
            }
            filtro.setIdUAsHijas(ids);
        } else if (!filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> idsUa = new ArrayList<>();
            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                idsUa.add(ua.getCodigo());
            }
            idsUa.add(sessionBean.getUnidadActiva().getCodigo());
            filtro.setIdUAsHijas(idsUa);
        }
    }

    public void filtroUnidadOrganicasChange() {
        if (filtro.isTodasUnidadesOrganicas()) {
            if (filtro.isHijasActivas()) {
                List<Long> ids = new ArrayList<>();

                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    List<Long> idsUa = unidadAdministrativaServiceFacade.getListaHijosRecursivo(ua.getCodigo());
                    ids.addAll(idsUa);
                }
                filtro.setIdUAsHijas(ids);
            } else {
                List<Long> idsUa = new ArrayList<>();
                for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                    idsUa.add(ua.getCodigo());
                }
                idsUa.add(sessionBean.getUnidadActiva().getCodigo());
                filtro.setIdUAsHijas(idsUa);
            }
        } else if(filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()){
            filtro.setIdUAsHijas(unidadAdministrativaServiceFacade.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        }
    }

    public void limpiarFiltro() {
        filtro = new NormativaFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        this.buscar();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    public void buscar() {

        lazyModel = new LazyDataModel<NormativaGridDTO>() {
            @Override
            public NormativaGridDTO getRowData(String rowKey) {
                for (NormativaGridDTO pers : (List<NormativaGridDTO>) getWrappedData()) {
                    if (pers.getCodigo().toString().equals(rowKey))
                        return pers;
                }
                return null;
            }

            @Override
            public Object getRowKey(NormativaGridDTO pers) {
                return pers.getCodigo().toString();
            }

            @Override
            public List<NormativaGridDTO> load(int first, int pageSize, String sortField,
                                               SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
                    filtro.setIdioma(sessionBean.getLang());
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    Pagina<NormativaGridDTO> pagina = normativaServiceFacade.findByFiltro(filtro);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                } catch (Exception e) {
                    LOG.error("Error llamando", e);
                    Pagina<NormativaGridDTO> pagina = new Pagina(new ArrayList(), 0);
                    setRowCount((int) pagina.getTotal());
                    return pagina.getItems();
                }
            }

        };
    }

    public void nuevaNormativa() {
        abrirVentana(TypeModoAcceso.ALTA);
    }

    public void editarNormativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirVentana(TypeModoAcceso.EDICION);
        }
    }

    public void consultarNormativa() {
        if (datoSeleccionado != null) {
            abrirVentana(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarNormativa() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            boolean existen = normativaServiceFacade.existeProcedimientoConNormativa(datoSeleccionado.getCodigo());
            if (existen) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.error.relacionProcedimientos"));
            } else {
                normativaServiceFacade.delete(datoSeleccionado.getCodigo());
            }
        }
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        isTraspaso = false;
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
        if(modoAcceso == TypeModoAcceso.ALTA && isTraspaso == true) {
            params.put("isTraspaso", isTraspaso.toString());
        }
        UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
    }

    public void abrirTraspaso() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogTraspasoBOIB", TypeModoAcceso.ALTA, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
    }

    public void returnDialogoTraspaso(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if(!respuesta.isCanceled()) {
            NormativaDTO normativaDTO = (NormativaDTO) respuesta.getResult();
            UtilJSF.anyadirMochila("normativaBOIB", normativaDTO);
            isTraspaso = true;
            PrimeFaces.current().executeScript("triggerNuevaNormativa()");
        }
    }

    private void cargarFiltros() {
        listTipoBoletin = maestrasSupServiceFacade.findBoletines();
        listTipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
    }

    public NormativaGridDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(NormativaGridDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }

    public NormativaFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(NormativaFiltro filtro) {
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

    public Boolean isTraspaso() {
        return isTraspaso;
    }

    public void setTraspaso(Boolean traspaso) {
        isTraspaso = traspaso;
    }

    public List<TipoNormativaDTO> getListTipoNormativa() {
        return listTipoNormativa;
    }

    public void setListTipoNormativa(List<TipoNormativaDTO> listTipoNormativa) {
        this.listTipoNormativa = listTipoNormativa;
    }

    public List<TipoBoletinDTO> getListTipoBoletin() {
        return listTipoBoletin;
    }

    public void setListTipoBoletin(List<TipoBoletinDTO> listTipoBoletin) {
        this.listTipoBoletin = listTipoBoletin;
    }
}
