package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.PersonalServiceFacade;
import es.caib.rolsac2.service.model.PersonalDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

/**
 * Controlador para editar un personal.
 *
 * @author areus
 */
@Named
@ViewScoped
public class DialogPersonal extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogPersonal.class);

    private String codigo;

    private PersonalDTO data;

    private UnidadAdministrativaDTO ua;

    @EJB
    private PersonalServiceFacade personalService;

    public void load() {
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        data = new PersonalDTO();
        if (this.isModoAlta()) {
            data = new PersonalDTO();
            data.setUnidadAdministrativa(sessionBean.getUnidadActiva());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = personalService.findById(Long.valueOf(codigo));
            ua = data.getUnidadAdministrativa();
        }

    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            personalService.create(this.data);
        } else {
            personalService.update(this.data);
        }

        // Retornamos resultado
        LOG.debug("Acceso:" + this.getModoAcceso());
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() == null) {
            result.setModoAcceso(TypeModoAcceso.ALTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {

        if (Objects.nonNull(this.data.getEmail()) && !ValidacionTipoUtils.esEmailValido(this.data.getEmail())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.email.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getTelefonoExteriorFijo())
                && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefonoExteriorFijo())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getTelefonoExteriorMovil())
                && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefonoExteriorMovil())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }
        return true;
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (Objects.isNull(this.getModoAcceso())) {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
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

    public PersonalDTO getData() {
        return data;
    }

    public void setData(PersonalDTO data) {
        this.data = data;
    }

    public void test() {
        LOG.info("");
    }

    public UnidadAdministrativaDTO getUa() {
        return ua;
    }

    public void setUa(UnidadAdministrativaDTO ua) {
        this.ua = ua;
    }

    public PersonalServiceFacade getPersonalService() {
        return personalService;
    }

    public void setPersonalService(PersonalServiceFacade personalService) {
        this.personalService = personalService;
    }
}
