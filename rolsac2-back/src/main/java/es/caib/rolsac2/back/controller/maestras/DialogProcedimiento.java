package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.model.RespuestaFlujo;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.facade.SystemServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.*;
import org.primefaces.event.SelectEvent;
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
public class DialogProcedimiento extends AbstractController implements Serializable {


    private ProcedimientoDTO data;

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
    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimiento.class);
    private final Integer FASE_INICIACION = 1;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();

        if (this.isModoAlta()) {
            data = ProcedimientoDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUaInstructor(sessionBean.getUnidadActiva());
            data.setUaResponsable(sessionBean.getUnidadActiva());
            Literal lopdDerechos = new Literal();
            Literal lopdDestinatario = new Literal();
            Literal lopdInfoAdicional = new Literal();
            Literal lopdFinalidad = new Literal();
            for (String idioma : UtilJSF.getSessionBean().getIdiomasPermitidosList()) {
                lopdDerechos.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_DERECHOS, idioma)));
                lopdDestinatario.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_DESTINATARIO, idioma)));
                //lopdInfoAdicional.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_INFO, idioma));
                lopdFinalidad.add(new Traduccion(idioma, systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.LOPD_FINALIDAD, idioma)));
            }
            data.setLopdDerechos(lopdDerechos);
            data.setLopdDestinatario(lopdDestinatario);
            data.setLopdInfoAdicional(lopdInfoAdicional);
            data.setLopdFinalidad(lopdFinalidad);
            data.setLopdResponsable(uaService.obtenerPadreDir3(UtilJSF.getSessionBean().getUnidadActiva().getCodigo(), UtilJSF.getSessionBean().getLang()));
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = (ProcedimientoDTO) UtilJSF.getValorMochilaByKey("PROC");
            //data = procedimientoServiceFacade.findById(Long.valueOf(id));
            UtilJSF.vaciarMochila();
        }

        comunUA = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.UA_COMUN, this.getIdioma());
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
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        ProcedimientoDTO datoDTO = (ProcedimientoDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDatosPersonalesDestinatario(datoDTO.getDatosPersonalesDestinatario());
        }
    }

    public void guardarFlujo() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("mensajes", this.data.getMensajes());
        //params.put("SOLO_MENSAJES","N");
        params.put("ESTADO", data.getEstado().toString());

        if (sessionBean.getPerfil() == TypePerfiles.GESTOR) {

            if (data.getEstado() != TypeProcedimientoEstado.MODIFICACION) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.soloEstadoModificacion"));
                return;
            }

            boolean tiene = false;
            if (data.getTramites() != null) {
                for (ProcedimientoTramiteDTO tramite : data.getTramites()) {
                    if (tramite.getFase() == FASE_INICIACION) {
                        tiene = true;
                        break;
                    }
                }
            }

            if (!tiene) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.obligatorio.flujo.tramiteFaseIniciacion"));
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
            procedimientoServiceFacade.guardarFlujo(data, respuestaFlujo.getEstadoDestino(), respuestaFlujo.getMensajes());
            final DialogResult result = new DialogResult();
            if (this.getModoAcceso() != null) {
                result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
            } else {
                result.setModoAcceso(TypeModoAcceso.CONSULTA);
            }
            data.setEstado(respuestaFlujo.getEstadoDestino());
            data.setMensajes(respuestaFlujo.getMensajes());
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
        if (!checkObligatorio()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            procedimientoServiceFacade.create(this.data);
        } else {
            procedimientoServiceFacade.update(this.data);
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

        return true;
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

    //PUBLICO OBJETIVO

    public void returnDialogPubObjEnt(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
            if (tipPubObjEntSeleccionadas == null) {
                data.setTiposPubObjEntGrid(new ArrayList<>());
            } else {
                if (data.getTiposPubObjEntGrid() == null) {
                    data.setTiposPubObjEntGrid(new ArrayList<>());
                }
                data.setTiposPubObjEntGrid(new ArrayList<>());
                data.getTiposPubObjEntGrid().addAll(tipPubObjEntSeleccionadas);
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
            data.getTiposPubObjEntGrid().remove(tipoPubObjEntGridSeleccionado);
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
            UtilJSF.anyadirMochila("tipoPubObjEntSeleccionadas", data.getTiposPubObjEntGrid());
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
                data.setMateriasGridSIA(new ArrayList<>());
            } else {
                if (data.getMateriasGridSIA() == null) {
                    data.setMateriasGridSIA(new ArrayList<>());
                }
                data.setMateriasGridSIA(new ArrayList<>());
                data.getMateriasGridSIA().addAll(materiasSeleccionadas);
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
            data.getMateriasGridSIA().remove(materiaSIAGridSeleccionada);
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
            UtilJSF.anyadirMochila("materiasSeleccionadas", data.getMateriasGridSIA());
            final Map<String, String> params = new HashMap<>();
            UtilJSF.openDialog("tipo/dialogSeleccionMateriaSIA", modoAcceso, params, true, 1040, 460);
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

    public void borrarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getTramites().remove(tramiteSeleccionado);
            tramiteSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
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
            UtilJSF.anyadirMochila("nombreProcedimiento", data.getNombreProcedimientoWorkFlow());
            UtilJSF.openDialog("dialogProcedimientoTramite", modoAcceso, params, true, 1240, 600);
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

    public String getComunUA() {
        return comunUA;
    }

    public void setComunUA(String comunUA) {
        this.comunUA = comunUA;
    }
}

