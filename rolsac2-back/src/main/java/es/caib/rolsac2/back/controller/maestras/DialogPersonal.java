package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para editar un personal.
 *
 * @author areus
 */
@Named
@ViewScoped
public class DialogPersonal extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogPersonal.class);


    private String id;

    private PersonalDTO data;

    @Inject
    private SessionBean sessionBean;
    @EJB
    PersonalServiceFacade personalService;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        data = new PersonalDTO();
        if (this.isModoAlta()) {
            data = new PersonalDTO();
            data.setUnidadAdministrativa(sessionBean.getUnidadActiva().getId());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = personalService.findById(Long.valueOf(id));
        }

    }

    public void initMockup() {
        data = new PersonalDTO();
        data.setCargo("Cargo 1");
        data.setEmail("Email");
        data.setId(1l);
        data.setTelefonoExteriorFijo("661");
        data.setTelefonoExteriorMovil("66174");
        data.setFunciones("Administrador");
        data.setTelefonoExteriorMovil("4886");
        data.setTelefonoExteriorFijo("32");
        data.setNombre("Persona 1");
        data.setIdentificador("p1");
        data.setUnidadAdministrativa(1l);
    }

    public void abrirDlg() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogPersonal", TypeModoAcceso.ALTA, params, true, 1050, 550);
    }

    public void testMsg() {
        UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Test MSG desde dialog", "INFO");// UtilJSF.getLiteral("info.borrado.ok"));
    }

    public void guardar() {

        if (this.data.getId() == null) {
            personalService.create(this.data, sessionBean.getUnidadActiva().getId());
        } else {
            personalService.update(this.data);
        }

        // Retornamos resultado
        LOG.error("Acceso:" + this.getModoAcceso());
        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {

        final DialogResult result = new DialogResult();
        result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonalDTO getData() {
        return data;
    }

    public void setData(PersonalDTO data) {
        this.data = data;
    }
}
