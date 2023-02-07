package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.comun.UtilsArbolTemas;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Variable booleana para saber si es guardar o flujo
     **/
    boolean esSoloGuardar;


    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        canalesSeleccionados = new ArrayList<>();
        platTramitElectronica = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo());
        temasPadre = temaServiceFacade.getGridRoot(sessionBean.getLang(), sessionBean.getEntidad().getCodigo());
        temasPadreAnyadidos = new ArrayList<>();


        if (this.isModoAlta()) {
            data = ServicioDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUaInstructor(sessionBean.getUnidadActiva());
            data.setUaResponsable(sessionBean.getUnidadActiva());
            data.setLopdDerechos(sessionBean.getEntidad().getLopdDerechos());
            data.setLopdDestinatario(sessionBean.getEntidad().getLopdDestinatario());
            data.setLopdInfoAdicional(new Literal());
            data.setLopdFinalidad(sessionBean.getEntidad().getLopdFinalidad());
            data.setLopdResponsable(uaService.obtenerPadreDir3(UtilJSF.getSessionBean().getUnidadActiva().getCodigo(), UtilJSF.getSessionBean().getLang()));
            data.setTemas(new ArrayList<>());

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
            UtilJSF.vaciarMochila();
        }

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
                PrimeFaces.current().ajax().update("formDialog:selectComun");
                PrimeFaces.current().ajax().update("selectComun");
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
        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        //params.put("SOLO_MENSAJES","N");
        params.put("ESTADO", data.getEstado().toString());

        if (sessionBean.getPerfil() == TypePerfiles.GESTOR) {

            if (data.getEstado() != TypeProcedimientoEstado.MODIFICACION) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.soloEstadoModificacion"));
                return;
            }

            if (data.getDocumentosLOPD() == null || data.getDocumentosLOPD().isEmpty()) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.documentosLOPD"));
                return;
            }


        }
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void verMensajes() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        params.put("SOLO_MENSAJES", "S");
        params.put("ESTADO", data.getEstado().toString());
        UtilJSF.openDialog("dialogProcedimientoFlujo", TypeModoAcceso.EDICION, params, true, 830, 500);
    }

    public void returnDialogFlujo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            RespuestaFlujo respuestaFlujo = (RespuestaFlujo) respuesta.getResult();
            resetearOrdenListas();
            procedimientoServiceFacade.guardarFlujo(data, respuestaFlujo.getEstadoDestino(), respuestaFlujo.getMensajes(), sessionBean.getPerfil());
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
            procedimientoServiceFacade.actualizarMensajes(data.getCodigo(), respuestaFlujo.getMensajes());
            data.setMensajes(respuestaFlujo.getMensajes());
        }
    }

    public void guardar() {

        esSoloGuardar = true;
        this.data.setTramitPresencial(canalesSeleccionados.contains("PRE"));
        this.data.setTramitElectronica(canalesSeleccionados.contains("TEL"));
        this.data.setTramitTelefonica(canalesSeleccionados.contains("TFN"));


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
            procedimientoServiceFacade.create(this.data);
        } else {
            procedimientoServiceFacade.update(this.data, this.dataOriginal, UtilJSF.getSessionBean().getPerfil());
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
        if (this.data.getUaInstructor() == null || this.data.getUaInstructor().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaInstructor"));
            return false;
        }

        if (this.data.getUaResponsable() == null || this.data.getUaResponsable().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.uaResponsable"));
            return false;
        }

        if (this.data.getFechaPublicacion() != null && this.data.getFechaCaducidad() != null && data.getFechaCaducidad().before(this.data.getFechaPublicacion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionCaducidad"));
            return false;
        }

        if (!this.data.isTramitElectronica() && !this.data.isTramitPresencial() && !this.data.isTramitTelefonica()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.algunCanalPresentacion"));
            return false;
        }

        if (this.data.getPublicosObjetivo() == null || this.data.getPublicosObjetivo().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogServicio.error.algunPublicoObjetivo"));
            return false;
        }

        if (this.data.getMateriasSIA() == null || this.data.getMateriasSIA().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogServicio.error.algunaMateriaSIA"));
            return false;
        }

        if (this.data.getNormativas() == null || this.data.getNormativas().isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogServicio.error.algunaNormativa"));
            return false;
        }

        return true;
    }

    public void cerrar() {
        if (this.getModoAcceso() != null && !this.getModoAcceso().equals(TypeModoAcceso.CONSULTA.toString()) && (this.data.getCodigoWF() == null || this.data.compareTo(this.dataOriginal) != 0)) {
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
            UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true,
                    (Integer.parseInt(sessionBean.getScreenWidth()) - 200),
                    (Integer.parseInt(sessionBean.getScreenHeight()) - 150));
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
        params.put(TypeParametroVentana.TIPO.toString(), "PROC_DOC");

        UtilJSF.openDialog("dialogDocumentoProcedimiento", modoAcceso, params, true,
                800, 350);
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
        UtilJSF.openDialog("dialogDocumentoProcedimientoLOPD", modoAcceso, params, true,
                800, 320);
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
            UtilJSF.openDialog("dialogTema", TypeModoAcceso.CONSULTA, params, true, 700, 300);
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
                if (tema.getMathPath().contains(temaPadre.getCodigo().toString()) && !temasSeleccionados.contains(tema)) {
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
}

