package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoSilencioAdministrativoDTO;
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
 * Controlador para editar un  DialogTipoSilencioAdministrativo.
 *
 * @author jsegovia
 */
@Named
@ViewScoped
public class DialogTipoSilencioAdministrativo extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoSilencioAdministrativo.class);

    private String id;

    private TipoSilencioAdministrativoDTO data;

    private String identificadorOld;

    @EJB
    private MaestrasSupServiceFacade maestrasSupService;

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.
        this.setearIdioma();
        data = new TipoSilencioAdministrativoDTO();
        if (this.isModoAlta()) {
            data = new TipoSilencioAdministrativoDTO();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = maestrasSupService.findTipoSilencioAdministrativoById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }
        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
        }

    }

    public void guardar() {

        if (Objects.isNull(this.data.getCodigo())
                && maestrasSupService.checkIdentificadorTipoSilencioAdministrativo(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && maestrasSupService.checkIdentificadorTipoSilencioAdministrativo(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return;
        }

        if (this.data.getCodigo() == null) {
            maestrasSupService.create(this.data, sessionBean.getUnidadActiva().getCodigo());
        } else {
            maestrasSupService.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
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

    public TipoSilencioAdministrativoDTO getData() {
        return data;
    }

    public void setData(TipoSilencioAdministrativoDTO data) {
        this.data = data;
    }
}
