package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.model.ConfiguracionGlobalDTO;
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

/**
 * Controlador para editar un DialogConfiguracionGlobal.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogConfiguracionGlobal extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogConfiguracionGlobal.class);

    private String id;

    private ConfiguracionGlobalDTO data;

    @Inject
    private SessionBean sessionBean;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    public void load() {
        LOG.debug("init");
        this.setearIdioma();
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        data = new ConfiguracionGlobalDTO();
        if (this.isModoEdicion() || this.isModoConsulta()) {
            data = administracionSupServiceFacade.findConfGlobalById(Long.valueOf(id));
        }
    }

    public void guardar() {

        if (this.data.getCodigo() != null) {
            administracionSupServiceFacade.updateConfGlobal(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            this.setModoAcceso(TypeModoAcceso.CONSULTA.name());
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
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
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConfiguracionGlobalDTO getData() {
        return data;
    }

    public void setData(ConfiguracionGlobalDTO data) {
        this.data = data;
    }
}
