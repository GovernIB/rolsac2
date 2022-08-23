package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.MaestrasEntServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoMediaUADTO;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.primefaces.event.SelectEvent;
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
public class DialogTipoMediaUA extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoMediaUA.class);

    private String id;
    @EJB
    private MaestrasEntServiceFacade tipoMediaUAService;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    private TipoMediaUADTO data;
    private String identificadorAntiguo;
    private Literal descripcion;

    public void load() {
        this.setearIdioma();
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        data = new TipoMediaUADTO();
        if (this.isModoAlta()) {
            data.setEntidad(sessionBean.getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoMediaUAService.findTipoMediaUAById(Long.valueOf(id));
            this.identificadorAntiguo = data.getIdentificador();
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance());
        }
    }

    public void abrirDlg() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogTipoMediaUA", TypeModoAcceso.ALTA, params, true, 1050, 550);
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            tipoMediaUAService.create(this.data);
        } else {
            tipoMediaUAService.update(this.data);
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

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo()) && tipoMediaUAService.existeIdentificadorTipoMediaUA(
                this.data.getIdentificador(), sessionBean.getEntidad().getCodigo())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                && tipoMediaUAService.existeIdentificadorTipoMediaUA(this.data.getIdentificador(), sessionBean.getEntidad().getCodigo())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        return true;
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

    public TipoMediaUADTO getData() {
        return data;
    }

    public void setData(TipoMediaUADTO data) {
        this.data = data;
    }

    /**
     * Gestión de retorno Descripcion.
     *
     * @param event
     */
    public void returnDialogoDescripcion(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();
        if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
            final Literal literales = (Literal) respuesta.getResult();
            data.setDescripcion(literales);
        }
    }

    public Literal getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }


}