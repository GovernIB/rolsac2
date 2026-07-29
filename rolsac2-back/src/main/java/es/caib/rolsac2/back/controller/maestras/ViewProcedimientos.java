package es.caib.rolsac2.back.controller.maestras;

//import es.caib.rolsac2.api.externa.v1.model.Procedimientos;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.comun.UtilsArbolTemas;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilExport;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.exportar.ExportarCampos;
import es.caib.rolsac2.service.model.exportar.ExportarDatos;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
    MaestrasSupServiceFacade maestrasSupService;

    @EJB
    TemaServiceFacade temaServiceFacade;

    @EJB
    SystemServiceFacade systemService;

    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;
    private ProcedimientoGridDTO datoSeleccionado;
    private ProcedimientoDTO procedimientoSeleccionado;
    private String uaRaiz;
    private ProcedimientoFiltro filtro;
    private LazyDataModel<ProcedimientoGridDTO> lazyModel;

    private List<TipoProcedimientoDTO> listTipoProcedimiento;
    private List<TipoSilencioAdministrativoDTO> listTipoSilencio;
    private List<TipoPublicoObjetivoDTO> listTipoPublicoObjetivo;
    private List<TipoFormaInicioDTO> listTipoFormaInicio;
    private List<TipoLegitimacionDTO> listTipoLegitimacion;
    private List<TipoViaDTO> listFinVias;
    private List<TemaGridDTO> temasPadre;
    private List<TipoTramitacionDTO> listPlantillas;
    private List<PlatTramitElectronicaDTO> listPlataformas;

    public LazyDataModel<ProcedimientoGridDTO> getLazyModel() {
        return lazyModel;
    }

    /**
     * Cuando se exporta los datos
     **/
    private ExportarDatos exportarDatos;
    private String idioma;

    /**
     * Pagina detalle
     */
    private String wfProcedimiento;
    private ProcedimientoDTO wfPublicado;
    private ProcedimientoDTO wfModificado;
    private NormativaGridDTO normativaSeleccionada;
    private CategoriaPDUGridDTO categoriaPDUSeleccionada;
    private ProcedimientoDocumentoDTO documentoSeleccionado;
    private ProcedimientoDocumentoDTO documentoLOPDSeleccionado;
    private TreeNode temaSeleccionado;
    private ProcedimientoTramiteDTO tramiteSeleccionado;
    private List<TreeNode> temasTabla;
    private List<TreeNode> roots;
    private List<TemaGridDTO> temasPadreAnyadidos = new ArrayList<>();
    private Literal lopdResponsable;
    private Literal comunUA;

    // Flag para indicar si se puede descargar el fichero exportado
    private boolean downloadReady;

    public void load() {
        LOG.debug("load View Procedimientos");
        permisoAccesoVentana(ViewProcedimientos.class);

        /*if (this.isGestor()) {
            codigosUaDescendientesGestor = uaService.listarDescendientes(sessionBean.getUnidadActiva().getCodigo());
        }*/

        this.limpiarFiltro();
        cargarFiltros();
        buscar();
        idioma = sessionBean.getLang();
        roots = new ArrayList<>();//construirArbol();
        comunUA = sessionBean.getEntidad().getUaComun();
        temasTabla = new ArrayList<>();
        for (TemaGridDTO tema : temasPadre) {
            temasTabla.add(new DefaultTreeNode(new TemaGridDTO(), null));
        }

    }


    String wfProcedimientoPrevio = null;

    public void cambiarProcedimientoSeleccionadoWF() {
        /** Nos guardamos el ultimo click **/
        if (wfProcedimiento != null) {
            wfProcedimientoPrevio = wfProcedimiento;
        }

        if (wfProcedimiento == null) {
            if (wfProcedimientoPrevio == null) {
                procedimientoSeleccionado = ProcedimientoDTO.createInstance(this.sessionBean.getIdiomasObligatoriosList());
                return;
            } else {
                wfProcedimiento = wfProcedimientoPrevio;
            }
        }

        if (wfProcedimiento.equals("P") && wfPublicado != null) {
            procedimientoSeleccionado = wfPublicado;
        } else if (wfProcedimiento.equals("M") && wfModificado != null) {
            procedimientoSeleccionado = wfModificado;
        }
    }


    public void onTabChange(TabChangeEvent event) {
        String tabId = event.getTab().getId();
        if ("tabDef".equals(tabId)) {
            procedimientoSeleccionado = wfPublicado;
        } else if ("tabMod".equals(tabId)) {
            procedimientoSeleccionado = wfModificado;
        } else {
            procedimientoSeleccionado = ProcedimientoDTO.createInstance(this.sessionBean.getIdiomasObligatoriosList());
        }
    }

    public void calcularProc() {
        wfPublicado = null;
        wfModificado = null;
        wfProcedimiento = "";

        if (datoSeleccionado == null) {
            procedimientoSeleccionado = ProcedimientoDTO.createInstance(this.sessionBean.getIdiomasObligatoriosList());
        } else {
            if (datoSeleccionado.getCodigoWFPub() != null) {
                procedimientoSeleccionado = procedimientoService.findProcedimientoById(datoSeleccionado.getCodigoWFPub());
                uaRaiz = Boolean.valueOf(this.procedimientoSeleccionado.getUaInstructor() != null && this.procedimientoSeleccionado.getUaInstructor().esRaiz()).toString();
                wfProcedimiento = "P";
                wfProcedimientoPrevio = "P";
                wfPublicado = procedimientoSeleccionado;
                if (datoSeleccionado.getCodigoWFMod() != null) {
                    wfModificado = procedimientoService.findProcedimientoById(datoSeleccionado.getCodigoWFMod());
                }
            } else if (datoSeleccionado.getCodigoWFMod() != null) {
                procedimientoSeleccionado = procedimientoService.findProcedimientoById(datoSeleccionado.getCodigoWFMod());
                uaRaiz = Boolean.valueOf(this.procedimientoSeleccionado.getUaInstructor() != null && this.procedimientoSeleccionado.getUaInstructor().esRaiz()).toString();
                wfProcedimiento = "M";
                wfProcedimientoPrevio = "M";
                wfModificado = procedimientoSeleccionado;
            } else {
                procedimientoSeleccionado = ProcedimientoDTO.createInstance(this.sessionBean.getIdiomasObligatoriosList());
                uaRaiz = "";
            }

            temasTabla = new ArrayList<>();
            if (procedimientoSeleccionado.getCodigo() != null) {
                for (TemaGridDTO tema : temasPadre) {
                    temasTabla.add(new DefaultTreeNode(new TemaGridDTO(), null));
                }
                construirArbol();
            }

        }
    }


    private void construirArbol() {
        roots = new ArrayList<>();
        UtilsArbolTemas.construirArbol(roots, temasPadre, temasPadreAnyadidos, procedimientoSeleccionado.getTemas(), temaServiceFacade);
    }


    public void filtroHijasActivasChange() {
        if (filtro.isHijasActivas() && !filtro.isTodasUnidadesOrganicas()) {
            List<Long> idsUasInstructor = uaService.listarDescendientes(sessionBean.getUnidadActiva().getCodigo());
            idsUasInstructor.add(sessionBean.getUnidadActiva().getCodigo());
            filtro.setIdUAsInstructor(idsUasInstructor);
        } else if (filtro.isTodasUnidadesOrganicas()) {
            List<Long> ids = new ArrayList<>();

            for (UnidadAdministrativaDTO ua : sessionBean.obtenerUnidadesAdministrativasUsuario()) {
                if (filtro.isHijasActivas()) {
                    List<Long> idsUa = uaService.listarDescendientes(ua.getCodigo());
                    ids.addAll(idsUa);
                }

                ids.add(ua.getCodigo());
            }
            filtro.setIdUAsInstructor(ids);
        } else {
            filtro.setIdUAsInstructor(null);
            filtro.setIdUAInstructor(sessionBean.getUnidadActiva().getCodigo());
        }
    }

    public void filtroUnidadOrganicasChange() {
        filtroHijasActivasChange();
    }

    public void limpiarFiltro() {
        filtro = new ProcedimientoFiltro();
        filtro.setFechaCierreTramiteDesde(null);
        filtro.setFechaCierreTramiteHasta(null);
        filtro.setHijasActivas(true);
        filtro.setTodasUnidadesOrganicas(true);
        if (sessionBean.getUnidadActiva() != null) {
            filtro.setIdUAInstructor(sessionBean.getUnidadActiva().getCodigo());
        } else {
            filtro.setIdUAInstructor(null);
        }

        filtro.setIdioma(sessionBean.getLang());
        //filtro.setIdEntidad(sessionBean.getEntidad().getCodigo());
        filtro.setEsProcedimiento(Boolean.TRUE);
        filtro.setOrder("DESCENDING");
        filtro.setTipo("P");
    }

    private void cargarFiltros() {
        filtro.setEsProcedimiento(Boolean.TRUE);

        listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());
        listTipoPublicoObjetivo = maestrasSupService.findAllTiposPublicoObjetivo();
        listFinVias = maestrasSupService.findAllTipoVia();
        temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        listPlantillas = new ArrayList<>();
        listPlantillas.addAll(maestrasSupService.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), null));

        listPlataformas = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String paramCodigo = params.get("codigoProc");
        if (paramCodigo != null) {
            filtro.setCodigoProc(Long.valueOf(paramCodigo));
        }
    }

    public void nuevoProcedimiento() {
        abrirVentana(TypeModoAcceso.ALTA, null, null);
    }

    public void dblClickProcedimiento() {
        if (datoSeleccionado == null) {
            return;
        }

        if (!mostrarConsultar(datoSeleccionado) && !mostrarBorrar(datoSeleccionado) && !mostrarEditar(datoSeleccionado)) {
            return;
        } else {
            if (mostrarEditar(datoSeleccionado) && (mostrarConsultar(datoSeleccionado) || mostrarBorrar(datoSeleccionado))) {
                return;
            } else if (mostrarConsultar(datoSeleccionado) && mostrarBorrar(datoSeleccionado)) {
                return;
            } else if (mostrarConsultar(datoSeleccionado)) {
                consultarProcedimiento();
            } else if (mostrarBorrar(datoSeleccionado)) {
                PrimeFaces.current().executeScript("PF('confirmBorrar').show();");
            } else {
                editarProcedimiento();
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

            TypeModoAcceso modo = BooleanUtils.isTrue((datoSeleccionado.getComun()) && (this.isGestor() || this.isInformador()) || ((datoSeleccionado.getEstado().equals("PV") || datoSeleccionado.getEstado().equals("PPV")) && this.isGestor())) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
            String estados = procedimientoService.getWorkflowEstados(this.datoSeleccionado.getCodigo());
            abrirVentana(modo, proc, estados);

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
                String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
                idProcMod = procedimientoService.generarModificacion(datoSeleccionado.getCodigoWFPub(), usuario, sessionBean.getPerfil(), ruta);
                realizarBusqueda = true;
            }
            this.datoSeleccionado.setCodigoWFMod(idProcMod);
            ProcedimientoDTO proc = procedimientoService.findProcedimientoById(idProcMod);

            TypeModoAcceso modo = BooleanUtils.isTrue(datoSeleccionado.getComun()) && (this.isGestor() || this.isInformador()) ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
            String estados = procedimientoService.getWorkflowEstados(this.datoSeleccionado.getCodigo());
            abrirVentana(modo, proc, estados);
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
                String estados = procedimientoService.getWorkflowEstados(this.datoSeleccionado.getCodigo());
                abrirVentana(TypeModoAcceso.CONSULTA, proc, estados);
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

    public void editarProcedimiento(ProcedimientoGridDTO procedimiento) {
        this.datoSeleccionado = procedimiento;
        editarProcedimiento();
    }

    public void consultarProcedimiento(ProcedimientoGridDTO procedimiento) {
        this.datoSeleccionado = procedimiento;
        consultarProcedimiento();
    }

    public void borrarProcedimiento(ProcedimientoGridDTO procedimiento) {
        this.datoSeleccionado = procedimiento;
        PrimeFaces.current().executeScript("PF('confirmBorrar').show();");
    }

    public void clonarProcedimiento(ProcedimientoGridDTO procedimiento) {
        this.datoSeleccionado = procedimiento;
        clonarProcedimiento();
    }

    public ProcedimientoDTO getBorrador(ProcedimientoGridDTO procedimiento) {
        if (procedimiento == null) {
            return null;
        }
        Long idMod = procedimiento.getCodigoWFMod();
        if (idMod == null) {
            return null;
        }
        try {
            return procedimientoService.findProcedimientoById(idMod);
        } catch (Exception e) {
            LOG.error("Error obteniendo borrador de procedimiento: " + idMod, e);
            return null;
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

    /**
     * Imprime el listado de normativas.
     */
    public void exportar() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.TIPO.toString(), "PROC");
        UtilJSF.anyadirMochila("exportar", exportarDatos);
        UtilJSF.openDialog("dialogExportar", TypeModoAcceso.ALTA, params, true, 800, 700);
    }


    /**
     * Devuelve el resultado del dialogo de traspaso.
     *
     * @param event
     */
    public void returnDialogoExportar(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            exportarDatos = (ExportarDatos) respuesta.getResult();
            this.downloadReady = true;
        } else {
            this.downloadReady = false;
        }
    }

    /**
     * Devuelve el fichero
     */
    public StreamedContent getFile() {

        ExportarDatos exportarDatos = this.exportarDatos.clone();

        List<ExportarCampos> campos = new ArrayList<>();
        // Eliminamos los campos no seleccionados
        for (ExportarCampos campo : exportarDatos.getCampos()) {
            if (campo.isSeleccionado()) {
                campos.add(campo);
            }
        }
        exportarDatos.setCampos(campos);
        List<ProcedimientoCompletoDTO> procedimientos = procedimientoService.findExportByFiltro(filtro, exportarDatos);

        Map<String, String> literalesWF = new HashMap<>();
        literalesWF.put("1", getLiteral("dict.wf.1"));
        literalesWF.put("0", getLiteral("dict.wf.0"));

        Map<String, String> literalesEstado = new HashMap<>();
        literalesEstado.put("M", getLiteral("TypeProcedimientoEstado.M"));
        literalesEstado.put("T", getLiteral("TypeProcedimientoEstado.T"));
        literalesEstado.put("PT", getLiteral("TypeProcedimientoEstado.PT"));
        literalesEstado.put("PV", getLiteral("TypeProcedimientoEstado.PV"));
        literalesEstado.put("P", getLiteral("TypeProcedimientoEstado.P"));
        literalesEstado.put("PPV", getLiteral("TypeProcedimientoEstado.PPV"));
        literalesEstado.put("PM", getLiteral("TypeProcedimientoEstado.PM"));
        literalesEstado.put("TPV", getLiteral("TypeProcedimientoEstado.TPV"));
        literalesEstado.put("TM", getLiteral("TypeProcedimientoEstado.TM"));

        Map<String, String> literalesEstadoSIA = new HashMap<>();
        literalesEstadoSIA.put("A", getLiteral("dialogProcedimiento.estadoSIA.A"));
        literalesEstadoSIA.put("B", getLiteral("dialogProcedimiento.estadoSIA.B"));

        String[][] datos = UtilExport.getValoresCompletos(procedimientos, exportarDatos, this.getIdioma(), literalesWF, literalesEstado, literalesEstadoSIA);
        String[] cabecera = UtilExport.getCabecera(exportarDatos);
        return UtilExport.generarStreamedContent("Procediment", cabecera, datos, exportarDatos);
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

    public void clonarProcedimiento() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dict.info"), getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.ID.toString(), datoSeleccionado.getCodigo().toString());
            params.put(TypeParametroVentana.TIPO.toString(), "P");
            UtilJSF.openDialog("dialogClonar", TypeModoAcceso.ALTA, params, true, 500, 340);
        }
    }

    public void returnDialogoClonar(final SelectEvent event) {

        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled()) {
            //ProcedimientoDTO proc = procedimientoService.findProcedimientoById((Long) respuesta.getResult());
            this.buscar();
            filtro.setPaginaFirst(0);  //La pongo al principio donde saldra
            ProcedimientoFiltro filtroClonado = filtro.clone();
            filtroClonado.setCodigoWF((Long) respuesta.getResult());
            Pagina<ProcedimientoGridDTO> pagina = procedimientoService.findProcedimientosByFiltro(filtroClonado);
            if (pagina != null && pagina.getItems() != null && !pagina.getItems().isEmpty()) {
                this.seleccionarPorId(pagina.getItems().get(0));
            }
            //abrirVentana(TypeModoAcceso.EDICION, proc);
        }
    }

    public void test1() {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("test", TypeModoAcceso.CONSULTA, params, true, 400, 400);
    }


    private void abrirVentana(TypeModoAcceso modoAcceso, ProcedimientoDTO proc, String estado) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (estado != null && !estado.isEmpty()) {
            params.put(TypeParametroVentana.ESTADO_PROCEDIMIENTO.toString(), estado);
        }
        if (proc != null) {
            UtilJSF.anyadirMochila("PROC", proc);
        }
        Integer ancho = 1190;
        /** Anyadimos también los tipos. **/
        UtilJSF.anyadirMochila("listTipoFormaInicio", listTipoFormaInicio);
        UtilJSF.anyadirMochila("listTipoSilencio", listTipoSilencio);
        UtilJSF.anyadirMochila("listTipoLegitimacion", listTipoLegitimacion);
        UtilJSF.anyadirMochila("listTipoProcedimiento", listTipoProcedimiento);
        UtilJSF.anyadirMochila("listFinVias", listFinVias);
        UtilJSF.anyadirMochila("temasPadre", temasPadre);
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
        if (filtro.isTodasUnidadesOrganicas()) {
            filtroUnidadOrganicasChange();
        } else if (filtro.isHijasActivas()) {
            filtroHijasActivasChange();
        }
        if (filtro.getIdUAInstructor() == null || filtro.getIdUAInstructor().compareTo(sessionBean.getUnidadActiva().getCodigo()) != 0) {
            /*if (this.isGestor()) {
                codigosUaDescendientesGestor = uaService.listarDescendientes(sessionBean.getUnidadActiva().getCodigo());
                filtro.setIdUAInstructorOComun(codigosUaDescendientesGestor);
            } else if (this.isInformador()) {
                filtro.setIdUAInstructorOComun(Arrays.asList(sessionBean.getUnidadActiva().getCodigo()));
            } else {
                filtro.setIdUAInstructor(sessionBean.getUnidadActiva() != null ? sessionBean.getUnidadActiva().getCodigo() : null);
            }*/
            filtro.setIdUAInstructor(sessionBean.getUnidadActiva() != null ? sessionBean.getUnidadActiva().getCodigo() : null);
            buscar();
        }
    }

    public boolean validateDate() {
        if (filtro.getFechaCierreTramiteDesde() != null && filtro.getFechaCierreTramiteHasta() != null
                && filtro.getFechaCierreTramiteHasta().before(filtro.getFechaCierreTramiteDesde())) {
            String mensajeError = getLiteral("msg.error.fecha.rango.invalido");
            FacesContext.getCurrentInstance().addMessage("datePicker",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, mensajeError));

            return false;
        }

        return true;
    }

    public void buscar() {

        if (!validateDate()) {
            return;
        }

        lazyModel = new LazyDataModel<ProcedimientoGridDTO>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getRowKey(ProcedimientoGridDTO procedimiento) {
                return procedimiento.getCodigo().toString();
            }

            @Override
            public ProcedimientoGridDTO getRowData(String rowKey) {
                for (Object o : this.getWrappedData()) {
                    ProcedimientoGridDTO proc = (ProcedimientoGridDTO) o;
                    if (proc.getCodigo() == Integer.parseInt(rowKey)) {
                        return proc;
                    }
                }

                return null;
            }


            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return getRowCount();
            }

            @Override
            public List<ProcedimientoGridDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {

                    if (sortBy != null && !sortBy.isEmpty()) {
                        SortMeta sortMeta = sortBy.values().iterator().next();
                        SortOrder sortOrder = sortMeta.getOrder();
                        if (sortOrder != null) {
                            filtro.setAscendente(sortOrder.equals(SortOrder.ASCENDING));
                        }
                        filtro.setOrderBy(sortMeta.getField());
                    }
                    if (filtro.isHijasActivas() && (CollectionUtils.size(filtro.getIdUAsHijas()) > 1000)) {
                        List<Long> unidadesHijasAux = new ArrayList<>(filtro.getIdUAsHijas());
                        filtro.setIdUAsInstructor(unidadesHijasAux.subList(0, 999));
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


    public void returnDialogoMochilaX() {

        ProcedimientoGridDTO proc = null;
        String recordamos = null;
        try {
            if (UtilJSF.getValorMochilaByKey("proc") != null && UtilJSF.getValorMochilaByKey("proc") instanceof ProcedimientoDTO) {
                ProcedimientoDTO procActualizado = (ProcedimientoDTO) UtilJSF.getValorMochilaByKey("proc");
                wfProcedimiento = procedimientoService.getWorkflowEstados(procActualizado.getCodigo());

                ProcedimientoFiltro filtroX = new ProcedimientoFiltro();
                filtroX.setCodigo(procActualizado.getCodigo());
                Pagina<ProcedimientoGridDTO> datos = procedimientoService.findProcedimientosByFiltro(filtroX);
                if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {
                    for (ProcedimientoGridDTO procGridDTO : datos.getItems()) {
                        if (procGridDTO.getCodigo().equals(procActualizado.getCodigo())) {
                            this.datoSeleccionado = procGridDTO;
                            break;
                        }
                    }
                }
            }
            proc = this.datoSeleccionado;
            recordamos = wfProcedimiento;

            calcularProc();
            this.buscar();
            this.seleccionarPorId(proc);
            if (recordamos != null) {
                wfProcedimiento = recordamos;
                cambiarProcedimientoSeleccionadoWF();
            }
        } catch (Exception e) {
            LOG.error("Error al refrescar la lista de servicios", e);
            if (recordamos != null) {
                LOG.error("Recordamos : " + recordamos);
            }
            if (proc != null) {
                LOG.error("SERV : " + proc);
            }
            if (proc != null) {
                LOG.error("SERV : " + proc);
            }
        }

    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            ProcedimientoGridDTO proc = null;
            String recordamos = null;
            try {
                if (respuesta.getResult() != null && respuesta.getResult() instanceof ProcedimientoDTO) {
                    ProcedimientoDTO procActualizado = (ProcedimientoDTO) respuesta.getResult();
                    wfProcedimiento = procedimientoService.getWorkflowEstados(procActualizado.getCodigo());

                    ProcedimientoFiltro filtroX = new ProcedimientoFiltro();
                    filtroX.setCodigo(procActualizado.getCodigo());
                    Pagina<ProcedimientoGridDTO> datos = procedimientoService.findProcedimientosByFiltro(filtroX);
                    if (datos != null && datos.getItems() != null && !datos.getItems().isEmpty()) {
                        for (ProcedimientoGridDTO procGridDTO : datos.getItems()) {
                            if (procGridDTO.getCodigo().equals(procActualizado.getCodigo())) {
                                this.datoSeleccionado = procGridDTO;
                                break;
                            }
                        }
                    }
                }
                proc = this.datoSeleccionado;
                recordamos = wfProcedimiento;

                calcularProc();
                this.buscar();
                this.seleccionarPorId(proc);
                if (recordamos != null) {
                    wfProcedimiento = recordamos;
                    cambiarProcedimientoSeleccionadoWF();
                }
            } catch (Exception e) {
                LOG.error("Error al refrescar la lista de servicios", e);
                if (recordamos != null) {
                    LOG.error("Recordamos : " + recordamos);
                }
                if (proc != null) {
                    LOG.error("SERV : " + proc);
                }
                if (proc != null) {
                    LOG.error("SERV : " + proc);
                }
            }
        }
    }

    public void seleccionarMaterias() {
        UtilJSF.anyadirMochila("materiasSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("tipo/dialogSeleccionMateriaSIA", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarUaInstructor() {
        if (filtro.getUaInstructorCodigo() != null) {
            UnidadAdministrativaDTO uaInstructor = uaService.findUASimpleByID(filtro.getUaInstructorCodigo(), sessionBean.getLang(), null);
            if (uaInstructor != null) {
                UtilJSF.anyadirMochila("ua", uaInstructor);
            }
        }
        UtilJSF.openDialog("/comun/dialogSeleccionarUA", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 750);
    }

    public void seleccionarNormativas() {
        UtilJSF.anyadirMochila("normativasSeleccionadas", filtro.getNormativas());
        UtilJSF.openDialog("tipo/dialogSeleccionNormativa", TypeModoAcceso.EDICION, new HashMap<>(), true, 1200, 750);
    }

    public void seleccionarPubObjetivos() {
        UtilJSF.anyadirMochila("tipoPubObjEntSeleccionadas", filtro.getMaterias());
        UtilJSF.openDialog("dialogSeleccionTipoPublicoObjetivoEntidad", TypeModoAcceso.EDICION, new HashMap<>(), true, 1040, 460);
    }

    public void seleccionarTemas() {
        final Map<String, String> params = new HashMap<>();
        params.put("filtrado", "true");
        UtilJSF.anyadirMochila("temas", filtro.getTemas());
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", TypeModoAcceso.EDICION, params, true, 1040, 500);
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

    public void returnDialogUaInstructor(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            UnidadAdministrativaDTO uaInstructor = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaInstructor == null) {
                if (sessionBean.getUnidadActiva() != null) {
                    filtro.setIdUAInstructor(sessionBean.getUnidadActiva().getCodigo());
                } else {
                    filtro.setIdUAInstructor(null);
                }
                filtro.setUaInstructorNombre(null);
            } else {
                filtro.setIdUAInstructor(uaInstructor.getCodigo());
                if (uaInstructor.getNombre() != null) {
                    filtro.setUaInstructorNombre(uaInstructor.getNombre().getTraduccionConValor(sessionBean.getLang()));
                } else {
                    filtro.setUaInstructorNombre(null);
                }
            }
        }
    }

    public void abrirMensajes(Long codigo) {
        final Map<String, String> params = new HashMap<>();
        String mensajes = procedimientoService.getMensajesByCodigo(codigo);
        UtilJSF.anyadirMochila("mensajes", mensajes);
        UtilJSF.anyadirMochila("tipo", "P");
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

    public void consultarNormativa() {
        if (normativaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", normativaSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("dialogNormativa", TypeModoAcceso.CONSULTA, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
        }
    }

    public void consultarCategoriaPDU() {
        if (categoriaPDUSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", categoriaPDUSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("dialogCategoriaPDU", TypeModoAcceso.CONSULTA, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
        }
    }

    public void consultarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.ID.toString(), procedimientoSeleccionado.getCodigo() == null ? "" : procedimientoSeleccionado.getCodigoWF().toString());
            UtilJSF.anyadirMochila("documento", this.documentoSeleccionado.clone());
            params.put(TypeParametroVentana.TIPO.toString(), "PROC_DOC");
            UtilJSF.openDialog("dialogDocumentoProcedimiento", TypeModoAcceso.CONSULTA, params, true, 800, 380);
        }
    }

    public void consultarDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", procedimientoSeleccionado.getCodigo() == null ? "" : procedimientoSeleccionado.getCodigo().toString());
            UtilJSF.anyadirMochila("documento", this.documentoLOPDSeleccionado.clone());
            params.put(TypeParametroVentana.TIPO.toString(), "PROC_DOC");
            UtilJSF.openDialog("dialogDocumentoProcedimientoLOPD", TypeModoAcceso.CONSULTA, params, true, 800, 350);
        }
    }

    public void consultarTema(Integer index) {
        if (temasTabla == null || temasTabla.get(index) == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            TemaGridDTO tema = (TemaGridDTO) temasTabla.get(index).getData();
            params.put("ID", tema.getCodigo().toString());
            UtilJSF.openDialog("/entidades/dialogTema", TypeModoAcceso.CONSULTA, params, true, 700, 300);
        }
    }

    private final Integer FASE_INICIACION = 1;

    public void consultarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            UtilJSF.anyadirMochila("fechaPublicacion", procedimientoSeleccionado.getFechaPublicacion());
            UtilJSF.anyadirMochila("tramiteSel", tramiteSeleccionado.clone());
            UtilJSF.anyadirMochila("uasInstructor", new ArrayList<>()); //uasInstructor);
            UtilJSF.anyadirMochila("nombreProcedimiento", procedimientoSeleccionado.getNombreProcedimientoWorkFlow());
            UtilJSF.openDialog("dialogProcedimientoTramite", TypeModoAcceso.CONSULTA, params, true, 950, 600);
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

    public ProcedimientoDTO getProcedimientoSeleccionado() {
        return procedimientoSeleccionado;
    }

    public void setProcedimientoSeleccionado(ProcedimientoDTO procedimientoSeleccionado) {
        this.procedimientoSeleccionado = procedimientoSeleccionado;
    }

    public String getUaRaiz() {
        return uaRaiz;
    }

    public void setUaRaiz(String uaRaiz) {
        this.uaRaiz = uaRaiz;
    }

    @Override
    public String getIdioma() {
        return idioma;
    }

    @Override
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getWfProcedimiento() {
        return wfProcedimiento;
    }

    public void setWfProcedimiento(String wfProcedimiento) {
        this.wfProcedimiento = wfProcedimiento;
    }

    public ProcedimientoDTO getWfPublicado() {
        return wfPublicado;
    }

    public void setWfPublicado(ProcedimientoDTO wfPublicado) {
        this.wfPublicado = wfPublicado;
    }

    public ProcedimientoDTO getWfModificado() {
        return wfModificado;
    }

    public void setWfModificado(ProcedimientoDTO wfModificado) {
        this.wfModificado = wfModificado;
    }

    public NormativaGridDTO getNormativaSeleccionada() {
        return normativaSeleccionada;
    }

    public void setNormativaSeleccionada(NormativaGridDTO normativaSeleccionada) {
        this.normativaSeleccionada = normativaSeleccionada;
    }

    public CategoriaPDUGridDTO getCategoriaPDUSeleccionada() {
        return categoriaPDUSeleccionada;
    }

    public void setCategoriaPDUSeleccionada(CategoriaPDUGridDTO categoriaPDUSeleccionada) {
        this.categoriaPDUSeleccionada = categoriaPDUSeleccionada;
    }

    public ProcedimientoDocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ProcedimientoDocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public ProcedimientoDocumentoDTO getDocumentoLOPDSeleccionado() {
        return documentoLOPDSeleccionado;
    }

    public void setDocumentoLOPDSeleccionado(ProcedimientoDocumentoDTO documentoLOPDSeleccionado) {
        this.documentoLOPDSeleccionado = documentoLOPDSeleccionado;
    }

    public TreeNode getTemaSeleccionado() {
        return temaSeleccionado;
    }

    public void setTemaSeleccionado(TreeNode temaSeleccionado) {
        this.temaSeleccionado = temaSeleccionado;
    }

    public ProcedimientoTramiteDTO getTramiteSeleccionado() {
        return tramiteSeleccionado;
    }

    public void setTramiteSeleccionado(ProcedimientoTramiteDTO tramiteSeleccionado) {
        this.tramiteSeleccionado = tramiteSeleccionado;
    }

    public List<TreeNode> getTemasTabla() {
        return temasTabla;
    }

    public void setTemasTabla(List<TreeNode> temasTabla) {
        this.temasTabla = temasTabla;
    }

    public List<TreeNode> getRoots() {
        return roots;
    }

    public void setRoots(List<TreeNode> roots) {
        this.roots = roots;
    }

    public List<TemaGridDTO> getTemasPadreAnyadidos() {
        return temasPadreAnyadidos;
    }

    public void setTemasPadreAnyadidos(List<TemaGridDTO> temasPadreAnyadidos) {
        this.temasPadreAnyadidos = temasPadreAnyadidos;
    }

    public String getIcono(TemaGridDTO valor) {
        if (valor.getTipoMateriaSIA() == null) {
            return "";
        } else {
            return Constantes.INDEXAR_SIA_ICONO;
        }
    }

    public String getTooltip(TemaGridDTO valor) {
        if (valor.getTipoMateriaSIA() == null) {
            return "";
        } else {
            return "SIA: " + valor.getTipoMateriaSIA().getDescripcion().getTraduccion(this.getIdioma()) + " - " + valor.getTipoMateriaSIA().getCodigoSIA();
        }
    }

    public String getIconoSIA() {
        return Constantes.INDEXAR_SIA_ICONO;
    }

    public Literal getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(Literal lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public Literal getComunUA() {
        return comunUA;
    }

    public void setComunUA(Literal comunUA) {
        this.comunUA = comunUA;
    }

    public boolean isDownloadReady() {
        return downloadReady;
    }

    public void setDownloadReady(boolean downloadReady) {
        this.downloadReady = downloadReady;
    }

    public boolean mostrarClonar(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && !(this.isGestor() && BooleanUtils.isTrue(procedimiento.getComun()));
    }

    public boolean mostrarConsultar(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && procedimiento.getCodigoWFPub() != null;
    }

    public boolean mostrarEditar(ProcedimientoGridDTO procedimiento) {
        if (procedimiento == null || isModoConsulta()) {
            return false;
        }

        String estado = procedimiento.getEstado();
        return "M".equals(estado) || "P".equals(estado) || ("PV".equals(estado) && !this.isGestor());
    }

    public boolean mostrarBorrar(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && "M".equals(procedimiento.getEstado())
                && !(this.isGestor() && BooleanUtils.isTrue(procedimiento.getComun()));
    }

    public boolean mostrarMenuBorrador(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && ("PM".equals(procedimiento.getEstado()) || "PPV".equals(procedimiento.getEstado()));
    }

    public boolean mostrarConsultarBorradorPublicado(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && "PV".equals(procedimiento.getEstado()) && this.isGestor();
    }

    public boolean mostrarEditarBorrador(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta()
                && ("PM".equals(procedimiento.getEstado()) || ("PPV".equals(procedimiento.getEstado()) && !this.isGestor()));
    }

    public boolean mostrarConsultarBorrador(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && "PPV".equals(procedimiento.getEstado()) && this.isGestor();
    }

    public boolean mostrarBorrarBorrador(ProcedimientoGridDTO procedimiento) {
        return procedimiento != null && !isModoConsulta() && "PM".equals(procedimiento.getEstado())
                && !(this.isGestor() && BooleanUtils.isTrue(procedimiento.getComun()));
    }

    public boolean isEstadoEditable() {
        boolean editable;
        if (this.datoSeleccionado != null && this.datoSeleccionado.getEstado() != null && !this.datoSeleccionado.getEstado().startsWith("PT") && !this.datoSeleccionado.getEstado().startsWith("T")) {
            if (this.isGestor() && BooleanUtils.isTrue(this.datoSeleccionado.getComun())) {
                editable = true;
            } else {
                editable = false;
            }
        } else {
            editable = true;
        }
        return editable;
    }

    public boolean isClonarBorrarDisabled() {
        return this.isGestor() && this.datoSeleccionado.getComun() == true;
    }
}
