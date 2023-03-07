package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import es.caib.rolsac2.service.utils.UtilComparador;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogPlatTramitElectronica extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogPlatTramitElectronica.class);

    private String codigo;

    private PlatTramitElectronicaDTO data;

    private PlatTramitElectronicaDTO dataOriginal;

    private List<EntidadDTO> entidadesActivas;

    private String identificadorOld = "";

    @Inject
    private SessionBean sessionBean;

    @EJB
    PlatTramitElectronicaServiceFacade serviceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new PlatTramitElectronicaDTO();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        if (this.isModoAlta()) {
            data = new PlatTramitElectronicaDTO();
            data.setCodEntidad(sessionBean.getEntidad());
            data.setUrlAcceso(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            dataOriginal = data.clone();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = serviceFacade.findById(Long.valueOf(codigo));
            dataOriginal = data.clone();
            this.identificadorOld = data.getIdentificador();
        }
    }

    public void traducir() {
        //UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
        final Map<String, String> params = new HashMap<>();

        UtilJSF.anyadirMochila("dataTraduccion", data);
        UtilJSF.openDialog("dialogTraduccion", TypeModoAcceso.ALTA, params, true, 800, 500);
    }

    public void returnDialogTraducir(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        PlatTramitElectronicaDTO tramitDTO = (PlatTramitElectronicaDTO) respuesta.getResult();

        if (tramitDTO != null) {
            data.setDescripcion(tramitDTO.getDescripcion());
            data.setUrlAcceso(tramitDTO.getUrlAcceso());
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            serviceFacade.create(this.data);
        } else {
            serviceFacade.update(this.data);
        }

        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null){
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && serviceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && serviceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        //Verificamos que los literales tienen los idiomas obligatorios marcados si estos campos son obligatorios
        // (los que son campos obligatorios ya que puede ser que se hayan rellenado a través del input en vez del dialog y no estén todos)

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if(!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }

        List<String> idiomasPendientesUrl = ValidacionTipoUtils.esLiteralCorrecto(this.data.getUrlAcceso(), sessionBean.getIdiomasObligatoriosList());
        if(!idiomasPendientesUrl.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.urlAcceso", "dialogLiteral.validacion.idiomas", idiomasPendientesUrl), true);
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
        return UtilComparador.compareTo(data.getCodigo(), dataOriginal.getCodigo()) != 0
                || UtilComparador.compareTo(data.getIdentificador(), dataOriginal.getIdentificador()) != 0
                || UtilComparador.compareTo(data.getDescripcion(), dataOriginal.getDescripcion()) != 0
                || UtilComparador.compareTo(data.getCodEntidad().getCodigo(), dataOriginal.getCodEntidad().getCodigo()) != 0
                || UtilComparador.compareTo(data.getUrlAcceso(), dataOriginal.getUrlAcceso()) != 0;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public PlatTramitElectronicaDTO getData() {
        return data;
    }

    public void setData(PlatTramitElectronicaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }
}
