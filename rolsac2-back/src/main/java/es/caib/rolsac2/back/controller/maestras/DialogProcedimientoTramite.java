package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogProcedimientoTramite extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogProcedimientoTramite.class);

    @EJB
    private ProcedimientoServiceFacade procedimientoServiceFacade;

    @EJB
    private PlatTramitElectronicaServiceFacade platTramitElectronicaServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    private ProcedimientoTramiteDTO data;

    private String id = "";

    private Literal nombreProcedimiento;

    private List<String> idiomasPermitidos = new ArrayList<>();

    private List<String> idiomasObligatorios = new ArrayList<>();

    private List<PlatTramitElectronicaDTO> platTramitElectronica;

    private List<String> canalesSeleccionados;

    private List<TipoTramitacionDTO> plantillasTipoTramitacion;

    private TipoTramitacionDTO canalPresentacion;

    private ProcedimientoDocumentoDTO documentoSeleccionado;
    private ProcedimientoDocumentoDTO modeloSeleccionado;

    private boolean mostrarIniciacion = true;
    private String ocultarIniciacion;
    private Date fechaPublicacion;

    public void load() {
        this.setearIdioma();

        nombreProcedimiento = (Literal) UtilJSF.getValorMochilaByKey("nombreProcedimiento");
        fechaPublicacion = (Date) UtilJSF.getValorMochilaByKey("fechaPublicacion");
        canalesSeleccionados = new ArrayList<>();

        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = (ProcedimientoTramiteDTO) UtilJSF.getValorMochilaByKey("tramiteSel");

            if (data != null && data.getTipoTramitacion() != null && data.isTramitPresencial()) {
                canalesSeleccionados.add("PRE");
            }
            if (data != null && data.getTipoTramitacion() != null && data.isTramitElectronica()) {
                canalesSeleccionados.add("TEL");
            }
            if (data != null && data.getTipoTramitacion() != null && data.isTramitTelefonica()) {
                canalesSeleccionados.add("TFN");
            }

        } else if (this.isModoAlta()) {
            data = ProcedimientoTramiteDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            data.setUnidadAdministrativa(sessionBean.getUnidadActiva());
            data.getUnidadAdministrativa().setEntidad(sessionBean.getEntidad());
        } else {
            data = ProcedimientoTramiteDTO.createInstance(null);
        }

        platTramitElectronica = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo());

        if (this.data.getTipoTramitacion() == null) {
            this.data.setTipoTramitacion(TipoTramitacionDTO.createInstance(sessionBean.getIdiomasPermitidosList()));
            this.data.getTipoTramitacion().setEntidad(UtilJSF.getSessionBean().getEntidad());
        }

        if (ocultarIniciacion != null && "S".equals(ocultarIniciacion)) {
            this.mostrarIniciacion = false;
        }
    }

    private boolean verificarGuardar() {
        if (this.data.getFechaPublicacion() != null && this.data.getFechaInicio() != null && data.getFechaInicio().before(this.data.getFechaPublicacion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionInicio"));
            return false;
        }

        if (this.data.getFechaPublicacion() != null && this.data.getFechaCierre() != null && data.getFechaCierre().before(this.data.getFechaPublicacion())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionCierre"));
            return false;
        }

        if (this.data.getFechaInicio() != null && this.data.getFechaCierre() != null && data.getFechaCierre().before(this.data.getFechaInicio())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaInicioCierre"));
            return false;
        }

        if (fechaPublicacion != null && this.data.getFechaPublicacion() != null && this.data.getFechaPublicacion().before(fechaPublicacion)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionProcFechaPublicacion"));
            return false;
        }

        if (fechaPublicacion != null && this.data.getFechaCierre() != null && this.data.getFechaCierre().before(fechaPublicacion)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionProcFechaCierre"));
            return false;
        }

        if (fechaPublicacion != null && this.data.getFechaInicio() != null && this.data.getFechaInicio().before(fechaPublicacion)) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.fechas.fechaPublicacionProcFechaInicio"));
            return false;
        }

        if (!this.data.isTramitElectronica() && !this.data.isTramitPresencial()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.algunCanalPresentacion"));
            return false;
        }
        return true;
    }

    public void guardar() {

        this.data.setTramitPresencial(canalesSeleccionados.contains("PRE"));
        this.data.setTramitElectronica(canalesSeleccionados.contains("TEL"));
        this.data.setTramitTelefonica(canalesSeleccionados.contains("TFN"));

        if (!verificarGuardar()) {
            return;
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

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();
        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        ProcedimientoTramiteDTO datoDTO = (ProcedimientoTramiteDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setDocumentacion(datoDTO.getDocumentacion());
            data.setRequisitos(datoDTO.getRequisitos());
            data.setNombre(datoDTO.getNombre());
            data.setObservacion(datoDTO.getObservacion());
            data.setTerminoMaximo(datoDTO.getTerminoMaximo());
        }
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

    //DOCUMENTO
    public void returnDialogDocumento(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            // Verificamos si se ha modificado
            ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
            if (doc != null) {
                if (data.getListaDocumentos() == null) {
                    data.setListaDocumentos(new ArrayList<>());
                }
                data.agregarDocumento(doc);
            }
        }
    }


    public void abrirDialogDocumento(TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (modoAcceso == TypeModoAcceso.CONSULTA || modoAcceso == TypeModoAcceso.EDICION) {
            UtilJSF.anyadirMochila("documento", this.documentoSeleccionado);
        }
        params.put(TypeParametroVentana.TIPO.toString(), "TRAM_DOC");
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
            data.getListaDocumentos().remove(documentoSeleccionado);
            documentoSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    //DOCUMENTO
    public void returnDialogModelo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            // Verificamos si se ha modificado
            ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
            if (doc != null) {
                if (data.getListaModelos() == null) {
                    data.setListaModelos(new ArrayList<>());
                }
                data.agregarModelo(doc);
            }
        }
    }


    public void abrirDialogModelo(TypeModoAcceso modoAcceso) {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (modoAcceso == TypeModoAcceso.CONSULTA || modoAcceso == TypeModoAcceso.EDICION) {
            UtilJSF.anyadirMochila("documento", this.modeloSeleccionado);
        }
        params.put(TypeParametroVentana.TIPO.toString(), "TRAM_MOD");

        UtilJSF.openDialog("dialogDocumentoProcedimiento", modoAcceso, params, true,
                800, 350);
    }

    public void nuevoModelo() {
        abrirDialogModelo(TypeModoAcceso.ALTA);
    }

    public void editarModelo() {
        if (modeloSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogModelo(TypeModoAcceso.EDICION);
        }
    }

    public void consultarModelo() {
        if (modeloSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogModelo(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarModelo() {
        if (modeloSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            data.getListaModelos().remove(modeloSeleccionado);
            modeloSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProcedimientoTramiteDTO getData() {
        return data;
    }

    public void setData(ProcedimientoTramiteDTO data) {
        this.data = data;
    }

    public void setIdiomas() {
        idiomasPermitidos = sessionBean.getIdiomasObligatoriosList();
        idiomasObligatorios = sessionBean.getIdiomasObligatoriosList();
    }

    public List<String> getIdiomasPermitidos() {
        return idiomasPermitidos;
    }

    public void setIdiomasPermitidos(List<String> idiomasPermitidos) {
        this.idiomasPermitidos = idiomasPermitidos;
    }

    public List<String> getIdiomasObligatorios() {
        return idiomasObligatorios;
    }

    public void setIdiomasObligatorios(List<String> idiomasObligatorios) {
        this.idiomasObligatorios = idiomasObligatorios;
    }

    public List<PlatTramitElectronicaDTO> getPlatTramitElectronica() {
        return platTramitElectronica;
    }

    public void setPlatTramitElectronica(List<PlatTramitElectronicaDTO> platTramitElectronica) {
        this.platTramitElectronica = platTramitElectronica;
    }

    public List<String> getCanalesSeleccionados() {
        return canalesSeleccionados;
    }

    public void setCanalesSeleccionados(List<String> canalesSeleccionados) {
        this.canalesSeleccionados = canalesSeleccionados;
    }

    public Literal getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(Literal nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public List<TipoTramitacionDTO> getPlantillasTipoTramitacion() {
        return plantillasTipoTramitacion;
    }

    public void setPlantillasTipoTramitacion(List<TipoTramitacionDTO> plantillasTipoTramitacion) {
        this.plantillasTipoTramitacion = plantillasTipoTramitacion;
    }


    public TipoTramitacionDTO getCanalPresentacion() {
        return canalPresentacion;
    }

    public void setCanalPresentacion(TipoTramitacionDTO canalPresentacion) {
        this.canalPresentacion = canalPresentacion;
    }

    public void cambiaTipo() {

        /*
        if (canalesSeleccionados != null && !canalesSeleccionados.isEmpty()) {

            if (data.getPlantillaSel() == null && canalPresentacion == null) {
                canalPresentacion = new TipoTramitacionDTO();
            }

            if (canalesSeleccionados.stream().noneMatch(c -> "TEL".equals(c))) {
                data.setPlantillaSel(null);
            } else {
                if (data.getPlantillaSel() != null) {
                    //canalPresentacion = new TipoTramitacionDTO(plantillaSel);
                }
            }
            //canalPresentacion.setTramitPresencial(canalesSeleccionados.contains("PRE"));
            //canalPresentacion.setTramitElectronica(canalesSeleccionados.contains("TEL"));

            // data.getTipoTramitacion().setTramitTelefonico(Arrays.asList(canalesSeleccionados).contains("TFN"));
        } else {
            canalPresentacion = null;
            data.setPlantillaSel(null);
        } */
    }

    public ProcedimientoDocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ProcedimientoDocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public ProcedimientoDocumentoDTO getModeloSeleccionado() {
        return modeloSeleccionado;
    }

    public void setModeloSeleccionado(ProcedimientoDocumentoDTO modeloSeleccionado) {
        this.modeloSeleccionado = modeloSeleccionado;
    }

    public boolean isMostrarIniciacion() {
        return mostrarIniciacion;
    }

    public void setMostrarIniciacion(boolean mostrarIniciacion) {
        this.mostrarIniciacion = mostrarIniciacion;
    }

    public String getOcultarIniciacion() {
        return ocultarIniciacion;
    }

    public void setOcultarIniciacion(String ocultarIniciacion) {
        this.ocultarIniciacion = ocultarIniciacion;
    }
}
