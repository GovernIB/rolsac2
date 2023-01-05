package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.PrimeFaces;
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
public class DialogNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogNormativa.class);

    private String id = "";

    private NormativaDTO data;

    private List<AfectacionDTO> afectacion;

    private List<TipoNormativaDTO> tipoNormativa;

    private List<TipoBoletinDTO> tipoBoletin;

    private List<ProcedimientoNormativaDTO> procedimientosRelacionados;

    private List<String> documentosRelacionados;

    private List<UnidadAdministrativaDTO> uaRelacionadas;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    private DocumentoNormativaDTO documentoRelacionadoSeleccionado;

    private UnidadAdministrativaGridDTO uaSeleccionada;

    private ProcedimientoNormativaDTO procSeleccionado;

    private AfectacionDTO afectacionSeleccionada;


    public void load() {
        LOG.debug("init");
        this.setearIdioma();

        tipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
        tipoBoletin = maestrasSupServiceFacade.findBoletines();
        boolean isTraspaso = false;
        String traspaso = (String) UtilJSF.getDialogParam("isTraspaso");
        if (traspaso != null) {
            isTraspaso = traspaso.equals("true");
        }

        if (isTraspaso) {
            data = (NormativaDTO) UtilJSF.getValorMochilaByKey("normativaBOIB");
        } else {
            if (this.isModoAlta()) {
                data = new NormativaDTO();
                data.setEntidad(sessionBean.getEntidad());
                data.setNombre(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
                data.setUrlBoletin(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
                List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
                UnidadAdministrativaGridDTO uaActiva = unidadAdministrativaServiceFacade.findById(sessionBean.getUnidadActiva().getCodigo()).convertDTOtoGridDTO();
                unidadesAdministrativas.add(uaActiva);
                data.setUnidadesAdministrativas(unidadesAdministrativas);
            } else if (this.isModoEdicion() || this.isModoConsulta()) {
                data = normativaServiceFacade.findById(Long.valueOf(id));
                findProcedimientosRelacionados();
                data.setAfectaciones(findAfectaciones());
                if (data.getDocumentosNormativa() != null) {
                    for (DocumentoNormativaDTO documentoNormativaDTO : data.getDocumentosNormativa()) {
                        documentoNormativaDTO.setCodigoTabla(UUID.randomUUID().toString());
                    }
                }
                if (data.getAfectaciones() != null) {
                    for (AfectacionDTO afectacionDTO : data.getAfectaciones()) {
                        afectacionDTO.setCodigoTabla(UUID.randomUUID().toString());
                    }
                }
            }
        }

        UtilJSF.vaciarMochila();

    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }
        if (this.data.getCodigo() == null) {
            normativaServiceFacade.create(this.data);
        } else {
            normativaServiceFacade.update(this.data);
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public boolean verificarGuardar() {

        if (Objects.nonNull(this.data.getNumero()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
            return false;
        }

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getNombre(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogNormativa.titulo", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
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

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("/entidades/dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        NormativaDTO datoDTO = (NormativaDTO) respuesta.getResult();

        if (datoDTO != null) {
            data.setNombre(datoDTO.getNombre());
            data.setUrlBoletin(datoDTO.getUrlBoletin());
        }
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Documentos Relacionados
     *********************************************************************************************************************************/

    public void abrirDialogDocumento(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (this.isModoAlta() && !modoAcceso.equals(TypeModoAcceso.ALTA)) {
            UtilJSF.anyadirMochila("documentoNormativa", documentoRelacionadoSeleccionado);
        }
        if (!this.isModoAlta() && !modoAcceso.equals(TypeModoAcceso.ALTA)) {
            params.put("idDocumento", this.documentoRelacionadoSeleccionado.getCodigo().toString());
        }
        params.put("modoAccesoNormativa", this.getModoAcceso());
        UtilJSF.openDialog("dialogDocumentoNormativa", modoAcceso, params, true, 750, 450);
    }

    public void returnDialogoDocumento(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        //Si se da de alta la normativa, creamos el listado de documentos
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso()) && isModoAlta()) {
            DocumentoNormativaDTO documento = (DocumentoNormativaDTO) respuesta.getResult();
            if (this.data.getDocumentosNormativa() == null) {
                this.data.setDocumentosNormativa(new ArrayList<>());
            }
            if (this.data.getDocumentosNormativa().contains(documento)) {
                UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
            } else {
                this.data.getDocumentosNormativa().add(documento);
            }
        } else if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscarDocumentos();
        }
    }

    public void abrirDocumentoRelacionado() {
        abrirDialogDocumento(TypeModoAcceso.ALTA);
    }

    public void editarDocumentoRelacionado() {
        if (documentoRelacionadoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumento(TypeModoAcceso.EDICION);
        }
    }

    public void consultarDocumentoRelacionado() {
        if (documentoRelacionadoSeleccionado != null) {
            abrirDialogDocumento(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarDocumentoRelacionado() {
        if (documentoRelacionadoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else if (this.isModoAlta()) {
            this.data.getDocumentosNormativa().remove(documentoRelacionadoSeleccionado);
        } else {
            normativaServiceFacade.deleteDocumentoNormativa(documentoRelacionadoSeleccionado.getCodigo());
            buscarDocumentos();
        }
    }

    public void buscarDocumentos() {
        List<DocumentoNormativaDTO> docs = normativaServiceFacade.findDocumentosNormativa(this.data.getCodigo());
        if (docs != null) {
            for (DocumentoNormativaDTO documentoNormativaDTO : docs) {
                documentoNormativaDTO.setCodigoTabla(UUID.randomUUID().toString());
            }
        }
        this.data.setDocumentosNormativa(docs);
        PrimeFaces.current().ajax().update("formDialog:dataDocumentosRelacionados");
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de UAs
     *********************************************************************************************************************************/

    /**
     * Abrir dialogo de Selección de Unidades Administrativas
     */
    public void abrirDialogUAs(TypeModoAcceso modoAcceso) {

        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", uaSeleccionada.getCodigo().toString());
            UtilJSF.openDialog("/superadministrador/dialogUnidadAdministrativa", modoAcceso, params, true, 1530, 733);
        } else if (TypeModoAcceso.ALTA.equals(modoAcceso)) {
            UtilJSF.anyadirMochila("unidadesAdministrativas", data.getUnidadesAdministrativas());
            final Map<String, String> params = new HashMap<>();
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.ALTA.toString());
            //params.put("esCabecera", "true");
            String direccion = "/comun/dialogSeleccionarUA";
            UtilJSF.openDialog(direccion, modoAcceso, params, true, 850, 575);
        }
    }

    public void returnDialogoUAs(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO uaSeleccionada = (UnidadAdministrativaDTO) respuesta.getResult();
            uaSeleccionada = unidadAdministrativaServiceFacade.findById(uaSeleccionada.getCodigo());
            if (uaSeleccionada != null) {
                UnidadAdministrativaGridDTO uaSeleccionadaGrid = uaSeleccionada.convertDTOtoGridDTO();
                //verificamos qeu la UA no esté seleccionada ya, en caso de estarlo mostramos mensaje
                if (this.data.getUnidadesAdministrativas() == null) {
                    this.data.setUnidadesAdministrativas(new ArrayList<>());
                }
                if (this.data.getUnidadesAdministrativas().contains(uaSeleccionadaGrid)) {
                    UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.elementoRepetido"));
                } else {
                    this.data.getUnidadesAdministrativas().add(uaSeleccionadaGrid);
                }
            }
        }
    }

    /**
     * Método para dar de alta UAs en un usuario
     */
    public void anyadirUAs() {
        abrirDialogUAs(TypeModoAcceso.ALTA);
    }

    /**
     * Método para consultar el detalle de una UA
     */
    public void consultarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogUAs(TypeModoAcceso.CONSULTA);
        }
    }

    /**
     * Método para borrar un usuario en una UA
     */
    public void borrarUA() {
        if (uaSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else if (uaSeleccionada.getCodigo() == sessionBean.getUnidadActiva().getCodigo()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("ç"));
        } else {

            data.getUnidadesAdministrativas().remove(uaSeleccionada);
            uaSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Procedimientos
     *********************************************************************************************************************************/

    private void findProcedimientosRelacionados() {
        procedimientosRelacionados = normativaServiceFacade.listarProcedimientosByNormativa(this.data.getCodigo());
    }

    private List<AfectacionDTO> findAfectaciones() {
        return normativaServiceFacade.findAfectacionesByNormativa(this.data.getCodigo());
    }

    public void consultarProcs() {
        if (procSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", procSeleccionado.getCodigo().toString());
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
            Integer ancho = sessionBean.getScreenWidthInt();
            if (ancho == null) {
                ancho = 1433;
            }
            UtilJSF.openDialog("dialogProcedimiento", TypeModoAcceso.CONSULTA, params, true, ancho, 733);
        }
    }


    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Afectaciones
     *********************************************************************************************************************************/

    public void abrirDialogAfectacion(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        if (TypeModoAcceso.CONSULTA.equals(modoAcceso)) {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", afectacionSeleccionada.getNormativaOrigen().getCodigo().toString());
            UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true, 1530, 733);
            return;
        }
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if (this.data.getAfectaciones() != null) {
            UtilJSF.anyadirMochila("afectacionesNormativa", this.data.getAfectaciones());
        }
        params.put("modoAccesoNormativa", this.getModoAcceso());
        UtilJSF.openDialog("dialogSeleccionNormativaAfectacion", modoAcceso, params, true, 1000, 733);
    }

    public void returnDialogoAfectacion(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        //Si se da de alta la normativa, creamos el listado de documentos
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso()) && isModoAlta()) {
            List<AfectacionDTO> afectaciones = (List<AfectacionDTO>) respuesta.getResult();
            if (afectaciones == null) {
                data.setAfectaciones(new ArrayList<>());
            } else {
                if (data.getAfectaciones() == null) {
                    data.setAfectaciones(new ArrayList<>());
                }
                data.setAfectaciones(new ArrayList<>());
                data.getAfectaciones().addAll(afectaciones);
            }
        } else if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscarAfectaciones();
        }
    }

    public void abrirAfectacion() {
        abrirDialogAfectacion(TypeModoAcceso.ALTA);
    }

    public void consultarAfectacion() {
        if (afectacionSeleccionada != null) {
            abrirDialogAfectacion(TypeModoAcceso.CONSULTA);
        } else {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        }
    }

    public void borrarAfectacion() {
        if (afectacionSeleccionada == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else if (this.isModoAlta()) {
            this.data.getAfectaciones().remove(afectacionSeleccionada);
            afectacionSeleccionada = null;
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        } else {
            normativaServiceFacade.deleteAfectacion(afectacionSeleccionada.getCodigo());
            afectacionSeleccionada = null;
            buscarAfectaciones();
            addGlobalMessage(getLiteral("msg.eliminaciocorrecta"));
        }
    }

    public void buscarAfectaciones() {
        List<AfectacionDTO> afectaciones = normativaServiceFacade.findAfectacionesByNormativa(this.data.getCodigo());
        if (afectaciones != null) {
            for (AfectacionDTO afectacionDTO : afectaciones) {
                afectacionDTO.setCodigoTabla(UUID.randomUUID().toString());
            }
        }
        this.data.setAfectaciones(afectaciones);
        PrimeFaces.current().ajax().update("formDialog:dataDocumentosRelacionados");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NormativaDTO getData() {
        return data;
    }

    public void setData(NormativaDTO data) {
        this.data = data;
    }

    public List<AfectacionDTO> getAfectacion() {
        return afectacion;
    }

    public void setAfectacion(List<AfectacionDTO> afectacion) {
        this.afectacion = afectacion;
    }

    public List<TipoNormativaDTO> getTipoNormativa() {
        return tipoNormativa;
    }

    public void setTipoNormativa(List<TipoNormativaDTO> tipoNormativa) {
        this.tipoNormativa = tipoNormativa;
    }

    public List<TipoBoletinDTO> getTipoBoletin() {
        return tipoBoletin;
    }

    public void setTipoBoletin(List<TipoBoletinDTO> tipoBoletin) {
        this.tipoBoletin = tipoBoletin;
    }

    public List<ProcedimientoNormativaDTO> getProcedimientosRelacionados() {
        return procedimientosRelacionados;
    }

    public void setProcedimientosRelacionados(List<ProcedimientoNormativaDTO> procedimientosRelacionados) {
        this.procedimientosRelacionados = procedimientosRelacionados;
    }

    public List<String> getDocumentosRelacionados() {
        return documentosRelacionados;
    }

    public void setDocumentosRelacionados(List<String> documentosRelacionados) {
        this.documentosRelacionados = documentosRelacionados;
    }

    public List<UnidadAdministrativaDTO> getUaRelacionadas() {
        return uaRelacionadas;
    }

    public void setUaRelacionadas(List<UnidadAdministrativaDTO> uaRelacionadas) {
        this.uaRelacionadas = uaRelacionadas;
    }

    public DocumentoNormativaDTO getDocumentoRelacionadoSeleccionado() {
        return documentoRelacionadoSeleccionado;
    }

    public void setDocumentoRelacionadoSeleccionado(DocumentoNormativaDTO documentoRelacionadoSeleccionado) {
        this.documentoRelacionadoSeleccionado = documentoRelacionadoSeleccionado;
    }

    public UnidadAdministrativaGridDTO getUaSeleccionada() {
        return uaSeleccionada;
    }

    public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) {
        this.uaSeleccionada = uaSeleccionada;
    }

    public ProcedimientoNormativaDTO getProcSeleccionado() {
        return procSeleccionado;
    }

    public void setProcSeleccionado(ProcedimientoNormativaDTO procSeleccionado) {
        this.procSeleccionado = procSeleccionado;
    }

    public AfectacionDTO getAfectacionSeleccionada() {
        return afectacionSeleccionada;
    }

    public void setAfectacionSeleccionada(AfectacionDTO afectacionSeleccionada) {
        this.afectacionSeleccionada = afectacionSeleccionada;
    }
}
