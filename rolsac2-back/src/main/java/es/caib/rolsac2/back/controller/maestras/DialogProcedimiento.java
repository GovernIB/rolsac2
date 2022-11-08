package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
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

    private Long contadorProcMemoria = -1L;

    private String destinatarios;

    private String termino;

    private String legitimacion;

    private String validacion;

    private String tipoProcedimientoSeleccionado;


    private TipoMateriaSIAGridDTO materiaSIAGridSeleccionada;

    private NormativaDTO normativaSeleccionada;
    private List<NormativaGridDTO> normativaGrid;

    private NormativaGridDTO normativaGridSeleccionada;

    private ProcedimientoDocumentoDTO documentoSeleccionado;

    private List<TipoFormaInicioDTO> listTipoFormaInicio;
    private List<TipoSilencioAdministrativoDTO> listTipoSilencio;
    private List<TipoLegitimacionDTO> listTipoLegitimacion;

    private List<TipoProcedimientoDTO> listTipoProcedimiento;

    private List<ProcedimientoTramiteDTO> tramites;

    private ProcedimientoTramiteDTO tramiteSeleccionado;
    private TipoPublicoObjetivoEntidadGridDTO tipoPubObjEntGridSeleccionado;


    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    private String id = "";

    private String textoValor;

    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimiento.class);

    public void load() {


        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.
        this.setearIdioma();
        tramites = new ArrayList<>();

        if (this.isModoAlta()) {
            data = ProcedimientoDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUaInstructor(sessionBean.getUnidadActiva());
            data.setUaResponsable(sessionBean.getUnidadActiva());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = procedimientoServiceFacade.findById(Long.valueOf(id));
        }

        listTipoFormaInicio = maestrasSupService.findAllTipoFormaInicio();
        listTipoSilencio = maestrasSupService.findAllTipoSilencio();
        listTipoLegitimacion = maestrasSupService.findAllTipoLegitimacion();
        listTipoProcedimiento = maestrasSupService.findAllTipoProcedimiento(sessionBean.getEntidad().getCodigo());

    }

/*
    public void setIdiomas() {

        if (this.isModoAlta()) {
            idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();

            idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
        } else if (this.isModoEdicion()) {
            if (this.data.getIdiomasPermitidos() == null) {
                idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();
            } else {
                String[] idiomasPermitidosArr = this.data.getIdiomasPermitidos().split(";");
                for (int i = 0; i < idiomasPermitidosArr.length; i++) {
                    idiomasPermitidos.add(idiomasPermitidosArr[i]);
                }
            }

            if (this.data.getIdiomasObligatorios() == null) {
                idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
            } else {
                String[] idiomasObligatoriosArr = this.data.getIdiomasObligatorios().split(";");
                for (int i = 0; i < idiomasObligatoriosArr.length; i++) {
                    idiomasObligatorios.add(idiomasObligatoriosArr[i]);
                }
            }


        }

    }
*/

    public NormativaGridDTO getNormativaGridSeleccionada() {
        return normativaGridSeleccionada;
    }

    public void setNormativaGridSeleccionada(NormativaGridDTO normativaGridSeleccionada) {
        this.normativaGridSeleccionada = normativaGridSeleccionada;
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void guardarFlujo() {

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

    public List<ProcedimientoTramiteDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<ProcedimientoTramiteDTO> tramites) {
        this.tramites = tramites;
    }

    public TipoPublicoObjetivoEntidadGridDTO getTipoPubObjEntGridSeleccionado() {
        return tipoPubObjEntGridSeleccionado;
    }

    public void setTipoPubObjEntGridSeleccionado(TipoPublicoObjetivoEntidadGridDTO tipoPubObjEntGridSeleccionado) {
        this.tipoPubObjEntGridSeleccionado = tipoPubObjEntGridSeleccionado;
    }

    //PUBLICO OBJETIVO

    public void returnDialogPubObjEnt(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        // Verificamos si se ha modificado
        List<TipoPublicoObjetivoEntidadGridDTO> tipPubObjEntSeleccionadas = (List<TipoPublicoObjetivoEntidadGridDTO>) respuesta.getResult();
        if (tipPubObjEntSeleccionadas != null) {
            if (data.getTiposPubObjEntGrid() == null) {
                data.setTiposPubObjEntGrid(new ArrayList<>());
            }
            data.setTiposPubObjEntGrid(new ArrayList<>());
            data.getTiposPubObjEntGrid().addAll(tipPubObjEntSeleccionadas);
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
        // Verificamos si se ha modificado
        List<TipoMateriaSIAGridDTO> materiasSeleccionadas = (List<TipoMateriaSIAGridDTO>) respuesta.getResult();
        if (materiasSeleccionadas != null) {
            if (data.getMateriasGridSIA() == null) {
                data.setMateriasGridSIA(new ArrayList<>());
            }
            data.setMateriasGridSIA(new ArrayList<>());
            data.getMateriasGridSIA().addAll(materiasSeleccionadas);
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

    public void returnDialogTramite(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        // Verificamos si se ha modificado
        ProcedimientoTramiteDTO procTramite = (ProcedimientoTramiteDTO) respuesta.getResult();

        //TODO Cambiar ID negativo en service.
        if (procTramite != null) {
            tramites.add(procTramite);
            data.setTramites(new ArrayList<>());
            data.setTramites(tramites);
        }
    }

    public void nuevoTramite() {
        abrirDialogTramite(TypeModoAcceso.ALTA);
    }

    public void editarTramite() {
        TypeModoAcceso modoAcceso = isModoConsulta() ? TypeModoAcceso.CONSULTA : TypeModoAcceso.EDICION;
        abrirDialogTramite(modoAcceso);
    }

    public void borrarTramite() {
        if (tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            tramites.remove(tramiteSeleccionado);
            tramiteSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void abrirDialogTramite(TypeModoAcceso modoAcceso) {
        if (TypeModoAcceso.EDICION.equals(modoAcceso) && tramiteSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            if (TypeModoAcceso.EDICION.equals(modoAcceso)) {
                UtilJSF.anyadirMochila("tramiteSel", tramiteSeleccionado);
            } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
                params.put("ID", String.valueOf(contadorProcMemoria--));
            }
            UtilJSF.anyadirMochila("nombreProcedimiento", data.getNombreProcedimientoWorkFlow());
            UtilJSF.openDialog("dialogTramite", modoAcceso, params, true, 1240, 600);
        }
    }

    //NORMATIVA

    public void returnDialogNormativa(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        // Verificamos si se ha modificado
        List<NormativaGridDTO> normativaG = (List<NormativaGridDTO>) respuesta.getResult();
        if (normativaG != null) {
            if (data.getNormativasGrid() == null) {
                data.setNormativasGrid(new ArrayList<>());
            }
            //normativas.addAll(normativaG);
            data.setNormativasGrid(new ArrayList<>());
            data.getNormativasGrid().addAll(normativaG);
        }

    }


    public void abrirDialogNormativa(TypeModoAcceso modoAcceso) {
        if (data == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
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
    }

    public void nuevaNormativa() {
        abrirDialogNormativa(TypeModoAcceso.ALTA);
    }

    public void consultarNormativa() {
        abrirDialogNormativa(TypeModoAcceso.CONSULTA);
    }

    public void borrarNormativa() {
        if (normativaGridSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getNormativasGrid().remove(normativaGridSeleccionada);
            normativaGridSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public List<TipoProcedimientoDTO> getListTipoProcedimiento() {
        return listTipoProcedimiento;
    }

    public void setListTipoProcedimiento(List<TipoProcedimientoDTO> listTipoProcedimiento) {
        this.listTipoProcedimiento = listTipoProcedimiento;
    }

    public List<NormativaGridDTO> getNormativaGrid() {
        return normativaGrid;
    }

    public void setNormativaGrid(List<NormativaGridDTO> normativaGrid) {
        this.normativaGrid = normativaGrid;
    }
}
