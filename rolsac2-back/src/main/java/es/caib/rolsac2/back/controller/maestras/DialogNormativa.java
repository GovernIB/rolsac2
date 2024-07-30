package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.*;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import es.caib.rolsac2.service.model.types.TypePropiedadConfiguracion;
import es.caib.rolsac2.service.utils.UtilComparador;
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

    private NormativaDTO dataOriginal;

    private List<AfectacionDTO> afectacion;

    private List<TipoNormativaDTO> tipoNormativa;

    private List<TipoBoletinDTO> tipoBoletin;

    private List<ProcedimientoNormativaDTO> procedimientosRelacionados;
    private List<ProcedimientoNormativaDTO> serviciosRelacionados;

    private List<String> documentosRelacionados;

    private List<UnidadAdministrativaDTO> uaRelacionadas;
    private boolean mostrarUAs;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private MaestrasSupServiceFacade maestrasSupServiceFacade;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    private SystemServiceFacade systemServiceFacade;

    /**
     * Documento seleccionado
     */
    private DocumentoNormativaDTO documentoRelacionadoSeleccionado;

    /**
     * Unidad administrativa seleccionada
     */
    private UnidadAdministrativaGridDTO uaSeleccionada;

    /**
     * Procedimiento seleccionado
     */
    private ProcedimientoNormativaDTO procSeleccionado;

    /**
     * Servicio seleccionado
     */
    private ProcedimientoNormativaDTO servicioSeleccionado;

    /**
     * Afectacion seleccionada
     */
    private AfectacionDTO afectacionSeleccionada;

    /**
     * Inicialización del bean
     */
    public void load() {
        LOG.debug("init");
        this.setearIdioma();

        mostrarUAs = false;
        String propiedad = systemServiceFacade.obtenerPropiedadConfiguracion("normativa.mostrar.uasRelacionadas");
        if (propiedad != null && (propiedad.toLowerCase().equals("true") || propiedad.toLowerCase().equals("false"))) {
            mostrarUAs = Boolean.valueOf(propiedad.toLowerCase());
        }
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
                data.setTitulo(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
                data.setUrlBoletin(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
                List<UnidadAdministrativaGridDTO> unidadesAdministrativas = new ArrayList<>();
                UnidadAdministrativaGridDTO uaRaiz = unidadAdministrativaServiceFacade.getUaRaizEntidad(sessionBean.getUnidadActiva().getCodigo());
                unidadesAdministrativas.add(uaRaiz);
                data.setUnidadesAdministrativas(new ArrayList<>(unidadesAdministrativas));
                data.setDocumentosNormativa(new ArrayList<>());
                data.setAfectaciones(new ArrayList<>());
                data.setVigente(true);
                dataOriginal = data.clone();
                dataOriginal.setUnidadesAdministrativas(new ArrayList<>(data.getUnidadesAdministrativas()));
                dataOriginal.setAfectaciones(new ArrayList<>());
                dataOriginal.setDocumentosNormativa(new ArrayList<>());
            } else if (this.isModoEdicion() || this.isModoConsulta()) {
                data = normativaServiceFacade.findById(Long.valueOf(id));
                findProcedimientosRelacionados();
                findServiciosRelacionados();
                data.setAfectaciones(findAfectaciones());
                if (data.getAfectaciones() != null) {
                    for (AfectacionDTO afectacionDTO : data.getAfectaciones()) {
                        afectacionDTO.setCodigoTabla(UUID.randomUUID().toString());
                    }
                }
                dataOriginal = data.clone();
                dataOriginal.setDocumentosNormativa(new ArrayList<>(data.getDocumentosNormativa()));
                dataOriginal.setUnidadesAdministrativas(new ArrayList<>(data.getUnidadesAdministrativas()));
                dataOriginal.setAfectaciones(new ArrayList<>(data.getAfectaciones()));
            }
        }

        UtilJSF.vaciarMochila();

    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        String path = systemServiceFacade.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
        if (this.data.getCodigo() == null) {
            normativaServiceFacade.create(this.data, path);
        } else {
            normativaServiceFacade.update(this.data, path);
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

        String regNumero = "[0-9]{1,5}/[0-9]{4}";
        if (Objects.nonNull(this.data.getNumero()) && !this.data.getNumero().matches(regNumero)) {
            /** El patron que debería de tener  NNNNN/YYYY **/
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
            return false;
        }

        String regNumeroBoletin = "[0-9]{1,3}/[0-9]{4}";
        if (Objects.nonNull(this.data.getNumeroBoletin()) && !this.data.getNumeroBoletin().matches(regNumeroBoletin)) {
            /** El patron que debería de tener  NNNNN/YYYY **/
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numeroBoletin.novalido"), true);
            return false;
        }

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getTitulo(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogNormativa.titulo", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        return true;
    }

    public void cerrar() {
        if (data != null && dataOriginal != null && comprobarModificacion()) {
            PrimeFaces.current().executeScript("PF('confirmCerrar').show();");
        } else {
            cerrarDefinitivo();
        }
    }

    private boolean comprobarModificacion() {
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0 || UtilComparador.compareTo(data.getTitulo(), dataOriginal.getTitulo()) != 0 || UtilComparador.compareTo(data.getNumero(), dataOriginal.getNumero()) != 0

                || (data.getFechaAprobacion() != null && dataOriginal.getFechaAprobacion() != null && !data.getFechaAprobacion().equals(dataOriginal.getFechaAprobacion())) || ((data.getFechaAprobacion() == null || dataOriginal.getFechaAprobacion() == null) && (data.getFechaAprobacion() != null || dataOriginal.getFechaAprobacion() != null))

                || (data.getFechaBoletin() != null && dataOriginal.getFechaBoletin() != null && !data.getFechaBoletin().equals(dataOriginal.getFechaBoletin())) || ((data.getFechaBoletin() == null || dataOriginal.getFechaBoletin() == null) && (data.getFechaBoletin() != null || dataOriginal.getFechaBoletin() != null))

                || UtilComparador.compareTo(data.getNumeroBoletin(), dataOriginal.getNumeroBoletin()) != 0 || UtilComparador.compareTo(data.getUrlBoletin(), dataOriginal.getUrlBoletin()) != 0 || UtilComparador.compareTo(data.getNombreResponsable(), dataOriginal.getNombreResponsable()) != 0 || UtilComparador.compareTo(data.getTitulo(), dataOriginal.getTitulo()) != 0 || (data.getBoletinOficial() != null && dataOriginal.getBoletinOficial() != null && UtilComparador.compareTo(data.getBoletinOficial().getCodigo(), dataOriginal.getBoletinOficial().getCodigo()) != 0) || ((data.getBoletinOficial() == null || dataOriginal.getBoletinOficial() == null) && (data.getBoletinOficial() != null || dataOriginal.getBoletinOficial() != null)) || (data.getTipoNormativa() != null && dataOriginal.getTipoNormativa() != null && UtilComparador.compareTo(data.getTipoNormativa().getCodigo(), dataOriginal.getTipoNormativa().getCodigo()) != 0) || ((data.getTipoNormativa() == null || dataOriginal.getTipoNormativa() == null) && (data.getTipoNormativa() != null || dataOriginal.getTipoNormativa() != null)) || !data.getDocumentosNormativa().equals(dataOriginal.getDocumentosNormativa()) || !data.getUnidadesAdministrativas().equals(dataOriginal.getUnidadesAdministrativas()) || !data.getAfectaciones().equals(dataOriginal.getAfectaciones());
    }

    public void cerrarDefinitivo() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
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
            data.setTitulo(datoDTO.getTitulo());
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
        if (!modoAcceso.equals(TypeModoAcceso.ALTA)) {
            UtilJSF.anyadirMochila("documentoNormativa", documentoRelacionadoSeleccionado);
        }
        params.put("modoAccesoNormativa", this.getModoAcceso());
        UtilJSF.openDialog("dialogDocumentoNormativa", modoAcceso, params, true, 750, 450);
    }

    public void returnDialogoDocumento(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        //Si se da de alta la normativa, creamos el listado de documentos
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            DocumentoNormativaDTO documento = (DocumentoNormativaDTO) respuesta.getResult();

            if (this.data.getDocumentosNormativa() == null) {
                this.data.setDocumentosNormativa(new ArrayList<>());
            }

            boolean encontrado = false;
            int posicion = 0;
            for (DocumentoNormativaDTO doc : this.data.getDocumentosNormativa()) {
                if (doc.getCodigo() != null && doc.getCodigo().equals(documento.getCodigo())) {
                    encontrado = true;
                    break;
                }
                if (doc.getCodigo() == null && doc.getCodigoTemporal() != null && documento.getCodigoTemporal() != null && doc.getCodigoTemporal().equals(documento.getCodigoTemporal())) {
                    encontrado = true;
                    break;
                }
                posicion++;
            }

            if (encontrado) {
                this.data.getDocumentosNormativa().remove(posicion);
                this.data.getDocumentosNormativa().add(posicion, documento);

            } else {
                this.data.getDocumentosNormativa().add(documento);
            }
            this.documentoRelacionadoSeleccionado = documento;
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
        } else {
            this.data.getDocumentosNormativa().remove(documentoRelacionadoSeleccionado);
        }
    }

    public void buscarDocumentos() {
        List<DocumentoNormativaDTO> docs = normativaServiceFacade.findDocumentosNormativa(this.data.getCodigo());
        this.data.setDocumentosNormativa(docs);
        PrimeFaces.current().ajax().update("formDialog:dataDocumentosRelacionados");
    }

    /********************************************************************************************************************************
     * Funciones relativas al manejo de la relación de Procedimientos
     *********************************************************************************************************************************/

    private void findProcedimientosRelacionados() {
        procedimientosRelacionados = normativaServiceFacade.listarProcedimientosByNormativa(this.data.getCodigo());
    }

    private void findServiciosRelacionados() {
        serviciosRelacionados = normativaServiceFacade.listarServiciosByNormativa(this.data.getCodigo());
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

    public void consultarServicio() {
        if (servicioSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            final Map<String, String> params = new HashMap<>();
            params.put("ID", servicioSeleccionado.getCodigo().toString());
            params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
            Integer ancho = sessionBean.getScreenWidthInt();
            if (ancho == null) {
                ancho = 1433;
            }
            UtilJSF.openDialog("dialogServicio", TypeModoAcceso.CONSULTA, params, true, ancho, 733);
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
            UtilJSF.anyadirMochila("normativa", this.data);
            UtilJSF.openDialog("dialogNormativa", modoAcceso, params, true, 1530, 733);
            return;
        }
        final Map<String, String> params = new HashMap<>();
        params.put(TypeParametroVentana.ID.toString(), data.getCodigo() == null ? "" : data.getCodigo().toString());
        UtilJSF.anyadirMochila("normativa", this.data);
        if (this.data.getAfectaciones() != null) {
            UtilJSF.anyadirMochila("afectacionesNormativa", this.data.getAfectaciones());
        }
        params.put("modoAccesoNormativa", this.getModoAcceso());
        UtilJSF.openDialog("dialogSeleccionNormativaAfectacion", modoAcceso, params, true, 1410, 733);
    }

    public void returnDialogoAfectacion(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        //Si se da de alta la normativa, creamos el listado de documentos
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
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

    public List<ProcedimientoNormativaDTO> getServiciosRelacionados() {
        return serviciosRelacionados;
    }

    public void setServiciosRelacionados(List<ProcedimientoNormativaDTO> serviciosRelacionados) {
        this.serviciosRelacionados = serviciosRelacionados;
    }

    public ProcedimientoNormativaDTO getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(ProcedimientoNormativaDTO servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public boolean isMostrarUAs() {
        return mostrarUAs;
    }

    public void setMostrarUAs(boolean mostrarUAs) {
        this.mostrarUAs = mostrarUAs;
    }
}
