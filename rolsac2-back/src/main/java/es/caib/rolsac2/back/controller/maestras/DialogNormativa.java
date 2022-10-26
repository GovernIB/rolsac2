package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
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

    private String identificadorOld;

    private List<ProcedimientoDTO> procedimientosRelacionados;

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

    private DocumentoNormativaDTO datoSeleccionado;



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
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = normativaServiceFacade.findById(Long.valueOf(id));
        }


        procedimientosRelacionados = new ArrayList<>();
        final ProcedimientoDTO pr1= new ProcedimientoDTO();
        pr1.setNombreProcedimiento("Instancia genérica 1");
        procedimientosRelacionados.add(pr1);
        documentosRelacionados=new ArrayList<>();

        uaRelacionadas = new ArrayList<>();
        final UnidadAdministrativaDTO ua = new UnidadAdministrativaDTO();
        final Literal l1= new Literal();
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
        if (Objects.nonNull(this.data.getUrlBoletin()) && !ValidacionTipoUtils.esUrlValido(this.data.getUrlBoletin())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.url.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getNumero()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getNumeroBoletin()) && !ValidacionTipoUtils.esEntero(this.data.getNumero())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.numero.novalido"), true);
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

    public void abrirDialogDocumento(TypeModoAcceso modoAcceso) {
        // Muestra dialogo
        final Map<String, String> params = new HashMap<>();
        if (this.data.getCodigo() != null
                && (modoAcceso.equals(TypeModoAcceso.CONSULTA)|| modoAcceso.equals(TypeModoAcceso.EDICION))) {
            params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
        }
        params.put("idNormativa", this.data.getCodigo().toString());

        UtilJSF.openDialog("dialogDocumentoNormativa", modoAcceso, params, true, 750, 450);
    }

    public void returnDialogo(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (!respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            this.buscarDocumentos();
        }
    }

    public void abrirDocumentoRelacionado() {
        abrirDialogDocumento(TypeModoAcceso.ALTA);
    }

    public void editarDocumentoRelacionado() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            abrirDialogDocumento(TypeModoAcceso.EDICION);
        }
    }

    public void consultarDocumentoRelacionado() {
        if (datoSeleccionado != null) {
            abrirDialogDocumento(TypeModoAcceso.CONSULTA);
        }
    }

    public void borrarDocumentoRelacionado() {
        if (datoSeleccionado == null) {
            UtilJSF.addMessageContext(TypeNivelGravedad.INFO, getLiteral("msg.seleccioneElemento"));
        } else {
            normativaServiceFacade.deleteDocumentoNormativa(datoSeleccionado.getCodigo());
            buscarDocumentos();
        }
    }

    public void buscarDocumentos() {
        List<DocumentoNormativaDTO> docs = normativaServiceFacade.findDocumentosNormativa(this.data.getCodigo());
        this.data.setDocumentosNormativa(docs);
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

    public List<ProcedimientoDTO> getProcedimientosRelacionados() {
        return procedimientosRelacionados;
    }

    public void setProcedimientosRelacionados(List<ProcedimientoDTO> procedimientosRelacionados) {
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

    public DocumentoNormativaDTO getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(DocumentoNormativaDTO datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }
}
