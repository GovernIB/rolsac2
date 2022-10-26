package es.caib.rolsac2.back.controller.maestras.tipo;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.MaestrasSupServiceFacade;
import es.caib.rolsac2.service.model.Literal;
import es.caib.rolsac2.service.model.TipoProcedimientoDTO;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@ViewScoped
public class DialogTipoProcedimiento extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogTipoProcedimiento.class);
    private static final long serialVersionUID = -978862425481233306L;

    private String id;

    @EJB
    MaestrasSupServiceFacade tipoProcedimientoService;

    private TipoProcedimientoDTO data;
    private String identificadorAntiguo;
    private Literal descripcion;

    public void load() {
        this.setearIdioma();
        LOG.debug("init");
        // Inicializamos combos/desplegables/inputs
        // De momento, no tenemos desplegables.

        data = new TipoProcedimientoDTO();
        if (this.isModoAlta()) {
            data.setEntidad(sessionBean.getEntidad());
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = tipoProcedimientoService.findTipoProcedimientoById(Long.valueOf(id));
            identificadorAntiguo = data.getIdentificador();
        }

        if (data.getDescripcion() == null) {
            data.setDescripcion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
        }
    }

    public void traducir() {
        UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No está implementado la traduccion", true);
    }

    public void abrirDlg() {
        final Map<String, String> params = new HashMap<>();
        UtilJSF.openDialog("dialogTipoProcedimiento", TypeModoAcceso.ALTA, params, true, 1050, 550);
    }

    public void guardar() {

        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            tipoProcedimientoService.create(this.data);
        } else {
            tipoProcedimientoService.update(this.data);
        }

        // Retornamos resultado
        final DialogResult result = new DialogResult();
        //TODO Se produce un error por eso se pone que si null, entonces poner alta
        if (this.getModoAcceso() == null) {
            result.setModoAcceso(TypeModoAcceso.ALTA);
        } else {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo()) && tipoProcedimientoService.existeIdentificadorTipoProcedimiento(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        if (Objects.nonNull(this.data.getCodigo()) && !identificadorAntiguo.equals(this.data.getIdentificador())
                && tipoProcedimientoService.existeIdentificadorTipoProcedimiento(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getDescripcion(), sessionBean.getIdiomasObligatoriosList());
        if (!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dialogPlatTramitElectronica.descripcion", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
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

    public TipoProcedimientoDTO getData() {
        return data;
    }

    public void setData(TipoProcedimientoDTO data) {
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

    public String getIdentificadorAntiguo() {
        return identificadorAntiguo;
    }

    public void setIdentificadorAntiguo(String identificadorAntiguo) {
        this.identificadorAntiguo = identificadorAntiguo;
    }
}
