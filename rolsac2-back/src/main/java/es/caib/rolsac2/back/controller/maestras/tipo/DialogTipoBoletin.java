package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.TipoBoletinDTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogTipoBoletin extends AbstractController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoBoletin.class);
    private static final long serialVersionUID = -978862425481233306L;
    @EJB
    MaestrasSupServiceFacade tipoBoletinService;
    private String id;
    private TipoBoletinDTO data;

    private String identificadorOld = "";

    public void load() {
        LOG.debug("init");
        //Inicializamos combos/desplegables/inputs
        //De momento, no tenemos desplegables.

        data = new TipoBoletinDTO();
        if (this.isModoAlta()) {
            data = new TipoBoletinDTO();
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoBoletinService.findTipoBoletinById(Long.valueOf(id));
            identificadorOld = data.getIdentificador();
        }

    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void abrirDlg() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogTipoBoletin", TypeModoAcceso.ALTA, params, true, 1050, 550);
    }

    public void guardar() {

        if (Objects.isNull(this.data.getCodigo())
                && tipoBoletinService.checkIdentificadorTipoBoletin(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && tipoBoletinService.checkIdentificadorTipoBoletin(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return;
        }

        if (this.data.getCodigo() == null) {
            tipoBoletinService.create(this.data);
        } else {
            tipoBoletinService.update(this.data);
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
        if (getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(getModoAcceso()));
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

    public TipoBoletinDTO getData() {
        return data;
    }

    public void setData(TipoBoletinDTO data) {
        this.data = data;
    }
}
