package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.*;
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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class DialogNormativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogNormativa.class);

    private String id = "";

    private NormativaDTO data;

    private List<EntidadDTO> entidadesActivas;

    private List<AfectacionDTO> afectacion;

    private List<TipoNormativaDTO> tipoNormativa;

    private List<TipoBoletinDTO> tipoBoletin;

    private List<BoletinOficialDTO> boletinOficial;

    private List<ProcedimientoNormativaDTO> procedimientosRelacionados;

    private List<String> documentosRelacionados;

    private List<UnidadAdministrativaDTO> uaRelacionadas;

    @Inject
    private SessionBean sessionBean;

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


    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new NormativaDTO();

        tipoNormativa = maestrasSupServiceFacade.findTipoNormativa();
        tipoBoletin = maestrasSupServiceFacade.findBoletines();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        afectacion = normativaServiceFacade.findAfectacion();
        boletinOficial = normativaServiceFacade.findBoletinOficial();

        if (this.isModoAlta()) {
            data = new NormativaDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setNombre(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setUrlBoletin(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
            UnidadAdministrativaGridDTO uaActiva = sessionBean.getUnidadActiva().convertDTOtoGridDTO();
            unidadesAdministrativas.add(uaActiva);
            data.setUnidadesAdministrativas(unidadesAdministrativas);
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = normativaServiceFacade.findById(Long.valueOf(id));
            findProcedimientosRelacionados();
            if(data.getDocumentosNormativa() != null) {
                for(DocumentoNormativaDTO documentoNormativaDTO : data.getDocumentosNormativa()) {
                    documentoNormativaDTO.setCodigoTabla(UUID.randomUUID().toString());
                }
            }
        }

        documentosRelacionados = new ArrayList<>();

        uaRelacionadas = new ArrayList<>();
        final UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        final Literal l1 = new Literal();
        final List<Traduccion> traducciones = new ArrayList<>();
        final Traduccion t1 = new Traduccion();
        t1.setLiteral("Descripción del tipo de materia SIA.");
        t1.setIdioma("es");
        traducciones.add(t1);
        l1.setTraducciones(traducciones);
        ua.setNombre(l1);
        uaRelacionadas.add(ua);


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
        if (Objects.nonNull(this.data.getNumeroBoletin()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
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
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Documentos Relacionados
     *********************************************************************************************************************************/

    public void abrirDialogDocumento(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        if(this.isModoAlta() && !modoAcceso.equals(TypeModoAcceso.ALTA)) {
            UtilJSF.anyadirMochila("documentoNormativa", documentoRelacionadoSeleccionado);
        }
        if(!this.isModoAlta() && !modoAcceso.equals(TypeModoAcceso.ALTA)) {
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
        } else if(this.isModoAlta()) {
            this.data.getDocumentosNormativa().remove(documentoRelacionadoSeleccionado);
        } else {
            normativaServiceFacade.deleteDocumentoNormativa(documentoRelacionadoSeleccionado.getCodigo());
            buscarDocumentos();
        }
    }

    public void buscarDocumentos() {
        List<DocumentoNormativaDTO> docs = normativaServiceFacade.findDocumentosNormativa(this.data.getCodigo());
        if(docs != null) {
            for(DocumentoNormativaDTO documentoNormativaDTO : docs) {
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
        } else if(uaSeleccionada.getCodigo() == sessionBean.getUnidadActiva().getCodigo()) {
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

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
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

    public List<BoletinOficialDTO> getBoletinOficial() {
        return boletinOficial;
    }

    public void setBoletinOficial(List<BoletinOficialDTO> boletinOficial) {
        this.boletinOficial = boletinOficial;
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

    public UnidadAdministrativaGridDTO getUaSeleccionada() { return uaSeleccionada; }

    public void setUaSeleccionada(UnidadAdministrativaGridDTO uaSeleccionada) { this.uaSeleccionada = uaSeleccionada; }

    public ProcedimientoNormativaDTO getProcSeleccionado() {
        return procSeleccionado;
    }

    public void setProcSeleccionado(ProcedimientoNormativaDTO procSeleccionado) {
        this.procSeleccionado = procSeleccionado;
    }
}
