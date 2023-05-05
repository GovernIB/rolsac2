package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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


    @EJB
    UnidadAdministrativaServiceFacade uaService;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;
    private ProcedimientoGridDTO datoSeleccionado;
    private ProcedimientoFiltro filtro;
    private LazyDataModel<ProcedimientoGridDTO> lazyModel;

    private List<TipoProcedimientoDTO> listTipoProcedimiento;
    private List<TipoSilencioAdministrativoDTO> listTipoSilencio;
    private List<TipoPublicoObjetivoDTO> listTipoPublicoObjetivo;
    private List<TipoFormaInicioDTO> listTipoFormaInicio;
    private List<TipoLegitimacionDTO> listTipoLegitimacion;
    private List<TipoViaDTO> listFinVias;
    private List<TipoTramitacionDTO> listPlantillas;
    private List<PlatTramitElectronicaDTO> listPlataformas;

    public LazyDataModel<ProcedimientoGridDTO> getLazyModel() {
        return lazyModel;
    }

    public void load() {
        LOG.debug("load View Procedimientos");
        permisoAccesoVentana(ViewProcedimientos.class);
        this.limpiarFiltro();
        cargarFiltros();
        buscar();
    }

    public void filtroHijasActivasChange() {
        if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            filtro.setIdUAsHijas(uaService.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        } else if (filtro.isHijasActivas() && filtro.isTodasUnidadesOrganicas()) {
            List<Long> ids = new ArrayList<>();

            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                List<Long> idsUa = uaService.getListaHijosRecursivo(ua.getCodigo());
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
                    List<Long> idsUa = uaService.getListaHijosRecursivo(ua.getCodigo());
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
            filtro.setIdUAsHijas(uaService.getListaHijosRecursivo(sessionBean.getUnidadActiva().getCodigo()));
        }
    }

    public void limpiarFiltro() {
        filtro = new ProcedimientoFiltro();
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        filtro.setIdioma(sessionBean.getLang());
        filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setTipo("P");
    }

    private void cargarFiltros() {
        listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());
        listTipoPublicoObjetivo = maestrasSupService.findAllTiposPublicoObjetivo();
        listFinVias = maestrasSupService.findAllTipoVia();
        listPlantillas = maestrasSupService.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), null);
        listPlataformas = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
    }

    public void nuevoProcedimiento() {
        abrirVentana(TypeModoAcceso.ALTA, null);
    }

    public void dblClickProcedimiento() {
        if (datoSeleccionado != null) {
            if (datoSeleccionado.getCodigoWFMod() != null) {
                editarProcedimiento();
            } else if (datoSeleccionado.getCodigoWFPub() != null) {
                consultarProcedimiento();
            }
        }
    }

    public void editarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            Long idProcMod = this.datoSeleccionado.getCodigoWFMod();
            if (idProcMod == null) {
                PrimeFaces.current().executeScript("PF('cdDeseaCrearEditar').show();");
                return;
            }
            ProcedimientoDTO proc = procedimientoService.findProcedimientoById(idProcMod);
            abrirVentana(TypeModoAcceso.EDICION, proc);

        }
    }

    public void editarProcedimientoSinPreguntar() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            Long idProcMod = this.datoSeleccionado.getCodigoWFMod();
            boolean realizarBusqueda = false;
            if (idProcMod == null) {
                String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                idProcMod = procedimientoService.generarModificacion(datoSeleccionado.getCodigoWFPub(), usuario, sessionBean.getPerfil());
                realizarBusqueda = true;
            }
            this.datoSeleccionado.setCodigoWFMod(idProcMod);
            ProcedimientoDTO proc = procedimientoService.findProcedimientoById(idProcMod);
            abrirVentana(TypeModoAcceso.EDICION, proc);
            if (realizarBusqueda) {
                this.buscar();
            }
        }
    }

    public void consultarProcedimiento() {
        if (datoSeleccionado != null) {
            Long idProcPub = datoSeleccionado.getCodigoWFPub();//procedimientoService.getCodigoByWF(datoSeleccionado.getCodigo(), TypeProcedimientoWorkflow.PUBLICADO.getValor());
            if (idProcPub == null) {
                // Mensaje --> No tiene publicado el dato
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewProcedimientos.error.procNoPublicado"), getLiteral("msg.seleccioneElemento"));
            } else {
                ProcedimientoDTO proc = procedimientoService.findProcedimientoById(idProcPub);
                abrirVentana(TypeModoAcceso.CONSULTA, proc);
            }
        }
    }

    public void borrarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcMod = datoSeleccionado.getCodigoWFMod();
            Long idProcPub = datoSeleccionado.getCodigoWFPub();
            if (idProcMod != null) {
                //PrimeFaces.current().executeScript("PF('confirmDlgBorrarModificado').show();");
                procedimientoService.deleteWF(idProcMod);
                this.datoSeleccionado.setCodigoWFMod(null);
                ProcedimientoGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            } else if (idProcPub != null) {
                //PrimeFaces.current().executeScript("PF('confirmDlgBorrarPublicado').show();");
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("viewProcedimientos.error.borrarPublicado"));
            } else {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + ".");// UtilJSF.getLiteral("info.borrado.ok"));
            }
        }
    }

    public void borrarProcedimentoMod() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcMod = datoSeleccionado.getCodigoWFMod();
            if (idProcMod == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + " Modificacion");
            } else {
                procedimientoService.deleteWF(idProcMod);
                ProcedimientoGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            }
        }
    }


    public void borrarProcedimentoPub() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));// UtilJSF.getLiteral("info.borrado.ok"));
        } else {
            Long idProcPub = datoSeleccionado.getCodigoWFPub();
            if (idProcPub == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento") + " Modificacion");
            } else {
                procedimientoService.deleteWF(idProcPub);
                ProcedimientoGridDTO proc = this.datoSeleccionado;
                this.buscar();
                this.seleccionarPorId(proc);
            }
        }
    }

    private void seleccionarPorId(ProcedimientoGridDTO idProcSeleccionado) {
        if (idProcSeleccionado == null || this.lazyModel == null) {
            return;
        }

        this.datoSeleccionado = idProcSeleccionado;
        /*
        Iterator<ProcedimientoGridDTO> it = this.lazyModel.iterator();

        while (it != null && it.hasNext()) {
            ProcedimientoGridDTO procGrid = it.next();
            if (procGrid != null && procGrid.getCodigo().compareTo(idProcSeleccionado) == 0) {
                this.datoSeleccionado = procGrid;
                break;
            }
        }*/
    }

    private void abrirVentana(TypeModoAcceso modoAcceso, ProcedimientoDTO proc) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (proc != null) {
            UtilJSF.anyadirMochila("PROC", proc);
        }
        //Integer ancho = sessionBean.getScreenWidthInt();
        //if (ancho == null) {
        //    ancho = 1433;
        //}
        Integer ancho = 1010;
        UtilJSF.openDialog("dialogProcedimiento", modoAcceso, params, true, ancho, 733);
    }

    public void cambiarUAbuscarEvt(UnidadAdministrativaDTO ua) {
        sessionBean.cambiarUnidadAdministrativa(ua);
        buscarEvt();
    }

    /**
     * El buscar desde el evento de seleccionar una UA.
     */
    public void buscarEvt() {
        if(filtro.isTodasUnidadesOrganicas()) {
            filtroUnidadOrganicasChange();
        } else if(filtro.isHijasActivas()) {
            filtroHijasActivasChange();
        }
        if (filtro.getIdUA() == null || filtro.getIdUA().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            buscar();
        }
    }

    public void buscar() {
        filtro.setIdUA(sessionBean.getUnidadActiva().getCodigo());
        lazyModel = new LazyDataModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ProcedimientoGridDTO getRowData(String rowKey) {
                if (getWrappedData() != null) {
                    for (ProcedimientoGridDTO proc : (List<ProcedimientoGridDTO>) getWrappedData()) {
                        if (proc.getCodigo().toString().equals(rowKey)) return proc;
                    }
                }
                return null;
            }

            @Override
            public Object getRowKey(ProcedimientoGridDTO proc) {
                return proc.getCodigo().toString();
            }

            @Override
            public List<ProcedimientoGridDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                try {
                    filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                    if (!sortField.equals("filtro.orderBy")) {
                        filtro.setOrderBy(sortField);
                    }
                    if(filtro.isHijasActivas() && (filtro.getIdUAsHijas().size() > 1000)) {
                        List<Long> unidadesHijasAux = new ArrayList<>(filtro.getIdUAsHijas());
                        filtro.setIdUAsHijas(unidadesHijasAux.subList(0, 999));
                        filtro.setIdsUAsHijasAux(unidadesHijasAux.subList(1000, unidadesHijasAux.size() - 1));
                    }
                    Pagina<ProcedimientoGridDTO> pagina = procedimientoService.findProcedimientosByFiltro(filtro);
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

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            ProcedimientoGridDTO proc = this.datoSeleccionado;
            this.buscar();
            this.seleccionarPorId(proc);

        }
    }

    public void seleccionarMaterias() {
        UtilJSF.anyadirMochila("materiasSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("tipo/dialogSeleccionMateriaSIA", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarNormativas() {
        UtilJSF.anyadirMochila("normativasSeleccionadas", filtro.getNormativas());
        UtilJSF.openDialog("tipo/dialogSeleccionNormativa", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarPubObjetivos() {
        UtilJSF.anyadirMochila("tipoPubObjEntSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("dialogSeleccionTipoPublicoObjetivoEntidad", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarTemas() {
        final Map<String, String> params = new HashMap<>();
        params.put("filtrado", "true");
        UtilJSF.anyadirMochila("temas", filtro.getTemas());
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", TypeModoAcceso.EDICION, params, true, 1040, 460);
    }

    public void returnDialogMateria(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoMateriaSIAGridDTO> materiasSeleccionadas = (List<TipoMateriaSIAGridDTO>) respuesta.getResult();
            if (materiasSeleccionadas == null) {
                filtro.setMaterias(new ArrayList<>());
            } else {
                if (filtro.getMaterias() == null) {
                    filtro.setMaterias(new ArrayList<>());
                }
                filtro.setMaterias(new ArrayList<>());
                filtro.getMaterias().addAll(materiasSeleccionadas);
            }
        }
    }

    public void abrirMensajes(Long codigo) {
        final Map<String, String> params = new HashMap<>();
        String mensajes = procedimientoService.getMensajesByCodigo(codigo);
        UtilJSF.anyadirMochila("mensajes", mensajes);
        params.put("SOLO_MENSAJES", "S");
        params.put("ID", codigo.toString());
        //params.put("ESTADO", data.getEstado().toString());
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void returnDialogMensajes(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
            procedimientoService.actualizarMensajes(Long.valueOf(respuestaFlujo.getCodigoProcedimiento()), respuestaFlujo.getMensajes(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor());
        }
    }


    public void returnDialogNormativa(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<NormativaGridDTO> normativaG = (List<NormativaGridDTO>) respuesta.getResult();

            if (normativaG == null) {
                filtro.setNormativas(new ArrayList<>());
            } else {
                if (filtro.getNormativas() == null) {
                    filtro.setNormativas(new ArrayList<>());
                }
                filtro.setNormativas(new ArrayList<>());
                filtro.getNormativas().addAll(normativaG);
            }
        }
    }

    public void returnDialogPubObjEnt(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
            if (tipPubObjEntSeleccionadas == null) {
                filtro.setPublicoObjetivos(new ArrayList<>());
            } else {
                if (filtro.getPublicoObjetivos() == null) {
                    filtro.setPublicoObjetivos(new ArrayList<>());
                }
                filtro.setPublicoObjetivos(new ArrayList<>());
                filtro.getPublicoObjetivos().addAll(tipPubObjEntSeleccionadas);
            }
        }
    }

    public void returnDialogTema(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TemaGridDTO> temas = (List<TemaGridDTO>) respuesta.getResult();

            if (temas == null) {
                filtro.setTemas(new ArrayList<>());
            } else {
                if (filtro.getTemas() == null) {
                    filtro.setTemas(new ArrayList<>());
                }
                filtro.setTemas(new ArrayList<>());
                filtro.getTemas().addAll(temas);
            }
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

    public List<TipoProcedimientoDTO> getListTipoProcedimiento() {
        return listTipoProcedimiento;
    }

    public void setListTipoProcedimiento(List<TipoProcedimientoDTO> listTipoProcedimiento) {
        this.listTipoProcedimiento = listTipoProcedimiento;
    }

    public List<TipoSilencioAdministrativoDTO> getListTipoSilencio() {
        return listTipoSilencio;
    }

    public void setListTipoSilencio(List<TipoSilencioAdministrativoDTO> listTipoSilencio) {
        this.listTipoSilencio = listTipoSilencio;
    }

    public List<TipoPublicoObjetivoDTO> getListTipoPublicoObjetivo() {
        return listTipoPublicoObjetivo;
    }

    public void setListTipoPublicoObjetivo(List<TipoPublicoObjetivoDTO> listTipoPublicoObjetivo) {
        this.listTipoPublicoObjetivo = listTipoPublicoObjetivo;
    }

    public List<TipoFormaInicioDTO> getListTipoFormaInicio() {
        return listTipoFormaInicio;
    }

    public void setListTipoFormaInicio(List<TipoFormaInicioDTO> listTipoFormaInicio) {
        this.listTipoFormaInicio = listTipoFormaInicio;
    }

    public List<TipoLegitimacionDTO> getListTipoLegitimacion() {
        return listTipoLegitimacion;
    }

    public void setListTipoLegitimacion(List<TipoLegitimacionDTO> listTipoLegitimacion) {
        this.listTipoLegitimacion = listTipoLegitimacion;
    }

    public List<TipoViaDTO> getListFinVias() {
        return listFinVias;
    }

    public void setListFinVias(List<TipoViaDTO> listFinVias) {
        this.listFinVias = listFinVias;
    }

    public List<TipoTramitacionDTO> getListPlantillas() {
        return listPlantillas;
    }

    public void setListPlantillas(List<TipoTramitacionDTO> listPlantillas) {
        this.listPlantillas = listPlantillas;
    }

    public List<PlatTramitElectronicaDTO> getListPlataformas() {
        return listPlataformas;
    }

    public void setListPlataformas(List<PlatTramitElectronicaDTO> listPlataformas) {
        this.listPlataformas = listPlataformas;
    }


}
