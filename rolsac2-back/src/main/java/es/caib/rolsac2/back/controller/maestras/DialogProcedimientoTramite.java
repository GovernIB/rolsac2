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

    private boolean mostrarSoloIniciacion;
    private List<ProcedimientoTramiteDTO> listaTramites;

    private Integer opcionTelematica = null;

    private Literal comunUA;

    boolean procedimientoComun;

    public void load() {
        this.setearIdioma();

        nombreProcedimiento = (Literal) UtilJSF.getValorMochilaByKey("nombreProcedimiento");
        fechaPublicacion = (Date) UtilJSF.getValorMochilaByKey("fechaPublicacion");
        canalesSeleccionados = new ArrayList<>();
        uasInstructor = (List<Long>) UtilJSF.getValorMochilaByKey("uasInstructor");
        if (ocultarIniciacion != null && "S".equals(ocultarIniciacion)) {
            this.mostrarIniciacion = false;
            this.mostrarSoloIniciacion = false;
        }

        listaTramites = (List<ProcedimientoTramiteDTO>) UtilJSF.getValorMochilaByKey("listaTramites");

        if (listaTramites.isEmpty()) {
            this.mostrarSoloIniciacion = true;
        }

        procedimientoComun = (boolean) UtilJSF.getValorMochilaByKey("comun");
        if (this.isModoEdicion() || this.isModoConsulta()) {

            data = (ProcedimientoTramiteDTO) UtilJSF.getValorMochilaByKey("tramiteSel");

            if (data != null && data.isTramitPresencial()) {
                canalesSeleccionados.add("PRE");
            }
            if (data != null && data.isTramitElectronica()) {
                canalesSeleccionados.add("TEL");
            }
            if (data != null && data.isTramitTelefonica()) {
                canalesSeleccionados.add("TFN");
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
        } else if (this.isModoAlta()) {
            data = ProcedimientoTramiteDTO.createInstance(sessionBean.getIdiomasPermitidosList());
            canalesSeleccionados.add("TEL");
            data.setUnidadAdministrativa(sessionBean.getUnidadActiva());
            data.getUnidadAdministrativa().setEntidad(sessionBean.getEntidad());
            if (mostrarIniciacion) {
                data.setFase(1);
            } else {
                data.setFase(2);
            }
        } else {
            data = ProcedimientoTramiteDTO.createInstance(null);
        }

        platTramitElectronica = platTramitElectronicaServiceFacade.findAll(sessionBean.getEntidad().getCodigo());
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), data.getFase());

        if (this.data.getTipoTramitacion() == null) {
            this.data.setTipoTramitacion(TipoTramitacionDTO.createInstance(sessionBean.getIdiomasPermitidosList()));
            this.data.getTipoTramitacion().setEntidad(UtilJSF.getSessionBean().getEntidad());
        }

        comunUA = sessionBean.getEntidad().getUaComun();
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

    public void cambiaFase() {
        plantillasTipoTramitacion = maestrasSupServiceFacade.findPlantillasTiposTramitacion(sessionBean.getEntidad().getCodigo(), data.getFase());
        data.setPlantillaSel(null);
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

        if (this.data.isTramitPresencial() && (this.data.getListaModelos() == null || this.data.getListaModelos().isEmpty())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.faltaAlgunModelo"));
            return false;
        }

        if (this.data.getUnidadAdministrativa() == null || this.data.getUnidadAdministrativa().getCodigo() == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.faltaUnidadAdministrativa"));
            return false;
        }

        if (data.getPlantillaSel() == null || data.getPlantillaSel().getCodigo() == null) {
            if (this.data.isTramitElectronica() && (this.data.getTipoTramitacion().getUrl() == null || this.data.getTipoTramitacion().getUrl().estaVacio()) && this.data.getTipoTramitacion().getCodPlatTramitacion() == null) {
                UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, getLiteral("dialogProcedimiento.error.faltaUrlPlataforma"));
                return false;
            }
        }

        return true;
    }


    private List<Long> uasInstructor = new ArrayList<>();

    /**
     * Devuelve el css para el boton de la UA Instructor.
     * Si no está en la lista de UA del instructor, se pone en rojo y se muestra el ojo
     *
     * @return
     */
    public String getCssUA() {
        return uasInstructor.contains(data.getUnidadAdministrativa().getCodigo()) ? "" : "pi-exclamation-circle botonNaranjaRequired";
    }

    public void returnDialogoUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            if (uaSeleccionada != null) {
                this.data.setUnidadAdministrativa(uaSeleccionada);
            }
        }
    }

    public void abrirVentanaUA() {
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.MODO_ACCESO.toString(), this.getModoAcceso());
        String direccion = "/comun/dialogSeleccionarUA";

        UtilJSF.anyadirMochila("ua", data.getUnidadAdministrativa());
        //params.put("esCabecera", null);
        UtilJSF.openDialog(direccion, TypeModoAcceso.valueOf(this.getModoAcceso()), params, true, 850, 575);
    }

    public void guardar() {

        this.data.setTramitPresencial(canalesSeleccionados.contains("PRE"));
        this.data.setTramitElectronica(canalesSeleccionados.contains("TEL"));
        this.data.setTramitTelefonica(canalesSeleccionados.contains("TFN"));

        if (!verificarGuardar()) {
            return;
        }

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
            if (respuesta.isAlta()) {
                // Verificamos si se ha modificado
                ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
                if (doc != null) {
                    if (data.getListaDocumentos() == null) {
                        data.setListaDocumentos(new ArrayList<>());
                    }
                    data.agregarDocumento(doc);
                }
            } else if (respuesta.isEdicion()) {
                ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
                if (doc != null) {
                    data.getListaDocumentos().remove(documentoSeleccionado);
                    data.agregarDocumento(doc);
                    documentoSeleccionado = null;
                }
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
            data.getListaDocumentos().remove(documentoSeleccionado);
            documentoSeleccionado = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }


    public void bajarDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getListaDocumentos().indexOf(documentoSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getListaDocumentos().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getListaDocumentos().remove(posicion);
                this.data.getListaDocumentos().add(posicion + 1, documentoSeleccionado);
            }
        }
    }

    public void subirDocumento() {
        if (documentoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getListaDocumentos().indexOf(documentoSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getListaDocumentos().remove(posicion);
                this.data.getListaDocumentos().add(posicion - 1, documentoSeleccionado);
            }
        }
    }

    //MODELO
    public void returnDialogModelo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled()) {
            if (respuesta.isAlta()) {
                // Verificamos si se ha modificado
                ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
                if (doc != null) {
                    if (data.getListaModelos() == null) {
                        data.setListaModelos(new ArrayList<>());
                    }
                    data.agregarModelo(doc);
                }
            } else if (respuesta.isEdicion()) {
                ProcedimientoDocumentoDTO doc = (ProcedimientoDocumentoDTO) respuesta.getResult();
                if (doc != null) {
                    data.getListaModelos().remove(modeloSeleccionado);
                    data.agregarModelo(doc);

                    modeloSeleccionado = null;
                }
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

        UtilJSF.openDialog("dialogDocumentoProcedimiento", modoAcceso, params, true, 800, 380);
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

    public void bajarModelo() {
        if (modeloSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getListaModelos().indexOf(modeloSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion < this.data.getListaModelos().size() - 1) {
                //Mientras no sea el ultimo elemento, se puede bajar
                this.data.getListaModelos().remove(posicion);
                this.data.getListaModelos().add(posicion + 1, modeloSeleccionado);
            }
        }
    }

    public void subirModelo() {
        if (modeloSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            int posicion = this.data.getListaModelos().indexOf(modeloSeleccionado);
            if (posicion == -1) {
                return;
            }

            if (posicion != 0) {
                //Mientras no sea el primer elemento, se puede subir
                this.data.getListaModelos().remove(posicion);
                this.data.getListaModelos().add(posicion - 1, modeloSeleccionado);
            }
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

    public boolean isMostrarSoloIniciacion() {
        return mostrarSoloIniciacion;
    }

    public void setMostrarSoloIniciacion(boolean mostrarSoloIniciacion) {
        this.mostrarSoloIniciacion = mostrarSoloIniciacion;
    }

    public List<ProcedimientoTramiteDTO> getListaTramites() {
        return listaTramites;
    }

    public void setListaTramites(List<ProcedimientoTramiteDTO> listaTramites) {
        this.listaTramites = listaTramites;
    }

    public String getOcultarIniciacion() {
        return ocultarIniciacion;
    }

    public void setOcultarIniciacion(String ocultarIniciacion) {
        this.ocultarIniciacion = ocultarIniciacion;
    }

    public Integer getOpcionTelematica() {
        return opcionTelematica;
    }

    public void setOpcionTelematica(Integer opcionTelematica) {
        this.opcionTelematica = opcionTelematica;
    }

    public Literal getComunUA() {
        return comunUA;
    }

    public void setComunUA(Literal comunUA) {
        this.comunUA = comunUA;
    }

    public void setProcedimientoComun(boolean procedimientoComun) {
        this.procedimientoComun = procedimientoComun;
    }

    public boolean isProcedimientoComun() {
        return procedimientoComun;
    }
}
