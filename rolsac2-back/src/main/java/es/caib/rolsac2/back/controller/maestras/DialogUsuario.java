package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionEntServiceFacade;
import es.caib.rolsac2.service.model.UsuarioDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@ViewScoped
public class DialogUsuario extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUsuario.class);

    private String id;

    private UsuarioDTO data;


    private String identificadorOld = "";

    @Inject
    private SessionBean sessionBean;

    @EJB
    AdministracionEntServiceFacade administracionEntService;

    public void load() {
        LOG.debug("init");

        this.setearIdioma();
        data = new UsuarioDTO();
        if (this.isModoAlta()) {
            data = new UsuarioDTO();
            data.setEntidad(sessionBean.getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionEntService.findUsuarioById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }
    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            administracionEntService.create(this.data);
        } else {
            administracionEntService.update(this.data);
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

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && administracionEntService.checkIdentificadorUsuario(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
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

    public UsuarioDTO getData() {
        return data;
    }

    public void setData(UsuarioDTO data) {
        this.data = data;
    }

}
