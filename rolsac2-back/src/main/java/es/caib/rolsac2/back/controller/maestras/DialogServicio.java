package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.comun.UtilsArbolTemas;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypeProcedimientoEstado;
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
public class DialogServicio extends AbstractController implements Serializable {


    private ServicioDTO data;
    private ServicioDTO dataOriginal;

    private String objeto;


    private String destinatarios;

    private String termino;

    private String legitimacion;

    private String validacion;

    private String tipoProcedimientoSeleccionado;


    private TipoMateriaSIAGridDTO materiaSIAGridSeleccionada;

    private NormativaDTO normativaSeleccionada;
    private NormativaGridDTO normativaGridSeleccionada;
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

    @EJB
    private SystemServiceFacade systemServiceFacade;
    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    @EJB
    private UnidadAdministrativaServiceFacade uaService;


    private String id = "";

    private String textoValor;
    private String comunUA;
    private String uaRaiz;

    private static final Logger LOG = LoggerFactory.getLogger(DialogServicio.class);
    private List<PlatTramitElectronicaDTO> platTramitElectronica;
    private List<TipoTramitacionDTO> plantillasTipoTramitacion;
    private List<String> canalesSeleccionados;
    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    private TemaServiceFacade temaServiceFacade;

    private Literal lopdDerechos;
    private Literal lopdDestinatario;
    private Literal lopdInfoAdicional;
    private Literal lopdFinalidad;

    /**
     * Variable booleana para saber si es guardar o flujo
     **/
    boolean esSoloGuardar;
    private Integer opcionTelematica = null;


    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        canalesSeleccionados = new ArrayList<>();
        platTramitElectronica = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), 1);
        temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        temasPadreAnyadidos = new ArrayList<>();

        this.setLopdDerechos(sessionBean.getEntidad().getLopdDerechos());
        this.setLopdDestinatario(sessionBean.getEntidad().getLopdDestinatario());
        this.setLopdInfoAdicional(new Literal());
        this.setLopdFinalidad(sessionBean.getEntidad().getLopdFinalidad());

        if (this.isModoAlta()) {
            data = ServicioDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUaInstructor(sessionBean.getUnidadActiva());
            data.setUaResponsable(sessionBean.getUnidadActiva());
            data.setLopdResponsable(uaService.obtenerPadreDir3(UtilJSF.getSessionBean().getUnidadActiva().getCodigo(), UtilJSF.getSessionBean().getLang()));
            data.setTemas(new ArrayList<>());
            data.setHabilitadoFuncionario("N");
            dataOriginal = (ServicioDTO) data.clone();

        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            if (id != null && !id.isEmpty()) {
                data = procedimientoServiceFacade.findServicioById(Long.valueOf(id));
            } else {
                data = (ServicioDTO) UtilJSF.getValorMochilaByKey("SERV");
            }
            dataOriginal = (ServicioDTO) data.clone();
            if (data.getTipoTramitacion() == null) {
                data.setTipoTramitacion(TipoTramitacionDTO.createInstance(sessionBean.getIdiomasPermitidosList()));
            }

            if (data.isTramitElectronica()) {
                if (data.getPlantillaSel() != null && data.getPlantillaSel().getCodigo() != null) {
                    opcionTelematica = 1;
                } else if (data.getTipoTramitacion() != null && data.getTipoTramitacion().getTramiteId() != null && !data.getTipoTramitacion().getTramiteId().isEmpty()) {
                    opcionTelematica = 2;
                } else if (data.getTipoTramitacion() != null) {// && data.getTipoTramitacion().getUrl() != null && data.getTipoTramitacion().getUrl().estaCompleto(sessionBean.getIdiomasObligatoriosList())) {
                    opcionTelematica = 3;
                }
            }
            UtilJSF.vaciarMochila();
        }

        uaRaiz = Boolean.valueOf(this.data.getUaResponsable() != null && this.data.getUaResponsable().esRaiz()).toString();
        temasTabla = new ArrayList<>();
        for (TemaGridDTO tema : temasPadre) {
            temasTabla.add(new DefaultTreeNode(new TemaGridDTO(), null));
        }
        this.construirArbol();

        if (data != null && data.isTramitPresencial()) {
            canalesSeleccionados.add("PRE");
        }
        if (data != null && data.isTramitElectronica()) {
            canalesSeleccionados.add("TEL");
        }
        if (data != null && data.isTramitTelefonica()) {
            canalesSeleccionados.add("TFN");
        }

        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        data.setUsuarioAuditoria(usuario);
        comunUA = sessionBean.getEntidad().getUaComun().getTraduccion(this.getIdioma());
        listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());
        listTipoVia = maestrasSupService.findAllTipoVia();
        if (this.data.getTipoTramitacion() == null) {
            this.data.setTipoTramitacion(TipoTramitacionDTO.createInstance(sessionBean.getIdiomasPermitidosList()));
            this.data.getTipoTramitacion().setEntidad(UtilJSF.getSessionBean().getEntidad());
        }

        /** Si es alta y hay un tipo legitimacion por defecto, lo setea **/
        if (this.isModoAlta() && listTipoLegitimacion != null && !listTipoLegitimacion.isEmpty()) {
            for (TipoLegitimacionDTO tipoLegitimacion : listTipoLegitimacion) {
                if (tipoLegitimacion.isPorDefecto()) {
                    this.data.setDatosPersonalesLegitimacion(tipoLegitimacion);
                    break;
                }
            }
        }
    }

    public boolean isOpcionTelematicaPlantilla() {
        return canalesSeleccionados.contains("TEL") && this.opcionTelematica != null && this.opcionTelematica.compareTo(1) == 0;
    }

    public boolean isOpcionTelematicaDatos() {
        return canalesSeleccionados.contains("TEL") && this.opcionTelematica != null && this.opcionTelematica.compareTo(2) == 0;
    }

    public boolean isOpcionTelematicaUrl() {
        return canalesSeleccionados.contains("TEL") && this.opcionTelematica != null && this.opcionTelematica.compareTo(3) == 0;
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        String url = "/entidades/dialogTraduccion";
        UtilJSF.openDialog(url, TypeModoAcceso.ALTA, params, true, 800, 500);
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
        ServicioDTO datoDTO = (ServicioDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDatosPersonalesDestinatario(datoDTO.getDatosPersonalesDestinatario());
        }
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


    public boolean esUAResponsableRaiz() {
        return this.data.getUaResponsable() != null && this.data.getUaResponsable().esRaiz();
    }

    public void returnDialogoUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaSeleccionada != null) {
                this.data.setUaResponsable(uaSeleccionada);
                uaRaiz = Boolean.valueOf(uaSeleccionada.esRaiz()).toString();
                if (!uaSeleccionada.esRaiz()) {
                    this.data.setComun(0); //Es raro que lo estuviese como comun pero por si acaso
                }
            }
        }
    }

    public void abrirVentanaUA() {
        final Map<String, String> params = new HashMap<>();
        /*
         * if (this.datoSeleccionado != null && (modoAcceso == TypeModoAcceso.EDICION || modoAcceso ==
         * TypeModoAcceso.CONSULTA)) { params.put(TypeParametroVentana.ID.toString(),
         * this.datoSeleccionado.getId().toString()); }
         */

        params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.getModoAcceso());
        String direccion = "/comun/dialogSeleccionarUA";

        UtilJSF.anyadirMochila("ua", data.getUaResponsable());
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

        if (data.isActivoLOPD() && (data.getDocumentosLOPD() == null || data.getDocumentosLOPD().isEmpty())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.documentosLOPD"));
            return;
        }

        if (data.getTemas() == null || data.getTemas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.sinTemas"));
            return;
        }

        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        params.put("ID", this.data.getCodigo().toString());
        params.put("ESTADO", data.getEstado().toString());
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void verMensajes() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        params.put("SOLO_MENSAJES", "S");
        params.put("ESTADO", data.getEstado().toString());
        params.put("ID", this.data.getCodigo().toString());
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void returnDialogFlujo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
            resetearOrdenListas();
            procedimientoServiceFacade.guardarFlujo(data, respuestaFlujo.getEstadoDestino(), respuestaFlujo.getMensajes(), sessionBean.getPerfil(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor(), UtilJSF.getSessionBean().getEntidad().getCodigo());
            final DialogResult result = new DialogResult();
            if (this.getModoAcceso() != null) {
                result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
            } else {
                result.setModoAcceso(TypeModoAcceso.CONSULTA);
            }
            data.setEstado(respuestaFlujo.getEstadoDestino());
            data.setMensajes(respuestaFlujo.getMensajes());

            this.data.setTramitPresencial(canalesSeleccionados.contains("PRE"));
            this.data.setTramitElectronica(canalesSeleccionados.contains("TEL"));
            this.data.setTramitTelefonica(canalesSeleccionados.contains("TFN"));

            result.setResult(data);
            UtilJSF.closeDialog(result);
        }
    }

    public void returnDialogMensajes(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
            procedimientoServiceFacade.actualizarMensajes(data.getCodigo(), respuestaFlujo.getMensajes(), respuestaFlujo.isPendienteMensajesSupervisor(), respuestaFlujo.isPendienteMensajesGestor());
            data.setMensajes(respuestaFlujo.getMensajes());
        }
    }

    public void guardar() {

        esSoloGuardar = true;
        this.data.setTramitPresencial(canalesSeleccionados.contains("PRE"));
        this.data.setTramitElectronica(canalesSeleccionados.contains("TEL"));
        this.data.setTramitTelefonica(canalesSeleccionados.contains("TFN"));

        if (data.isTramitElectronica()) {
            if (opcionTelematica == 1) {
                //Seleccionamos plantilla
                data.setTipoTramitacion(null);
            } else if (opcionTelematica == 2 && data.getTipoTramitacion() != null) {
                //Introducimos datos
                data.setPlantillaSel(null);
                data.getTipoTramitacion().setUrl(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            } else if (opcionTelematica == 3 && data.getTipoTramitacion() != null) {
                //Introducimos url
                data.setPlantillaSel(null);
                data.getTipoTramitacion().setTramiteId(null);
                data.getTipoTramitacion().setTramiteVersion(null);
                data.getTipoTramitacion().setTramiteParametros(null);
                data.getTipoTramitacion().setCodPlatTramitacion(null);
            }
        }

        if (this.data.getTipoTramitacion() != null) {
            this.data.getTipoTramitacion().setEntidad(sessionBean.getEntidad());
        }

        if (!checkObligatorio()) {
            return;
        }
        guardarSinCheck();
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
        if (this.data.getCodigo() == null) {
            procedimientoServiceFacade.create(this.data, sessionBean.getPerfil());
        } else {
            procedimientoServiceFacade.update(this.data, this.dataOriginal, UtilJSF.getSessionBean().getPerfil(), UtilJSF.getSessionBean().getEntidad().getCodigo());
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

        if (data.getPlantillaSel() != null && data.getPlantillaSel().getCodigo() != null) {
            //Si hay plantilla, se quita el resto de info
            data.setTipoTramitacion(new TipoTramitacionDTO());
        }

    }

    private boolean checkObligatorio() {
        boolean retorno = true;
        if (this.data.getUaInstructor() == null || this.data.getUaInstructor().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaInstructor"));
            retorno = false;
        }

        if (this.data.getUaResponsable() == null || this.data.getUaResponsable().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaResponsable"));
            retorno = false;
        }

        if (this.data.getFechaPublicacion() != null && this.data.getFechaCaducidad() != null && data.getFechaCaducidad().before(this.data.getFechaPublicacion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionCaducidad"));
            retorno = false;
        }

        if (!this.data.isTramitElectronica() && !this.data.isTramitPresencial() && !this.data.isTramitTelefonica()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.algunCanalPresentacion"));
            retorno = false;
        }

        if (this.data.getPublicosObjetivo() == null || this.data.getPublicosObjetivo().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogServicio.error.algunPublicoObjetivo"));
            retorno = false;
        }

        if (this.data.getMateriasSIA() == null || this.data.getMateriasSIA().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogServicio.error.algunaMateriaSIA"));
            retorno = false;
        }

        /*
        if (this.data.getPublicosObjetivo() != null && !this.data.getPublicosObjetivo().isEmpty()) {
            boolean empleadoPublico = this.data.getPublicosObjetivo().get(0).isEmpleadoPublico();
            if ((empleadoPublico && this.data.isHabilitadoApoderado()) || (!empleadoPublico && !this.data.isHabilitadoApoderado())) {
                PrimeFaces.current().executeScript("PF('cdApoderado').show();");
                return retorno;
            }
        }*/

        if (data.getPlantillaSel() == null || data.getPlantillaSel().getCodigo() == null) {
            if (this.data.isTramitElectronica() && (this.data.getTipoTramitacion().getUrl() == null || this.data.getTipoTramitacion().getUrl().estaVacio()) && this.data.getTipoTramitacion().getCodPlatTramitacion() == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.faltaUrlPlataforma"));
                return false;
            }
        }

        return retorno;
    }

    public void cerrar() {
        if (this.getModoAcceso() != null && !this.getModoAcceso().equals(TypeModoAcceso.CONSULTA.toString()) && this.data.compareTo(this.dataOriginal) != 0) {
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
        if (!respuesta.isCanceled()) {
            List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
            if (tipPubObjEntSeleccionadas == null) {
                data.setPublicosObjetivo(new ArrayList<>());
            } else {
                if (data.getPublicosObjetivo() == null) {
                    data.setPublicosObjetivo(new ArrayList<>());
                }
                data.setPublicosObjetivo(new ArrayList<>());
                data.getPublicosObjetivo().addAll(tipPubObjEntSeleccionadas);
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

    //MATERIA SIA

    public void returnDialogMateria(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoMateriaSIAGridDTO> materiasSeleccionadas = (List<TipoMateriaSIAGridDTO>) respuesta.getResult();
            if (materiasSeleccionadas == null) {
                data.setMateriasSIA(new ArrayList<>());
            } else {
                if (data.getMateriasSIA() == null) {
                    data.setMateriasSIA(new ArrayList<>());
                }
                data.setMateriasSIA(new ArrayList<>());
                data.getMateriasSIA().addAll(materiasSeleccionadas);
            }
        }
    }

    public void nuevaMateriaSIA() {
        abrirDialogMateria(TypeModoAcceso.ALTA);
    }

    public void consultarMateriaSIA() {
        if (materiaSIAGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogMateria(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarMateriaSIA() {
        if (materiaSIAGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            // maestrasSupService.deleteTipoMateriaSIA(materiaSIAGridSeleccionada.getCodigo());
            data.getMateriasSIA().remove(materiaSIAGridSeleccionada);
            materiaSIAGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void abrirDialogMateria(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", materiaSIAGridSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("tipo/dialogTipoMateriaSIA", modoAcceso, params, true, 700, 300);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("materiasSeleccionadas", data.getMateriasSIA());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("tipo/dialogSeleccionMateriaSIA", modoAcceso, params, true, 1040, 460);
        }
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
            UtilJSF.openDialog("tipo/dialogSeleccionNormativa", modoAcceso, params, true, 1040, 460);
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

    public void borrarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getNormativas().remove(normativaGridSeleccionada);
            normativaGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
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
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (modoAcceso == TypeModoAcceso.CONSULTA || modoAcceso == TypeModoAcceso.EDICION) {
            UtilJSF.anyadirMochila("documento", this.documentoSeleccionado.clone());
        }
        params.put(TypeParametroVentana.TIPO.toString(), "SERV_DOC");

        UtilJSF.openDialog("dialogDocumentoProcedimiento", modoAcceso, params, true, 800, 350);
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
        params.put(TypeParametroVentana.TIPO.toString(), "SERV_DOC");
        UtilJSF.openDialog("dialogDocumentoProcedimientoLOPD", modoAcceso, params, true, 800, 320);
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


    /********************************************************************************************************************************
     * Funciones relativas a las asignaciones temáticas
     *********************************************************************************************************************************/


    public void abrirSeleccionTematica(TemaGridDTO temaPadre, TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("temaPadre", temaPadre);
        UtilJSF.anyadirMochila("temasRelacionados", new ArrayList<>(data.getTemas()));
        UtilJSF.openDialog("/comun/dialogSeleccionarTemaMultiple", modoAcceso, params, true, 590, 460);

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
                if (Arrays.asList(tema.getMathPath().split(";")).contains(temaPadre.getCodigo().toString()) && !temasSeleccionados.contains(tema)) {
                    temasBorrado.add(tema);
                }
            }
            data.getTemas().removeAll(temasBorrado);
            temasPadreAnyadidos.clear();
            construirArbol();
        }

    }

    private void construirArbol() {
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
            return this.data.getEstado() == TypeProcedimientoEstado.MODIFICACION;
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

    public ServicioDTO getData() {
        return data;
    }

    public void setData(ServicioDTO data) {
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

    public List<PlatTramitElectronicaDTO> getPlatTramitElectronica() {
        return platTramitElectronica;
    }

    public void setPlatTramitElectronica(List<PlatTramitElectronicaDTO> platTramitElectronica) {
        this.platTramitElectronica = platTramitElectronica;
    }

    public List<TipoTramitacionDTO> getPlantillasTipoTramitacion() {
        return plantillasTipoTramitacion;
    }

    public void setPlantillasTipoTramitacion(List<TipoTramitacionDTO> plantillasTipoTramitacion) {
        this.plantillasTipoTramitacion = plantillasTipoTramitacion;
    }

    public List<String> getCanalesSeleccionados() {
        return canalesSeleccionados;
    }

    public void setCanalesSeleccionados(List<String> canalesSeleccionados) {
        this.canalesSeleccionados = canalesSeleccionados;
    }

    public void cambiaTipo() {
        String cambia = "";
    }

    public List<TemaGridDTO> getTemasPadre() {
        return temasPadre;
    }

    public void setTemasPadre(List<TemaGridDTO> temasPadre) {
        this.temasPadre = temasPadre;
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

    public String getComunUA() {
        return comunUA;
    }

    public void setComunUA(String comunUA) {
        this.comunUA = comunUA;
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

    public Literal getLopdDestinatario() {
        return lopdDestinatario;
    }

    public void setLopdDestinatario(Literal lopdDestinatario) {
        this.lopdDestinatario = lopdDestinatario;
    }

    public Literal getLopdInfoAdicional() {
        return lopdInfoAdicional;
    }

    public void setLopdInfoAdicional(Literal lopdInfoAdicional) {
        this.lopdInfoAdicional = lopdInfoAdicional;
    }

    public Literal getLopdFinalidad() {
        return lopdFinalidad;
    }

    public void setLopdFinalidad(Literal lopdFinalidad) {
        this.lopdFinalidad = lopdFinalidad;
    }

    public Integer getOpcionTelematica() {
        return opcionTelematica;
    }

    public void setOpcionTelematica(Integer opcionTelematica) {
        this.opcionTelematica = opcionTelematica;
    }
}

