package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.comun.UtilsArbolTemas;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.*;
import es.caib.rolsac2.service.model.util.SiaCumpleEnviable;
import org.apache.commons.lang3.BooleanUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogProcedimiento extends AbstractController implements Serializable {


    private ProcedimientoDTO data;
    private ProcedimientoDTO dataOriginal;

    private String objeto;
    private String destinatarios;
    private String estadoProcedimiento;
    private String estadosProcedimiento;

    private String termino;

    private String legitimacion;

    private String validacion;

    private String tipoProcedimientoSeleccionado;

    private TipoMateriaSIAGridDTO materiaSIAGridSeleccionada;

    private NormativaDTO normativaSeleccionada;
    private NormativaGridDTO normativaGridSeleccionada;
    private CategoriaPDUDTO categoriaPDUSeleccionada;
    private CategoriaPDUGridDTO categoriaPDUGridSeleccionada;
    private ProcedimientoDocumentoDTO documentoSeleccionado;
    private ProcedimientoDocumentoDTO documentoLOPDSeleccionado;

    private List<TipoFormaInicioDTO> listTipoFormaInicio;
    private List<TipoSilencioAdministrativoDTO> listTipoSilencio;
    private List<TipoLegitimacionDTO> listTipoLegitimacion;

    private List<TipoProcedimientoDTO> listTipoProcedimiento;
    private List<TipoViaDTO> listTipoVia;

    private ProcedimientoTramiteDTO tramiteSeleccionado;
    private TipoPublicoObjetivoEntidadGridDTO tipoPubObjEntGridSeleccionado;

    private List<TemaGridDTO> temasPadre;

    private TreeNode temaSeleccionado;

    private List<TreeNode> roots;

    private List<TreeNode> temasTabla;

    private List<TemaGridDTO> temasPadreAnyadidos;

    private List<Long> uasInstructor = new ArrayList<>();

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    @EJB
    private UnidadAdministrativaServiceFacade uaService;

    @EJB
    private TemaServiceFacade temaServiceFacade;

    @EJB
    private ProcesoTimerServiceFacade procesoTimerServiceFacade;

    @EJB
    private SystemServiceFacade systemService;

    private String id = "";
    private boolean mostrarRefreshSIA = false;

    private String textoValor;
    private Literal comunUA;
    private Literal comunLOPD;
    private Literal responsableUA;
    private Literal lopdResponsable;
    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimiento.class);
    private final Integer FASE_INICIACION = 1;
    private boolean esSoloGuardar;
    private String uaRaiz;
    private Literal lopdDerechos;
    private Literal lopdInfoAdicional;

    private boolean noEditable;
    private boolean mostrarBtnPDU = false;
    private boolean mostrarBtnFlujo = true;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();
        estadosProcedimiento = estadoProcedimiento;

        this.setLopdDerechos((Literal) sessionBean.getEntidad().getLopdDerechos().clone());
        this.setLopdInfoAdicional(new Literal());

        if (this.isModoAlta()) {
            data = ProcedimientoDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUaInstructor(sessionBean.getUnidadActiva());
            data.setUaResponsable(sessionBean.getUnidadActiva());
            data.setLopdResponsable(uaService.obtenerPadreDir3(UtilJSF.getSessionBean().getUnidadActiva().getCodigo(), UtilJSF.getSessionBean().getLang()));
            data.setUaCompetente(sessionBean.getUnidadActiva());
            this.setLopdResponsable(uaService.obtenerPadreDir3(UtilJSF.getSessionBean().getUnidadActiva().getCodigo()));
            if (this.getLopdResponsable() != null) {
                this.data.setLopdResponsable(this.getLopdResponsable().getTraduccionConValor(sessionBean.getLang()));
            }
            data.setTemas(new ArrayList<>());
            data.setCategoriasPDU(new ArrayList<>());
            data.setHabilitadoFuncionario("N");
            data.setLopdFinalidad((Literal) sessionBean.getEntidad().getLopdFinalidad().clone());
            data.setLopdDestinatario((Literal) sessionBean.getEntidad().getLopdDestinatario().clone());

        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            if (id != null && !id.isEmpty()) {
                data = procedimientoServiceFacade.findProcedimientoById(Long.valueOf(id));
            } else {
                data = (ProcedimientoDTO) UtilJSF.getValorMochilaByKey("PROC");
            }

            this.setLopdResponsable(uaService.obtenerPadreDir3(data.getUaInstructor().getCodigo()));
            if (this.getLopdResponsable() != null) {
                this.data.setLopdResponsable(this.getLopdResponsable().getTraduccionConValor(sessionBean.getLang()));
            }
            // Indra se entra a editar pero se deshabilitan los botones de edición si es un procedimiento de una ua común que sólo puede consultar el gestor
            if (this.isGestor()) {
                List<Long> listaUasUsuario = uaService.listarDescendientes(sessionBean.getUnidadActiva().getCodigo());
                listaUasUsuario.add(sessionBean.getUnidadActiva().getCodigo());
                noEditable = !listaUasUsuario.contains(data.getUaInstructor().getCodigo());
            }
        }

        uaRaiz = BooleanUtils.toStringTrueFalse(this.data.getUaInstructor() != null && this.data.getUaInstructor().esRaiz());
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        data.setUsuarioAuditoria(usuario);
        comunUA = sessionBean.getEntidad().getUaComun();
        comunLOPD = sessionBean.getEntidad().getLopdComun();

        cargarListas();
        temasPadreAnyadidos = new ArrayList<>();

        temasTabla = new ArrayList<>();
        for (TemaGridDTO tema : temasPadre) {
            temasTabla.add(new DefaultTreeNode(new TemaGridDTO(), null));
        }

        this.construirArbol();
        UtilJSF.vaciarMochila();

        /** Si es alta y hay un tipo legitimacion por defecto, lo setea **/
        if (this.isModoAlta() && listTipoLegitimacion != null && !listTipoLegitimacion.isEmpty()) {
            for (TipoLegitimacionDTO tipoLegitimacion : listTipoLegitimacion) {
                if (tipoLegitimacion.isPorDefecto()) {
                    this.data.setDatosPersonalesLegitimacion(tipoLegitimacion);
                    break;
                }
            }
        }

        actualizarResponsable();
        dataOriginal = (ProcedimientoDTO) data.clone();

        //Eso es para cargar las uas del instructor
        calcularUAhijosPadres();

        //Comprobamos si la entidad tiene un plugin de indexacion pdu para mostrar el botón
        try {
            IPlugin plgPDU = systemService.obtenerPluginEntidad(TypePluginEntidad.PDU, sessionBean.getEntidad().getCodigo());
            mostrarBtnPDU = plgPDU != null;
        } catch (Exception e) {
            LOG.error("Error al obtener el plugin de PDU", e);
            LOG.error("ENTIDAD:{}", sessionBean.getEntidad());
            mostrarBtnPDU = false;
        }
    }

    /**
     * Metodo para cargar las listas
     **/
    private void cargarListas() {
        listTipoFormaInicio = (List<TipoFormaInicioDTO>) UtilJSF.getValorMochilaByKey("listTipoFormaInicio");
        listTipoSilencio = (List<TipoSilencioAdministrativoDTO>) UtilJSF.getValorMochilaByKey("listTipoSilencio");
        listTipoLegitimacion = (List<TipoLegitimacionDTO>) UtilJSF.getValorMochilaByKey("listTipoLegitimacion");
        listTipoProcedimiento = (List<TipoProcedimientoDTO>) UtilJSF.getValorMochilaByKey("listTipoProcedimiento");
        listTipoVia = (List<TipoViaDTO>) UtilJSF.getValorMochilaByKey("listFinVias");
        temasPadre = (List<TemaGridDTO>) UtilJSF.getValorMochilaByKey("temasPadre");

        if (temasPadre == null || temasPadre.isEmpty()) {
            temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        }
        if (listTipoFormaInicio == null || listTipoFormaInicio.isEmpty()) {
            listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        }
        if (listTipoSilencio == null || listTipoSilencio.isEmpty()) {
            listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        }
        if (listTipoLegitimacion == null || listTipoLegitimacion.isEmpty()) {
            listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        }
        if (listTipoProcedimiento == null || listTipoProcedimiento.isEmpty()) {
            listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());
        }
        if (listTipoVia == null || listTipoVia.isEmpty()) {
            listTipoVia = maestrasSupService.findAllTipoVia();
        }


    }

    /**
     * Actualiza el literal de resonsable
     **/
    public void actualizarResponsable() {
        if (data.getComun() == 0) {
            if (data.getUaInstructor() == null) {
                lopdResponsable = Literal.createInstance(sessionBean.getIdiomasPermitidosList());
            } else {
                lopdResponsable = data.getUaInstructor().getNombre();
            }
        } else {
            lopdResponsable = sessionBean.getEntidad().getLopdComun();
        }
    }

    /**
     * Enviado a SIA para que se indexe.
     */
    public void enviarSIA() {
        if (data.getCodigo() != null && data.getCodigoSIA() == null) {
            SiaCumpleEnviable dato = procedimientoServiceFacade.isProcServEnviableCumpleDatos(data, this.getIdioma());
            if (dato.isCorrecto()) {
                ListaPropiedades listaPropiedades = new ListaPropiedades();
                Long idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();
                listaPropiedades.addPropiedad("accion", Constantes.INDEXAR_SIA_PROCEDIMIENTO_PUNTUAL);
                listaPropiedades.addPropiedad("id", data.getCodigo().toString());
                listaPropiedades.addPropiedad("tipo", "P");
                procesoTimerServiceFacade.procesadoManual("SIA_PUNT", listaPropiedades, idEntidad);
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("dialogProcedimiento.procesoLanzado"));
                mostrarRefreshSIA = true;
            } else {
                UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimiento.error.enviarSIA") + dato.getMensaje());
            }
        }
    }

    /**
     * Refresca los datos por si ya se ha indexado
     */
    public void refrescarSIA() {
        ProcedimientoBaseDTO proc = procedimientoServiceFacade.findProcedimientoBaseById(data.getCodigo());
        this.data.setCodigoSIA(proc.getCodigoSIA());
        this.data.setEstadoSIA(proc.getEstadoSIA());
        this.data.setErrorSIA(proc.getErrorSIA());
        this.data.setFechaSIA(proc.getFechaSIA());
    }

    public void traducir() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void verAuditorias() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();
        params.put("ID", data.getCodigo().toString());
        params.put("TIPO", "PROC");
        UtilJSF.openDialog("/comun/dialogAuditoria", TypeModoAcceso.CONSULTA, params, true, 800, 600);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        ProcedimientoDTO datoDTO = (ProcedimientoDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setLopdInfoAdicional(datoDTO.getLopdInfoAdicional());
        }
    }


    public void returnDialogoUAInstr(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaSeleccionada != null) {
                this.data.setUaInstructor(uaSeleccionada);
                boolean misma = uaSeleccionada.getCodigo().compareTo(data.getUaInstructor().getCodigo()) == 0;
                uaRaiz = BooleanUtils.toStringTrueFalse(uaSeleccionada.esRaiz());
                if (!uaSeleccionada.esRaiz()) {
                    this.data.setComun(0); //Es raro que lo estuviese como comun pero por si acaso
                }
                if (!misma) {
                    calcularUAhijosPadres();
                }
            }
        }
    }

    private void calcularUAhijosPadres() {
        uasInstructor = new ArrayList<>();
        if (data.getUaInstructor() == null) {
            return;
        }
        List<Long> idHijos = uaService.listarDescendientes(data.getUaInstructor().getCodigo());
        List<Long> idPadres = uaService.listarPadres(data.getUaInstructor().getCodigo());

        uasInstructor.add(data.getUaInstructor().getCodigo());
        uasInstructor.addAll(idHijos);
        uasInstructor.addAll(idPadres);
    }

    /**
     * Devuelve el css para el boton de la UA Instructor.
     * Si no está en la lista de UA del instructor, se pone en rojo y se muestra el ojo
     *
     * @return css
     */
    public String getCssUAResponsable() {
        if (data.getUaResponsable() == null) {
            return "";
        }
        return uasInstructor.contains(data.getUaResponsable().getCodigo()) ? "" : "pi-exclamation-circle botonNaranjaRequired";
    }

    public String getCssUACompetente() {
        if (data.getUaCompetente() == null) {
            return "";
        }
        return uasInstructor.contains(data.getUaCompetente().getCodigo()) ? "" : "pi-exclamation-circle botonNaranjaRequired";
    }

  /*  public boolean mostrarAlertaUAResponsable() {
        return !uasInstructor.contains(data.getUaResponsable().getCodigo());
    }*/

    public boolean mostrarAlertaUAInstructor() {
        return !uasInstructor.contains(data.getUaInstructor().getCodigo());
    }

    public void returnDialogoUACompetente(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaSeleccionada != null) {
                this.data.setUaCompetente(uaSeleccionada);
            }
        }
    }

    public void returnDialogoUAResp(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaSeleccionada != null) {
                this.data.setUaResponsable(uaSeleccionada);
            }
        }
    }

    public void abrirVentanaUAResp() {
        UtilJSF.vaciarMochila();
        abrirVentanaUA(this.data.getUaResponsable());
    }

    public void abrirVentanaUAInstr() {

        if (!this.isAdministradorContenidos()) {
            UtilJSF.anyadirMochila("uAsRaiz", sessionBean.obtenerUasEntidad());
        }
        abrirVentanaUA(this.data.getUaInstructor());
    }

    public void abrirVentanaUACompetente() {
        UtilJSF.vaciarMochila();
        abrirVentanaUA(this.data.getUaCompetente());
    }

    private void abrirVentanaUA(UnidadAdministrativaDTO ua) {
        final Map<String, String> params = new HashMap<>();
        /*
         * if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso ==
         * TypeModoAcceso.CONSULTA)) { params.put(TypeParametroVentana.ID.toString(),
         * this.datoSeleccionado.getId().toString()); }
         */

        params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.getModoAcceso());
        String direccion = "/comun/dialogSeleccionarUA";

        UtilJSF.anyadirMochila("ua", ua);


        //params.put("esCabecera", null);
        UtilJSF.openDialog(direccion, TypeModoAcceso.valueOf(this.getModoAcceso()), params, true, 850, 575);
    }

    public void guardarFlujo() {

        esSoloGuardar = false;
        if (!checkObligatorio()) {
            return;
        }

        guardarFlujoSinCheck();
    }

    public void guardarFlujoSinCheck() {
        final Map<String, String> params = new HashMap<>();

        /*if (sessionBean.getPerfil() == TypePerfiles.GESTOR) {

            if (data.getEstado() != TypeProcedimientoEstado.MODIFICACION) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.soloEstadoModificacion"));
                return;
            }
        }*/

        if (data.getEstado() == TypeProcedimientoEstado.MODIFICACION && (data.getDocumentosLOPD() == null || data.getDocumentosLOPD().isEmpty())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.documentosLOPD"));
            return;
        }

        boolean tiene = false;
        if (data.getTramites() != null) {
            for (ProcedimientoTramiteDTO tramite : data.getTramites()) {
                if (tramite.getFase() == FASE_INICIACION.intValue()) {
                    tiene = true;
                    break;
                }
            }
        }

        if (!tiene) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteFaseIniciacion"));
            return;
        }

        if (!comprobarTramites()) {
            return;
        }

        if (data.getTemas() == null || data.getTemas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.sinTemas"));
            return;
        }

        for (TemaGridDTO temaPadre : temasPadre) {
            if (data.getTemas().stream().filter(t -> t.getMathPath().split(";")[0].equals(temaPadre.getCodigo().toString())).findAny().orElse(null) == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.sinTemas"));
                return;
            }
        }

        if (data != null && "P".equals(data.getTipo()) && data.getEstado() == TypeProcedimientoEstado.MODIFICACION && data.isIntegrarPdu()) {
            boolean correcto = checkPDU();
            if (!correcto) {
                return;
            }
        }

        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        UtilJSF.anyadirMochila("tipo", "P");
        UtilJSF.anyadirMochila("procedimiento", this.data);
        params.put("ID", this.data.getCodigo().toString());
        params.put("IDWF", this.data.getCodigoWF().toString());
        params.put("ESTADO", data.getEstado().toString());
        if (estadosProcedimiento != null) {
            params.put("ESTADO_PROCEDIMIENTO", estadosProcedimiento);
        }
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    private boolean comprobarTramites() {
        List<String> idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
        if (data.getTramites() != null && !data.getTramites().isEmpty()) {
            for (ProcedimientoTramiteDTO tramite : data.getTramites()) {
                List<String> camposIncompletos = new ArrayList<>();
                if (tramite.getNombre() == null || !tramite.getNombre().estaCompleto(idiomasObligatorios)) {
                    camposIncompletos.add(getLiteral("dialogProcedimientoTramite.nombreTramite"));
                }
                if (tramite.getRequisitos() == null || !tramite.getRequisitos().estaCompleto(idiomasObligatorios)) {
                    camposIncompletos.add(getLiteral("dialogProcedimientoTramite.requisitos"));
                }
                if (tramite.getTerminoMaximo() == null || !tramite.getTerminoMaximo().estaCompleto(idiomasObligatorios)) {
                    camposIncompletos.add(getLiteral("dialogProcedimientoTramite.terminoMaximo"));
                }
                if (!camposIncompletos.isEmpty()) {
                    String nombreTramite = tramite.getNombre() != null ? tramite.getNombre().getTraduccion("ca") : "";
                    UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteCamposIdiomasObligatorios", new Object[]{nombreTramite}));
                    return false;
                }

                // Validar títulos de documentos relacionados y modelos del trámite
                String nombreTramite = tramite.getNombre() != null ? tramite.getNombre().getTraduccion("ca") : "";
                if (tramite.getListaDocumentos() != null) {
                    for (ProcedimientoDocumentoDTO doc : tramite.getListaDocumentos()) {
                        if (doc.getTitulo() == null || !doc.getTitulo().estaCompleto(idiomasObligatorios)) {
                            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteDocsTituloObligatorio", new Object[]{nombreTramite}));
                            return false;
                        }
                    }
                }
                if (tramite.getListaModelos() != null) {
                    for (ProcedimientoDocumentoDTO mod : tramite.getListaModelos()) {
                        if (mod.getTitulo() == null || !mod.getTitulo().estaCompleto(idiomasObligatorios)) {
                            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteDocsTituloObligatorio", new Object[]{nombreTramite}));
                            return false;
                        }
                    }
                }

                // Validar que si el trámite es telemático tenga plantilla, datos de tramitación o URL informados
                if (tramite.isTramitElectronica()) {
                    boolean tienePlantilla = tramite.getPlantillaSel() != null && tramite.getPlantillaSel().getCodigo() != null;
                    boolean tieneDatosTramitacion = tramite.getTipoTramitacion() != null && tramite.getTipoTramitacion().getTramiteId() != null && !tramite.getTipoTramitacion().getTramiteId().isEmpty();
                    boolean tieneUrl = tramite.getTipoTramitacion() != null && tramite.getTipoTramitacion().getUrl() != null && tramite.getTipoTramitacion().getUrl().estaCompleto(idiomasObligatorios);
                    if (!tienePlantilla && !tieneDatosTramitacion && !tieneUrl) {
                        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteTelematicaSinDatos", new Object[]{nombreTramite}));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkPDU() {
        boolean rellenoCategoriaPDU = data.getCategoriasPDU() != null && !data.getCategoriasPDU().isEmpty();
        boolean rellenoCamposProc = data.isRellenoIdiomasPDU();
        boolean rellenoTramites = true;
        if (data.getTramites() != null && !data.getTramites().isEmpty()) {
            for (ProcedimientoTramiteDTO tram : data.getTramites()) {
                if (!tram.isRellenoIdiomasPDU()) {
                    rellenoTramites = false;
                    break;
                }
            }
        }

        if (!rellenoCategoriaPDU || !rellenoTramites || !rellenoCamposProc) {
            String msgError = getLiteral("dialogProcedimientoFlujo.errorMoverPDU") + "<br /><br />";
            if (!rellenoCategoriaPDU) {
                msgError += "  - " + getLiteral("dialogProcedimientoFlujo.errorFaltanCategoriasPDU") + "<br /><br />";
            }
            List<String> campos = data.getRellenoIdiomasCamposPDU();
            if (campos != null && !campos.isEmpty()) {
                String nombreCampos = "";
                for (String campo : campos) {
                    if (!nombreCampos.isEmpty()) {
                        nombreCampos += ", ";
                    }
                    nombreCampos += getLiteral(campo);
                }
                msgError += "  - " + getLiteral("dialogProcedimientoFlujo.errorFaltanIngles", new Object[]{nombreCampos}) + "<br /><br />";
            }

            if (!rellenoTramites) {
                for (ProcedimientoTramiteDTO tram : data.getTramites()) {
                    if (!tram.isRellenoIdiomasPDU()) {
                        List<String> camposTram = tram.getRellenoIdiomasCamposPDU();
                        String nombreCamposTram = "";
                        if (camposTram != null && !camposTram.isEmpty()) {
                            for (String campo : camposTram) {
                                if (!nombreCamposTram.isEmpty()) {
                                    nombreCamposTram += ", ";
                                }
                                nombreCamposTram += getLiteral(campo);
                            }
                        }
                        String identificador = tram.getNombre().getTraduccion("ca");
                        msgError += "  - " + getLiteral("dialogProcedimientoFlujo.errorFaltanInglesTramites", new Object[]{identificador, nombreCamposTram}) + "<br /><br />";
                    }
                }
            }
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError, true);
            return false;
        }

        /**
         if (!rellenoCategoriaPDU) {
         if (!campos.isEmpty()) {
         UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanCategoriasPDUeIngles"), true);
         return false;
         } else {
         UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanCategoriasPDU"), true);
         return false;
         }
         } else if (!campos.isEmpty()) {
         UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanIngles"), true);
         return false;
         }

         if (data.getTramites() != null && !data.getTramites().isEmpty()) {
         for (ProcedimientoTramiteDTO tram : data.getTramites()) {
         if (!tram.isRellenoIdiomasPDU()) {
         UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, msgError + getLiteral("dialogProcedimientoFlujo.errorFaltanInglesTramites"), true);
         return false;
         }
         }
         }**/

        return true;
    }

    public void verMensajes() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        UtilJSF.anyadirMochila("tipo", "P");
        params.put("SOLO_MENSAJES", "S");
        params.put("ESTADO", data.getEstado().toString());
        params.put("ID", this.data.getCodigo().toString());
        if (estadosProcedimiento != null) {
            params.put("ESTADO_PROCEDIMIENTO", estadosProcedimiento);
        }
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void returnDialogFlujo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();

            resetearOrdenListas();
            String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
            ProcedimientoBaseDTO dataDefinitivo = null;
            if (data.getWorkflow() != TypeProcedimientoWorkflow.DEFINITIVO) {
                Long codigoWF = procedimientoServiceFacade.getCodigoPublicado(data.getCodigo());
                if (codigoWF != null) {
                    dataDefinitivo = procedimientoServiceFacade.findProcedimientoById(codigoWF);
                }
            }
            procedimientoServiceFacade.guardarFlujo(data, dataDefinitivo, respuestaFlujo.getEstadoDestino(), respuestaFlujo.getMensajes(), sessionBean.getPerfil(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor(), UtilJSF.getSessionBean().getEntidad().getCodigo(), ruta);
            final DialogResult result = new DialogResult();
            if (this.getModoAcceso() != null && !this.getModoAcceso().equals(TypeModoAcceso.CONSULTA.toString())) {
                result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
            } else {
                result.setModoAcceso(TypeModoAcceso.EDICION);
            }
            data.setEstado(respuestaFlujo.getEstadoDestino());
            data.setMensajes(respuestaFlujo.getMensajes());
            data.setPendienteMensajesSupervisor(respuestaFlujo.isPendienteMensajesSupervisor());
            data.setPendienteMensajesGestor(respuestaFlujo.isPendienteMensajesGestor());
            result.setResult(data);

            UtilJSF.closeDialog(result);
            /*
                UtilJSF.vaciarMochila();
                UtilJSF.anyadirMochila("proc", data);
                PrimeFaces.current().executeScript(
                    "if (window.parent && window.parent.rcDialogCerrado) window.parent.rcDialogCerrado();"
            );*/
        }
    }

    public void returnDialogMensajes(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
        if (!respuesta.isCanceled()) {
            procedimientoServiceFacade.actualizarMensajes(data.getCodigo(), respuestaFlujo.getMensajes(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor());
        }
        // Sincronizar mensajes para que se actualicen en el padre
        if (respuestaFlujo != null) {
            data.setMensajes(respuestaFlujo.getMensajes());
            data.setPendienteMensajesSupervisor(respuestaFlujo.isPendienteMensajesSupervisor());
            data.setPendienteMensajesGestor(respuestaFlujo.isPendienteMensajesGestor());
        }
    }

    public void guardar() {

        esSoloGuardar = true;
        if (!checkObligatorio()) {
            return;
        }
        guardarSinCheck();
    }

    /**
     * Obtiene el literal a mostrar
     *
     * @return
     */
    public String getEstadoSIA() {
        if (data.getEstadoSIA() == null || data.getEstadoSIA().isEmpty()) {
            return "";
        }

        return getLiteral("dialogProcedimiento.estadoSIA." + data.getEstadoSIA());
    }

    public void accionSin() {
        if (esSoloGuardar) {
            guardarSinCheck();
        } else {
            guardarFlujoSinCheck();
        }
    }

    public void guardarSinCheck() {

        resetearOrdenListas();
        String ruta = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);

        if (this.data.getCodigo() == null) {
            procedimientoServiceFacade.create(this.data, sessionBean.getPerfil(), ruta);
        } else {
            procedimientoServiceFacade.update(this.data, this.dataOriginal, UtilJSF.getSessionBean().getPerfil(), UtilJSF.getSessionBean().getEntidad().getCodigo(), ruta);
        }


        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private void resetearOrdenListas() {
        if (data.getNormativas() != null && !data.getNormativas().isEmpty()) {
            int posicion = 0;
            for (NormativaGridDTO normativaGridDTO : data.getNormativas()) {
                normativaGridDTO.setOrden(posicion);
                posicion++;
            }
        }

        if (data.getDocumentos() != null && !data.getDocumentos().isEmpty()) {
            int posicion = 0;
            for (ProcedimientoDocumentoDTO doc : data.getDocumentos()) {
                doc.setOrden(posicion);
                posicion++;
            }
        }

        if (data.getDocumentosLOPD() != null && !data.getDocumentosLOPD().isEmpty()) {
            int posicion = 0;
            for (ProcedimientoDocumentoDTO doc : data.getDocumentosLOPD()) {
                doc.setOrden(posicion);
                posicion++;
            }
        }

        if (data.getTramites() != null && !data.getTramites().isEmpty()) {
            int posicion = 0;
            for (ProcedimientoTramiteDTO tram : data.getTramites()) {
                if (tram.getPlantillaSel() != null && tram.getPlantillaSel().getCodigo() != null) {
                    //Si hay plantilla, se quita el resto de info
                    tram.setTipoTramitacion(new TipoTramitacionDTO());
                }
                tram.setOrden(posicion);
                posicion++;

                if (tram.getListaDocumentos() != null && !tram.getListaDocumentos().isEmpty()) {
                    int posicionDoc = 0;
                    for (ProcedimientoDocumentoDTO doc : tram.getListaDocumentos()) {
                        doc.setOrden(posicionDoc);
                        posicionDoc++;
                    }
                }

                if (tram.getListaModelos() != null && !tram.getListaModelos().isEmpty()) {
                    int posicionDoc = 0;
                    for (ProcedimientoDocumentoDTO doc : tram.getListaModelos()) {
                        doc.setOrden(posicionDoc);
                        posicionDoc++;
                    }
                }
            }
        }
    }

    private boolean checkObligatorio() {
        boolean todoCorrecto = true;
        if (this.data.getUaInstructor() == null || this.data.getUaInstructor().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaInstructor"));
            todoCorrecto = false;
        }

        /*if (this.data.getUaResponsable() == null || this.data.getUaResponsable().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaResponsable"));
            todoCorrecto = false;
        }*/

        if (this.data.getFechaPublicacion() != null && this.data.getFechaCaducidad() != null && data.getFechaCaducidad().before(this.data.getFechaPublicacion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionCaducidad"));
            todoCorrecto = false;
        }

        if (this.data.getPublicosObjetivo() == null || this.data.getPublicosObjetivo().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.algunPublicoObjetivo"));
            todoCorrecto = false;
        }

        if (this.data.getNormativas() == null || this.data.getNormativas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.algunaNormativa"));
            todoCorrecto = false;
        }

        if (this.data.getTramites() != null) {
            for (ProcedimientoTramiteDTO tramite : this.data.getTramites()) {
                if (data.getFechaPublicacion() != null && tramite.getFechaPublicacion() != null && tramite.getFechaPublicacion().before(data.getFechaPublicacion())) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionProcFechaPublicacion"));
                    todoCorrecto = false;
                }
            }
        }

        return todoCorrecto;
    }

    public boolean isNoEditable() {

        return noEditable;

    }


    public void cerrar() {
        if (this.getModoAcceso() != null && !this.getModoAcceso().equals(TypeModoAcceso.CONSULTA.toString()) && this.data.compareTo(this.dataOriginal, false) != 0) {
            PrimeFaces.current().executeScript("PF('cdSalirSinGuardar').show();");
            return;
        }

        cerrarSinCheck();
    }

    public void cerrarSinCheck() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    //PUBLICO OBJETIVO

    public void returnDialogPubObjEnt(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (respuesta != null && !respuesta.isCanceled()) {
            List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
            if (tipPubObjEntSeleccionadas == null) {
                data.setPublicosObjetivo(new ArrayList<>());
            } else {
//                if (data.getPublicosObjetivo() == null) {
//                    data.setPublicosObjetivo(new ArrayList<>());
//                }
                data.setPublicosObjetivo(new ArrayList<>());
                data.getPublicosObjetivo().addAll(tipPubObjEntSeleccionadas);

                if (data.getPublicosObjetivo() != null && !data.getPublicosObjetivo().isEmpty()) {
                    data.setHabilitadoApoderado(!data.getPublicosObjetivo().get(0).isEmpleadoPublico());
                }
            }
        }
    }

    public void nuevoPubObjEnt() {
        abrirDialogPubObjEnt(TypeModoAcceso.ALTA);
    }

    public void consultarPubObjEnt() {
        if (tipoPubObjEntGridSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogPubObjEnt(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarPubObjEnt() {
        if (tipoPubObjEntGridSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getPublicosObjetivo().remove(tipoPubObjEntGridSeleccionado);
            tipoPubObjEntGridSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void abrirDialogPubObjEnt(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", tipoPubObjEntGridSeleccionado.getCodigo().toString());
            UtilJSF.openDialog("dialogTipoPublicoObjetivoEntidad", modoAcceso, params, true, 700, 300);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("tipoPubObjEntSeleccionadas", data.getPublicosObjetivo());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("dialogSeleccionTipoPublicoObjetivoEntidad", modoAcceso, params, true, 1040, 460);
        }
    }

    //TRAMITE
    public void returnDialogTramite(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            ProcedimientoTramiteDTO procTramite = (ProcedimientoTramiteDTO) respuesta.getResult();

            if (procTramite != null) {
                if (data.getTramites() == null) {
                    data.setTramites(new ArrayList<>());
                }

                data.addtramite(procTramite);
                tramiteSeleccionado = procTramite;
            }
        }
    }

    public void nuevoTramite() {
        abrirDialogTramite(TypeModoAcceso.ALTA);
    }

    public void editarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            TypeModoAcceso modoAcceso = isModoConsulta() ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
            abrirDialogTramite(modoAcceso);
        }
    }

    public void consultarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogTramite(TypeModoAcceso.CONSULTA);
        }
    }


    public void borrarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getTramites().remove(tramiteSeleccionado);
            tramiteSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void bajarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getTramites().indexOf(tramiteSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getTramites().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getTramites().remove(posicion);
                this.data.getTramites().add(posicion + 1, tramiteSeleccionado);
            }
        }
    }

    public void subirTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getTramites().indexOf(tramiteSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getTramites().remove(posicion);
                this.data.getTramites().add(posicion - 1, tramiteSeleccionado);
            }
        }
    }

    public void abrirDialogTramite(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.EDICION.equals(modoAcceso) && tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            UtilJSF.anyadirMochila("fechaPublicacion", data.getFechaPublicacion());
            if (TypeModoAcceso.EDICION.equals(modoAcceso) || TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
                UtilJSF.anyadirMochila("tramiteSel", tramiteSeleccionado.clone());
                if (tramiteSeleccionado.getFase() != FASE_INICIACION && yaExisteFaseIniciacion()) {
                    params.put(TypeParametroVentana.OCULTAR_INICIACION.toString(), "S");
                }
            } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
                if (yaExisteFaseIniciacion()) {
                    params.put(TypeParametroVentana.OCULTAR_INICIACION.toString(), "S");
                }
                //params.put(TypeParametroVentana.ID.toString(), String.valueOf(contadorProcMemoria--));
            }
            UtilJSF.anyadirMochila("uasInstructor", uasInstructor);
            UtilJSF.anyadirMochila("nombreProcedimiento", data.getNombreProcedimientoWorkFlow());
            if (data.getTramites() == null) {
                data.setTramites(new ArrayList<>());
            }
            UtilJSF.anyadirMochila("listaTramites", data.getTramites());
            UtilJSF.anyadirMochila("comun", data.esComun());
            UtilJSF.openDialog("dialogProcedimientoTramite", modoAcceso, params, true, 1000, 600);
        }
    }

    private boolean yaExisteFaseIniciacion() {
        boolean existeFaseIniciacion = false;
        if (this.data.getTramites() != null) {
            for (ProcedimientoTramiteDTO tramite : data.getTramites()) {
                if (tramite.getFase() == FASE_INICIACION) {
                    existeFaseIniciacion = true;
                }
            }
        }
        return existeFaseIniciacion;
    }

    //NORMATIVA
    public void returnDialogNormativa(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<NormativaGridDTO> normativaG = (List<NormativaGridDTO>) respuesta.getResult();

            if (normativaG == null) {
                data.setNormativas(new ArrayList<>());
            } else {
                if (data.getNormativas() == null) {
                    data.setNormativas(new ArrayList<>());
                }
                data.setNormativas(new ArrayList<>());
                data.getNormativas().addAll(normativaG);
            }
        }
    }


    public void abrirDialogNormativa(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", normativaGridSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true, (Integer.parseInt(sessionBean.getScreenWidth()) - 200), (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("normativasSeleccionadas", data.getNormativas());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("tipo/dialogSeleccionNormativa", modoAcceso, params, true, 1200, 750);
        }

    }

    public void nuevaNormativa() {
        abrirDialogNormativa(TypeModoAcceso.ALTA);
    }

    public void consultarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogNormativa(TypeModoAcceso.CONSULTA);
        }
    }

    public void bajarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getNormativas().indexOf(normativaGridSeleccionada);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getNormativas().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getNormativas().remove(posicion);
                this.data.getNormativas().add(posicion + 1, normativaGridSeleccionada);
            }
        }
    }

    public void subirNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getNormativas().indexOf(normativaGridSeleccionada);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getNormativas().remove(posicion);
                this.data.getNormativas().add(posicion - 1, normativaGridSeleccionada);
            }
        }
    }

    public void borrarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getNormativas().remove(normativaGridSeleccionada);
            normativaGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    //CATEGORIA PDU
    public void returnDialogCategoriaPDU(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<CategoriaPDUGridDTO> categoriasPDUs = (List<CategoriaPDUGridDTO>) respuesta.getResult();

            if (categoriasPDUs == null) {
                data.setCategoriasPDU(new ArrayList<>());
            } else {
                if (data.getCategoriasPDU() == null) {
                    data.setCategoriasPDU(new ArrayList<>());
                }
                data.setCategoriasPDU(new ArrayList<>());
                data.getCategoriasPDU().addAll(categoriasPDUs);
            }
        }
    }


    public void abrirDialogCategoriaPDU(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", categoriaPDUGridSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("/entidades/dialogCategoriaPDU", TypeModoAcceso.CONSULTA, params, true, 800, 340);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("categoriasPDUSeleccionadas", data.getCategoriasPDU());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("tipo/dialogSeleccionCategoriaPDU", modoAcceso, params, true, 1200, 750);
        }

    }

    public void nuevaCategoriaPDU() {
        abrirDialogCategoriaPDU(TypeModoAcceso.ALTA);
    }

    public void consultarCategoriaPDU() {
        if (categoriaPDUGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogCategoriaPDU(TypeModoAcceso.CONSULTA);
        }
    }

    public void bajarCategoriaPDU() {
        if (categoriaPDUGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getCategoriasPDU().indexOf(categoriaPDUGridSeleccionada);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getCategoriasPDU().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getCategoriasPDU().remove(posicion);
                this.data.getCategoriasPDU().add(posicion + 1, categoriaPDUGridSeleccionada);
            }
        }
    }

    public void subirCategoriaPDU() {
        if (categoriaPDUGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getCategoriasPDU().indexOf(categoriaPDUGridSeleccionada);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getCategoriasPDU().remove(posicion);
                this.data.getCategoriasPDU().add(posicion - 1, categoriaPDUGridSeleccionada);
            }
        }
    }

    public void borrarCategoriaPDU() {
        if (categoriaPDUGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getCategoriasPDU().remove(categoriaPDUGridSeleccionada);
            categoriaPDUGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    //DOCUMENTO
    public void returnDialogDocumento(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            // Verificamos si se ha modificado
            ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
            if (doc != null) {
                if (data.getDocumentos() == null) {
                    data.setDocumentos(new ArrayList<>());
                }
                data.agregarDocumento(doc);
            }
        }
    }


    public void abrirDialogDocumento(TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigoWF() == null ? "" : data.getCodigoWF().toString());
        if (modoAcceso == TypeModoAcceso.CONSULTA || modoAcceso == TypeModoAcceso.EDICION) {
            UtilJSF.anyadirMochila("documento", this.documentoSeleccionado.clone());
        }
        params.put(TypeParametroVentana.TIPO.toString(), "PROC_DOC");

        UtilJSF.openDialog("dialogDocumentoProcedimiento", modoAcceso, params, true, 800, 380);
    }

    public void nuevoDocumento() {
        abrirDialogDocumento(TypeModoAcceso.ALTA);
    }

    public void editarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumento(TypeModoAcceso.EDICION);
        }
    }

    public void consultarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumento(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getDocumentos().remove(documentoSeleccionado);
            documentoSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void bajarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getDocumentos().indexOf(documentoSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getDocumentos().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getDocumentos().remove(posicion);
                this.data.getDocumentos().add(posicion + 1, documentoSeleccionado);
            }
        }
    }

    public void subirDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getDocumentos().indexOf(documentoSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getDocumentos().remove(posicion);
                this.data.getDocumentos().add(posicion - 1, documentoSeleccionado);
            }
        }
    }

    //DOCUMENTO LOPD
    public void returnDialogDocumentoLOPD(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            // Verificamos si se ha modificado
            ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
            if (doc != null) {
                if (data.getDocumentos() == null) {
                    data.setDocumentos(new ArrayList<>());
                }
                data.agregarDocumentoLOPD(doc);
            }
        }
    }

    public void abrirDialogDocumentoLOPD(TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        params.put("ID", data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (modoAcceso == TypeModoAcceso.CONSULTA || modoAcceso == TypeModoAcceso.EDICION) {
            UtilJSF.anyadirMochila("documento", this.documentoLOPDSeleccionado.clone());
        }
        params.put(TypeParametroVentana.TIPO.toString(), "PROC_DOC");
        UtilJSF.openDialog("dialogDocumentoProcedimientoLOPD", modoAcceso, params, true, 800, 350);
    }

    public void nuevoDocumentoLOPD() {
        abrirDialogDocumentoLOPD(TypeModoAcceso.ALTA);
    }


    public void editarDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumentoLOPD(TypeModoAcceso.EDICION);
        }
    }

    public void consultarDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumentoLOPD(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getDocumentosLOPD().remove(documentoLOPDSeleccionado);
            documentoLOPDSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void bajarDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getDocumentosLOPD().indexOf(documentoLOPDSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getDocumentosLOPD().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getDocumentosLOPD().remove(posicion);
                this.data.getDocumentosLOPD().add(posicion + 1, documentoLOPDSeleccionado);
            }
        }
    }

    public void subirDocumentoLOPD() {
        if (documentoLOPDSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getDocumentosLOPD().indexOf(documentoLOPDSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getDocumentosLOPD().remove(posicion);
                this.data.getDocumentosLOPD().add(posicion - 1, documentoLOPDSeleccionado);
            }
        }
    }

    /********************************************************************************************************************************
     * Funciones relativas a las asignaciones temáticas
     *********************************************************************************************************************************/


    public void abrirSeleccionTematica(TemaGridDTO temaPadre, TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("temaPadre", temaPadre);
        UtilJSF.anyadirMochila("temasRelacionados", new ArrayList<>(data.getTemas()));
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", modoAcceso, params, true, 740, 500);

    }

    public void altaTematicas(TemaGridDTO temaPadre) {
        this.abrirSeleccionTematica(temaPadre, TypeModoAcceso.ALTA);
    }

    /**
     * Método para consultar el detalle de un tema en una UA
     */
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

    /**
     * Método para borrar un tema en una UA
     */
    public void borrarTema() {
        if (temaSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getTemas().remove(temaSeleccionado);
            temaSeleccionado = null;
            construirArbol();
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void returnDialogTemas(final SelectEvent event) {
        DialogResult resultado = (DialogResult) event.getObject();
        if (!resultado.isCanceled()) {
            List<TemaGridDTO> temasSeleccionados = (List<TemaGridDTO>) resultado.getResult();
            TemaGridDTO temaPadre = (TemaGridDTO) UtilJSF.getValorMochilaByKey("temaPadre");
            UtilJSF.vaciarMochila();

            for (TemaGridDTO tema : temasSeleccionados) {
                if (!data.getTemas().contains(tema)) {
                    data.getTemas().add(tema);
                }
            }
            List<TemaGridDTO> temasBorrado = new ArrayList<>();
            for (TemaGridDTO tema : data.getTemas()) {
                if (tema.getMathPath() != null && Arrays.asList(tema.getMathPath().split(";")).contains(temaPadre.getCodigo().toString()) && !temasSeleccionados.contains(tema)) {
                    temasBorrado.add(tema);
                }
            }
            data.getTemas().removeAll(temasBorrado);
            temasPadreAnyadidos.clear();
            construirArbol();
        }

    }

    private void construirArbol() {
        /*LOG.error("Construyendo árbol de temas...");
        if (data.getTemas() == null || data.getTemas().isEmpty()) {
            LOG.error("No hay temas asignados al procedimiento.");
        } else {
            LOG.error("Temas asignados al procedimiento: " + data.getTemas().size());
            for (TemaGridDTO tem : data.getTemas()) {
                LOG.error("Tema asignado: " + tem.toString());
            }
        }
        LOG.error("Roots antes de construir: " + (roots != null ? roots.size() : 0));
        LOG.error("Temas padre añadidos antes de construir: " + temasPadreAnyadidos.size());
        LOG.error("Temas padre antes de construir: " + (temasPadre != null ? temasPadre.size() : 0));
		*/
        roots = new ArrayList<>();
        UtilsArbolTemas.construirArbol(roots, temasPadre, temasPadreAnyadidos, data.getTemas(), temaServiceFacade);
    }

    /**
     * Se muestra el boton si:
     * <ul>
     *     <li>Es adm. contenido</li>
     *     <li>Si es gestor, solo puede editar si está en modificación</li>
     * </ul>
     *
     * @return
     */
    public boolean mostrarBotonGuardar() {
        if (isModoConsulta()) {
            return false;
        }

        if (this.isGestor()) {
            return this.data.getEstado() == TypeProcedimientoEstado.MODIFICACION && !this.noEditable;
        } else {
            return true;
        }
    }

    public List<TipoProcedimientoDTO> getListTipoProcedimiento() {
        return listTipoProcedimiento;
    }

    public void setListTipoProcedimiento(List<TipoProcedimientoDTO> listTipoProcedimiento) {
        this.listTipoProcedimiento = listTipoProcedimiento;
    }

    public NormativaGridDTO getNormativaGridSeleccionada() {
        return normativaGridSeleccionada;
    }

    public void setNormativaGridSeleccionada(NormativaGridDTO normativaGridSeleccionada) {
        this.normativaGridSeleccionada = normativaGridSeleccionada;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoMateriaSIAGridDTO getMateriaSIAGridSeleccionada() {
        return materiaSIAGridSeleccionada;
    }

    public void setMateriaSIAGridSeleccionada(TipoMateriaSIAGridDTO materiaSIAGridSeleccionada) {
        this.materiaSIAGridSeleccionada = materiaSIAGridSeleccionada;
    }

    public String getTextoValor() {
        return textoValor;
    }

    public void setTextoValor(String textoValor) {
        this.textoValor = textoValor;
    }

    public ProcedimientoDTO getData() {
        return data;
    }

    public void setData(ProcedimientoDTO data) {
        this.data = data;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getLegitimacion() {
        return legitimacion;
    }

    public void setLegitimacion(String legitimacion) {
        this.legitimacion = legitimacion;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public String getTipoProcedimientoSeleccionado() {
        return tipoProcedimientoSeleccionado;
    }

    public void setTipoProcedimientoSeleccionado(String tipoProcedimientoSeleccionado) {
        this.tipoProcedimientoSeleccionado = tipoProcedimientoSeleccionado;
    }

    public NormativaDTO getNormativaSeleccionada() {
        return normativaSeleccionada;
    }

    public void setNormativaSeleccionada(NormativaDTO normativaSeleccionada) {
        this.normativaSeleccionada = normativaSeleccionada;
    }

    public ProcedimientoDocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ProcedimientoDocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public ProcedimientoTramiteDTO getTramiteSeleccionado() {
        return tramiteSeleccionado;
    }

    public void setTramiteSeleccionado(ProcedimientoTramiteDTO tramiteSeleccionado) {
        this.tramiteSeleccionado = tramiteSeleccionado;
    }


    public List<TipoFormaInicioDTO> getListTipoFormaInicio() {
        return listTipoFormaInicio;
    }

    public void setListTipoFormaInicio(List<TipoFormaInicioDTO> listTipoFormaInicio) {
        this.listTipoFormaInicio = listTipoFormaInicio;
    }

    public List<TipoSilencioAdministrativoDTO> getListTipoSilencio() {
        return listTipoSilencio;
    }

    public void setListTipoSilencio(List<TipoSilencioAdministrativoDTO> listTipoSilencio) {
        this.listTipoSilencio = listTipoSilencio;
    }

    public List<TipoLegitimacionDTO> getListTipoLegitimacion() {
        return listTipoLegitimacion;
    }

    public void setListTipoLegitimacion(List<TipoLegitimacionDTO> listTipoLegitimacion) {
        this.listTipoLegitimacion = listTipoLegitimacion;
    }

    public TipoPublicoObjetivoEntidadGridDTO getTipoPubObjEntGridSeleccionado() {
        return tipoPubObjEntGridSeleccionado;
    }

    public void setTipoPubObjEntGridSeleccionado(TipoPublicoObjetivoEntidadGridDTO tipoPubObjEntGridSeleccionado) {
        this.tipoPubObjEntGridSeleccionado = tipoPubObjEntGridSeleccionado;
    }

    public ProcedimientoDocumentoDTO getDocumentoLOPDSeleccionado() {
        return documentoLOPDSeleccionado;
    }

    public void setDocumentoLOPDSeleccionado(ProcedimientoDocumentoDTO documentoLOPDSeleccionado) {
        this.documentoLOPDSeleccionado = documentoLOPDSeleccionado;
    }

    public List<TipoViaDTO> getListTipoVia() {
        return listTipoVia;
    }

    public void setListTipoVia(List<TipoViaDTO> listTipoVia) {
        this.listTipoVia = listTipoVia;
    }

    public TreeNode getTemaSeleccionado() {
        return temaSeleccionado;
    }

    public void setTemaSeleccionado(TreeNode temaSeleccionado) {
        this.temaSeleccionado = temaSeleccionado;
    }

    public List<TreeNode> getRoots() {
        return roots;
    }

    public void setRoots(List<TreeNode> roots) {
        this.roots = roots;
    }

    public List<TreeNode> getTemasTabla() {
        return temasTabla;
    }

    public void setTemasTabla(List<TreeNode> temasTabla) {
        this.temasTabla = temasTabla;
    }

    public List<TemaGridDTO> getTemasPadre() {
        return temasPadre;
    }

    public void setTemasPadre(List<TemaGridDTO> temasPadre) {
        this.temasPadre = temasPadre;
    }

    public boolean isEsSoloGuardar() {
        return esSoloGuardar;
    }

    public void setEsSoloGuardar(boolean esSoloGuardar) {
        this.esSoloGuardar = esSoloGuardar;
    }

    public String getUaRaiz() {
        return uaRaiz;
    }

    public void setUaRaiz(String uaRaiz) {
        this.uaRaiz = uaRaiz;
    }

    public Literal getLopdDerechos() {
        return lopdDerechos;
    }

    public void setLopdDerechos(Literal lopdDerechos) {
        this.lopdDerechos = lopdDerechos;
    }

    public Literal getLopdInfoAdicional() {
        return lopdInfoAdicional;
    }

    public void setLopdInfoAdicional(Literal lopdInfoAdicional) {
        this.lopdInfoAdicional = lopdInfoAdicional;
    }

    public boolean isMostrarRefreshSIA() {
        return mostrarRefreshSIA;
    }

    public void setMostrarRefreshSIA(boolean mostrarRefreshSIA) {
        this.mostrarRefreshSIA = mostrarRefreshSIA;
    }

    public Literal getComunUA() {
        return comunUA;
    }

    public void setComunUA(Literal comunUA) {
        this.comunUA = comunUA;
    }

    public Literal getResponsableUA() {
        return responsableUA;
    }

    public void setResponsableUA(Literal responsableUA) {
        this.responsableUA = responsableUA;
    }

    public Literal getLopdResponsable() {
        return lopdResponsable;
    }

    public void setLopdResponsable(Literal lopdResponsable) {
        this.lopdResponsable = lopdResponsable;
    }

    public CategoriaPDUDTO getCategoriaPDUSeleccionada() {
        return categoriaPDUSeleccionada;
    }

    public void setCategoriaPDUSeleccionada(CategoriaPDUDTO categoriaPDUSeleccionada) {
        this.categoriaPDUSeleccionada = categoriaPDUSeleccionada;
    }

    public CategoriaPDUGridDTO getCategoriaPDUGridSeleccionada() {
        return categoriaPDUGridSeleccionada;
    }

    public void setCategoriaPDUGridSeleccionada(CategoriaPDUGridDTO categoriaPDUGridSeleccionada) {
        this.categoriaPDUGridSeleccionada = categoriaPDUGridSeleccionada;
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

    public boolean posiblePdu() {
        return mostrarBtnPDU;
    }

    public String getStyleDivCategoriasPDU() {
        if (data.isIntegrarPdu()) {
            return "";
        } else {
            return "display:none";
        }
    }

    public String getEstadoProcedimiento() {
        return estadoProcedimiento;
    }

    public void setEstadoProcedimiento(String estadoProcedimiento) {
        this.estadoProcedimiento = estadoProcedimiento;
    }

    public boolean isMostrarBtnFlujo() {
        if (this.isGestor()) {
            if (this.data.getComun() == 1) {
                // Si es común, no mostrar botón
                return false;
            }
            if (this.data.getEstado() == TypeProcedimientoEstado.PENDIENTE_PUBLICAR) {
                // En caso de estar pendiente de validación, no mostrar botón
                return false;
            }
            return !this.isNoEditable();
        }
        if (this.isInformador()) {
            return false;
        }
        if (this.isAdministradorContenidos()) {
            return true;
        }
        return false;

    }

    public void setMostrarBtnFlujo(boolean mostrarBtnFlujo) {
        this.mostrarBtnFlujo = mostrarBtnFlujo;
    }

    public boolean isMostrarBtnMensajes() {
        return isMostrarBtnFlujo();
    }

    public Literal getComunLOPD() {
        return comunLOPD;
    }

    public void setComunLOPD(Literal comunLOPD) {
        this.comunLOPD = comunLOPD;
    }
}

