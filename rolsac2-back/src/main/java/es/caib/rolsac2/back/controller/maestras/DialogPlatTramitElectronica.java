package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.PlatTramitElectronicaServiceFacade;
import es.caib.rolsac2.service.model.EntidadDTO;
import es.caib.rolsac2.service.model.PlatTramitElectronicaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class DialogPlatTramitElectronica extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogPlatTramitElectronica.class);

    private String codigo;

    private PlatTramitElectronicaDTO data;

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
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = serviceFacade.findById(Long.valueOf(codigo));
            this.identificadorOld = data.getIdentificador();
        }
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
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

        if (Objects.nonNull(this.data.getUrlAcceso()) && !ValidacionTipoUtils.esUrlValido(this.data.getUrlAcceso())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.url.novalido"), true);
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